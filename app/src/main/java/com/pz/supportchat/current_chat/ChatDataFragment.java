package com.pz.supportchat.current_chat;

import android.app.Fragment;
import android.os.Bundle;

public class ChatDataFragment extends Fragment {

    private ChatViewModel mChatViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    public void setData(final ChatViewModel viewModel) {
        mChatViewModel = viewModel;
    }
}
