package com.pz.supportchat.current_chat;

import android.app.Fragment;
import android.os.Bundle;

public class ChatDataFragment extends Fragment {


    public final static String CHAT_DATA_FRAGMENT_KEY = "_chat_data_fragment";
    private ChatViewModel mChatViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    public void setData(final ChatViewModel viewModel) {
        mChatViewModel = viewModel;
    }

    public ChatViewModel getData() {
        return mChatViewModel;
    }
}
