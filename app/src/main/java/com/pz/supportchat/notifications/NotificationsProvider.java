package com.pz.supportchat.notifications;

import com.pz.supportchat.Intents;
import com.pz.supportchat.commons.models.InternalMessage;
import com.pz.supportchat.current_chat.ChatActivity;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import javax.inject.Inject;

public class NotificationsProvider {

    @Inject
    protected Intents mIntents;

    public Notification getNewMessageNotification(final Context context,
            final InternalMessage message) {
        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setContentTitle("New message")
                        .setContentText(message.message);
        final Intent resultIntent = mIntents.getCurrentChatIntent(context, message.fromName);

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

}
