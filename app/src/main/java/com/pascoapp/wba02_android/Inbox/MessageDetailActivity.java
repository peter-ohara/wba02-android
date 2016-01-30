package com.pascoapp.wba02_android.Inbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

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
                mProgressView.setVisibility(View.GONE);
                if (e == null) {
                    mMessage = object;
                    loadItemInWebView(mMessage, webview);
                } else {
                    Snackbar.make(coordinatorLayoutView,
                            e.getCode() + " : " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadItemInWebView(ParseObject item, WebView webview) {
        String content = item.getString("content");

        // Now we do some html magic
        String title = mMessage.getString("title");
        String HTMLString = "<h3 style='text-align:center;'>" + title + "</h3>";
        HTMLString += "<hr>";
        HTMLString += content;

        // Load the edited string
        webview.loadDataWithBaseURL("", HTMLString, "text/html", "UTF-8", "");
    }
}
