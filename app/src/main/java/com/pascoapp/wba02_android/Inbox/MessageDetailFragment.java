package com.pascoapp.wba02_android.Inbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Message;

/**
 * A fragment representing a single message detail screen.
 * This fragment is either contained in a {@link MessageListActivity}
 * in two-pane mode (on tablets) or a {@link MessageDetailActivity}
 * on handsets.
 */
public class MessageDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Message mMessage;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MessageDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // TODO: use a Loader
            // to load content from a content provider.
            String messageId = getArguments().getString(ARG_ITEM_ID);
            ParseQuery<Message> query = Message.getQuery();
            query.getInBackground(messageId, new GetCallback<Message>() {
                @Override
                public void done(Message message, ParseException e) {
                    mMessage = message;
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.message_detail, container, false);

        // Show the message content as text in a TextView.
        if (mMessage != null) {
            ((TextView) rootView.findViewById(R.id.message_detail)).setText(mMessage.getContent());
        }

        return rootView;
    }
}
