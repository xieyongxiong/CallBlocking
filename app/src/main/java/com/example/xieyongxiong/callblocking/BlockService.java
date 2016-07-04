package com.example.xieyongxiong.callblocking;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.example.xieyongxiong.callblocking.DB.blacklist_dao;
import com.example.xieyongxiong.callblocking.DB.location_dao;
import com.example.xieyongxiong.callblocking.core.AbsHandler;
import com.example.xieyongxiong.callblocking.core.AbsTrigger;
import com.example.xieyongxiong.callblocking.core.BlockerBuilder;
import com.example.xieyongxiong.callblocking.core.IBlocker;
import com.example.xieyongxiong.callblocking.core.IFilter;
import com.example.xieyongxiong.callblocking.core.MessageData;
import com.example.xieyongxiong.callblocking.impl.LocationFilter;
import com.example.xieyongxiong.callblocking.impl.NumeralFilter;
import com.example.xieyongxiong.callblocking.impl.SystemContactFilter;
import com.example.xieyongxiong.callblocking.impl.TimeRangFilter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class BlockService extends Service {

    private blacklist_dao dao;
    private location_dao loc_dao;
    private List<String> blackList,locationList;
    private List<String> contactList;
    private IBlocker mBlocker;
    private AbsTrigger mTrigger;
    private AbsHandler mHandler;
    private SharedPreferences preferences;
    private int startHour;
    private int endHour;
    private String number,province;
    private boolean contact_open;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        getLoction();
        dao = new blacklist_dao(getBaseContext());
        loc_dao = new location_dao(getBaseContext());
        preferences = getSharedPreferences("Block",MODE_PRIVATE);

        startHour = preferences.getInt("startHour",0);
        endHour = preferences.getInt("endHour",0);
        contact_open =preferences.getBoolean("contact_open",false);
        contactList = new ArrayList<>();

        Cursor cursor =getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);

        while(cursor.moveToNext()){
                String contact = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String s=contact.replace(" ","");
                contactList.add(s);
        }


        try{
            number = intent.getBundleExtra("bundle").getString("number");

        }catch (NullPointerException ex){

        }

        blackList = dao.query();
        locationList = loc_dao.query();

        mTrigger = new Trigger(number);
        mHandler = new Handler();

        setupBlocker();

        ((Trigger)mTrigger).phoneCall();

        return super.onStartCommand(intent, flags, startId);
    }


    private void setupBlocker(){

        BlockerBuilder builder = new BlockerBuilder();
        if(contact_open){
            mBlocker = builder
                    .setTrigger(mTrigger)
                    .setHandler(mHandler)
                    .addFilters(new TimeRangFilter(startHour,endHour))
                    .addFilters(new NumeralFilter(IFilter.OP_BLOCKED, blackList))
                    .addFilters(new SystemContactFilter(contactList))
                    .addFilters(new LocationFilter(locationList,province))
                    .create();
        }else {
            mBlocker = builder
                    .setTrigger(mTrigger)
                    .setHandler(mHandler)
                    .addFilters(new TimeRangFilter(startHour,endHour))
                    .addFilters(new NumeralFilter(IFilter.OP_BLOCKED, blackList))
                    .addFilters(new LocationFilter(locationList,province))
                    .create();
        }

        mBlocker.enable();
    }


    /**
     * 根据反射获取end()方法2
     * @param context
     * @return
     */
    private static ITelephony getITelephony(Context context) {
        ITelephony iTelephony = null;
        TelephonyManager mTelephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        Class<TelephonyManager> c = TelephonyManager.class;
        Method getITelephonyMethod = null;
        try {
            getITelephonyMethod = c.getDeclaredMethod("getITelephony",
                    (Class[]) null); // 获取声明的方法
            getITelephonyMethod.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            iTelephony = (ITelephony) getITelephonyMethod.invoke(
                    mTelephonyManager, (Object[]) null); // 获取实例
            return iTelephony;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iTelephony;
    }

    class Trigger extends AbsTrigger{

        private boolean mState = false;
        private String mNumber;

        public Trigger(String number) {
            this.mNumber = number;
        }

        @Override
        protected void enable() {
            mState = true;
        }

        @Override
        protected void disable() {
            mState = false;
        }

        public void phoneCall(){
            if(mState){
                MessageData data =new MessageData();
                data.setString(MessageData.KEY_DATA,mNumber);
                notify(data);
            }
        }
    }

    class Handler extends AbsHandler{
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handle(MessageData data) {

            int opcode = data.getInt(MessageData.KEY_OP);
            String phone = data.getString(MessageData.KEY_DATA);
            Log.e("thg","phone: "+phone+" number "+number);

            if(opcode==IFilter.OP_BLOCKED){
                try {
                    ITelephony iTelephony = getITelephony(getApplicationContext()); //获取电话接口
                    iTelephony.endCall(); // 挂断电话
                    NotificationManager manager = (NotificationManager) getBaseContext()
                            .getSystemService(getBaseContext().NOTIFICATION_SERVICE);
                    Notification.Builder builder = new Notification.Builder(getBaseContext());
                    Notification notification = builder
                            .setContentTitle(number)
                            .setContentText(phone)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .build();
                    manager.notify(22,notification);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            super.handle(data);
        }
    }

    public String getLoction(){
        new Thread(() -> {
            okHttpGet("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel="+number);

        }).start();
        return "s";
    }

    private void okHttpGet(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String resStr =  response.body().string();
                findProvince(resStr);
            }
        });
    }

    private void findProvince(String jsonString) {

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < jsonString.length(); ++i) {
            if(jsonString.charAt(i) == 'p') {
                i += 10;
                for(int j = i; j < jsonString.length(); ++j) {
                    if(jsonString.charAt(j) == '\'') {
                        break;
                    }
                    stringBuilder.append(jsonString.charAt(j));
                }
                province = stringBuilder.toString();

                break;
            }
        }
    }




}
