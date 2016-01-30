package com.pascoapp.wba02_android.Inbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by peter on 1/30/16.
 */
public class PascoPushBroadCastReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String jsonData = extras.getString("com.parse.Data");
        JSONObject json = null;
        try {
            json = new JSONObject(jsonData);
            String messageId = json.getString("messageId");
            Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
            intent.putExtra(MessageDetailActivity.EXTRA_MESSAGE_ID, messageId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onPushOpen(context, intent);
    }

    @Override
    protected Class<? extends Activity> getActivity(Context context, Intent intent) {
        return MessageDetailActivity.class;
    }
}
