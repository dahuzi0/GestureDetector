<h2>一、手势简介</h2>
<blockquote><p>移动设备大行其道的今天，手势是一个流行词汇，那手势是什么呢？手，是人类各种创造性活动的天然工具，人们天生就会使用手的动作去表达情感，比如人们 会使用握手来表示友好，聋哑人使用一套用手语来代替语言交流，这些都是手势在生活中的应用。可见自古以来手势就是一套特定的语言系统，在人的交流中发挥重 要的作用。从交互上看，手势实际上是一种输入模式。我们现在在直观意义上理解的人机交互是指人与机器之间的互动方式，这种互动方式经历了鼠标、物理硬件、 屏幕触控、远距离的体感操作的逐步发展的过程。</p></blockquote>
<p><em>（引用自腾讯CDC-<a href="http://cdc.tencent.com/?p=4226" target="_blank">移动设备手势设计初探</a>）</em></p>
<p>对于开发者而言，如同按钮的点击一样，手势的触发也是一种<code>事件（Event）</code>。而我们通常将用户的手指或者是能够触发手势的设备（比如触控笔）在能识别手势的设备（如触摸屏）上的触碰动作认为是手势事件。这样的设计能够充分体现移动设备的交互优势，能够让用户以更加自然的方式参与到与应用的交互之中。</p>
<p>下面列举了一些常见的手势操作：</p>
<div class="image-package">
<img src="https://github.com/EARL8888/picturePath/blob/master/GestureDetector/wm.png?raw=true"></div>
</div>
<p>当然，每种手势具体能用来做什么，是由你的应用决定的，但我们推荐你参考一些较新的移动互联网应用开发规范来使得你的应用不那么与世隔绝。</p>
<p>对于一些常见的手势，比如<code>短按</code>、<code>长按</code>、<code>双击</code>、<code>拖拽</code>等，Android已实现了相应的手势检测，并为其提供了相应的API（主要是监听器）来满足开发需要。这些手势将在本实验被详细的介绍。</p>
<p>而另外一些非常规的手势，例如在屏幕上画个圈、画一个特殊的几何形状等，Android没有为它们提供特定的手势检测，但允许开发者自己来添加手势，通过与手势相似度相关的API来负责识别。</p>
<h2>二、在Android上实现手势监听</h2>
<p>在Android中，是由<code>GestureDetector</code>类来负责手势的检测，每一个<code>GestureDetector</code>类的实例都代表一个手势监听器。我们在为按钮设置点击事件监听器时会用到<code>OnClickListener</code>，同样，你在创建手势监听器时也需要一个类似的<code>OnGestureListener</code>实例。</p>
<p>在<code>OnGestureListerner</code>接口里面，有以下事件处理的方法可供你调用：</p>
<blockquote>
<p>-<code>boolean onDown(MotionEvent e)</code>：当用户在屏幕上按下时会触发该方法，但在移动或抬起手指时不会触发。</p>
<p>-<code>void onShowPress(MotionEvent e)</code>：当用户在屏幕上按下，并且既没有移动有没有抬起手指时，会触发该方法。一般通过该方法告知用户他们的动作已经被识别到了，你可以高亮某个元素来提醒他们。</p>
<p>-<code>boolean onSingleTapUp(MotionEvent e);</code>：当用户在屏幕上轻击时（通常是指点击屏幕的时间很短）会触发该方法。</p>
<p>-<code>boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);</code>：当用户在屏幕上发起滚动的手势时会触发该方法。参数中的<code>e1</code>指第一个按下开始滚动的动作事件，<code>e2</code>指触发当前这个方法的移动动作的事件，<code>distanceX</code>和<code>distanceY</code>则分别触发<code>onScroll</code>方法期间的X、Y轴上的滚动距离，<strong>而不是</strong>指<code>e1</code>和<code>e2</code>两个事件发生直接的距离。</p>
<p>-<code>void onLongPress(MotionEvent e);</code>：当用户在屏幕上持续地长按时会触发该方法。</p>
<p>-<code>boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);</code>：当用户在屏幕上持续地按下并且有“抛”的动作时，会触发该方法。对于该事件的理解，你可以体会一下按住一个图标然后把它扔到某个地方的感觉。参数中的<code>e1</code>指第一个按下的动作事件，<code>e2</code>指触发当前这个方法的“猛扔”动作的事件，<code>velocityX</code>和<code>velocityY</code>则分别触发<code>onFling</code>方法期间X、Y轴上的移动速度。</p>
</blockquote>
<p>如果你翻阅这些API的源代码，你还能发现还有一个名为<code>OnDoubleTapListener</code>的接口，显然是双击事件的一个监听器，它包含了下面这些方法。</p>
<blockquote>
<p>-<code>boolean onSingleTapConfirmed(MotionEvent e)</code>：当用户在屏幕上单击是会触发此方法，与上面的<code>onSingleTapUp</code>方法不同的地方在于，该方法只会在监听器确定了用户在第一次单击后不会触发双击事件时才会被触发。</p>
<p>-<code>boolean onDoubleTap(MotionEvent e)</code>：当用户在屏幕上双击时会触发此方法。这里的按下动作事件指的时双击中的第一次触击。</p>
<p>-<code>boolean onDoubleTapEvent(MotionEvent e)</code>：在双击事件发生时会触发此方法，包括了按下、移动和抬起事件。</p>
</blockquote>
<p>掌握了上面这些方法后，我们通过一个实例来实际应用一下：</p>
<p>实验步骤主要有：</p>
<ol>
<li>（<strong>若你已在第二小节完成，请跳至下一步</strong>）使用Android Studio创建应用项目<code>GesturePractice</code>，包名为<code>com.shiyanlou.android.gesturepractice</code>，基于<code>Android 5.1</code>。同时添加<code>MainActivity</code>及其布局资源文件。在项目创建好之后再创建并打开<code>AVD</code>模拟器（镜像选择<code>API22：Android 5.1.1</code>）。</li>
<li>在<code>res/layout/activity_main.xml</code>资源文件放入一个文本标签。</li>
<li>在<code>MainActivity.java</code>中，为这个Activity添加手势监听相关的代码。</li>
<li>编译并运行这个应用，等待应用安装至模拟器，在模拟器中试用该应用。</li>
</ol>
<p>下面是<code>res/layout/avtivity_main.xml</code>中的布局：</p>
<pre><code>&lt;RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:paddingBottom="@dimen/activity_vertical_margin" tools:context=".MainActivity"&gt;

    &lt;TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/textView_domain"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:text="Shiyanlou.com"
        android:textSize="40dp"
        android:textColor="#11AA8C" /&gt;

    &lt;TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/textView_title"
        android:layout_below="@+id/textView_domain"
        android:layout_centerHorizontal="true"
        android:text="Gesture Practice"
        android:textColor="#000000"
        android:textSize="40dp"/&gt;


    &lt;TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/textView_helloWorld"
        android:layout_below="@+id/textView_title"
        android:layout_marginTop="50dp"
        android:layout_centerHorizontal="true"
        android:text="Hello World!"
        android:textColor="#000000"
        /&gt;

