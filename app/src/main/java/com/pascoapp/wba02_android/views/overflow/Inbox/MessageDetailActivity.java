package com.pascoapp.wba02_android.views.overflow.Inbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.messages.Message;

/**
 * An activity representing a single message detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MessageListActivity}.
 */
public class MessageDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE_ID = "message_id";
    public static final String MESSAGE_CLASSNAME = "Message";
    private View coordinatorLayoutView;
    private WebView webview;
    private View mProgressView;
    private DatabaseReference mMessageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coordinatorLayoutView = findViewById(R.id.snackbarPosition);
        mProgressView = findViewById(R.id.login_progress);
        webview = (WebView) this.findViewById(R.id.webview);

        webview.getSettings().setJavaScriptEnabled(true);

        Intent intent = getIntent();
        String messageKey = intent.getStringExtra(EXTRA_MESSAGE_ID);

        mProgressView.setVisibility(View.VISIBLE);

        mMessageRef = FirebaseDatabase.getInstance().getReference()
                .child("messages").child(messageKey);

        mMessageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                loadItemInWebView(message, webview);
                mProgressView.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(coordinatorLayoutView,
                        databaseError.getCode() + " : " + databaseError.getMessage(),
                        Snackbar.LENGTH_SHORT).show();
                mProgressView.setVisibility(View.GONE);
            }
        });
    }

    private void loadItemInWebView(Message message, final WebView w) {
        String title = message.getTitle();
        String content = message.getContent();

        String HTMLString = String.format(
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<title>%s</title>" +
                        "<style>" +
                        "    h3 {" +
                        "        text-align:center;" +
                        "        font-weight:100;" +
                        "    }" +
                        "    a {" +
                        "        text-decoration: none;" +
                        "        color: #ff0093;" +
                        "    }" +
                        "    hr {" +
                        "        border: 0;" +
                        "        height: 0;" +
                        "        border-top: 1px solid rgba(0, 0, 0, 0.1);" +
                        "        border-bottom: 1px solid rgba(255, 255, 255, 0.3);" +
                        "    }" +
                        "</style>" +
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
