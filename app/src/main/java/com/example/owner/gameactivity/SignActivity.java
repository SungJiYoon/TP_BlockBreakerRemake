package com.example.owner.gameactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class SignActivity extends Info {
    EditText NAME,PASS,PASSSIGN,ID;
    String Tname, Tpass, Tpasssign,Tid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        NAME = (EditText) findViewById(R.id.name);
        PASS = (EditText) findViewById(R.id.password);
        PASSSIGN = (EditText) findViewById(R.id.passsign);
        ID = (EditText) findViewById(R.id.id);

        ImageButton join = (ImageButton) findViewById(R.id.join_btn);
        join.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Tname = NAME.getText().toString();
                Tpass = PASS.getText().toString();
                Tpasssign = PASSSIGN.getText().toString();
                Tid = ID.getText().toString();
                Cursor cursor = database.rawQuery("SELECT name, id FROM MEMBER", null);
                int count = cursor.getCount();

                for(int i=0;i<count;i++) {
                    cursor.moveToNext();
                    Cname = cursor.getString(0);
                    Cid = cursor.getString(1);

                }

                if (Tname.length()<2) {
                    Toast.makeText(getApplicationContext(), "닉네임을 2자리 이상 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                } else if (Tpass.length() <4) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 4자리 이상 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                } else if (Tpasssign.equals(Tpass)==false) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 동일하지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                } else if (Tid.length() <4 || Tid.equals(Cid)) {
                    Toast.makeText(getApplicationContext(), "이미 등록된 아이디거나 정확하지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    try{
                        if (database != null) {
                            database.execSQL("INSERT INTO " + login_table + "(name, pass, id) VALUES" +
                                    "(" + "'" + Tname + "'" + "," + "'" + Tpass + "'" + "," + "'" + Tid + "'" +  ")");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    Intent login = new Intent(getApplication(), LoginActivity.class);
                    login.putExtra("splash", "splash");
                    startActivity(login);
                    finish();
                    Toast.makeText(getApplication(), Tname + "님 회원가입을 축하합니다.", Toast.LENGTH_SHORT).show();
                }

            } });
    }

}