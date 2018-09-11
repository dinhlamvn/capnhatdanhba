package android.vn.lcd.data;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultContact implements Serializable {

    private String contactName;
    private String oldHomeNumber;
    private String newHomeNumber;
    private String oldPhoneNumber;
    private String newPhoneNumber;
    private String oldWorkNumber;
    private String newWorkNumber;
    private int totalResult;
    private ArrayList<ResultContact> resultSet;

    public ResultContact() {
        this.contactName = "";
        this.oldHomeNumber = "";
        this.newHomeNumber = "";
        this.oldPhoneNumber = "";
        this.newPhoneNumber = "";
        this.oldWorkNumber = "";
        this.newWorkNumber = "";
        this.totalResult = 0;
        this.resultSet = new ArrayList<>();
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

    public String getOldHomeNumber() {
        return oldHomeNumber;
    }

    public void setOldHomeNumber(String oldHomeNumber) {
        this.oldHomeNumber = oldHomeNumber;
    }

    public String getNewHomeNumber() {
        return newHomeNumber;
    }

    public void setNewHomeNumber(String newHomeNumber) {
        this.newHomeNumber = newHomeNumber;
    }

    public String getOldWorkNumber() {
        return oldWorkNumber;
    }

    public void setOldWorkNumber(String oldWorkNumber) {
        this.oldWorkNumber = oldWorkNumber;
    }

    public String getNewWorkNumber() {
        return newWorkNumber;
    }

    public void setNewWorkNumber(String newWorkNumber) {
        this.newWorkNumber = newWorkNumber;
    }
}
