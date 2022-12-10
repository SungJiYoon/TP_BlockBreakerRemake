package com.example.owner.gameactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import static com.example.owner.gameactivity.R.string.block_count;
import static com.example.owner.gameactivity.R.string.clear;

import androidx.appcompat.app.AppCompatActivity;

public class ClearActivity extends Info {
    public static final String EXTRA_IS_CLEAR = "EXTRA.IS_CLEAR";
    public static final String EXTRA_BLOCK_COUNT = "EXTRA.BLOCK_COUNT";
    public static final String EXTRA_TIME = "EXTRA.TIME";
    private static final String TAG = "ClearActivity";

    @SuppressLint("StringFormatMatches")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String [] ranks = {"0","0","0","0","0"};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_clear);
        Intent receiveIntent = getIntent();
        if (receiveIntent == null) {
            finish();
        }
        Bundle receiveExtras = receiveIntent.getExtras();
        if (receiveExtras == null) {
            finish();
        }
        boolean isClear = receiveExtras.getBoolean(EXTRA_IS_CLEAR, false);
        int blockCount = receiveExtras.getInt(EXTRA_BLOCK_COUNT, 0);
        long clearTime = receiveExtras.getLong(EXTRA_TIME, 0);
        int nowScore = (100 - blockCount) * (100 - blockCount) * 10;

        Log.d(TAG, Integer.toString(nowScore));

        if (database != null) {
            database.execSQL("INSERT INTO " + rank_table + "(score) VALUES" +
                    "(" + "'" + nowScore + "'" + ")");

            Cursor cursor = database.rawQuery("SELECT * FROM RANK ORDER BY score desc", null);
            int count = cursor.getCount();
            for(int i=0;i<count;i++) {
                if (i==5) {
                    break;
                }
                cursor.moveToNext();
                Cscore = cursor.getString(1);
                ranks[i] = Cscore;
                Log.d(TAG, Cscore);
                Log.d(TAG, ranks[i]);
                Log.d(TAG, "******");
            }
        }



        TextView textTitle = (TextView) findViewById(R.id.textTitle);
        TextView textBlockCount = (TextView)findViewById(R.id.textBlockCount);
        TextView textClearTime = (TextView)findViewById(R.id.textClearTime);
        Button gameStart = (Button)findViewById(R.id.buttonGameStart);
        Button mainStart = (Button)findViewById(R.id.buttonMainStart);

        TextView s1 = (TextView) findViewById(R.id.score1);
        TextView s2 = (TextView) findViewById(R.id.score2);
        TextView s3 = (TextView) findViewById(R.id.score3);
        TextView s4 = (TextView) findViewById(R.id.score4);
        TextView s5 = (TextView) findViewById(R.id.score5);


        if (isClear) {
            textTitle.setText(R.string.clear);
        } else {
            textTitle.setText(R.string.game_over);
        }

        textBlockCount.setText(getString(R.string.block_count, blockCount));
        textClearTime.setText(getString(R.string.time, clearTime / 1000, clearTime % 1000));
        s1.setText("1. "+ranks[0]);
        s2.setText("2. "+ranks[1]);
        s3.setText("3. "+ranks[2]);
        s4.setText("4. "+ranks[3]);
        s5.setText("5. "+ranks[4]);

        gameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClearActivity.this, GameActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        mainStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClearActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });


    }

}
