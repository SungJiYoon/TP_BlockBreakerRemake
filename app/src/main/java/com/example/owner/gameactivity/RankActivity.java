package com.example.owner.gameactivity;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RankActivity extends Info{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String [] ranks = {"0","0","0","0","0"};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        if (database != null) {
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

        TextView s1 = (TextView) findViewById(R.id.rankscore1);
        TextView s2 = (TextView) findViewById(R.id.rankscore2);
        TextView s3 = (TextView) findViewById(R.id.rankscore3);
        TextView s4 = (TextView) findViewById(R.id.rankscore4);
        TextView s5 = (TextView) findViewById(R.id.rankscore5);

        s1.setText("1. "+ranks[0]);
        s2.setText("2. "+ranks[1]);
        s3.setText("3. "+ranks[2]);
        s4.setText("4. "+ranks[3]);
        s5.setText("5. "+ranks[4]);

    }



}
