package com.pz.supportchat.login_to_chat;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;
import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.PostingConnectionChangeListener;
import com.pz.supportchat.R;
import org.mockito.Mock;
import static com.pz.supportchat.PostingConnectionChangeListener.XMPPConnectionStatus;
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

        assertThat(editTextLogin).hasError("You need choose a nickname.");
    }

    private void givenAppIsConnectedToTheServer() {
        testedActivity.onConnectionStatusChange(new XMPPConnectionStatus(PostingConnectionChangeListener.CONNECTED));
    }

    private void givenAppIsNotConnectedToTheServer() {
        testedActivity.onConnectionStatusChange(new XMPPConnectionStatus(PostingConnectionChangeListener.DISCONNECTED));
    }

    private void givenUserDataAreEmpty() {
        editTextLogin.setText("");
        editTextPassword.setText("");
    }

}