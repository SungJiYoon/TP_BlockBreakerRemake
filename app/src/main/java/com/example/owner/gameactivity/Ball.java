package com.example.owner.gameactivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball implements DrawableItem {
    public int color =4;
    private float mX;
    private float mY;
    private float mSpeedX;
    private float mSpeedY;
    private final float mRadius;
    // 초기속도
    private final float mInitialSpeedX;
    private final float mInitialSpeedY;

    //출현위치
    private final float mInitialX;
    private final float mInitialY;

    public float getSpeedX(){
        return mSpeedX;
    }

    public float getSpeedY(){
        return mSpeedY;
    }
    public float getY(){
        return mY;
    }
    public float getX(){return  mX;}
    public void setSpeedX(float speedX){
        mSpeedX = speedX;
    }
    public void setSpeedY(float speedY){
        mSpeedY = speedY;
    }



    public Ball(float radius, float initialX, float initialY, int xcolor){
        mRadius = radius;
        mSpeedX = radius /2;
        mSpeedY = radius/2;
        mX = initialX;
        mY = initialY;

        mInitialSpeedX = mSpeedX;
        mInitialSpeedY = mSpeedY;
        mInitialX = mX;
        mInitialY = mY;

        xcolor = color; //회색인 이유?  is never used 좀 찾아보기
    }
    public void move(){
        mX += mSpeedX;
        mY += mSpeedY;
    }
    public void red(){
        color = 1;
    }
    public void yellow(){
        color = 2;
    }
    public void blue(){
        color = 3;
    }
    public void white(){
        color = 4;
    }

    public void draw(Canvas canvas, Paint paint){

//        Intent intent = getIntent();
//        colortmp = intent.getIntExtra("colortmp",2);

        if(color == 1){
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mX,mY,mRadius, paint);
        }
        else if(color == 2){
            paint.setColor(Color.YELLOW);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mX,mY,mRadius, paint);
        }
        else if(color == 3){
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mX,mY,mRadius, paint);
        }
        else{
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mX,mY,mRadius, paint);

        }
    }

    public void reset(){
        mX = mInitialX;
        mY = mInitialY;
        mSpeedX = mInitialSpeedX + ((float)Math.random() -0.5f);
        mSpeedY = mInitialSpeedY;
    }
}
