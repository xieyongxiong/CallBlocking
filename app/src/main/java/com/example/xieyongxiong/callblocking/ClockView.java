package com.example.xieyongxiong.callblocking;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xieyongxiong on 16-6-19.
 */
public class ClockView extends View implements View.OnTouchListener{

    private float x,y,x1,y1,radius;
    private Paint text_paint, arc_paint,circle_paint,num_paint,title_paint;
    private double rotate,factor,temp1,temp2,dis1,dis2;
    double save_xy,is_xy;
    int startHour,endHour;
    SharedPreferences preferences;
    Context mcontext;
    view mview;
    public ClockView(Context context, AttributeSet set) {
        super(context,set);
        mcontext = context;
        mview = (view)mcontext;

        circle_paint = new Paint();
        circle_paint.setColor(getResources().getColor(R.color.dark));
        circle_paint.setStrokeWidth(10);
        circle_paint.setStyle(Paint.Style.STROKE);

        text_paint = new Paint();
        text_paint.setColor(getResources().getColor(R.color.dark));
        text_paint.setStrokeWidth(2);
        text_paint.setTextSize(30);
        text_paint.setStyle(Paint.Style.STROKE);
        text_paint.setAntiAlias(true);

        arc_paint = new Paint();
        arc_paint.setStrokeWidth(10);
        arc_paint.setColor(getResources().getColor(R.color.colorPrimary));
        arc_paint.setStyle(Paint.Style.FILL_AND_STROKE);
        arc_paint.setAlpha(50);

        num_paint = new Paint();
        num_paint.setColor(getResources().getColor(R.color.dark));
        num_paint.setTextSize(150);
        num_paint.setStrokeWidth(10);


        temp1=0;
        temp2=0;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        radius = getWidth()/3;
        is_xy = Math.sqrt((x*3/2)*(x*3/2)+(y*2+50)*(y*2+50));

        Log.e("thg","x="+getWidth()+" y="+getHeight());
        canvas.save();
        canvas.drawColor(getResources().getColor(R.color.white));
        canvas.drawCircle(x,y,radius,circle_paint);
        canvas.drawCircle(x,y,10,circle_paint);
        canvas.restore();

        for (int i = 1; i <= 24; i++) {
            canvas.save();//保存当前画布
            canvas.rotate(360/24*i,x,y);
            //左起：起始位置x坐标，起始位置y坐标，终止位置x坐标，终止位置y坐标，画笔(一个Paint对象)
            if(i%2==0){
                canvas.drawLine(x, y - radius, x, y - radius - 30, text_paint);
                //左起：文本内容，起始位置x坐标，起始位置y坐标，画笔
                canvas.drawText(""+i, x, y - radius - 70, text_paint);
            }else {
                canvas.drawLine(x, y - radius, x, y - radius + 30, text_paint);
                //左起：文本内容，起始位置x坐标，起始位置y坐标，画笔
                canvas.drawText(""+i, x, y - radius + 70, text_paint);
            }

            canvas.restore();//
        }

        canvas.drawArc(new RectF(x-radius,y-radius,x+radius,y+radius), (float) (temp1-90), (float) (temp2-temp1),true, arc_paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        x = width/2;
        y = height/2;
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        x1 = event.getX();
        y1 = event.getY();
        factor = (180*Math.atan((x1-x)/(y1-y))/Math.PI);

        save_xy = Math.sqrt(x1*x1+y1*y1);

        if(Math.abs(save_xy-is_xy)<100){
            SharedPreferences.Editor editor = preferences.edit();
            if(temp1>temp2){
               editor.putInt("startHour",(int)temp2*24/360);
                editor.putInt("endHour",(int)temp1*24/360);
                editor.commit();
            }else {
                editor.putInt("startHour",(int)temp1*24/360);
                editor.putInt("endHour",(int)temp2*24/360);
                editor.commit();
            }
        }

        if(x1>x){
            if(y>y1){
                rotate = Math.abs(factor);
            }else {
                rotate = 180-factor;
            }
        }else{
            if(y>y1){
                rotate = 360-factor;
            }else {
                rotate = 180+Math.abs(factor);
            }
        }

        dis1 = Math.abs(rotate-temp1);
        dis2 = Math.abs(rotate-temp2);
        if(dis1<20||dis2<20){
            if(dis1<dis2){
                temp1 = rotate;
            }else {
                temp2 = rotate;
            }
        }

        invalidate();
        if(temp1>temp2){
            mview.showText((int)temp2*24/360,(int)temp1*24/360);
        }else {
            mview.showText((int)temp1*24/360,(int)temp2*24/360);
        }

        return super.onTouchEvent(event);
    }


    interface view{
        void showText(int x,int y);
    }
}
