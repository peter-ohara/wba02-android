package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.questions.Question;
import com.pascoapp.wba02_android.services.questions.Questions;
import com.pascoapp.wba02_android.views.WebviewActivity;
import com.pascoapp.wba02_android.views.discussion.DiscussionActivity;
import com.pascoapp.wba02_android.views.main.MainActivity;
import com.wang.avi.AVLoadingIndicatorView;
import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

public class McqFragment extends Fragment {

    // the fragment initialization parameters
    private static final String ARG_QUESTION_KEY = "com.pascoapp.wba02_android.questionKey";

    // Member Variables related to the questionKey
    private String questionKey;
    private Map<String, Integer> answerHash;


    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @BindView(R.id.webview)
    WebView webview;

    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loadingIndicator;

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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fetchedQuestion -> {
                    loadingIndicator.hide();
                    loadItemInWebView(getActivity(), webview, fetchedQuestion);
                    setAnswerHash(fetchedQuestion);
                }, throwable -> {
                    loadingIndicator.hide();
                    Snackbar.make(webview, throwable.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData(questionKey))
                            .show();
                });
    }

    private void setAnswerHash(Question question) {
        // Fetch users previous answer
        if (question.getSubmittedAnswers() != null) {
            answerHash = new HashMap<>();
            for (Map.Entry<String, String> entry : question.getSubmittedAnswers().entrySet())
            {
//                if (entry.getKey().equals(user.getUid())) {
//                    continue;
//                }

                if (answerHash.get(entry.getValue()) != null) {
                    answerHash.put(entry.getValue(), answerHash.get(entry.getValue()) + 1);
                } else {
                    answerHash.put(entry.getValue(), 1);
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

        return chunk.toString();
    }

    public void loadItemInWebView(Context context, WebView w, Question question) {
        w.getSettings().setJavaScriptEnabled(true);
        w.addJavascriptInterface(new WebAppInterface(context, question.getKey()), "Android");
        w.setBackgroundColor(Color.TRANSPARENT);

        String mime = "text/html";
        String encoding = "utf-8";
        String baseURL = "file:///android_res/raw/";
        w.loadDataWithBaseURL(baseURL, getHtml(question), mime, encoding, null);
    }

    public class WebAppInterface {
        Context mContext;
        private String sorryText = "This feature will be unlocked in a later update. Stay tuned!";

        private String questionKey;

        public WebAppInterface(Context mContext, String questionKey) {
            this.mContext = mContext;
            this.questionKey = questionKey;
        }

        @JavascriptInterface
        public void openDiscussionScreen() {
            Toast.makeText(mContext, sorryText, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), DiscussionActivity.class);
            intent.putExtra(DiscussionActivity.EXTRA_QUESTION_KEY, questionKey);
            startActivity(intent);
        }

        @JavascriptInterface
        public void checkMcq(String result) {
            Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();

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
