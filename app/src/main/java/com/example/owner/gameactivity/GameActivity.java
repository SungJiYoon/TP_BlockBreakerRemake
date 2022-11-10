package com.example.owner.gameactivity;


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

    //전역적으로 사용될 변수
    int leveltmp;
    int colortmp;
    boolean vibrationtmp;
    boolean bgmtmp;
    boolean languagetmp;

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
        actionBar.setTitle(" 남은 체력 : 5/5");
        
      //받은 변수들을 GameView에 파라미터로 넘겨줌
        mView = new GameView(this,leveltmp,colortmp,vibrationtmp,bgmtmp,languagetmp,actionBar);
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
