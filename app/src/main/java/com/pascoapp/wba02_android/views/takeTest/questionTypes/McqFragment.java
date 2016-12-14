package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.comments.Comment;
import com.pascoapp.wba02_android.services.comments.Comments;
import com.pascoapp.wba02_android.services.questions.Question;
import com.pascoapp.wba02_android.services.questions.Questions;
import com.wang.avi.AVLoadingIndicatorView;
import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

import static android.content.ContentValues.TAG;

public class McqFragment extends Fragment {

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
    private long commentCount;
    protected JSONArray commentsArray = new JSONArray();

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
                .concatMap(fetchedQuestion -> {
                    question = fetchedQuestion;
                    Query commentsQuery = Comments.COMMENTS_REF.child(questionKey);
                    return Comments.fetchListOfComments(commentsQuery);
                })
                .onErrorReturn(throwable -> new ArrayList<Comment>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(comments -> {
                    loadingIndicator.hide();

                    setCommentsArray(comments);
                    commentCount = comments.size();

                    loadItemInWebView(getActivity(), webview, question);
                    setAnswerHash(question);
                }, throwable -> {
                    loadingIndicator.hide();
                    Log.d(TAG, "refreshData: " + throwable.getMessage());
                    Snackbar.make(webview, throwable.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData(questionKey))
                            .show();
                });
    }

    private void setCommentsArray(List<Comment> comments) {
        for (Comment comment : comments) {
            try {
                JSONObject commentJson = QuestionHelpers.convertCommentToJson(comment);
                commentsArray.put(commentJson);
            } catch (JSONException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
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

        chunk.set("commentCount", commentCount);

        return chunk.toString();
    }

    public void loadItemInWebView(Context context, WebView w, Question question) {
        w.getSettings().setJavaScriptEnabled(true);
        w.addJavascriptInterface(new McqWebAppInterface(this, question.getKey()), "Android");
        w.setBackgroundColor(Color.TRANSPARENT);

        String mime = "text/html";
        String encoding = "utf-8";
        String baseURL = "file:///android_res/raw/";
        w.loadDataWithBaseURL(baseURL, getHtml(question), mime, encoding, null);
    }


    public class McqWebAppInterface extends WebAppInterface {

        public McqWebAppInterface(McqFragment questionFragment, String questionKey) {
            super(questionFragment, questionKey);
        }

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
