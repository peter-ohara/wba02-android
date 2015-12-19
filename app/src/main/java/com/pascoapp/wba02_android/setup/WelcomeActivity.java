package com.pascoapp.wba02_android.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.pascoapp.wba02_android.main.ChooseTestActivity;
import com.pascoapp.wba02_android.R;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

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
        // TODO: Set Demo Parameters
        String ELECTRICAL_ENGINEERING_PR0GRAMME_ID = "4G7KJXH4mY";
        Intent intent = new Intent(WelcomeActivity.this, ChooseTestActivity.class);
        intent.putExtra(ChooseTestActivity.EXTRA_PROGRAMME_ID, ELECTRICAL_ENGINEERING_PR0GRAMME_ID);
        startActivity(intent);
    }

}
