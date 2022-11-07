package com.example.owner.gameactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //게임시작 버튼 클릭시 액티비티 전환
        Button name_rule_btn = (Button) findViewById(R.id.start_btn);
        name_rule_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
            }
        });
    }
}