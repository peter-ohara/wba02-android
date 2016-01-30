package com.pascoapp.wba02_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.crashlytics.android.Crashlytics;
import com.pascoapp.wba02_android.main.MainActivity;
import com.pascoapp.wba02_android.parseSubClasses.Student;
import com.pascoapp.wba02_android.setup.SetupWizardActivity;
import com.pascoapp.wba02_android.setup.WelcomeActivity;

public class AuthenticateUserActivity extends AppCompatActivity {

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        student = Student.getCurrentUser();

        // Crashlytics
        logUser();


        if (student.getSchool() == null) {
            goToSetupWizard(SetupWizardActivity.CHOOSE_SCHOOL_PAGE);
        } else if (student.getProgramme() == null) {
            goToSetupWizard(SetupWizardActivity.CHOOSE_PROGRAMME_PAGE);
        } else if (student.getLevel() == null) {
            goToSetupWizard(SetupWizardActivity.CHOOSE_LEVEL_PAGE);
        } else if (student.getSemester() == null) {
            goToSetupWizard(SetupWizardActivity.CHOOSE_SEMESTER_PAGE);
        } else if (student.getVoucher() == null) {
            goToWelcomeActivity();
        } else {
            // TODO: Implement proper voucher verification

            goToMainActivity();
        }

        finish();
    }

    private void logUser() {
        Crashlytics.setUserEmail(student.getEmail());
        Crashlytics.setUserName(student.getFullName());
    }


    private void goToMainActivity() {
        Intent intent = new Intent(AuthenticateUserActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void goToWelcomeActivity() {
        Intent intent = new Intent(AuthenticateUserActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

    private void goToSetupWizard(int page) {
        Intent intent = new Intent(AuthenticateUserActivity.this, SetupWizardActivity.class);
        intent.putExtra(SetupWizardActivity.EXTRA_PAGE, page);
        startActivity(intent);
    }

}
