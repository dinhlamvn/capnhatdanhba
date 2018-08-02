package android.vn.lcd.sql;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactHelper {

    private Context mContext;

    public ContactHelper(Context context) {
        mContext = context;
    }

    public void updateContactPhoneNumber(ContentResolver contentResolver,
                                         long contactId, int phoneType, String newPhoneNumber) {
        /* Content value object. */
        ContentValues values = new ContentValues();

        /* Put new phone number to content value */
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, newPhoneNumber);

        /* Create query condition with contact id */
        StringBuffer whereClause = new StringBuffer();

        /* Specify update with contact id */
        whereClause.append(ContactsContract.Data.RAW_CONTACT_ID);
        whereClause.append("=");
        whereClause.append(contactId);

        /* Specify the row data mimetype to phone mimetype */
        whereClause.append(" and ");
        whereClause.append(ContactsContract.Data.MIMETYPE);
        whereClause.append("='");
        whereClause.append(ContactsContract.CommonDataKinds.Phone.MIMETYPE);
        whereClause.append("'");

        /* Specify phone by type */
        whereClause.append(" and ");
        whereClause.append(ContactsContract.CommonDataKinds.Phone.TYPE);
        whereClause.append("=");
        whereClause.append(phoneType);

        /* Update phone info */
        Uri uri = ContactsContract.Data.CONTENT_URI;

        /* Get update data count */
        int updateCount = contentResolver.update(uri, values, whereClause.toString(), null);
    }
}
