package com.pascoapp.wba02_android.Inbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.dataFetching.Message;

/**
 * An activity representing a list of messages. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MessageDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MessageListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private View coordinatorLayoutView;
    private ProgressBar loadingIndicator;
    private LinearLayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter<Message, MessageHolder> mAdapter;
    private DatabaseReference mMessagesRef;
    private ColorGenerator generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** TextDrawable library from github.com/amulyakhare/TextDrawable  **/
        generator = ColorGenerator.MATERIAL;

        coordinatorLayoutView = findViewById(R.id.message_list);
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.message_list);
        assert mRecyclerView != null;
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(MessageListActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mMessagesRef = FirebaseDatabase.getInstance().getReference().child("messages");
        // TODO: Filter query by this users information;
        // TODO: Order by date

        loadingIndicator.setVisibility(View.VISIBLE);
        mAdapter = new FirebaseRecyclerAdapter<Message, MessageHolder>(Message.class, R.layout.message_list_content, MessageHolder.class, mMessagesRef) {
            @Override
            public void populateViewHolder(final MessageHolder messageViewHolder, final Message message, final int position) {

                final String messageKey = getRef(position).getKey();

                messageViewHolder.setTitle(message.getTitle());

                messageViewHolder.setDate(message.getDate());

                messageViewHolder.setIcon(generator, message, messageKey);

                messageViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MessageListActivity.this, MessageDetailActivity.class);
                        intent.putExtra(MessageDetailActivity.EXTRA_MESSAGE_ID, messageKey);
                        startActivity(intent);
                    }
                });
            }
        };

        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                loadingIndicator.setVisibility(View.GONE);
            }
        });

        // Set the adapter object to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        if (findViewById(R.id.message_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    public static class MessageHolder extends RecyclerView.ViewHolder{

        public View mView;

        private int color;

        public MessageHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView field = (TextView) mView.findViewById(R.id.message_title);
            field.setText(title);
        }

        private void setDate(Long date) {
            TextView field = (TextView) mView.findViewById(R.id.message_date);
            // Setting the date
            String timeAgo = (String) DateUtils.getRelativeTimeSpanString(date,
                    System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
            field.setText(timeAgo);
        }

        private void setIcon(ColorGenerator generator, Message message, String messageKey) {
            ImageView field = (ImageView) mView.findViewById(R.id.message_icon);

            // generate color based on a nodeKey (same nodeKey returns the same color), useful for list/grid views
            color = generator.getColor(messageKey);

            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(
                            ((String) message.getTitle()).substring(0,1).toUpperCase(),
                            color
                    );

           field.setImageDrawable(drawable);
        }
    }
}
