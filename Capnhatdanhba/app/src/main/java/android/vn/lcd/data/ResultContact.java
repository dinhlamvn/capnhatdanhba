package android.vn.lcd.data;

public class ResultContact {

    private String contactName;
    private String oldPhoneNumber;
    private String newPhoneNumber;

    public ResultContact(String contactName, String oldPhoneNumber, String newPhoneNumber) {
        this.contactName = contactName;
        this.oldPhoneNumber = oldPhoneNumber;
        this.newPhoneNumber = newPhoneNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public String getOldPhoneNumber() {
        return oldPhoneNumber;
    }

    public String getNewPhoneNumber() {
        return newPhoneNumber;
    }
}
