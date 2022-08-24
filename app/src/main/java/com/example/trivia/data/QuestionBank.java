package com.example.trivia.data;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    ArrayList<Question> questionArrayList = new ArrayList<>();

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack){

        String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(
                Request.Method.GET,
                url,
                (JSONArray) null,
                response -> {
                    for(int i=0; i<response.length(); i++){
                        try {
                            Question question = new Question();
                            question.setAnswer(response.getJSONArray(i).get(0).toString());
                            question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));
                            questionArrayList.add(question);        //add question to list
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(callBack != null){
                            callBack.processFinished(questionArrayList);
                        }
                    }
                }, error -> {

                });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }

}
