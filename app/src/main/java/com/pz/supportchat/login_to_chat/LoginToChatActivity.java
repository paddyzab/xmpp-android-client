package com.pz.supportchat.login_to_chat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.InjectView;
import butterknife.OnClick;
import com.pz.supportchat.InjectableActivity;
import com.pz.supportchat.Intents;
import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.PostingConnectionChangeListener;
import com.pz.supportchat.R;
import com.pz.supportchat.storage.SharedPreferencesKeyValueStorage;
import com.pz.supportchat.xmpp.XMPPConnectionProvider;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import static android.view.View.VISIBLE;
import static com.pz.supportchat.PostingConnectionChangeListener.XMPPConnectionStatus;


public class LoginToChatActivity extends InjectableActivity implements LoginView {

    private final String LOG_TAG = LoginToChatActivity.class.getSimpleName();

    @Inject
    protected Intents intents;

    @Inject
    protected XMPPConnectionProvider mXMPPConnectionProvider;

    @Inject
    protected MainThreadBus mBus;

    @Inject
    protected SharedPreferencesKeyValueStorage mSharedPreferencesKeyValueStorage;

    @Inject
    protected LoginPresenterImpl mLoginPresenter;

    @InjectView(R.id.editTextLogin)
    protected EditText editTextPickNickname;

    @InjectView(R.id.editTextPassword)
    protected EditText editTextPassword;

    @InjectView(R.id.buttonJoin)
    protected Button buttonJoin;

    @InjectView(R.id.imageViewConnectionStatus)
    protected ImageView imageViewConnectionStatus;

    @InjectView(R.id.progressBar)
    protected ProgressBar progressBar;

    @OnClick(R.id.layoutConnectionStatus)
    protected void reconnect() {
        mLoginPresenter.reconnectToServer();
    }

    @OnClick(R.id.buttonJoin)
    protected void clickJoin() {
        mLoginPresenter.validateCredentials(editTextPickNickname.getText().toString(), editTextPassword.getText().toString());
    }

    private XMPPTCPConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(intents.getChatServiceIntent(LoginToChatActivity.this));

        mConnection = mXMPPConnectionProvider.getConnection();
        mBus.register(this);
        mLoginPresenter.setLoginView(this);

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

    private void restoreLoginCredentials() {
        if (!TextUtils.isEmpty(mSharedPreferencesKeyValueStorage.getString(mSharedPreferencesKeyValueStorage.LOGIN_KEY))) {
            editTextPickNickname.setText(mSharedPreferencesKeyValueStorage.getString(mSharedPreferencesKeyValueStorage.LOGIN_KEY));
        }

        if (!TextUtils.isEmpty(mSharedPreferencesKeyValueStorage.getString(mSharedPreferencesKeyValueStorage.PASSWORD_KEY))) {
            editTextPassword.setText(mSharedPreferencesKeyValueStorage.getString(mSharedPreferencesKeyValueStorage.PASSWORD_KEY));
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        editTextPickNickname.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        editTextPassword.setError(getString(R.string.password_error));
    }

    @Override
    public void navigateToContactsList() {
        startActivity(
                intents.getContactsIntent(this));
    }
}
