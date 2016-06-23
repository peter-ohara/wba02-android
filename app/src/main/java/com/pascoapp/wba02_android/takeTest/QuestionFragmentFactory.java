package com.pascoapp.wba02_android.takeTest;

import com.pascoapp.wba02_android.firebasePojos.Question;

/**
 * Generates appropriate Fragment for question
 */
public class QuestionFragmentFactory {

    public static QuestionFragment getQuestionFragment(Question question) {

        if (question.getType().equalsIgnoreCase("mcq")) {
            return McqFragment.newInstance(question);
        } else if (question.getType().equalsIgnoreCase("fillIn")) {
            return FillInFragment.newInstance(question);
        } else if (question.getType().equalsIgnoreCase("essay")) {
            return EssayFragment.newInstance(question);
        }

        return null;
    }
}
