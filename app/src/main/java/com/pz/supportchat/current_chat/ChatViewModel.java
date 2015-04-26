package com.pz.supportchat.current_chat;

import com.pz.supportchat.commons.models.InternalMessage;
import java.util.List;

public class ChatViewModel {

    public final List<InternalMessage> mMessageList;

    public ChatViewModel(List<InternalMessage> mMessageList) {
        this.mMessageList = mMessageList;
    }
}
