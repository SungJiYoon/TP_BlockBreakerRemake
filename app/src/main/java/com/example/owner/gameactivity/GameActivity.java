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
    private static MediaPlayer game_mp; //게임화면 배경음악

    //전역적으로 사용될 변수
    int leveltmp;
    int colortmp;
    boolean vibrationtmp;
    boolean bgmtmp;
    boolean languagetmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        game_mp = MediaPlayer.create(this, R.raw.game_mugic);
        game_mp.setLooping(true);

        //최종적으로 받아서 영향을 받는 GameActivity에서 모든 변수를 getExtra해줌
        Intent outIntent = getIntent();
        leveltmp = outIntent.getIntExtra("leveltmp",1);
        colortmp = outIntent.getIntExtra("colortmp",2);
        vibrationtmp = outIntent.getBooleanExtra("vibrationtmp",true);
        bgmtmp = outIntent.getBooleanExtra("bgmtmp",true);
        languagetmp = outIntent.getBooleanExtra("languagetmp",true);

        //타이틀바를 파라미터로 넘겨줌
        actionBar = getSupportActionBar();
        actionBar.setTitle(" 남은 체력 : 5/5");
        
      //받은 변수들을 GameView에 파라미터로 넘겨줌
        mView = new GameView(this,leveltmp,colortmp,vibrationtmp,game_mp,bgmtmp,languagetmp,actionBar);
        setContentView(mView);
    }
    
    //메뉴바 추가,활성화
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
                startActivity(myIntent);
                game_mp.pause(); //다른 Activity로 넘어가면 bgm끄기
                finish();
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
