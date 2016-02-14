package com.example.joakim.mattekampen;

import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends ActionBarActivity {
    private static List<Question> questions = new ArrayList<Question>();
    private static TextView questionNumber, questionLine, option1, option2, option3, option4, correctCounter, wrongCounter;
    private static Question q;
    private static int numberOfQuestions = 10, numberOfQuestionsInDataBase;
    private static Random rn = new Random();
    private static MediaPlayer correctSound, wrongSound,finishSound;
    public static User user = new User("NAME");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    // Det första som sker när aktiviteten startas
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        // Sätter utseendet till vår XML-fil
        populateQuestions();
        // Skapa frågor från fil och lägg i variabeln questions
        initUI();
        // Hämta hem komponenter från layout-filen
        generateNewQuestion(null);
        // Genererera den första frågan
    }

    private void populateQuestions(){
        InputStream in;
        AssetManager assetManager = getAssets();
        try {
            in = assetManager.open("quizzes.txt");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            String text = new String(buffer);
            String[] q = text.split("###\n");
            for (String question  : q){
                List<String> questionPaste = new ArrayList<String>(Arrays.asList(question.split("\n")));
                Question newQ = new Question(questionPaste);
                questions.add(newQ);
            }
            Collections.shuffle(questions);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        questionNumber = (TextView) findViewById(R.id.questionNumber);
        questionLine = (TextView) findViewById(R.id.questionLine);
        option1 = (TextView) findViewById(R.id.option1);
        option2 = (TextView) findViewById(R.id.option2);
        option3 = (TextView) findViewById(R.id.option3);
        option4 = (TextView) findViewById(R.id.option4);
        correctCounter = (TextView) findViewById(R.id.correctCounter);
        wrongCounter = (TextView) findViewById(R.id.wrongCounter);
        numberOfQuestionsInDataBase = questions.size();
        correctSound = MediaPlayer.create(this, R.raw.correct);
        wrongSound = MediaPlayer.create(this, R.raw.wrong);
        finishSound = MediaPlayer.create(this, R.raw.finish);
    }

    public void generateNewQuestion(View answer) {
        // Denna metod körs varje gång en knapp/view trycks
        if (answer == null) {
            // Om det är spelets första fråga
            // do nothing.
        } else if (q.getCorrectAnswer().equals(((TextView) answer).getText())) {
            // Om användaren svarade rätt
            user.correct();
            makeToast(true);
        } else {
            // Om användaren svarade fel
            user.wrong();
            makeToast(false);
        }
        updateView();

        if (questions.size() < numberOfQuestionsInDataBase - numberOfQuestions) {
            // Om detta var den sista frågan
            try{
                Thread.currentThread().sleep(1000);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finishSound.start();
            questionNumber.setText("");
            questionLine.setTextSize(25);
            questionLine.setText("Slut på frågor");
            option1.setText("");
            option2.setText("");
            option3.setTextColor(getResources().getColor(R.color.silver));
            option3.setText(Tools.getUnderlinedText("START NEW GAME"));
            option3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(getIntent());
                }
            });
            option4.setTextColor(getResources().getColor(R.color.silver));
            option4.setText(Tools.getUnderlinedText("SEE HISTORY"));
            option4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent seeHistory = new Intent(MainActivity.this, SeeHistoryActivity.class);
                    startActivity(seeHistory);
                }
            });
            correctCounter.setText(Integer.toString(user.getNumberOfCorrect()));
            wrongCounter.setText(Integer.toString(user.getNumberOfWrong()));
            Toast toast = Toast.makeText(this, user.getPercentage()+ "% rätt", Toast.LENGTH_LONG);
            toast.show();
            user.addResult();
        }
    }

    public static void updateView(){
        questionNumber.setText("Fråga " + Integer.toString(user.getNumberOfCorrect() + user.getNumberOfWrong() + 1) + " av " + Integer.toString(numberOfQuestions));
        q = questions.get(0);
        // Hämta ny fråga
        questions.remove(0);
        questionLine.setTextSize(Tools.calculateTextSize(q.getQuestionLine()));
        // Preparerar den nya frågan beroende på hur lång questionLine är
        questionLine.setText(q.getQuestionLine());
        List<String> dummies = q.getDummies();
        dummies.add(rn.nextInt(4), q.getCorrectAnswer());
        TextView[] options = {option1, option2, option3, option4};
        int i = 0;
        for (TextView o : options)
            o.setText(dummies.get(i++));
        correctCounter.setText(Integer.toString(user.getNumberOfCorrect()));
        wrongCounter.setText(Integer.toString(user.getNumberOfWrong()));
    }

    public void makeToast(Boolean correct){
        Toast toast = new Toast(this);
        LayoutInflater myInflater = LayoutInflater.from(this);
        View view;
        if (correct) {
            correctSound.start();
            view = myInflater.inflate(R.layout.correct_toast, null);
        }
        else{
            wrongSound.start();
            view = myInflater.inflate(R.layout.wrong_toast, null);
        }
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, -160);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
