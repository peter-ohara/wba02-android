package com.pascoapp.wba02_android.Inbox;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Message;

/**
 * An activity representing a single message detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MessageListActivity}.
 */
public class MessageDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE_ID = "message_id";
    public static final String MESSAGE_CLASSNAME = "Message";
    private ParseObject mMessage;
    private View coordinatorLayoutView;
    private WebView webview;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coordinatorLayoutView = findViewById(R.id.snackbarPosition);
        mProgressView = findViewById(R.id.login_progress);
        webview = (WebView)this.findViewById(R.id.webview);

        webview.getSettings().setJavaScriptEnabled(true);

        Intent intent = getIntent();
        String guideId = intent.getStringExtra(EXTRA_MESSAGE_ID);

        mProgressView.setVisibility(View.VISIBLE);
        ParseQuery<Message> query = Message.getQuery();
        query.getInBackground(guideId, new GetCallback<Message>() {
            public void done(Message object, ParseException e) {
                if (e == null) {
                    mMessage = object;
                    loadItemInWebView(mMessage, webview);
                    mProgressView.setVisibility(View.GONE);
                } else if (e.getCode() == 120) {
                    // Result not cached Error. Ignore it
                } else {
                    Snackbar.make(coordinatorLayoutView,
                            e.getCode() + " : " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    mProgressView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadItemInWebView(ParseObject item, final WebView w) {
        String title = item.getString("title");
        String content = item.getString("content");

        String HTMLString = String.format(
                "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>%s</title>" +
                "<style>"                           +
                "    h3 {"                          +
                "        text-align:center;"        +
                "        font-weight:100;"          +
                "    }"                             +
                "    a {"                           +
                "        text-decoration: none;"    +
                "        color: #ff0093;"           +
                "    }"                             +
                "    hr {"                          +
                "        border: 0;"                +
                "        height: 0;"                +
                "        border-top: 1px solid rgba(0, 0, 0, 0.1);"             +
                "        border-bottom: 1px solid rgba(255, 255, 255, 0.3);"    +
                "    }"                             +
                "</style>"                          +
                "<script type=\"text/javascript\" async " +
                "  src=\"file:///android_asset/MathJax/MathJax.js?config=AM_CHTML-full\"></script>" +
                "</head>" +
                "<body>" +
                "<h3>%s</h3>" +
                "<hr>" +
                "<p>%s</p>" +
                "</body>" +
                "</html>", title, title, content);

        w.getSettings().setJavaScriptEnabled(true);
        w.loadDataWithBaseURL("http://bar", HTMLString, "text/html", "utf-8", "");
    }
}
