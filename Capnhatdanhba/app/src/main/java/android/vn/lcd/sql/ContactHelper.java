package android.vn.lcd.sql;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.vn.lcd.data.Contact;
import android.vn.lcd.data.ContactPhoneNumberHelper;
import android.vn.lcd.utils.AlphabetSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ContactHelper extends ContactPhoneNumberHelper implements ContactHelperInterface {

    private Context mContext;

    private static ContactHelper instance = null;
    private ArrayList<Contact> contacts;

    private ContactHelper(Context context) {
        super(context);
        mContext = context;
    }

    public static ContactHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ContactHelper(context);
        }
        return instance;
    }

    public void loadContact() {
        contacts = getContactList();
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    @Override
    public ArrayList<Contact> getContactList() {

        // Create list to storage result
        ArrayList<Contact> results = new ArrayList<>();

        // Get content resolver
        ContentResolver contentResolver = mContext.getContentResolver();

        // Query list contact in device
        Cursor contactCursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        // Check result of contact query
        if (contactCursor != null && contactCursor.moveToFirst()) {

            // If it not null and have more zero element
            do {

                // Init data to storage contact info
                Contact data = new Contact();

                // Get id of contact
                String id = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.RawContacts._ID));
                data.setId(Integer.parseInt(id));


                // Get name of contact
                String name = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                data.setName(name);

                // Check contact has phone or no
                int hasPhoneNumber = Integer.parseInt(contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {

                    data.setHasPhone(true);

                    // Query phone number of contact
                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                            ,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[] {id}, null);

                    if (phoneCursor != null && phoneCursor.moveToFirst()) {

                        do {

                            // Get phone number of contact
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            // Get type of contact number
                            int type = phoneCursor.getInt(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

                            // Catch type for every case
                            switch (type) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME: {
                                    data.setHomePhone(phoneNumber);
                                    break;
                                }
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE: {
                                    data.setMobilePhone(phoneNumber);
                                    break;
                                }
                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE: {
                                    data.setWorkPhone(phoneNumber);
                                    break;
                                }
                            }
                        } while (phoneCursor.moveToNext());

                        phoneCursor.close();
                    }
                }
                results.add(data);
            } while (contactCursor.moveToNext());

            contactCursor.close();
        }
        Collections.sort(results, new AlphabetSort());
        return results;
    }

    public void filterList11() {
        ArrayList<Contact> list = new ArrayList<>();
        for (Contact contact : contacts) {
            if (ContactPhoneNumberHelper.isPhoneNumber11(mContext, contact.getMobilePhone())) {
                list.add(contact);
            }
        }
        contacts.clear();
        contacts.addAll(list);
    }

    public void filterList10() {
        ArrayList<Contact> list = new ArrayList<>();
        for (Contact contact : contacts) {
            if (ContactPhoneNumberHelper.isPhoneNumber10(mContext, contact.getMobilePhone())) {
                list.add(contact);
            }
        }
        contacts.clear();
        contacts.addAll(list);
    }

    public void filterListCustom() {
        ArrayList<Contact> list = new ArrayList<>();
        for (Contact contact : contacts) {
            if (!contact.getMobilePhone().equals("")
                    || !contact.getWorkPhone().equals("")
                    || !contact.getHomePhone().equals("")) {
                list.add(contact);
            }
        }
        contacts.clear();
        contacts.addAll(list);
    }


    @Override
    public int updateSingleContact(int contactId, String newPhoneNumber, int typePhoneNumber) {
        return updateContact(contactId, newPhoneNumber, typePhoneNumber);
    }

    @Override
    public List<HashMap<String, HashMap<String, String>>> updateContactList(List<Contact> listContact, boolean isUpdate) {

        List<HashMap<String, HashMap<String, String>>> resultSet = new ArrayList<>();

        for (Contact contact : listContact) {

            if (contact.isHasPhone()) {

                resultSet.add(new HashMap<String, HashMap<String, String>>());
                HashMap<String, String> hm = new HashMap<>();

                if (!contact.getHomePhone().equals("")) {

                    String currentPhoneNumber = ContactPhoneNumberHelper.formatPhoneNumberWithoutWhiteSpace(contact.getHomePhone());
                    String newPhoneNumber = ContactPhoneNumberHelper
                            .formatPhoneNumberWithoutWhiteSpace(changePhoneNumber(contact.getHomePhone(), isUpdate));

                    if (!currentPhoneNumber.equals(newPhoneNumber)) {
                        int i = this.updateSingleContact(
                                contact.getId(),
                                ContactPhoneNumberHelper.formatPhoneNumber(newPhoneNumber),
                                ContactsContract.CommonDataKinds.Phone.TYPE_HOME
                        );
                        if (i > 0) {
                            hm.put("HOME_OLD", currentPhoneNumber);
                            hm.put("HOME_NEW", newPhoneNumber);
                        }
                    }
                }

                if (!contact.getMobilePhone().equals("")) {

                    String currentPhoneNumber = ContactPhoneNumberHelper.formatPhoneNumberWithoutWhiteSpace(contact.getMobilePhone());
                    String newPhoneNumber = ContactPhoneNumberHelper
                            .formatPhoneNumberWithoutWhiteSpace(changePhoneNumber(contact.getMobilePhone(), isUpdate));

                    if (!currentPhoneNumber.equals(newPhoneNumber)) {
                        int i = this.updateSingleContact(
                                contact.getId(),
                                ContactPhoneNumberHelper.formatPhoneNumber(newPhoneNumber),
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                        );
                        if (i > 0) {
                            hm.put("MOBILE_OLD", currentPhoneNumber);
                            hm.put("MOBILE_NEW", newPhoneNumber);
                        }
                    }
                }

                if (!contact.getWorkPhone().equals("")) {

                    String currentPhoneNumber = ContactPhoneNumberHelper.formatPhoneNumberWithoutWhiteSpace(contact.getWorkPhone());
                    String newPhoneNumber = ContactPhoneNumberHelper
                            .formatPhoneNumberWithoutWhiteSpace(changePhoneNumber(contact.getWorkPhone(), isUpdate));

                    if (!currentPhoneNumber.equals(newPhoneNumber)) {
                        int i = this.updateSingleContact(
                                contact.getId(),
                                ContactPhoneNumberHelper.formatPhoneNumber(newPhoneNumber),
                                ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE
                        );

                        if (i > 0) {
                            hm.put("MOBILE_WORK_OLD", currentPhoneNumber);
                            hm.put("MOBILE_WORK_NEW", newPhoneNumber);
                        }
                    }
                }
                resultSet.get(resultSet.size() - 1).put(contact.getName(), hm);
            }

        }
        return resultSet;
    }

    @Override
    public List<HashMap<String, HashMap<String, String>>> updateContactListWithStartNumber(List<Contact> listContact, String oldStartNumber, String newStartNumber) {

        List<HashMap<String, HashMap<String, String>>> resultSet = new ArrayList<>();

        for (Contact contact : listContact) {

            if (contact.isHasPhone()) {

                resultSet.add(new HashMap<String, HashMap<String, String>>());
                HashMap<String, String> hm = new HashMap<>();

                if (!contact.getHomePhone().equals("")) {

                    String currentPhoneNumber = ContactPhoneNumberHelper.formatPhoneNumberWithoutWhiteSpace(contact.getHomePhone());

                    if (currentPhoneNumber.startsWith(oldStartNumber)) {
                        String newPhoneNumber = ContactPhoneNumberHelper
                                .formatPhoneNumberWithoutWhiteSpace(
                                        ContactPhoneNumberHelper.changeStartNumberPhone(currentPhoneNumber, oldStartNumber, newStartNumber));

                        if (!currentPhoneNumber.equals(newPhoneNumber)) {
                            int i = this.updateSingleContact(
                                    contact.getId(),
                                    ContactPhoneNumberHelper.formatPhoneNumber(newPhoneNumber),
                                    ContactsContract.CommonDataKinds.Phone.TYPE_HOME
                            );
                            if (i > 0) {
                                hm.put("HOME_OLD", currentPhoneNumber);
                                hm.put("HOME_NEW", newPhoneNumber);
                            }
                        }
                    }
                }

                if (!contact.getMobilePhone().equals("")) {

                    String currentPhoneNumber = ContactPhoneNumberHelper.formatPhoneNumberWithoutWhiteSpace(contact.getMobilePhone());

                    if (currentPhoneNumber.startsWith(oldStartNumber)) {

                        String newPhoneNumber = ContactPhoneNumberHelper
                                .formatPhoneNumberWithoutWhiteSpace(
                                        ContactPhoneNumberHelper.changeStartNumberPhone(currentPhoneNumber, oldStartNumber, newStartNumber));

                        if (!currentPhoneNumber.equals(newPhoneNumber)) {
                            int i = this.updateSingleContact(
                                    contact.getId(),
                                    ContactPhoneNumberHelper.formatPhoneNumber(newPhoneNumber),
                                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                            );
                            if (i > 0) {
                                hm.put("MOBILE_OLD", currentPhoneNumber);
                                hm.put("MOBILE_NEW", newPhoneNumber);
                            }
                        }
                    }


                }

                if (!contact.getWorkPhone().equals("")) {

                    String currentPhoneNumber = ContactPhoneNumberHelper.formatPhoneNumberWithoutWhiteSpace(contact.getWorkPhone());

                    if (currentPhoneNumber.startsWith(oldStartNumber)) {

                        String newPhoneNumber = ContactPhoneNumberHelper
                                .formatPhoneNumberWithoutWhiteSpace(
                                        ContactPhoneNumberHelper.changeStartNumberPhone(currentPhoneNumber, oldStartNumber, newStartNumber));

                        if (!currentPhoneNumber.equals(newPhoneNumber)) {
                            int i = this.updateSingleContact(
                                    contact.getId(),
                                    ContactPhoneNumberHelper.formatPhoneNumber(newPhoneNumber),
                                    ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE
                            );

                            if (i > 0) {
                                hm.put("MOBILE_WORK_OLD", currentPhoneNumber);
                                hm.put("MOBILE_WORK_NEW", newPhoneNumber);
                            }
                        }
                    }
                }
                resultSet.get(resultSet.size() - 1).put(contact.getName(), hm);

            }

        }
        return resultSet;
    }

    private int updateContact(int contactId, String newPhone, int type) {

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        String whereClause = ContactsContract.RawContacts.CONTACT_ID + " = ? AND "
                + ContactsContract.Data.MIMETYPE + " = ? AND "
                + String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE) + " = ?";

        String[] params = new String[] {
                String.valueOf(contactId),
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                String.valueOf(type)
        };

        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(whereClause, params)
                .withValue(ContactsContract.CommonDataKinds.Phone.DATA, newPhone)
                .build());
        try {
            return mContext.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops).length;
        } catch (RemoteException ex) {
            return 0;
        } catch (OperationApplicationException e) {
            return 0;
        }
    }




}
