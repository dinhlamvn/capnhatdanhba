package android.vn.lcd.data;

public abstract class ContactPhoneNumberHelper {

    public String changePhoneNumberWithFormat(String phoneNumber, String format) {
        return changePhone(phoneNumber, format);
    }

    public String getFormatPhoneNumber(String phoneNumber) {
        return "";
    }

    private String changePhone(String p, String f) {
        return "";
    }
}
