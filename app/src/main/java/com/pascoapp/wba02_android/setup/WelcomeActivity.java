package com.pascoapp.wba02_android.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.pascoapp.wba02_android.main.MainActivity;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Programme;
import com.pascoapp.wba02_android.parseSubClasses.School;
import com.pascoapp.wba02_android.parseSubClasses.Student;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String KNUST_ID = "3xC8GeiRik";
    public static final String ELECTRICAL_ENGINEERING_ID = "4G7KJXH4mY";

    Button activateScratchCardButton, demoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activateScratchCardButton = (Button)findViewById(R.id.scratch_card_button);
        activateScratchCardButton.setOnClickListener(this);
        demoButton = (Button)findViewById(R.id.demo_button);
        demoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scratch_card_button:
                openScratchCard();
                break;
            case R.id.demo_button:
                openDemo();
                break;
        }
    }

    void openScratchCard(){
        startActivity(new Intent(WelcomeActivity.this, SetupWizardActivity.class));
    }

    void openDemo() {
        setDemoParameters();

        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void setDemoParameters() {
        School school = ParseObject.createWithoutData(School.class, KNUST_ID);
        Programme programme = ParseObject.createWithoutData(Programme.class, ELECTRICAL_ENGINEERING_ID);
        Integer level = 2;
        Integer semester = 1;

        Student student = (Student) ParseUser.getCurrentUser();
        student.setSchool(school);
        student.setProgramme(programme);
        student.setLevel(level);
        student.setSemester(semester);
        // Do not save this modified user, we want the changes to be lost on app shutdown
    }

}
