package com.pascoapp.wba02_android.takeTest;

/**
 * Created by peter on 7/1/16.
 */

public class QuestionTemplates {

    public static String css =
            "body{\n"+
            "    margin:0px;\n"+
            "    font-family:lato, raleway;\n"+
            "    background:  #f9f9f9;\n"+
            "}\n"+
            "\n"+
            ".question-num{\n"+
            "    width:100%;\n"+
            "    height:30px;\n"+
            "    background:#eaeaea;\n"+
            "}\n"+
            ".question-page{\n"+
            "    width:100%;\n"+
            "}\n"+
            ".card{\n"+
            "    box-shadow: 0px 0px 3px 1px #b2b2b2;\n"+
            "    width:auto;\n"+
            "    margin:10px 15px 0px;\n"+
            "}\n"+
            ".question-container{\n"+
            "    height:auto;\n"+
            "}\n"+
            ".question-container p{\n"+
            "    font-size:20px;\n"+
            "    color:#575757;\n"+
            "    padding:20px;\n"+
            "}\n"+
            ".fill-in{\n"+
            "    border:none;\n"+
            "    border-bottom:1px solid #575757;\n"+
            "    width:150px;\n"+
            "    height:30px;\n"+
            "    font-size:20px;\n"+
            "    color:#575757;\n"+
            "}\n"+
            ".fill-in:focus{\n"+
            "    border:none;\n"+
            "}\n"+
            ".options-container{\n"+
            "    height: 255px;\n"+
            "}\n"+
            "textarea{\n"+
            "    width:100%;\n"+
            "    height:100%;\n"+
            "    font-size:20px;\n"+
            "    color:#575757;\n"+
            "}\n"+
            ".option{\n"+
            "    float:left;\n"+
            "}\n"+
            ".option-input{\n"+
            "    margin:25px 20px 0px;\n"+
            "}\n"+
            ".option-text{\n"+
            "    color:#575757;\n"+
            "    font-size:20px;\n"+
            "    font-weight:100;\n"+
            "    margin:20px 0px 0px;\n"+
            "}\n"+
            "fieldset{\n"+
            "    border:none;\n"+
            "}\n"+
            ".fieldset{\n"+
            "    border:none;\n"+
            "    height:auto;\n"+
            "    min-height:60px;\n"+
            "    border-bottom:1px solid #cbcbcb;\n"+
            "    \n"+
            "}\n"+
            ".fieldset:nth-last-of-type(1){\n"+
            "    border:none;\n"+
            "}\n"+
            ".answer-question{\n"+
            "    height:50px;\n"+
            "    width:auto;\n"+
            "    margin:15px\n"+
            "}\n"+
            ".answer{\n"+
            "    color:#808080;\n"+
            "}\n"+
            ".answer-question p{\n"+
            "    text-align:right;\n"+
            "    color:#ff5800;\n"+
            "    font-size:20px;\n"+
            "}\n"+
            ".submit{\n"+
            "    width:100%;\n"+
            "    background:#21c3f3;\n"+
            "    border:none;\n"+
            "    height:50px;\n"+
            "    color:white;\n"+
            "    font-size:20px;\n"+
            "    //box-shadow: 0px 0px 3px 1px #b2b2b2;\n"+
            "}\n"+
            ".control-bars{\n"+
            "    width:100%;\n"+
            "    height:60px;\n"+
            "    background:#009688;\n"+
            "}\n";

    public static String mcqTemplate =
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <title>Question</title>\n" +
                    "    <style>" + css + "</style>\n" +
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

    public static String fillInTemplate =
            "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <title>Question</title>\n" +
                    "    <style>" + css + "</style>\n" +
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

    public static String essayTemplate =
            "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <title>Question</title>\n" +
                    "    <style>" + css + "</style>\n" +
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
