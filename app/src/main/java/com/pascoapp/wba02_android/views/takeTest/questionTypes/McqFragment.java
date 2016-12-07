package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.content.Context;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
    private Map<String, Integer> answerHash = new HashMap<>();

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loadingIndicator;
    private String someJs;
    private long commentCount;

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
                    loadComments(fetchedQuestion, questionKey);
                }, throwable -> {
                    loadingIndicator.hide();
                    Snackbar.make(webview, throwable.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData(questionKey))
                            .show();
                });
    }

    private void loadComments(Question fetchedQuestion, String questionKey) {
        loadingIndicator.show();

        Query query = Comments.COMMENTS_REF.child(questionKey);
        System.out.println(query.getRef());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadingIndicator.hide();
                String commentsArray = "var commentsArray = [  ";

                if (dataSnapshot.getValue() != null) {
                    commentCount = dataSnapshot.getChildrenCount();
                    for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                        Comment comment = commentSnapshot.getValue(Comment.class);
                        comment.setKey(commentSnapshot.getKey());
                        System.out.println(comment);


                        commentsArray += "{";

                        commentsArray += "\"id\":" + comment.getId() + ",";
                        commentsArray += "\"parent\":" + comment.getParent() + ",";
                        commentsArray += "\"created\":\"" + comment.getCreated() + "\",";
                        commentsArray += "\"modified\":\"" + comment.getModified() + "\",";
                        commentsArray += "\"content\":\"" + comment.getContent() + "\",";
                        commentsArray += "\"fullname\":\"" + comment.getFullname() + "\",";
                        commentsArray += "\"profile_picture_url\":\"" + comment.getProfile_picture_url() + "\",";
                        commentsArray += "\"created_by_admin\":" + comment.getCreated_by_admin() + ",";
                        commentsArray += "\"created_by_current_user\":" + comment.getCreated_by_current_user() + ",";
                        commentsArray += "\"upvote_count\":" + comment.getUpvote_count() + ",";
                        commentsArray += "\"user_has_upvoted\":" + comment.getUser_has_upvoted();

                        commentsArray += "},";
                    }
                    commentsArray  = commentsArray.substring(0, commentsArray.length()-1);
                    commentsArray += "]";
                }

                System.out.println(commentsArray);

                someJs = commentsArray;

                loadItemInWebView(getActivity(), webview, fetchedQuestion);
                setAnswerHash(fetchedQuestion);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loadingIndicator.hide();
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

        chunk.set("commentsArray", someJs);
        chunk.set("commentCount", commentCount);

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
