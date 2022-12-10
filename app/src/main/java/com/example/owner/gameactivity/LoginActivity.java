package com.example.owner.gameactivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;

import android.widget.Toast;

public class LoginActivity extends Info {
    EditText idText; //로그인 값
    EditText passText;
    String Id;
    String Pass;

    MediaPlayer main_mp = null; //게임화면 배경음악

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //로그인 화면 보여줌
        idText = (EditText) findViewById(R.id.LogId); //값에서 아이디 져옴
        passText = (EditText) findViewById(R.id.LogPass);

        main_mp = MediaPlayer.create(this, R.raw.main_mugic);
        main_mp.setLooping(true);
        main_mp.start();

        if (database != null) { //데이터 베이스에 있으면
            Cursor cursor = database.rawQuery("SELECT name, id, pass FROM MEMBER", null);
            int count = cursor.getCount();
            for(int i=0;i<count;i++) {
                cursor.moveToNext();
                Cname = cursor.getString(0);
                Cid = cursor.getString(1);
                Cpass = cursor.getString(2);
            }

            ImageButton loginButton = (ImageButton)findViewById(R.id.login_btn); /*페이지 전환버튼*/
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Id = idText.getText().toString();
                    Pass = passText.getText().toString();
                    if (Id.equals(Cid) && Pass.equals(Cpass)) {
                        Intent gameStart = new Intent(getApplication(), MainActivity.class); //로그인되면 메인으로 넘어감
                        gameStart.putExtra("splash", "splash");
                        startActivity(gameStart);
                        Toast.makeText(getApplicationContext(), Cname + "님 환영합니다.",
                                Toast.LENGTH_SHORT).show();
                        main_mp.stop();
                    } else {
                        Toast.makeText(getApplicationContext(), "없는 아이디거나 패스워드가 틀렸습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cursor.close();
        }


        ImageButton signButton = (ImageButton)findViewById(R.id.signup_btn); /*페이지 전환버튼*/
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = new Intent(getApplication(), SignActivity.class);
                sign.putExtra("splash", "splash");
                startActivity(sign);
            }
        });
    }



}