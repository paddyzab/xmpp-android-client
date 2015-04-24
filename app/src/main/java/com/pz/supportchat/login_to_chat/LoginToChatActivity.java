package com.pz.supportchat.login_to_chat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
import com.pz.supportchat.notifications.NotificationService;
import com.pz.supportchat.notifications.NotificationsProvider;
import com.pz.supportchat.storage.SharedPreferencesKeyValueStorage;
import com.pz.supportchat.xmpp.XMPPConnectionProvider;
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
    protected SharedPreferencesKeyValueStorage mSharedPreferencesKeyValueStorage;

    @Inject
    protected LoginPresenterImpl mLoginPresenter;

    @Inject
    protected MainThreadBus mBus;

    @Inject
    protected NotificationsProvider mNotificationsProvider;

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
        mLoginPresenter.validateCredentials(editTextPickNickname.getText().toString(),
                editTextPassword.getText().toString());
    }

    private XMPPTCPConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent chatServiceIntent = intents.getNotificationService(LoginToChatActivity.this);
        bindService(chatServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        mConnection = mXMPPConnectionProvider.getConnection();
        mLoginPresenter.setLoginView(this);

        restoreLoginCredentials();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final NotificationService.INotificationService notificationService = (NotificationService.INotificationService) service;
            notificationService.startNotificationService(mBus, mNotificationsProvider);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mLoginPresenter.onResume();
        makeConnectionAwareComponentsEnable(mConnection.isConnected());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLoginPresenter.onPause();
    }

    @Override
    public void connectionChanged(final XMPPConnectionStatus status) {
        makeConnectionAwareComponentsEnable(
                StringUtils.equals(status.mStatus, PostingConnectionChangeListener.CONNECTED));
    }

    @Override
    public int getLayoutResource() {
        return R.layout.login_to_chat;
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

    private void restoreLoginCredentials() {
        if (!TextUtils.isEmpty(mSharedPreferencesKeyValueStorage
                .getString(mSharedPreferencesKeyValueStorage.LOGIN_KEY))) {
            editTextPickNickname.setText(mSharedPreferencesKeyValueStorage
                    .getString(mSharedPreferencesKeyValueStorage.LOGIN_KEY));
        }

        if (!TextUtils.isEmpty(mSharedPreferencesKeyValueStorage
                .getString(mSharedPreferencesKeyValueStorage.PASSWORD_KEY))) {
            editTextPassword.setText(mSharedPreferencesKeyValueStorage
                    .getString(mSharedPreferencesKeyValueStorage.PASSWORD_KEY));
        }
    }

    private void makeConnectionAwareComponentsEnable(boolean enabled) {
        buttonJoin.setEnabled(enabled);
        imageViewConnectionStatus.setBackgroundColor(enabled
                ? getResources().getColor(R.color.green)
                : getResources().getColor(R.color.red));
    }

}
