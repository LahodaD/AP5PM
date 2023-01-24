package com.example.lahodazapocet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class QuizStatistic extends AppCompatActivity {

    private static final String TAG = "MMMMM";
    final static String appFileName = "appFile.txt";
    final static String appDirName = "/appDir/";
    final static String appDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + appDirName;
    String oneLine = "";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_statistic);

        textView = findViewById(R.id.scoreText);

        final Button backBtn = findViewById(R.id.backBtnStat);

        readFromExternalMemory();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizStatistic.this, MainActivity.class));
                finish();
            }
        });

    }

    private boolean readFromExternalMemory()
    {
        try
        {
            File appFile = new File(appDirPath + appFileName);
            if(appFile.exists())
            {
                FileInputStream fis = new FileInputStream (new File(appDirPath + appFileName));
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);

                String readResult = "";

                while ( (oneLine = bufferedReader.readLine()) != null )
                {
                    readResult += oneLine;
                    readResult += "\n";
                }

                bufferedReader.close();
                isr.close();
                fis.close();

                textView.setText(readResult);
            }

            return true;
        }
        catch (Exception e)
        {
            textView.setText("Chyba cteni");
            //Log.d(TAG, "Chyba cteni \n" + e.toString());
            return false;
        }
    }
}