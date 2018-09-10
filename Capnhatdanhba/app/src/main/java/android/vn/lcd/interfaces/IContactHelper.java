package android.vn.lcd.interfaces;

import android.vn.lcd.data.Contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IContactHelper {

    ArrayList<Contact> getContactList();

    int updateSingleContact(int contactId, String newPhoneNumber, int typePhoneNumber);

    List<HashMap<String, HashMap<String, String>>> updateContactList(List<Contact> listContact, boolean isUpdate);

    List<HashMap<String, HashMap<String, String>>> updateContactListWithStartNumber(List<Contact> listContact, String oldStartNumber, String newStartNumber);
}
