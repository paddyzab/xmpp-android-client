package com.pz.supportchat.current_chat;

import com.pz.supportchat.xmpp.InjectableDataFragment;
import javax.inject.Inject;

public class ChatDataFragment extends InjectableDataFragment<ChatViewModel> {


    public final static String CHAT_DATA_FRAGMENT_KEY = "_chat_data_fragment";
    private ChatViewModel mChatViewModel;

    @Inject
    public ChatDataFragment() {

    }

    public void setData(final ChatViewModel viewModel) {
        mChatViewModel = viewModel;
    }

    public ChatViewModel getData() {
        return mChatViewModel;
    }
}