&lt;/RelativeLayout&gt;</code></pre>
<p>下面是<code>MainActivity.java</code>中的源代码：</p>
<pre><code>package com.shiyanlou.android.gesturepractice;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener{
    //在这个类中需要实现OnGestureListener相关的接口

    private TextView textview;
    //声明一个文本标签
    private float fontSize = 30;
    //声明一个用于指示字体大小的变量，初始值为30
    GestureDetector detector;
    //声明一个手势检测器对象

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = (TextView)findViewById(R.id.textView_helloWorld);
        textview.setTextSize(fontSize);
        //实例化这个文本标签并为其设置最初始的大小

        detector = new GestureDetector(this, this);
        //实例化这个手势检测器对象
    }



    //下面实现的这些接口负责处理所有在该Activity上发生的触碰屏幕相关的事件
    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        return detector.onTouchEvent(e);
    }

    //我们就onScroll方法来进行补充
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        showShortToast("The method has been called - onScroll");
        //当该方法被调用时，通过一个Toast来提示用户哪个方法被调用了，下同

        if(distanceY &gt;= 0){
            //和distanceX一样，distanceY这个参数有正有负，我们对该数值所处的不同范围分别处理
            //向上滚动的手势可以让文本标签的字号变小
            if(fontSize &gt; 10)
                fontSize -= 5;
            //加一个判断的目的是防止字号太小或者太大，下同
            textview.setTextSize(fontSize);
            //将计算好的字号应用到文本标签上
        }
        else{
            //向下滚动的手势可以让文本标签的字号变小
            if(fontSize &lt;60)
                fontSize += 5;
            textview.setTextSize(fontSize);
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e)
    {
        showShortToast("The method has been called - onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e)
    {
        showShortToast("The method has been called - onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        showShortToast("The method has been called - onSingleTapUp");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {
        showShortToast("The method has been called - onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        showShortToast("The method has been called - onFling");
        return false;
    }

    //我们将Toast封装一下，以便调用时只需要传入待显示的消息，而略去了填写Context和持续时间等参数。
    public void showShortToast(String message){
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}</code></pre>
<p>检查一下代码，编译并运行该应用，在模拟器上可以看到应用的主界面：</p>
<div class="image-package">
<img src="https://github.com/EARL8888/picturePath/blob/master/GestureDetector/ge02.png?raw=true" data-original-src="https://dn-anything-about-doc.qbox.me/document-uid85931labid1284timestamp1439279098295.png/wm"><br><div class="image-caption"></div>
</div>
<p>此时你在屏幕上尝试做出一些手势，就能看到下方的Toast显示刚刚触发了哪些方法。</p>
<p>向上或向下滚动，你就可以改变<code>Hello World</code>的字号大小了。</p>
<div class="image-package">
<img src="https://github.com/EARL8888/picturePath/blob/master/GestureDetector/ge01.gif?raw=true" data-original-src="https://dn-anything-about-doc.qbox.me/document-uid85931labid1284timestamp1439279620985.png/wm"><br><div class="image-caption"></div>
</div>
