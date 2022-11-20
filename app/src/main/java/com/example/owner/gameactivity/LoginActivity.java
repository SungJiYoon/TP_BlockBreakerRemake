package com.example.owner.gameactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Info {
    EditText idText; //로그인 값
    EditText passText;
    String Id;
    String Pass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //로그인 화면 보여줌
        idText = (EditText) findViewById(R.id.LogId); //값에서 아이디 져옴
        passText = (EditText) findViewById(R.id.LogPass);

        if (database != null) { //데이터 베이스에 있으면
            Cursor cursor = database.rawQuery("SELECT name, num, pass FROM " + tableName, null);
            int count = cursor.getCount();
            for(int i=0;i<count;i++) {
                cursor.moveToNext();
                Cname = cursor.getString(0);
                Cnum = cursor.getString(1);
                Cpass = cursor.getString(2);

            }

            Button loginButton = (Button)findViewById(R.id.login); /*페이지 전환버튼*/
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Id = idText.getText().toString();
                    Pass = passText.getText().toString();
                    if (Id.equals(Cnum) && Pass.equals(Cpass)) {
                        Intent main = new Intent(getApplication(), MainActivity.class); //로그인되면 메인으로 넘어감
                        main.putExtra("splash", "splash");
                        startActivity(main);
                        Toast.makeText(getApplicationContext(), Cname + "님 환영합니다.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "정확한 정보를 입력하세요.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cursor.close();
        }


        Button signButton = (Button)findViewById(R.id.signup); /*페이지 전환버튼*/
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