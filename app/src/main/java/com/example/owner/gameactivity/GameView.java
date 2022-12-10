package com.example.owner.gameactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import java.util.ArrayList;
import android.os.Handler;
import androidx.appcompat.app.ActionBar;

public class GameView extends TextureView implements TextureView.SurfaceTextureListener,
    View.OnTouchListener {
    private Thread mThread;
    volatile private boolean mIsRunnable;
    //volatile로 지정하지않은 변수는 Thread내에서 갱신되지않을경우
    // 변수값이 바뀌지않는다고 생각하여 초기값그대로 참조함 => 최적화처리를 무효로 할수있음
    volatile private float mTouchedX;
    volatile  private float mTouchedY;
    private float mBlockWidth;
    private float mBlockHeight;
    int BLOCK_COUNT = 100;
    //Life를 static에서 변경
    private int mLife;
    //체력이 바뀜에 따라 타이틀바 변경을 위한 Handler
    private Handler mLifeHandler ;
    private long mGameStartTime;
    private Handler mHandler ;
    //타이틀바 선언
    ActionBar actionBar;
    Vibrator vibration;

    //전역적으로 사용될 변수
    int leveltmp;
    int colortmp;
    boolean vibrationtmp;
    boolean bgmtmp;
    boolean themetmp;

    public GameView(final Context context,int recvleveltmp,int recvcolortmp,boolean recvvibrationtmp,Vibrator vibratortmp, boolean recvbgmtmp,boolean recvthemetmp,ActionBar recvactionbar) {
        super(context);
        setSurfaceTextureListener(this);
        setOnTouchListener(this);
        //파라미터로 받은 변수들을 저장
        //굳이 안해도 되겠지만 코드 이해를 변하게 하기 위해서 공통적으로 사용되는 변수로 변환
        leveltmp = recvleveltmp;
        colortmp = recvcolortmp;
        vibrationtmp = recvvibrationtmp;
        bgmtmp = recvbgmtmp;
        themetmp = recvthemetmp;
        actionBar = recvactionbar;
        vibration = vibratortmp;
        int num = 0;

        //난이도 설정 1이면 상(블록100), 2이면 중(블록80),3이면 하(블록60)
        if(leveltmp == 1){
            BLOCK_COUNT=100;
        }
        else if(leveltmp==2){
            BLOCK_COUNT=80;
        }
        else if(leveltmp==3){
            BLOCK_COUNT=60;
        }
        
        mHandler = new Handler() {
            @Override
        public void handleMessage(Message msg){
                Intent intent = new Intent(context, ClearActivity.class);	// 추가
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);	        //
                intent.putExtras(msg.getData());			            //
                context.startActivity(intent);                              //
            }
        };
        
        //남은 체력을 출력하는 handler
        mLifeHandler = new Handler() {
            @Override
            public void handleMessage(Message msg){
                recvactionbar.setTitle(" 남은 체력 : "+String.valueOf(mLife)+"/5");
            }
        };


    }


    public void start(){
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {

                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.RED);

                while(true){
                    long startTime = System.currentTimeMillis();
                    //앱 실행중 반복 호출
                    synchronized (GameView.this){
                        if(!mIsRunnable){
                            break; //루프 종료
                        }
                        Canvas canvas = lockCanvas();

                        if(canvas==null){
                            continue; // 캔버스를 잠글수없다면 다시 while문으로 이동
                        }

                        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.iphone2);
                        canvas.drawBitmap(b,0,0,null);
                    if(themetmp == true){
                        Bitmap c = BitmapFactory.decodeResource(getResources(), R.drawable.iphone2);
                        canvas.drawBitmap(c,0,0,null);
                    }
                    else if(themetmp == false){
                        Bitmap d = BitmapFactory.decodeResource(getResources(), R.drawable.iphone3);
                        canvas.drawBitmap(d,0,0,null);
                    }

                        float padLeft = mTouchedX - mPadHalfWidth;
                        float padRight = mTouchedX + mPadHalfWidth;
                        mPad.setLeftRight(padLeft, padRight);
                        mBall.move();
                        float ballTop = mBall.getY() - mBallRadius;
                        float ballLeft = mBall.getX() - mBallRadius;
                        float ballBottom = mBall.getY() + mBallRadius;
                        float ballRight = mBall.getX() + mBallRadius;

                        //벽충돌판정
                        if(ballLeft<0 && mBall.getSpeedX() < 0 || ballRight >= getWidth() && mBall.getSpeedX() >0){
                            mBall.setSpeedX(-mBall.getSpeedX()); // 가로방향 벽에 부딪히면 가로속도반전
                        }

                        if(ballTop<0 ){
                            mBall.setSpeedY(-mBall.getSpeedY()); // 위방향벽에 부딪히면 세로속도반전
                        }

                        //바닥에 떨어진 판정

                        if(ballBottom > getHeight()){
                            if(vibrationtmp == true){
                                vibration.vibrate(500); // 0.5초간 진동
                                    Log.v("Vibration", "YES");
                            }
                            if(mLife > 0){
                                mLife--;
                                //남은 체력을 출력해줄수있게 handler에 메세지보냄
                                mLifeHandler.sendMessage(new Message());
                                mBall.reset();
                            }else{
                                unlockCanvasAndPost(canvas);
                                Message message = Message.obtain();
                                Bundle bundle = new Bundle();
                                bundle.putBoolean(ClearActivity.EXTRA_IS_CLEAR, false);
                                bundle.putInt(ClearActivity.EXTRA_BLOCK_COUNT, getBlockCount());
                                bundle.putLong(ClearActivity.EXTRA_TIME, System.currentTimeMillis()-mGameStartTime);
                                message.setData(bundle);
                                //체력이 없을때 종료되기위해 메시지보냄
                                mHandler.sendMessage(message);
                                return;
                            }
                        }

                        //블록과 공의 충돌 판정
                        Block leftBlock = getBlock(ballLeft, mBall.getY());
                        Block topBlock = getBlock(mBall.getX(), ballTop);
                        Block rightBlock = getBlock(ballRight, mBall.getY());
                        Block bottomBlock = getBlock(mBall.getX(), ballBottom);

                        //게임 클리어는 블록과 공이 충돌한 순간에만 일어나므로 공과 블록의 충돌판정에 클리어 처리를 추가
                        //충돌이 한군데라도 발생하면 true,아니면 false
                        boolean isCollision = false;

                        //충돌 처리부분
                        if(leftBlock != null){
                           mBall.setSpeedX(-mBall.getSpeedX());
                            leftBlock.collision();
                            isCollision = true;
                        }
                        if(topBlock!=null){
                            mBall.setSpeedY(-mBall.getSpeedY());
                            topBlock.collision();
                            isCollision = true;
                        }
                        if(rightBlock!=null){
                            mBall.setSpeedX(-mBall.getSpeedX());
                            rightBlock.collision();
                            isCollision = true;
                        }
                        if(bottomBlock!=null){
                            mBall.setSpeedY(-mBall.getSpeedY());
                            bottomBlock.collision();
                            isCollision = true;
                        }

                        //패드와 공의 충돌판정 -> 공의 밑면이 패드를 넘는 순간
                        //패드의 윗면 좌표와 공의 속도 획득
                        float padTop = mPad.getTop();
                        float ballSpeedY = mBall.getSpeedY();


                        //공이 밑면이 패드를 넘는 순간,직전에 패드 윗면보다 위에있는지,아니면 닿았는지
                        if(ballBottom > padTop && ballBottom - ballSpeedY < padTop && padLeft < ballRight
                                && padRight > ballLeft){

                            //패드에 부딫힐때마다 5%씩 증가
                            if(ballSpeedY < mBlockHeight /3){
                                ballSpeedY *= -1.05F;
                            }else{
                                ballSpeedY = -ballSpeedY;
                            }
                            //가로 방량 속도 설정
                            float ballSpeedX = mBall.getSpeedX() + (mBall.getX() - mTouchedX)/10;
                            if(ballSpeedX> mBlockWidth /5){
                                ballSpeedX = mBlockWidth /5;
                            }
                            mBall.setSpeedY(ballSpeedY);
                            mBall.setSpeedX(ballSpeedX);


                        }

                       // mPad.draw(canvas, paint);

                        //인터페이스를 상속받아 아이템 그리는걸 통합함
                        for(DrawableItem item : mItemList){
                            item.draw(canvas,paint);
                        }
                        unlockCanvasAndPost(canvas);//화면에 그림림
                        if(isCollision && getBlockCount() == 0){
                            Message message = Message.obtain();
                            Bundle bundle = new Bundle();
                            bundle.putBoolean(ClearActivity.EXTRA_IS_CLEAR, true);
                            bundle.putInt(ClearActivity.EXTRA_BLOCK_COUNT,0);
                            bundle.putLong(ClearActivity.EXTRA_TIME, System.currentTimeMillis() - mGameStartTime); message.setData(bundle);
                            mHandler.sendMessage(message);
                        }

                    }

                    long sleepTime = 16 -System.currentTimeMillis() + startTime;
                    if(sleepTime >0){
                        try{
                            Thread.sleep(sleepTime);
                        }catch (InterruptedException e){

                        }
                    }
                }


            }
        });
        mIsRunnable =true;
        mThread.start();
    }
    public void stop(){
        mIsRunnable = false;
    }



    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        readyObjects(width, height);


        // paint.setStyle(Paint.Style.STROKE);
        // paint.setColor(Color.BLUE);
        //paint.setStrokeWidth(8);

        //canvas.drawRect(0,0,500,300,paint);

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    readyObjects(width, height);
    }

    private ArrayList<DrawableItem> mItemList;
    private ArrayList<Block> mBlockList;
    private Pad mPad;
    private float mPadHalfWidth;

    private Ball mBall;
    private float mBallRadius;

    public void readyObjects(int width, int height){
        mBlockWidth = width /10;
        mBlockHeight = height /20;
        mItemList = new ArrayList<DrawableItem>();
        mBlockList = new ArrayList<Block>();


        for(int i=0; i<BLOCK_COUNT; i++){
            float blockTop = i/10 * mBlockHeight ;
            float blockLeft = i %10 *mBlockWidth;
            float blockBottom = blockTop + mBlockHeight ;
            float blockRight = blockLeft +mBlockWidth;
            mBlockList.add(new Block(blockTop, blockLeft,blockBottom,blockRight));

        }
        mItemList.addAll(mBlockList);

        mPad = new Pad(height*0.8F, height*0.85F);
        mItemList.add(mPad);
        mPadHalfWidth = width /10;

        mBallRadius = width < height? width/40 : height/40;
        mBall = new Ball(mBallRadius, width/2, height/2, 0);
         if(colortmp == 1){
             mBall.red();
          }
          else if(colortmp==2){
              mBall.yellow();
          }
         else if(colortmp==3){
              mBall.blue();
          }
        mItemList.add(mBall);
        mLife = 5;
        mGameStartTime = System.currentTimeMillis();
    }

    private int getBlockCount(){
        int count = 0;
        for(Block block : mBlockList){
            if(block.isExist()){
                count++;
            }
        }
        return count;
    }


    private Block getBlock(float x, float y){
        int index =(int) (x/mBlockWidth + (int)(y/mBlockHeight)*10);
        if(0<=index && index<BLOCK_COUNT){
            Block block = (Block) mItemList.get(index);
            if(block.isExist()){
                return block;
            }
        }
        return null;
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        // SurfaceTextView 폐기될때 사용되는 메소드
        return true; // false로 설정할경우 프로그래머가 직접 폐기해야함
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mTouchedX = event.getX();
        mTouchedY = event.getY();
        return true;
    }
}
