package com.example.lahodazapocet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity {

    private Timer quizTimer;
    private int totalTimeInMins = 1;
    private int seconds = 0;

    private int correctAnsw = 0;
    private TextView question;

    private AppCompatButton option1, option2, option3, option4;

    private AppCompatButton nextBtn;

    private List<QuestionList> getQuestionListList;

    private int currentQuestionPosition = 0;

    private String selectedOptionByUser = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        getQuestionListList = (List<QuestionList>) getIntent().getSerializableExtra("LIST");

        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView timer = findViewById(R.id.timerText);

        question = findViewById(R.id.question);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        question.setTextColor(Color.WHITE);
        option1.setTextColor(Color.WHITE);
        option2.setTextColor(Color.WHITE);
        option3.setTextColor(Color.WHITE);
        option4.setTextColor(Color.WHITE);

        nextBtn = findViewById(R.id.next);

        startTimer(timer);

        question.setText(getQuestionListList.get(currentQuestionPosition).getQuestion());
        randomShaker();

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOptionByUser.isEmpty()) {

                    selectedOptionByUser = option1.getText().toString();
                    option1.setBackgroundResource(R.drawable.round_back_red);
                    option1.setTextColor(Color.WHITE);

                    isCorrect();
                }
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOptionByUser.isEmpty()) {

                    selectedOptionByUser = option2.getText().toString();
                    option2.setBackgroundResource(R.drawable.round_back_red);
                    option2.setTextColor(Color.WHITE);

                    isCorrect();
                }
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOptionByUser.isEmpty()) {

                    selectedOptionByUser = option3.getText().toString();
                    option3.setBackgroundResource(R.drawable.round_back_red);
                    option3.setTextColor(Color.WHITE);

                    isCorrect();
                }
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOptionByUser.isEmpty()) {

                    selectedOptionByUser = option4.getText().toString();
                    option4.setBackgroundResource(R.drawable.round_back_red);
                    option4.setTextColor(Color.WHITE);

                    isCorrect();
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOptionByUser.isEmpty()) {
                    Toast.makeText(QuizActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                } else {
                    changeNextQuestion();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizTimer.purge();
                quizTimer.cancel();

                startActivity(new Intent(QuizActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void changeNextQuestion() {
        currentQuestionPosition++;
        if ((currentQuestionPosition + 1) == getQuestionListList.size()) {
            nextBtn.setText("Submit Quiz");
        }

        if (currentQuestionPosition < getQuestionListList.size()) {

            selectedOptionByUser = "";

            option1.setBackgroundResource(R.drawable.round_back_light_light_purple);
            option2.setBackgroundResource(R.drawable.round_back_light_light_purple);
            option3.setBackgroundResource(R.drawable.round_back_light_light_purple);
            option4.setBackgroundResource(R.drawable.round_back_light_light_purple);

            randomShaker();
        }
        else {
            quizTimer.purge();
            quizTimer.cancel();

            Intent intent = new Intent(QuizActivity.this, QuizResults.class);
            intent.putExtra("correct", getCorrectAnswers());
            startActivity(intent);

            finish();
        }
    }

    private void startTimer(TextView timerTextView) {
        quizTimer = new Timer();

        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if ((seconds == 0) && (totalTimeInMins == 0)) {

                    //Toast.makeText(QuizActivity.this, "Time Over", Toast.LENGTH_SHORT).show();

                    quizTimer.purge();
                    quizTimer.cancel();

                    boolean timeOver = true;

                    Intent intent = new Intent(QuizActivity.this, QuizResults.class);
                    intent.putExtra("timeOver", timeOver);
                    intent.putExtra("correct", getCorrectAnswers());
                    startActivity(intent);

                    finish();
                } else if (seconds == 0) {
                    totalTimeInMins--;
                    seconds = 59;
                } else{
                    seconds--;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String finalMinutes = String.valueOf(totalTimeInMins);
                        String finalSeconds = String.valueOf(seconds);

                        if (finalMinutes.length() == 1) {
                            finalMinutes = "0" + finalMinutes;
                        }

                        if (finalSeconds.length() == 1) {
                            finalSeconds = "0" + finalSeconds;
                        }

                        timerTextView.setText(finalMinutes + ":" + finalSeconds);
                    }
                });

            }
        }, 1000, 1000);
    }

    private int getCorrectAnswers() {
        return correctAnsw;
    }

    @Override
    public void onBackPressed() {
        quizTimer.purge();
        quizTimer.cancel();

        startActivity(new Intent(QuizActivity.this, MainActivity.class));
        finish();
    }

    private void randomShaker() {
        Random random = new Random();
        int x = random.nextInt(4);

        question.setText(getQuestionListList.get(currentQuestionPosition).getQuestion());
        switch (x) {
            case 1:
                option1.setText(getQuestionListList.get(currentQuestionPosition).getCorrectAnswer());
                option2.setText(getQuestionListList.get(currentQuestionPosition).getIncorrectAnswers().get(0));
                option3.setText(getQuestionListList.get(currentQuestionPosition).getIncorrectAnswers().get(1));
                option4.setText(getQuestionListList.get(currentQuestionPosition).getIncorrectAnswers().get(2));
                break;
            case 2:
                option4.setText(getQuestionListList.get(currentQuestionPosition).getCorrectAnswer());
                option1.setText(getQuestionListList.get(currentQuestionPosition).getIncorrectAnswers().get(0));
                option2.setText(getQuestionListList.get(currentQuestionPosition).getIncorrectAnswers().get(1));
                option3.setText(getQuestionListList.get(currentQuestionPosition).getIncorrectAnswers().get(2));
                break;

            case 3:
                option3.setText(getQuestionListList.get(currentQuestionPosition).getCorrectAnswer());
                option4.setText(getQuestionListList.get(currentQuestionPosition).getIncorrectAnswers().get(0));
                option1.setText(getQuestionListList.get(currentQuestionPosition).getIncorrectAnswers().get(1));
                option2.setText(getQuestionListList.get(currentQuestionPosition).getIncorrectAnswers().get(2));
                break;

            case 0:
                option2.setText(getQuestionListList.get(currentQuestionPosition).getCorrectAnswer());
                option3.setText(getQuestionListList.get(currentQuestionPosition).getIncorrectAnswers().get(0));
                option4.setText(getQuestionListList.get(currentQuestionPosition).getIncorrectAnswers().get(1));
                option1.setText(getQuestionListList.get(currentQuestionPosition).getIncorrectAnswers().get(2));
                break;
        }
    }

    private void isCorrect() {
        final String getAnswer = getQuestionListList.get(currentQuestionPosition).getCorrectAnswer();
        if (option1.getText().toString().equals(getAnswer)) {
            option1.setBackgroundResource(R.drawable.round_back_green);
            option1.setTextColor(Color.WHITE);
        } else if (option2.getText().toString().equals(getAnswer)) {
            option2.setBackgroundResource(R.drawable.round_back_green);
            option2.setTextColor(Color.WHITE);
        } else if (option3.getText().toString().equals(getAnswer)) {
            option3.setBackgroundResource(R.drawable.round_back_green);
            option3.setTextColor(Color.WHITE);
        } else if (option4.getText().toString().equals(getAnswer)) {
            option4.setBackgroundResource(R.drawable.round_back_green);
            option4.setTextColor(Color.WHITE);
        }

        if (selectedOptionByUser.equals(getAnswer)) {
            correctAnsw++;
        }
    }
}