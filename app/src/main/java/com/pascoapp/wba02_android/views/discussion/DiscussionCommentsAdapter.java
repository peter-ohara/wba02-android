package com.pascoapp.wba02_android.views.discussion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.comments.Comment;
import com.pascoapp.wba02_android.services.comments.Comments;
import com.pascoapp.wba02_android.services.questions.Questions;
import com.pascoapp.wba02_android.services.users.Users;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pascoapp.wba02_android.Helpers.dip;

/**
 * Created by peter on 10/23/16.
 */
public class DiscussionCommentsAdapter
        extends RecyclerView.Adapter<DiscussionCommentsAdapter.CommentsViewHolder> {

    public static final String VOTES = "votes";
    private Context context;
    private List<Comment> mItems;
    private final String questionKey;

    private OnReplyListener onReplyListener;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public interface OnReplyListener {
        void onReply(String parent);
    }


    public DiscussionCommentsAdapter(Context context, List<Comment> mItems, String questionKey) {
        this.context = context;
        this.mItems = mItems;
        this.questionKey = questionKey;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);

        return new CommentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        Comment comment = mItems.get(position);

        Users.fetchUser(comment.getAuthor())
                .flatMap(user -> {
                    holder.author.setText(user.getUsername());
                    return Questions.fetchQuestion(questionKey);
                })
                .subscribe(question -> {
                    holder.answer.setText(question.getSubmittedAnswers().get(user.getUid()));
                }, throwable -> {
                    Toast.makeText(context, "Error fetching comment details",
                            Toast.LENGTH_SHORT).show();
                });

        if (comment.getParent() != null) {
            // comment is a child so indent by apprioprate commentDepth
            int indent = dip(8) + dip(8 * comment.getCommentDepth());
            setMargins(holder.itemView, indent, dip(4), dip(8), dip(4));

            if (comment.getCommentDepth() > 1) {
                holder.replyButton.setVisibility(View.GONE);
            }
        }

        holder.message.setText(comment.getMessage());
        holder.timestamp.setText(getTimeRelativeToNow(comment.getTimestamp()));


        // sum up all the votes
        Map<String, Integer> votes = comment.getVotes();
        Integer sumVotes = 0;
        if (votes != null) {
            for (Map.Entry<String, Integer> entry: comment.getVotes().entrySet()) {
                sumVotes += entry.getValue();
            }
            holder.upVoteButton.setText(String.valueOf(sumVotes));
            holder.downvoteButton.setText(String.valueOf(sumVotes));
        } else {
            holder.upVoteButton.setText(String.valueOf(sumVotes));
            holder.downvoteButton.setText(String.valueOf(sumVotes));
        }

        holder.replyButton.setOnClickListener(view -> {
            // Launch new comment Activity
            onReplyListener.onReply(comment.getKey());
        });

        holder.upVoteButton.setOnClickListener(view -> {
            if (votes == null) {
                Comments.COMMENTS_REF.child(questionKey).child(comment.getKey())
                        .child(VOTES).child(user.getUid()).setValue(1);
            } else if (votes.get(user.getUid()) == 1) {
                Comments.COMMENTS_REF.child(questionKey).child(comment.getKey())
                        .child(VOTES).child(user.getUid()).setValue(null);
            } else {
                Comments.COMMENTS_REF.child(questionKey).child(comment.getKey())
                        .child(VOTES).child(user.getUid()).setValue(1);
            }
        });

        holder.downvoteButton.setOnClickListener(view -> {
            if (votes == null) {
                Comments.COMMENTS_REF.child(questionKey).child(comment.getKey())
                        .child(VOTES).child(user.getUid()).setValue(-1);
            } else if (votes.get(user.getUid()) == -1) {
                Comments.COMMENTS_REF.child(questionKey).child(comment.getKey())
                        .child(VOTES).child(user.getUid()).setValue(null);
            } else {
                Comments.COMMENTS_REF.child(questionKey).child(comment.getKey())
                        .child(VOTES).child(user.getUid()).setValue(-1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnReplyListener(OnReplyListener onReplyListener) {
        this.onReplyListener = onReplyListener;
    }


    private String getTimeRelativeToNow(Long time) {
        return (String) DateUtils.getRelativeDateTimeString(context,
                time,
                DateUtils.DAY_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_ALL);
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.answer)
        TextView answer;
        @BindView(R.id.timeStamp)
        TextView timestamp;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.message)
        TextView message;

        @BindView(R.id.replyButton)
        Button replyButton;
        @BindView(R.id.upVoteButton)
        Button upVoteButton;
        @BindView(R.id.downvoteButton)
        Button downvoteButton;


        public View itemView;

        public CommentsViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}
