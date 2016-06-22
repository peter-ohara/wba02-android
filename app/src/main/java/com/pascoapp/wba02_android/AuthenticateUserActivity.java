package com.pascoapp.wba02_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.main.MainActivity;
import com.pascoapp.wba02_android.parseSubClasses.User;
import com.pascoapp.wba02_android.setup.SetupWizardActivity;

public class AuthenticateUserActivity extends AppCompatActivity {

    private static final String TAG = AuthenticateUserActivity.class.getSimpleName();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_user);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = databaseRef.child("users");
        usersRef.child(User.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                validateUser(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });
    }

    private void validateUser(User user) {
        Log.d(TAG, user.toString());
        if (user.getVoucher() == null) {
            goToSetupWizard(SetupWizardActivity.ENTER_VOUCHER_PAGE);
        } else if (user.getSchool() == null) {
            goToSetupWizard(SetupWizardActivity.CHOOSE_SCHOOL_PAGE);
        } else if (user.getProgramme() == null) {
            goToSetupWizard(SetupWizardActivity.CHOOSE_PROGRAMME_PAGE);
        } else if (user.getLevel() == null) {
            goToSetupWizard(SetupWizardActivity.CHOOSE_LEVEL_PAGE);
        } else if (user.getSemester() == null) {
            goToSetupWizard(SetupWizardActivity.CHOOSE_SEMESTER_PAGE);
        } else {
            // TODO: Implement proper voucher verification
            goToMainActivity();
        }

        finish();
    }

    private void goToSetupWizard(int page) {
        Intent intent = new Intent(getApplicationContext(), SetupWizardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(SetupWizardActivity.EXTRA_PAGE, page);
        startActivity(intent);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
