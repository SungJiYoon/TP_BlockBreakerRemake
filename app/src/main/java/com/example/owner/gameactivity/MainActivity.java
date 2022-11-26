package com.example.owner.gameactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    //전역적으로 사용될 변수
    int leveltmp = 1;//1은 상,2은 중,3은 하
    int colortmp = 2;//1은 빨간색,2은 노란색,3은 파란색
    boolean vibrationtmp = false;
    boolean bgmtmp = false;
    boolean languagetmp = false;//true는 한글,false는 영어
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //게임시작 버튼 클릭시 액티비티 전환
        ImageButton name_rule_btn = (ImageButton) findViewById(R.id.start_btn);
        name_rule_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);

                //Activity전환할때 기본적으로 넘겨야하는 변수들
                intent.putExtra("leveltmp",leveltmp);
                intent.putExtra("colortmp",colortmp);
                intent.putExtra("vibrationtmp",vibrationtmp);
                intent.putExtra("bgmtmp",bgmtmp);
                intent.putExtra("languagetmp",languagetmp);

                startActivity(intent);

            }
        });
    }
}