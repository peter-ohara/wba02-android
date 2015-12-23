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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReviewChoicesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReviewChoicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewChoicesFragment extends Fragment implements View.OnClickListener {

    public static boolean mIsVisible = false;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewChoicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewChoicesFragment newInstance(String param1, String param2) {
        ReviewChoicesFragment fragment = new ReviewChoicesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        voucherField.setText(String.valueOf(mStudent.getVoucher()));
        ///test
        if(mStudent.getSchool() != null)
            schoolField.setText(((School)mStudent.getSchool()).getName());
        else
            schoolField.setText("Not Set, please go back and select a school");

        if(mStudent.getProgramme() != null)
            programmeField.setText(((Programme)mStudent.getProgramme()).getName());
        else
            programmeField.setText("Not Set, please go back and select a programme");

        if(mStudent.getLevel() != null)
            levelField.setText("Year " + String.valueOf(mStudent.getLevel().toString()));
        else
            levelField.setText("Not Set, please go back and select a year");

        if(mStudent.getSemester() > 0)
            semesterField.setText("Semester " + String.valueOf(mStudent.getSemester().toString()));
        else
            semesterField.setText("Not Set, please go back and select a semester");
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
            mListener.onSubmitInteraction(student);
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
        void onSubmitInteraction(Student student);
    }
}
