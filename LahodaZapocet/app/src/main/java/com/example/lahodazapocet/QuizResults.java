package com.example.lahodazapocet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class QuizResults extends AppCompatActivity {
    private TextView correctAnsw;
    private EditText editText;
    private Button saveButton;

    private boolean timeOver = false;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SCORE = "score";

    final static String appFileName = "appFile.txt";
    final static String appDirName = "/appDir/";
    final static String appDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + appDirName;

    private int correct = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        editText = findViewById(R.id.editText);
        saveButton = findViewById(R.id.saveBtn);

        final Button backBtn = findViewById(R.id.backBtnResult);

        correct = getIntent().getIntExtra("correct", 0);

        timeOver = getIntent().getBooleanExtra("timeOver", false);

        correctAnsw = findViewById(R.id.correctAnsw);
        correctAnsw.setText("Correct Answer : " + correct);


        if (timeOver) {
            final TextView congra = findViewById(R.id.congratulation);
            congra.setText("TIME OVER");

            TextView textMessage = findViewById(R.id.textMessage);
            textMessage.setText("Time is up, the Quiz has ended");
        }




        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeToExternalMemory(editText.getText().toString() + " : score - " + correct + "\n");
                startActivity(new Intent(QuizResults.this, MainActivity.class));
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResults.this, MainActivity.class));
                finish();
            }
        });
    }

    private boolean writeToExternalMemory(String dataToWrite)
    {
        try
        {
            File appDir = new File(appDirPath);
            if(!appDir.exists())
            {
                appDir.mkdir();

            }
            FileOutputStream fos = new FileOutputStream(appDirPath + appFileName,true);
            fos.write(dataToWrite.getBytes());
            fos.flush();
            fos.close();

            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
            //Log.d(TAG, "ZAPIS JE OK.");
            return true;
        }
        catch (Exception e)
        {
            //Log.d(TAG, "Chyba zapisu: " + e.toString());
            return false;
        }
    }
}