package android.vn.lcd.utils;

import android.vn.lcd.data.Contact;

import java.util.Comparator;

public class AlphabetSort implements Comparator<Contact> {
    @Override
    public int compare(Contact contact, Contact t1) {
        return contact.getName().compareTo(t1.getName());
    }
}
