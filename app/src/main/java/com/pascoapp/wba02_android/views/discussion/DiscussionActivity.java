package com.pascoapp.wba02_android.views.discussion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.comments.Comment;
import com.pascoapp.wba02_android.services.comments.Comments;
import com.pascoapp.wba02_android.views.takeTest.questionTypes.QuestionHelpers;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiscussionActivity extends AppCompatActivity
        implements DiscussionCommentsAdapter.OnReplyListener,
        NewCommentFragment.OnFragmentInteractionListener {

    public static final String EXTRA_QUESTION_KEY = "com.pascoapp.wba02_android.questionKey";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loadingIndicator;
    @BindView(R.id.snackbarPosition)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.commentsList)
    RecyclerView commentsRecyclerView;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton replyButton;

    private List<Comment> mComments = new ArrayList<>();
    private DiscussionCommentsAdapter discussionCommentsAdapter;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


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
        discussionCommentsAdapter.setOnReplyListener(this);
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
        return intent.getStringExtra(EXTRA_QUESTION_KEY);
    }

    private void refreshData(String questionKey) {
        loadingIndicator.show();

        Query query = Comments.COMMENTS_REF.child(questionKey);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadingIndicator.hide();

                if (dataSnapshot.getValue() == null) {
                    mComments.clear();
                } else {
                    List<Comment> comments = new ArrayList<>();
                    for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                        Comment comment = commentSnapshot.getValue(Comment.class);
                        comment.setKey(commentSnapshot.getKey());
                        comments.add(comment);
                    }

                    mComments.clear();
                    mComments.addAll(QuestionHelpers.toThreadedComments(comments));
                }
                discussionCommentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loadingIndicator.hide();
                Snackbar.make(coordinatorLayout, databaseError.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Retry", view -> refreshData(questionKey))
                        .show();
            }
        });
    }

    @OnClick(R.id.floatingActionButton)
    public void OnReplyButtonClick(View view) {
        onReply(null);
    }

    @Override
    public void onReply(String parent) {
        NewCommentFragment newCommentFragment = NewCommentFragment.newInstance(parent, "");
        newCommentFragment.show(getSupportFragmentManager(), "newCommentDialog");
    }

    @Override
    public void onFragmentInteraction(String comment, String parent) {
        Comment newComment = new Comment();

        newComment.setAuthor(user.getUid());
        newComment.setMessage(comment);
        newComment.setParent(parent);
        newComment.setTimestamp((new Date()).getTime());

        Comments.COMMENTS_REF.child(getQuestionKey()).push().getRef().setValue(newComment);
    }
}
