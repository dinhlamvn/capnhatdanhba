package android.vn.lcd.sql;

import android.vn.lcd.data.Contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ContactHelperInterface {

    public ArrayList<Contact> getContactList();

    public boolean updateSingleContact(int contactId, String newPhoneNumber, int typePhoneNumber);

    public boolean updateContactList(List<Contact> listContact, boolean isUpdate);

}
