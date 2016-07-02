package com.pascoapp.wba02_android.takeTest;

import com.pascoapp.wba02_android.firebasePojos.Question;

import java.util.Comparator;

/**
 * Created by peter on 7/2/16.
 */
public class QuestionComparator implements Comparator<Question> {

    @Override
    public int compare(Question lhs, Question rhs) {
        /**
         * Sort by question number.
         * Since question numbers are strings, sorting by lexicographical order
         * straight away will make 10 appear before 2 because the comparison
         * is character by character and the first character of 10 is smaller than
         * the first character of 2.
         *
         * To fix this, we first check the lengths of the question numbers to see if
         * they are equal. If they are then we sort lexicographically, if they are
         * not, then the number with the greater length is the bigger number
         */

        // Compare the lengths of the Strings
        if (lhs.getNumber().length() < rhs.getNumber().length()) {
            return -1;
        } else if (lhs.getNumber().length() > rhs.getNumber().length()) {
            return 1;
        } else {
            // Lengths are equal e.g. ("10", "16") so
            // compare lexicographically
            return lhs.getNumber().compareTo(rhs.getNumber());
        }
    }
}
