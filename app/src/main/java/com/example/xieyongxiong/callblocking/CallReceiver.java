package com.example.xieyongxiong.callblocking;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by xieyongxiong on 16-6-18.
 */
public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
//
//        }else{

//        }

        TelephonyManager manager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        switch (manager.getCallState()){
            case TelephonyManager.CALL_STATE_RINGING:
                String inComingNumber=intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Intent service_intent=new Intent(context,BlockService.class);
                Bundle bundle=new Bundle();
                bundle.putString("number",inComingNumber);
                service_intent.putExtra("bundle",bundle);
                context.startService(service_intent);
                Log.e("thg", "incoming");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                break;
        }
    }
}
