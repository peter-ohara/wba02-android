package com.pascoapp.wba02_android.Inbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.R;

import com.pascoapp.wba02_android.parseSubClasses.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * An activity representing a list of messages. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MessageDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MessageListActivity extends AppCompatActivity {

    private List<Message> mMMessages = new ArrayList<>();

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private View coordinatorLayoutView;
    private ProgressBar loadingIndicator;
    private LinearLayoutManager mLayoutManager;
    private MessageListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coordinatorLayoutView = findViewById(R.id.message_list);
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.message_list);
        assert mRecyclerView != null;
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(MessageListActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create an object for the adapter
        mAdapter = new MessageListAdapter(mMMessages);

        // Set the adapter object to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        if (findViewById(R.id.message_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        String programmeId = null;
        fetchMessages(programmeId);
    }

    private void fetchMessages(final String programmeId) {
        loadingIndicator.setVisibility(View.VISIBLE);

        ParseQuery<Message> query = Message.getQuery();

        if (programmeId != null) {
            query.whereEqualTo("programme",
                    ParseObject.createWithoutData(Message.class, programmeId)
            );
        }

        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<Message>() {
            @Override
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMMessages.clear();
                    mMMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged();
                    loadingIndicator.setVisibility(View.GONE);
                } else if (e.getCode() == 120) {
                    // Result not cached Error. Ignore it
                } else {
                    Snackbar.make(coordinatorLayoutView, e.getCode() + " : " + e.getMessage(),
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    fetchMessages(programmeId);
                                }
                            }).show();
                    loadingIndicator.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

        private final List<Message> mMessages;
        private ColorGenerator generator;
        private int color;

        public MessageListAdapter(List<Message> messages) {
            mMessages = messages;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Message message = mMessages.get(position);
            holder.mMessage = message;
            holder.titleView.setText(message.getTitle());

            setMessageDate(holder, message);

            setMessageIcon(holder, message);


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MessageListActivity.this, MessageDetailActivity.class);
                    intent.putExtra(MessageDetailActivity.EXTRA_MESSAGE_ID, message.getObjectId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mMessages.size();
        }

        private void setMessageDate(ViewHolder holder, Message message) {
            // Setting the date
            String timeAgo = (String) DateUtils.getRelativeTimeSpanString(((Date) message.getCreatedAt()).getTime(),
                    System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
            holder.dateView.setText(timeAgo);
        }

        private void setMessageIcon(ViewHolder holder, Message message) {
            // Setting the Icon
            /** TextDrawable library from github.com/amulyakhare/TextDrawable  **/
            generator = ColorGenerator.MATERIAL;


            // generate color based on a key (same key returns the same color), useful for list/grid views
            color = generator.getColor((String) message.getObjectId());

            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(
                            ((String) message.get("title")).substring(0,1).toUpperCase(),
                            color
                    );

            holder.imageView.setImageDrawable(drawable);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public TextView titleView;
            public TextView dateView;
            public ImageView imageView;

            public Message mMessage;

            public ViewHolder(View view) {
                super(view);

                mView = view;
                titleView = (TextView) itemView.findViewById(R.id.message_title);
                dateView = (TextView) itemView.findViewById(R.id.message_date);
                imageView = (ImageView) itemView.findViewById(R.id.message_icon);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + titleView.getText() + "'";
            }
        }
    }
}
