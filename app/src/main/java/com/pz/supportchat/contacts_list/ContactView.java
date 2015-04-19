package com.pz.supportchat.contacts_list;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.pz.supportchat.R;
import com.pz.supportchat.commons.models.PresenceAwareRosterEntry;

public class ContactView extends RelativeLayout {

    @InjectView(R.id.textViewUserName)
    protected TextView textViewUserName;

    @InjectView(R.id.imageViewStatus)
    protected ImageView imageViewStatus;

    public ContactView(final Context context) {
        this(context, null);
    }

    public ContactView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.contact_view, this);
        ButterKnife.inject(this);
    }

    public void setData(PresenceAwareRosterEntry rosterEntry, Context context) {
        textViewUserName.setText(rosterEntry.getName());
        imageViewStatus.setBackgroundColor(setPresenceResource(rosterEntry.isPresent(), context));
    }

    private int setPresenceResource(final boolean isPresent, final Context context) {
        if (isPresent) {
            return context.getResources().getColor(R.color.green);
        } else {
            return context.getResources().getColor(R.color.red);
        }
    }
}
