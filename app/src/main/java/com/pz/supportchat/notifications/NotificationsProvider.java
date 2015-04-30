package com.pz.supportchat.notifications;

import com.pz.supportchat.Intents;
import com.pz.supportchat.commons.models.InternalMessage;
import com.pz.supportchat.current_chat.ChatActivity;
import com.pz.supportchat.utils.StringUtils;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationsProvider {

    private Intents mIntents;

    public NotificationsProvider(final Intents intents) {
        mIntents = intents;
    }

    public Notification getNewMessageNotification(final Context context,
                                                  final InternalMessage message) {

        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.ic_dialog_email)
                        .setContentTitle(getFormattedUserString(message))
                        .setContentText(message.getMessage());
        final Intent resultIntent = mIntents.getCurrentChatIntent(context, message.getContact().getName());

        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ChatActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        final PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        return mBuilder.build();
    }

    private String getFormattedUserString(InternalMessage message) {
        return "New message from: " + StringUtils.parseBareUsername(message.getContact().getName());
    }

}
