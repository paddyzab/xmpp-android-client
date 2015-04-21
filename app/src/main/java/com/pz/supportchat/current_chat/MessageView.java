package com.pz.supportchat.current_chat;

import com.pz.supportchat.R;
import com.pz.supportchat.commons.models.InternalMessage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MessageView extends RelativeLayout {

    @InjectView(R.id.textViewMessage)
    protected TextView textViewMessage;

    @InjectView(R.id.textViewMessageFrom)
    protected TextView textViewMessageFrom;

    @InjectView(R.id.relativeLayoutContainer)
    protected RelativeLayout relativeLayoutContainer;

    public MessageView(final Context context) {
        this(context, null);
    }

    public MessageView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public void setData(final InternalMessage internalMessage) {
        textViewMessage.setText(internalMessage.message);
        textViewMessageFrom.setText(internalMessage.fromName);
        setMessageBackground(relativeLayoutContainer, internalMessage.isSelf);
    }

    private void setMessageBackground(final RelativeLayout relativeLayoutContainer, final boolean isSelf) {
        if(isSelf) {
            relativeLayoutContainer.setBackgroundColor(getContext().getResources().getColor(R.color.detail1));
        } else {
            relativeLayoutContainer.setBackgroundColor(getContext().getResources().getColor(R.color.detail2));
        }
    }
}
