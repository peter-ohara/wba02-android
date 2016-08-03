package com.pascoapp.wba02_android.takeTest.TestSection;

import java.text.MessageFormat;

/**
 * Created by peter on 7/1/16.
 */

public class QuestionTemplates {

    private static String css =
            "body{\n"+
                    "    margin:0px;\n"+
                    "    font-family:lato, raleway;\n"+
                    "    overflow-x:hidden;\n" +
                    "        width:100%;\n" +
                    "        -webkit-box-sizing: border-box;\n" +
                    "           -moz-box-sizing: border-box;\n" +
                    "                box-sizing: border-box;"+
                    "}\n"+
                    "\n"+
                    ".questionKey-num{\n"+
                    "    width:100%;\n"+
                    "    height:30px;\n"+
                    "    background:#eaeaea;\n"+
                    "}\n"+
                    ".questionKey-page{\n"+
                    "    width:100%;\n"+
                    "}\n"+
                    ".card{\n"+
                    "    box-shadow: 0px 0px 3px 1px #b2b2b2;\n"+
                    "    width:auto;\n"+
                    "    margin:10px 15px 0px;\n"+
                    "}\n"+
                    ".questionKey-container{\n"+
                    "    height:auto;\n"+
                    "}\n"+
                    ".questionKey-container p{\n"+
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
                    ".answer-questionKey{\n"+
                    "    height:50px;\n"+
                    "    width:auto;\n"+
                    "    margin:15px\n"+
                    "}\n"+
                    ".answer{\n"+
                    "    color:#808080;\n"+
                    "}\n"+
                    ".answer-questionKey p{\n"+
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

    private static String index =
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <title>Question</title>\n" +
            "    <meta name=\"viewport\" content=\"userKey-scalable=no, width=device-width, initial-scale=1.0\" />\n" +
            "    <meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />\n" +
            "    <link rel=\"stylesheet\" href=\"file:///android_asset/katex/katex.min.css\">\n" +
            "    <style> {0} </style>\n" +
            "    <script type=\"text/javascript\"\n" +
            "            async src=\"file:///android_asset/MathJax/MathJax.js?config=AM_CHTML-full\"></script>\n" +
            "</head>\n" +
            "<body>\n" +
            "{1}" +
            "</body>\n" +
            "<script src=\"file:///android_asset/ASCIIMathTeXKaTeX-2016-06-17.min.js\"></script> \n" +
            "<script src=\"file:///android_asset/katex/katex.min.js\"></script> \n" +
            "<script>if(typeof(katex)!=\"undefined\") AMfunc(true); </script> \n" +
            "</html>";

    private static String mcqDiv =
            "<div class=\"questionKey-page\">\n" +
            "    <div class=\"questionKey-container\">\n" +
            "        <p>\n" +
            "            { questionKey }\n" +
            "        </p>\n" +
            "    </div>\n" +
            "</div>\n";

    private static String fillInDiv =
            "<div class=\"questionKey-page\">\n" +
            "    <div class=\"questionKey-container\">\n" +
            "        <p>\n" +
            "            { questionKey }\n" +
            "        </p>\n" +
            "    </div>\n" +
            "</div>\n";

    private static String essayDiv =
            "<div class=\"questionKey-page\">\n" +
            "    <div class=\"questionKey-container\">\n" +
            "        <p>\n" +
            "            { questionKey }\n" +
            "        </p>\n" +
            "    </div>\n" +
            "</div>\n";

    public static String mcqTemplate = MessageFormat.format(index, css, mcqDiv);
    public static String fillInTemplate = MessageFormat.format(index, css, fillInDiv);
    public static String essayTemplate = MessageFormat.format(index, css, essayDiv);
}
