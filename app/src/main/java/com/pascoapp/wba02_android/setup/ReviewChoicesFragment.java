package com.pascoapp.wba02_android.setup;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Programme;
import com.pascoapp.wba02_android.parseSubClasses.School;
import com.pascoapp.wba02_android.parseSubClasses.Student;

public class ReviewChoicesFragment extends Fragment implements View.OnClickListener {

    public static boolean mIsVisible = false;

    private OnFragmentInteractionListener mListener;
    private Student mStudent;

    TextView voucherField;
    TextView schoolField;
    TextView programmeField;
    TextView levelField;
    TextView semesterField;
    Button submitButton;

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

        submitButton.setOnClickListener(this);

        mStudent = Student.getCurrentUser();

        System.out.println(mStudent.getVoucher());
        System.out.println(mStudent.getLevel());
        System.out.println(mStudent.getSemester());

        return view;
    }

    public void checkReviews(){
        if (mStudent.getVoucher() != null) voucherField.setText(mStudent.getVoucher());
        else voucherField.setText("Not Set, please go back and enter a valid voucher number");

        if(mStudent.getSchool() != null) schoolField.setText(mStudent.getSchool().getName());
        else schoolField.setText("Not Set, please go back and select a school");

        if(mStudent.getProgramme() != null) programmeField.setText(mStudent.getProgramme().getName());
        else programmeField.setText("Not Set, please go back and select a programme");

        if(mStudent.getLevel() != null) levelField.setText("Year " + mStudent.getLevel());
        else levelField.setText("Not Set, please go back and select a year");

        if(mStudent.getSemester() > 0) semesterField.setText("Semester " + mStudent.getSemester());
        else semesterField.setText("Not Set, please go back and select a semester");
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

    public void onSubmitButtonPressed(Student student) {
        if (mListener != null) {
            mListener.onSubmitStudentsChoices(student);
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
        onSubmitButtonPressed(mStudent);
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
        void onSubmitStudentsChoices(Student student);
    }
}
