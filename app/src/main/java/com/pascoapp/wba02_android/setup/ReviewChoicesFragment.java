package com.pascoapp.wba02_android.setup;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Programme;
import com.pascoapp.wba02_android.parseSubClasses.School;
import com.pascoapp.wba02_android.parseSubClasses.User;

public class ReviewChoicesFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = ReviewChoicesFragment.class.getSimpleName();
    public static boolean mIsVisible = false;

    private OnFragmentInteractionListener mListener;

    TextView voucherField;
    TextView schoolField;
    TextView programmeField;
    TextView levelField;
    TextView semesterField;
    Button submitButton;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference userRef;

    public ReviewChoicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_choices, container, false);

        voucherField = (TextView) view.findViewById(R.id.voucher);
        schoolField = (TextView) view.findViewById(R.id.school);
        programmeField = (TextView) view.findViewById(R.id.programme);
        levelField = (TextView) view.findViewById(R.id.level);
        semesterField = (TextView) view.findViewById(R.id.semester);
        submitButton = (Button) view.findViewById(R.id.submit_button);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        userRef = mDatabaseRef.child("users").child(User.getUid());

        submitButton.setOnClickListener(this);

        checkReviews();

        return view;
    }

    private void checkReviews() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                Log.d(TAG, user.toString());

                if (user.getVoucher() != null) voucherField.setText(user.getVoucher());
                else voucherField.setText("Not Set, please go back and enter a valid voucher number");

                if(user.getSchool() != null) {
                    DatabaseReference schoolRef = mDatabaseRef.child("schools").child(user.getSchool());
                    schoolRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            School school = dataSnapshot.getValue(School.class);
                            schoolField.setText(school.getName());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "getSchool:onCancelled", databaseError.toException());
                        }
                    });
                } else {
                    schoolField.setText("Not Set, please go back and select a school");
                }

                if(user.getProgramme() != null) {
                    DatabaseReference programmeRef = mDatabaseRef.child("programmes").child(user.getProgramme());
                    programmeRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Programme programme = dataSnapshot.getValue(Programme.class);
                            programmeField.setText(programme.getName());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    programmeField.setText("Not Set, please go back and select a programme");
                }

                if(user.getLevel() != null) levelField.setText("Year " + user.getLevel());
                else levelField.setText("Not Set, please go back and select a year");

                if(user.getSemester() > 0) semesterField.setText("Semester " + user.getSemester());
                else semesterField.setText("Not Set, please go back and select a semester");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisible = false;
        if(isVisibleToUser){
            checkReviews();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkReviews();
    }

    public void onSubmitButtonPressed() {
        if (mListener != null) {
            mListener.onSubmitStudentsChoices();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        onSubmitButtonPressed();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onSubmitStudentsChoices();
    }
}
