package com.pascoapp.wba02_android.takeTest;

/**
 * Created by peter on 7/1/16.
 */

public class QuestionTemplates {

    public static String mcqTemplate =
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <title>Question</title>\n" +
            "    <link rel=\"stylesheet\" href=\"style.css\">\n" +
            "    <script type=\"text/javascript\"\n" +
            "            async src=\"file:///android_asset/MathJax/MathJax.js?config=AM_CHTML-full\"></script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div class=\"question-page\">\n" +
            "    <div class=\"question-container\">\n" +
            "        <p>\n" +
            "            { question }\n" +
            "        </p>\n" +
            "    </div>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";


}
