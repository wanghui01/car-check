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
     
    //��дView���onDraw()����
    @Override
    protected void onDraw(Canvas canvas) {
        //����ת����ת����Ϊ�ԣ�160,160������ΪԲ�ģ���תX�Ƕ�         
        canvas.save();         
        //������ı�������Ϊ����ͼ���ģ��ڲ�ͬ��Ļ��������������ܲ�ͬ��������Լ���������Ļ��С�Լ�����
        int xCenter = getWidth() ; // ���̵�����x����
		int yCenter = getHeight() ;// ���̵�����y����
		int w = drawable.getIntrinsicWidth(); // ��ͷͼƬ����Ļ�ϵĳ���
		int h = drawable.getIntrinsicHeight();// ��ͷͼƬ����Ļ�ϵĸ߶�
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
     	 animation.setDuration(time);// ���ö�������ʱ��
     	 this.setAnimation(animation);
         animation.setFillAfter(true);//����ִ������Ƿ�ͣ����ִ�����״̬
 		 animation.startNow();
 		 AngleNow=end;
 		this.postInvalidate();
    	 
     }
    public void stopRotate(){
    	
    }
 
}
