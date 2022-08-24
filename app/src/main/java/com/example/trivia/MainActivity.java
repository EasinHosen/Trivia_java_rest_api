package com.example.trivia;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionTextView, questionCounterTextView;

    private int currentQuestionIndex = 0;

    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button trueButton = findViewById(R.id.trueButton);
        Button falseButton = findViewById(R.id.falseButton);
        ImageButton nextButton = findViewById(R.id.nextButton);
        ImageButton prevButton = findViewById(R.id.prevButton);
        questionCounterTextView = findViewById(R.id.question_counter);
        questionTextView = findViewById(R.id.question_textView);

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);

         questionList = new QuestionBank().getQuestions(questionArrayList -> {
             int totalQuestion = questionList.size()+1;
             questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
             questionCounterTextView.setText(currentQuestionIndex+ 1 + " out of " + totalQuestion);
//                Log.d("Main", "Process " + questionArrayList);
         });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prevButton:
                if(currentQuestionIndex > 0){
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    updateQuestion();
                }
                break;
            case R.id.nextButton:
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                updateQuestion();
                break;
            case R.id.trueButton:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.falseButton:
                checkAnswer(false);
                updateQuestion();
                break;
        }
    }

    private void checkAnswer(boolean userChoose) {
        boolean actualAnswer = questionList.get(currentQuestionIndex).isAnswerTrue();
        int toastMessageId;

        if(userChoose == actualAnswer){
            toastMessageId = R.string.correct_answer;
            onCorrectAnimation();
//            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
//            updateQuestion();
        } else {
            shakeAnimation();
            toastMessageId = R.string.wrong_answer;
        }
        Toast.makeText(MainActivity.this, toastMessageId, Toast.LENGTH_SHORT).show();
    }

    private void onCorrectAnimation() {
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);

        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.LTGRAY);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextView.setText(question);
        questionCounterTextView.setText(currentQuestionIndex + 1 + " out of " + questionList.size());
    }

    private void shakeAnimation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.LTGRAY);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}