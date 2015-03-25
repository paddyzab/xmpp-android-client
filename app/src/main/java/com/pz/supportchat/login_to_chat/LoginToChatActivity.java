package com.pz.supportchat.login_to_chat;

import com.pz.supportchat.InjectableActivity;
import com.pz.supportchat.Intents;
import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.PostingConnectionChangeListener;
import com.pz.supportchat.R;
import com.pz.supportchat.storage.SharedPreferencesKeyValueStorage;
import com.pz.supportchat.xmpp.ConnectionManager;
import com.pz.supportchat.xmpp.XMPPConnectionProvider;
import com.squareup.otto.Subscribe;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

import static com.pz.supportchat.PostingConnectionChangeListener.XMPPConnectionStatus;


public class LoginToChatActivity extends InjectableActivity {

    private final String LOG_TAG = LoginToChatActivity.class.getSimpleName();

    private final String PASSWORD_KEY = "_password";
    private final String LOGIN_KEY = "_login";

    @Inject
    protected Intents intents;

    @Inject
    protected XMPPConnectionProvider mXMPPConnectionProvider;

    @Inject
    protected ConnectionManager mConnectionManager;

    @Inject
    protected MainThreadBus mBus;

    @Inject
    protected PostingConnectionChangeListener mPostingConnectionChangeListener;

    @Inject
    protected SharedPreferencesKeyValueStorage mSharedPreferencesKeyValueStorage;

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
            mConnectionManager.connect(mPostingConnectionChangeListener);
        }
    }

    @OnClick(R.id.buttonJoin)
    protected void clickJoin() {
        if (validateNick()) {
            editTextPickNickname.setError(null);
            loginAndStartChat();

            mSharedPreferencesKeyValueStorage.storeString(LOGIN_KEY,
                    editTextPickNickname.getText().toString());
            mSharedPreferencesKeyValueStorage
                    .storeString(PASSWORD_KEY, editTextPassword.getText().toString());
        } else {
            editTextPickNickname.setError("You need choose a nickname.");
        }
    }

    private XMPPTCPConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(intents.getChatServiceIntent(LoginToChatActivity.this));

        mConnection = mXMPPConnectionProvider.getConnection();
        buttonJoin.setEnabled(mConnection.isConnected());
        mBus.register(this);
        buttonJoin.setEnabled(true);

        restoreLoginCredentials();
    }

    @Override
    protected void onResume() {
        super.onResume();
        buttonJoin.setEnabled(mConnection.isConnected());
        imageViewConnectionStatus.setBackgroundColor(mConnection.isConnected()
                ? getResources().getColor(R.color.green)
                : getResources().getColor(R.color.red));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }

    @Subscribe
    public void onConnectionStatusChange(final XMPPConnectionStatus status) {
        buttonJoin.setEnabled(
                !StringUtils.equals(status.mStatus, PostingConnectionChangeListener.DISCONNECTED));

        imageViewConnectionStatus.setBackgroundColor(
                StringUtils.equals(status.mStatus, PostingConnectionChangeListener.CONNECTED)
                        ? getResources().getColor(R.color.green)
                        : getResources().getColor(R.color.red));
    }

    @Override
    public int getLayoutResource() {
        return R.layout.login_to_chat;
    }

    private void loginAndStartChat() {
        mConnectionManager.login(editTextPickNickname.getText().toString(),
                editTextPassword.getText().toString());

        if (mConnection.isAuthenticated()) {
            startActivity(
                    intents.getCurrentChatIntent(this, editTextPickNickname.getText().toString()));
        }
    }

    private boolean validateNick() {
        return StringUtils.isNotEmpty(editTextPickNickname.getText().toString()) && StringUtils
                .isNotEmpty(editTextPassword.getText().toString());
    }

    private void restoreLoginCredentials() {
        if (!TextUtils.isEmpty(mSharedPreferencesKeyValueStorage.getString(LOGIN_KEY))) {
            editTextPickNickname.setText(mSharedPreferencesKeyValueStorage.getString(LOGIN_KEY));
        }

        if (!TextUtils.isEmpty(mSharedPreferencesKeyValueStorage.getString(PASSWORD_KEY))) {
            editTextPassword.setText(mSharedPreferencesKeyValueStorage.getString(PASSWORD_KEY));
        }
    }
}
