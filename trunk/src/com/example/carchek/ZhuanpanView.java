package com.example.carchek;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.RotateAnimation;

public class ZhuanpanView extends View {        
    private Drawable drawable;
    private RotateAnimation animation;
    private float xPoint=0;
    private float yPoint=0;
    private int time=300;
    public float AngleNow=0;
    public ZhuanpanView(Context context, AttributeSet attrs) {         
        super(context, attrs);         
        Resources r=context.getResources();
        drawable = r.getDrawable(R.drawable.niddle);       
    }
    public ZhuanpanView(Context context, AttributeSet attrs,int flushtime) {
        
        super(context, attrs);
         
        Resources r=context.getResources();
        drawable = r.getDrawable(R.drawable.niddle);
        time=flushtime;
    }
     
    //重写View类的onDraw()函数
    @Override
    protected void onDraw(Canvas canvas) {
        //设置转盘旋转矩阵为以（160,160）坐标为圆心，旋转X角度         
        canvas.save();         
        //由于你的表盘是作为背景图做的，在不同屏幕下其中心坐标可能不同，这个你自己根据你屏幕大小自己计算
        int xCenter = getWidth() ; // 表盘的中心x坐标
		int yCenter = getHeight() ;// 表盘的中心y坐标
		int w = drawable.getIntrinsicWidth(); // 箭头图片在屏幕上的长度
		int h = drawable.getIntrinsicHeight();// 箭头图片在屏幕上的高度
		drawable.setBounds(0, yCenter-xCenter/2*h/w, xCenter/2,yCenter);
		//canvas.rotate(315f);
        drawable.draw(canvas);
        xPoint=xCenter/2;
        yPoint=yCenter-xCenter/4*h/w;
        canvas.restore();
    }
     
    public void StarRotate(){
    	StarRotate(AngleNow,90);
    	AngleNow=90;
    }
    public void StarRotate(float end){
    	StarRotate(AngleNow,end);
    	AngleNow=end;
    }
     public void StarRotate(float start,float end)
     {
    	 animation= new RotateAnimation(start,end,xPoint,yPoint);
    	// Log.i("End",String.valueOf(end));
     	 animation.setDuration(time);// 设置动画持续时间
     	 this.setAnimation(animation);
         animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
 		 animation.startNow();
 		 AngleNow=end;
 		this.postInvalidate();
    	 
     }
    public void stopRotate(){
    	
    }
 
}
