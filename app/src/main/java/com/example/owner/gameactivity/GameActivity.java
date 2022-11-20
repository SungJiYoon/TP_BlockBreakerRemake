package com.example.owner.gameactivity;


import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import android.content.Intent;

public class GameActivity extends AppCompatActivity {
    private GameView mView;
    ActionBar actionBar;
    MediaPlayer game_mp = null; //게임화면 배경음악

    //전역적으로 사용될 변수
    int leveltmp;
    int colortmp;
    boolean vibrationtmp;
    boolean bgmtmp;
    boolean languagetmp;

    int []farman = new int[]{R.drawable.iphone2};
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //최종적으로 받아서 영향을 받는 GameActivity에서 모든 변수를 getExtra해줌
        Intent intent = getIntent();
        leveltmp = intent.getIntExtra("leveltmp",1);
        colortmp = intent.getIntExtra("colortmp",2);
        vibrationtmp = intent.getBooleanExtra("vibrationtmp",false);
        bgmtmp = intent.getBooleanExtra("bgmtmp",false);
        languagetmp = intent.getBooleanExtra("languagetmp",false);
        
        //타이틀바를 파라미터로 넘겨줌
        actionBar = getSupportActionBar();
        actionBar.setTitle(" 남은 체력 : 1/1");
        
      //받은 변수들을 GameView에 파라미터로 넘겨줌
        mView = new GameView(this,leveltmp,colortmp,vibrationtmp,bgmtmp,languagetmp,actionBar);
        setContentView(mView);

        if(bgmtmp == true){
            startResAudio(mView);
        }
        else{
            stopResAudio(mView);
        }
    }

    public void startResAudio(View v){
        game_mp = MediaPlayer.create(this, R.raw.game_mugic);
        game_mp.setLooping(true);
        game_mp.start();
    }

    public void stopResAudio(View v){
        if(game_mp != null){
            game_mp.stop();
            game_mp.release();
        }
        game_mp = null;
    }
    
    //메뉴바 추가,활성화
    //res.menu.menu_main을 추가
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        return true;
    }
    //res.menu.menu_main을 추가
    
    //메뉴바에서 setting이 눌렀을때
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent myIntent = new Intent(GameActivity.this,SettingActivity.class);
                myIntent.putExtra("leveltmp",leveltmp);
                myIntent.putExtra("colortmp",colortmp);
                myIntent.putExtra("vibrationtmp",vibrationtmp);
                myIntent.putExtra("bgmtmp",bgmtmp);
                myIntent.putExtra("languagetmp",languagetmp);
                startActivity(myIntent);
                finish();
                return true;

            case R.id.action_stop:
                this.onPause();
                return true;

            case R.id.action_resume:
                this.onResume();
                return true;

            case R.id.action_Day:
                this.image();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
    public void image(){
        int g =0;
        img.setImageResource(farman[g]);
    }



    //setting화면으로 전환

    @Override
    protected void onPause() {
        super.onPause();
        mView.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mView.start();
    }

}
