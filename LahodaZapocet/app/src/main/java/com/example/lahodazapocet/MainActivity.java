package com.example.lahodazapocet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<QuestionList> questionListList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button startNewQuiz = findViewById(R.id.newGame);
        final Button statistic = findViewById(R.id.statistic);
        final Button exit = findViewById(R.id.exit);

        questionListList = new ArrayList<>();
        getQuestions();

        startNewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("LIST", (Serializable) questionListList);
                startActivity(intent);
            }
        });

        statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuizStatistic.class);
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAffinity(MainActivity.this);
            }
        });
    }

    public void getQuestions() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://the-trivia-api.com/api/questions?categories=arts_and_literature,film_and_tv,food_and_drink,general_knowledge,geography,history,music,science,society_and_culture,sport_and_leisure&limit=10";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray mJsonArray = new JSONArray(response);

                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject mJsonObject = mJsonArray.getJSONObject(i);

                        QuestionList questionList = new QuestionList(
                                mJsonObject.getString("category"),
                                mJsonObject.getString("id"),
                                mJsonObject.getString("correctAnswer"),
                                getListPropertieAnsw(mJsonObject, "incorrectAnswers"),
                                mJsonObject.getString("question"),
                                getListPropertieAnsw(mJsonObject, "tags"),
                                mJsonObject.getString("type"),
                                mJsonObject.getString("difficulty"),
                                getListPropertieAnsw(mJsonObject, "regions"),
                                mJsonObject.getString("difficulty").equals("true")
                        );
                        questionListList.add(questionList);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

    private List<String> getListPropertieAnsw(JSONObject object, String namePropertie) {

        List<String> list = new ArrayList<>();

        JSONArray mJsonArrayProperty = null;
        try {
            mJsonArrayProperty = object.getJSONArray(namePropertie);
            for (int i = 0; i < mJsonArrayProperty.length(); i++) {
                String mJsonObjectProperty = mJsonArrayProperty.getString(i);
                list.add(mJsonObjectProperty);
            }

            return list;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}