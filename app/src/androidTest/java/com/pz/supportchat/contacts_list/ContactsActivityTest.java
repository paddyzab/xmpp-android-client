package com.pz.supportchat.contacts_list;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.pz.supportchat.R;
import static org.assertj.android.api.Assertions.assertThat;

public class ContactsActivityTest extends ActivityInstrumentationTestCase2<ContactsActivity> {

    private ContactsActivity testedActivity;
    private ListView contactsListView;
    private TextView textViewEmpty;
    private Button buttonAddContact;


    public ContactsActivityTest() {
        super(ContactsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testedActivity = getActivity();

        contactsListView = (ListView) testedActivity.findViewById(R.id.contactsListView);
        buttonAddContact = (Button) testedActivity.findViewById(R.id.buttonAddContact);
        textViewEmpty = (TextView) testedActivity.findViewById(R.id.empty);
    }

    public void testStartsLoginActivityIfUserWasNotLoggedIn() {
        assertThat(textViewEmpty).isVisible();
    }
}