package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.comments.Comments;
import com.pascoapp.wba02_android.services.questions.Question;
import com.pascoapp.wba02_android.services.questions.Questions;
import com.wang.avi.AVLoadingIndicatorView;
import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class McqFragment extends Fragment {

    private static final String TAG = McqFragment.class.getSimpleName();

    // the fragment initialization parameters
    private static final String ARG_QUESTION_KEY = "com.pascoapp.wba02_android.questionKey";

    // Member Variables related to the questionKey
    private String questionKey;
    private Map<String, Integer> answerHash = new HashMap<>();

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loadingIndicator;

    private Question question;

    public static McqFragment newInstance(Question question) {
        McqFragment fragment = new McqFragment();
        Bundle args = new Bundle();

        args.putString(ARG_QUESTION_KEY, question.getKey());
        fragment.setArguments(args);

        return fragment;
    }

    public McqFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionKey = getArguments().getString(ARG_QUESTION_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mcq, container, false);
        ButterKnife.bind(this, view);

        refreshData(questionKey);

        return view;
    }

    private void refreshData(String questionKey) {
        loadingIndicator.show();
        Questions.fetchQuestion(questionKey)
                .subscribe(fetchedQuestion -> {
                    loadingIndicator.hide();
                    question = fetchedQuestion;
                    loadItemInWebView(getActivity(), webview, question);
                    setAnswerHash(question);
                }, throwable -> {
                    loadingIndicator.hide();
                    Log.d(TAG, "refreshData: " + throwable.getMessage());

                    // TODO: Show error message with retry button inside R.layout.fragment_mcq
                    // rather than with a Snackbar cos the snackbar makes it hard to find which
                    // question produced the error
                    Snackbar.make(webview, throwable.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData(questionKey))
                            .show();
                });
    }

    private void setUpCommentListener(DiscussionInterfaceToWebview discussionInterfaceToWebview) {
        String questionKey = discussionInterfaceToWebview.getQuestionKey();
        Query commentsQuery = Comments.COMMENTS_REF.child(questionKey);
        ChildEventListener commentEventListener
                = Comments.createCommentEventListener(getContext(), webview,
                    discussionInterfaceToWebview, user);
        commentsQuery.addChildEventListener(commentEventListener);
    }

    private void setAnswerHash(Question question) {
        // Fetch users previous answer
        for (Map.Entry<String, String> entry : question.getChoices().entrySet())
        {
            answerHash.put(entry.getKey(), 0);
        }
        if (question.getSubmittedAnswers() != null) {
            for (Map.Entry<String, String> entry : question.getSubmittedAnswers().entrySet())
            {
                if (entry.getKey().equals(user.getUid())) {
                    continue;
                }

                if (answerHash.get(entry.getValue()) != null) {
                    answerHash.put(entry.getValue(), answerHash.get(entry.getValue()) + 1);
                }
            }
        }
    }

    private String getHtml(Question question) {
        AndroidTemplates loader = new AndroidTemplates(getContext());
        Theme theme = new Theme(loader);
        Chunk chunk = theme.makeChunk("mcq");
        chunk.set("question", question.getQuestion());
        chunk.set("choices", question.getChoices());

        // Fetch users previous answer
        if (question.getSubmittedAnswers() != null) {
            String usersAnswer = question.getSubmittedAnswers().get(user.getUid());
            chunk.set("usersAnswer", usersAnswer);
        }


        if (user.getPhotoUrl() != null) {
            chunk.set("photoUrl", "\"" + user.getPhotoUrl() + "\"");
        } else {
            chunk.set("photoUrl", "null");
        }

        return chunk.toString();
    }

    public void loadItemInWebView(Context context, WebView w, Question question) {
        w.getSettings().setJavaScriptEnabled(true);
        w.setBackgroundColor(Color.TRANSPARENT);

        DiscussionInterfaceToWebview discussionInterfaceToWebview
                = new DiscussionInterfaceToWebview(McqFragment.this, question.getKey());
        w.addJavascriptInterface(new McqWebAppInterface(), "McqAndroid");
        w.addJavascriptInterface(discussionInterfaceToWebview, "Android");

        w.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setUpCommentListener(discussionInterfaceToWebview);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        });

        String mime = "text/html";
        String encoding = "utf-8";
        String baseURL = "file:///android_res/raw/";
        w.loadDataWithBaseURL(baseURL, getHtml(question), mime, encoding, null);
    }


    private class McqWebAppInterface {

        @JavascriptInterface
        public void checkMcq(String result) {

            // create or update question's submittedAnswers with this users answer
            Helpers.getDatabaseInstance().getReference().child(Questions.QUESTIONS_KEY)
                    .child(questionKey).child("submittedAnswers").child(user.getUid())
                    .setValue(result);

            CheckAnswerDialogFragment checkAnswerDialogFragment
                    = CheckAnswerDialogFragment.newInstance("Answers", answerHash);
            checkAnswerDialogFragment.show(getChildFragmentManager(), "CheckAnswerDialog");
        }
    }

}
