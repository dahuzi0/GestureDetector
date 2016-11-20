package com.earl.leangesturedetector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {//在这个类中需要实现OnGestureListener相关的接口
    private Button bt_scroll;
    private TextView textview;
    //声明一个文本标签
    private float fontSize = 30;
    //声明一个用于指示字体大小的变量，初始值为30
    GestureDetector detector;
    //声明一个手势检测器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_scroll = (Button) findViewById(R.id.bt_scroll);
        textview = (TextView) findViewById(R.id.textView_helloWorld);
        textview.setTextSize(fontSize);//实例化这个文本标签并为其设置最初始的大小

        detector = new GestureDetector(this, this);//实例化这个手势检测器对象

        //把事件都放在控件上
        bt_scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return detector.onTouchEvent(motionEvent);
            }
        });
    }

    //    //下面实现的这些接口负责处理所有在该Activity上发生的触碰屏幕相关的事件
    //    @Override
    //    public boolean onTouchEvent(MotionEvent e) {
    //        return detector.onTouchEvent(e);
    //    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float distanceX, float distanceY) {
        if (distanceY >= 0) {
            //和distanceX一样，distanceY这个参数有正有负，我们对该数值所处的不同范围分别处理
            //向上滚动的手势可以让文本标签的字号变小
            if (fontSize > 10)
                fontSize -= 5;
            //加一个判断的目的是防止字号太小或者太大，下同
            textview.setTextSize(fontSize);
            //将计算好的字号应用到文本标签上
        } else {
            //向下滚动的手势可以让文本标签的字号变小
            if (fontSize < 60)
                fontSize += 5;
            textview.setTextSize(fontSize);
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
