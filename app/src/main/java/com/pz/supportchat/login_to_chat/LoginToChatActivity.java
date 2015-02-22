package com.pz.supportchat.login_to_chat;

import com.pz.supportchat.InjectableActivity;
import com.pz.supportchat.Intents;
import com.pz.supportchat.PostingConnectionChangeListener;
import com.pz.supportchat.R;
import com.pz.supportchat.xmpp.ChatManager;
import com.pz.supportchat.xmpp.XMPPConnectionProvider;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

import static com.pz.supportchat.PostingConnectionChangeListener.XMPPConnectionStatus;


public class LoginToChatActivity extends InjectableActivity {

    private final String LOG_TAG = LoginToChatActivity.class.getSimpleName();

    @Inject
    protected Intents intents;

    @Inject
    protected XMPPConnectionProvider mXMPPConnectionProvider;

    @Inject
    protected ChatManager mChatManager;

    @Inject
    protected Bus mBus;

    @Inject
    protected PostingConnectionChangeListener mPostingConnectionChangeListener;

    @InjectView(R.id.editTextLogin)
    protected EditText editTextPickNickname;

    @InjectView(R.id.editTextPassword)
    protected EditText editTextPassword;

    @InjectView(R.id.buttonJoin)
    protected Button buttonJoin;

    @InjectView(R.id.imageViewConnectionStatus)
    protected ImageView imageViewConnectionStatus;

    @OnClick(R.id.layoutConnectionStatus)
    protected void reconnect() {
        if (!mConnection.isConnected()) {
            mChatManager.connect(mConnection, mPostingConnectionChangeListener);
        }
    }

    @OnClick(R.id.buttonJoin)
    protected void clickJoin() {
        if (validateNick()) {
            editTextPickNickname.setError(null);
            loginAndStartChat();
        } else {
            editTextPickNickname.setError("You need choose a nickname.");
        }
    }

    private XMPPTCPConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(intents.getChatServiceIntent(LoginToChatActivity.this));

        editTextPickNickname.setText("paddy");
        editTextPassword.setText("wiosna");
        mConnection = mXMPPConnectionProvider.getConnection();

        buttonJoin.setEnabled(mConnection.isConnected());

        mBus.register(this);

        buttonJoin.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }

    @Subscribe
    public void onConnectionStatusChange(final XMPPConnectionStatus status) {
        buttonJoin.setEnabled(
                !StringUtils.equals(status.mStatus, PostingConnectionChangeListener.CONNECTED));

        imageViewConnectionStatus.setBackgroundResource(
                StringUtils.equals(status.mStatus, PostingConnectionChangeListener.CONNECTED)
                        ? getResources().getColor(R.color.green)
                        : getResources().getColor(R.color.red));
        
        Log.d("SMACK", "ACTIVITY --> new status: " + status.mStatus);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.login_to_chat;
    }

    private void loginAndStartChat() {
        mChatManager.login(mConnection, editTextPickNickname.getText().toString(), editTextPassword.getText().toString());

        if (mConnection.isAuthenticated()) {
            startActivity(
                    intents.getChatsListIntent(this, editTextPickNickname.getText().toString()));
        }
    }

    private boolean validateNick() {
        return StringUtils.isNotEmpty(editTextPickNickname.getText().toString()) && StringUtils
                .isNotEmpty(editTextPassword.getText().toString());
    }
}
