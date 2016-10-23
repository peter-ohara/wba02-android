package com.pascoapp.wba02_android.views.discussion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.comments.Comment;
import com.pascoapp.wba02_android.services.comments.Comments;
import com.pascoapp.wba02_android.services.courses.Courses;
import com.pascoapp.wba02_android.services.questions.Question;
import com.pascoapp.wba02_android.services.questions.Questions;
import com.pascoapp.wba02_android.services.tests.Tests;
import com.pascoapp.wba02_android.views.main.MainViewListAdapter;
import com.pascoapp.wba02_android.views.takeTest.QuestionsPagerAdapter;
import com.pascoapp.wba02_android.views.takeTest.questionTypes.QuestionHelpers;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class DiscussionActivity extends AppCompatActivity {

    public static final String EXTRA_QUESTION_KEY = "com.pascoapp.wba02_android.questionKey";
    public static final String TIMESTAMP = "timestamp";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loadingIndicator;
    @BindView(R.id.snackbarPosition)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.commentsList)
    RecyclerView commentsRecyclerView;

    private List<Comment> mComments = new ArrayList<>();
    private DiscussionCommentsAdapter discussionCommentsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        ButterKnife.bind(this);

        setupToolbar();

        commentsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        commentsRecyclerView.setLayoutManager(linearLayoutManager);

        discussionCommentsAdapter = new DiscussionCommentsAdapter(this, mComments, getQuestionKey());
        commentsRecyclerView.setAdapter(discussionCommentsAdapter);


        refreshData(getQuestionKey());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // When the action bar's Up/Home button
            // Which in our case is the close button
            // Do the same thing as when the back button is pressed
            // In most cases this preserves the state of TakeTestActivity
            // from which this DiscussionActivity was launched.
            // TODO: Find more robust solution to the above problem.
            // Consult here https://developer.android.com/training/implementing-navigation/ancestral.html
            // It may give you some ideas
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        setTitle("Discussion");
    }

    public String getQuestionKey() {
        Intent intent = getIntent();
//        return intent.getStringExtra(EXTRA_QUESTION_KEY);
        return "KNUEE15103EAQ1";
    }

    private void refreshData(String questionKey) {
        loadingIndicator.show();

        Query query = Comments.COMMENTS_REF
                .child(questionKey).orderByChild(TIMESTAMP);

        Comments.fetchListOfComments(query)
                .map(comments -> QuestionHelpers.toThreadedComments(comments))
                .subscribe(comments -> {
                    loadingIndicator.hide();
                    mComments.clear();
                    mComments.addAll(comments);
                    discussionCommentsAdapter.notifyDataSetChanged();
                }, firebaseException -> {
                    loadingIndicator.hide();
                    Snackbar.make(coordinatorLayout, firebaseException.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData(questionKey))
                            .show();
                });
    }
}
