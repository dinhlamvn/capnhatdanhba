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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactHelper extends ContactPhoneNumberHelper implements ContactHelperInterface {

    private Context mContext;

    public ContactHelper(Context context) {
        mContext = context;
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

        return results;
    }

    @Override
    public boolean updateSingleContact(int contactId, String newPhoneNumber, int typePhoneNumber) {
        return updateContact(contactId, newPhoneNumber, typePhoneNumber);
    }

    @Override
    public boolean updateContactList(List<Contact> listContact) {

        for (Contact contact : listContact) {

            if (contact.isHasPhone()) {

                if (!contact.getHomePhone().equals("")) {
                    String pn = contact.getHomePhone();
                    updateSingleContact(
                            contact.getId(),
                            changePhoneNumberWithFormat(pn, getFormatPhoneNumber(pn)),
                            ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
                }

                if (!contact.getMobilePhone().equals("")) {
                    String pn = contact.getMobilePhone();
                    updateSingleContact(
                            contact.getId(),
                            changePhoneNumberWithFormat(pn, getFormatPhoneNumber(pn)),
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                }

                if (!contact.getWorkPhone().equals("")) {
                    String pn = contact.getWorkPhone();
                    updateSingleContact(
                            contact.getId(),
                            changePhoneNumberWithFormat(pn, getFormatPhoneNumber(pn)),
                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE);
                }

            }

        }
        return false;
    }


    private boolean updateContact(int contactId, String newPhone, int type) {

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
            mContext.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            return true;
        } catch (RemoteException ex) {
            return false;
        } catch (OperationApplicationException e) {
            return false;
        }
    }


}
