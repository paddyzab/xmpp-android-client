package com.pz.supportchat.login_to_chat;

import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.R;

import org.mockito.Mock;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

import static org.assertj.android.api.Assertions.assertThat;

public class LoginToChatActivityTest extends ActivityInstrumentationTestCase2<LoginToChatActivity> {


    private LoginToChatActivity testedActivity;
    private Button buttonJoin;
    private EditText editTextLogin;
    private EditText editTextPassword;

    @Mock
    private MainThreadBus mainThreadBus;

    public LoginToChatActivityTest() {
        super(LoginToChatActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testedActivity = getActivity();

        buttonJoin = (Button) testedActivity.findViewById(R.id.buttonJoin);
        editTextLogin = (EditText) testedActivity.findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) testedActivity.findViewById(R.id.editTextPassword);
    }


    @UiThreadTest
    public void testEnablesButtonOnConnectionChanged() {
        givenAppIsConnectedToTheServer();

        assertThat(buttonJoin).isEnabled();
    }

    @UiThreadTest
    public void testDisablesButtonOnDisonnection() {
        givenAppIsNotConnectedToTheServer();

        assertThat(buttonJoin).isDisabled();
    }

    @UiThreadTest
    public void testDisplaysErrorMessageIfUserDataAreNotValid() {
        givenUserDataAreEmpty();
        givenAppIsConnectedToTheServer();

        buttonJoin.performClick();

        assertThat(editTextLogin).hasError("Username cannot be empty.");
    }

    // This needs to be migrated behind dagger
    private void givenAppIsConnectedToTheServer() {
       // testedActivity.onConnectionStatusChange(new XMPPConnectionStatus(PostingConnectionChangeListener.CONNECTED));
    }

    private void givenAppIsNotConnectedToTheServer() {
       // testedActivity.onConnectionStatusChange(new XMPPConnectionStatus(PostingConnectionChangeListener.DISCONNECTED));
    }

    private void givenUserDataAreEmpty() {
        editTextLogin.setText("");
        editTextPassword.setText("");
    }

}