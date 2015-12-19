package com.pascoapp.wba02_android.takeTest;

import com.pascoapp.wba02_android.parseSubClasses.Question;

/**
 * Generates appropriate Fragment for question
 */
public class QuestionFragmentFactory {

    public static QuestionFragment getQuestionFragment(Question question) {
        String questionId = question.getObjectId();

        if (question.getType().equalsIgnoreCase("mcq")) {
            return McqFragment.newInstance(questionId);
        } else if (question.getType().equalsIgnoreCase("fillIn")) {
            return FillInFragment.newInstance(questionId);
        } else if (question.getType().equalsIgnoreCase("essay")) {
            return EssayFragment.newInstance(questionId);
        }

        return null;
    }
}
