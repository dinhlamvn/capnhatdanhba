package android.vn.lcd.sql;

import android.vn.lcd.data.Contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ContactHelperInterface {

    public ArrayList<Contact> getContactList();

    public int updateSingleContact(int contactId, String newPhoneNumber, int typePhoneNumber);

    public List<HashMap<String, HashMap<String, String>>> updateContactList(List<Contact> listContact, boolean isUpdate);

    public List<HashMap<String, HashMap<String, String>>> updateContactListWithStartNumber(List<Contact> listContact, String oldStartNumber, String newStartNumber);

}
