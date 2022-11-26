package com.example.owner.gameactivity;


import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
    //defalt값
    int leveltmp = 1;
    int colortmp = 2;
    boolean vibrationtmp = true;
    boolean bgmtmp = true;
    boolean languagetmp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //최종적으로 받아서 영향을 받는 GameActivity에서 모든 변수를 getExtra해줌
        Intent outIntent = getIntent();
        leveltmp = outIntent.getIntExtra("leveltmp",1);
        colortmp = outIntent.getIntExtra("colortmp",2);
        vibrationtmp = outIntent.getBooleanExtra("vibrationtmp",true);
        bgmtmp = outIntent.getBooleanExtra("bgmtmp",true);
        languagetmp = outIntent.getBooleanExtra("languagetmp",true);

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

    //메뉴바에서 setting이 눌렸을때
    //setting화면으로 전환
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
                return true;

            case R.id.action_stop:
                this.onPause();
                return true;

            case R.id.action_resume:
                this.onResume();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

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
