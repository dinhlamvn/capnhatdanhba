package android.vn.lcd.data;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultContact implements Serializable {

    private String contactName;
    private String oldPhoneNumber;
    private String newPhoneNumber;
    private int totalResult;
    private ArrayList<ResultContact> resultSet;

    public ResultContact() {
        this.contactName = "";
        this.oldPhoneNumber = "";
        this.newPhoneNumber = "";
        this.totalResult = 0;
        this.resultSet = new ArrayList<>();
    }

    public ResultContact(String contactName, String oldPhoneNumber, String newPhoneNumber) {
        this.contactName = contactName;
        this.oldPhoneNumber = oldPhoneNumber;
        this.newPhoneNumber = newPhoneNumber;
    }

    public void add(ResultContact resultContact) {
        resultSet.add(resultContact);
    }

    public ArrayList<ResultContact> getResultSet() {
        return resultSet;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
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

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setOldPhoneNumber(String oldPhoneNumber) {
        this.oldPhoneNumber = oldPhoneNumber;
    }

    public void setNewPhoneNumber(String newPhoneNumber) {
        this.newPhoneNumber = newPhoneNumber;
    }
}
