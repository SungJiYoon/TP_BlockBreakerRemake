package com.example.owner.gameactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingActivity extends AppCompatActivity{
    Button returnStart;
    RadioGroup levelGroup;
    RadioGroup colorGroup;
    ToggleButton vibrationToggle;
    ToggleButton bgmToggle;
    ToggleButton languageToggle;

    //선택이 안되어졌을때는 기본값으로 넘어가도록 변수설정
    int leveltmp = 1;//1은 상,2은 중,3은 하
    int colortmp = 2;//1은 빨간색,2은 노란색,3은 파란색
    boolean vibrationtmp = false;
    boolean bgmtmp = false;
    boolean languagetmp = false;//true는 한글,false는 영어

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);

        //returnstart버튼이 눌렸을때
        returnStart = (Button)findViewById(R.id.returngame);
        returnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(SettingActivity.this,GameActivity.class);

                //Activity전환할때 기본적으로 넘겨야하는 변수들
                myIntent.putExtra("leveltmp",leveltmp);
                myIntent.putExtra("colortmp",colortmp);
                myIntent.putExtra("vibrationtmp",vibrationtmp);
                myIntent.putExtra("bgmtmp",bgmtmp);
                myIntent.putExtra("languagetmp",languagetmp);

                startActivity(myIntent);
                finish();
            }
        });
        //game을 시작

        //난이도Radio버튼이 눌렸을때
        levelGroup = (RadioGroup) findViewById(R.id.RadioGroupLevel);
        levelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.RadioButtonLevel1:
                        leveltmp = 1;
                        Toast.makeText(getApplicationContext(),"상",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RadioButtonLevel2:
                        leveltmp = 2;
                        Toast.makeText(getApplicationContext(),"중",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RadioButtonLevel3:
                        leveltmp = 3;
                        Toast.makeText(getApplicationContext(),"하",Toast.LENGTH_SHORT).show();
                        break;
                        
                }
            }
        });
        //난이도를 설정하고 변경

        //색상Radio버튼이 눌렸을때
        colorGroup = (RadioGroup) findViewById(R.id.RadioGroupColor);
        colorGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.RadioButtonColorRed:
                        colortmp=1;
                        Toast.makeText(getApplicationContext(),"빨간색",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RadioButtonColorYellow:
                        colortmp=2;
                        Toast.makeText(getApplicationContext(),"노란색",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RadioButtonColorBlue:
                        colortmp=3;
                        Toast.makeText(getApplicationContext(),"파란색",Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
        //색상을 설정하고 변경

        //진동Toggle버튼이 눌렸을때
        vibrationToggle =(ToggleButton) findViewById(R.id.ToggleVibration);
        vibrationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    vibrationtmp = true;
                    Toast.makeText(getApplicationContext(),"진동ON",Toast.LENGTH_SHORT).show();
                }
                else{
                    vibrationtmp = false;
                    Toast.makeText(getApplicationContext(),"진동OFF",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //진동On/Off

        //배경음악Toggle버튼이 눌렸을때
        bgmToggle = (ToggleButton) findViewById(R.id.ToggleBgm);
        bgmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    bgmtmp=true;
                    Toast.makeText(getApplicationContext(),"배경음악ON",Toast.LENGTH_SHORT).show();
                }
                else{
                    bgmtmp=false;
                    Toast.makeText(getApplicationContext(),"배경음악OFF",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //배경음악On/Off

        //언어Toggle버튼이 눌렸을때
        languageToggle = (ToggleButton) findViewById(R.id.ToggleLanguage);
        languageToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    languagetmp=true;
                    Toast.makeText(getApplicationContext(),"한글설정",Toast.LENGTH_SHORT).show();
                }
                else{
                    languagetmp=false;
                    Toast.makeText(getApplicationContext(),"영어설정",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //언어On/Off

    }
}
