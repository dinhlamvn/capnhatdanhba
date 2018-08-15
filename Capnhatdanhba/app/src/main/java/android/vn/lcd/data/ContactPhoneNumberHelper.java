package android.vn.lcd.data;

import android.content.Context;
import android.lcd.vn.capnhatdanhba.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ContactPhoneNumberHelper {

    private Context mContext;

    private HashMap<String, List<String>> networks = new HashMap<>();
    private Set<Map.Entry<String, List<String>>> dataSets;

    public ContactPhoneNumberHelper(Context context) {
        this.mContext = context;
        networks.put("VIETTLE_OLD", Arrays.asList(context.getResources().getStringArray(R.array.viettels)));
        networks.put("VIETTLE_NEW", Arrays.asList(context.getResources().getStringArray(R.array.viettels_new)));
        networks.put("MOBIPHONE_OLD", Arrays.asList(context.getResources().getStringArray(R.array.mobifones)));
        networks.put("MOBIPHONE_NEW", Arrays.asList(context.getResources().getStringArray(R.array.mobifones_new)));
        networks.put("VINAPHONE_OLD", Arrays.asList(context.getResources().getStringArray(R.array.vinaphones)));
        networks.put("VINAPHONE_NEW", Arrays.asList(context.getResources().getStringArray(R.array.vinaphones_new)));
        networks.put("VIETNAMMOBILE_OLD", Arrays.asList(context.getResources().getStringArray(R.array.vietnammobile)));
        networks.put("VIETNAMMOBILE_NEW", Arrays.asList(context.getResources().getStringArray(R.array.vietnammobile_new)));
        networks.put("GMOBILE_OLD", Arrays.asList(context.getResources().getStringArray(R.array.gmobile)));
        networks.put("GMOBILE_NEW", Arrays.asList(context.getResources().getStringArray(R.array.gmobile_new)));
        dataSets = networks.entrySet();
    }

    protected String changePhoneNumber(String phoneNumber, boolean isUpdate) {

        if (formatPhoneNumberToStartWithZero(phoneNumber).length() == 10 && isUpdate) {
            return formatPhoneNumberToStartWithZero(phoneNumber);
        }

        if (!isUpdate && formatPhoneNumberToStartWithZero(phoneNumber).length() != 10) {
            return formatPhoneNumberToStartWithZero(phoneNumber);
        }
        return changePhone(phoneNumber, isUpdate);
    }

    private HashMap<String, String> getFormatPhoneNumber(String phoneNumber) {

        HashMap<String, String> result = new HashMap<>();
        phoneNumber = formatPhoneNumberToStartWithZero(phoneNumber);

        for (Map.Entry<String, List<String>> e : dataSets) {

            boolean isFound = false;

            List<String> startPhoneList = e.getValue();

            for (String s : startPhoneList) {
                if (phoneNumber.startsWith(s)) {
                    result.put("KEY", e.getKey());
                    result.put("NUMBER", s);
                    isFound = true;
                    break;
                }
            }

            if (isFound) {
                break;
            }
        }

        return result;
    }

    private String changePhone(String phoneNumber, boolean isUpdate) {

        HashMap<String, String> formats = getFormatPhoneNumber(phoneNumber);

        if (!formats.isEmpty()) {

            List<String> oldList = new ArrayList<>();
            List<String> newList = new ArrayList<>();

            String KEY = formats.get("KEY").split("_")[0];
            String NUMBER = formats.get("NUMBER");

            String newStartNumber = "";

            for (Map.Entry<String, List<String>> e : dataSets) {

                if (e.getKey().startsWith(KEY) && e.getKey().endsWith("OLD")) {
                    oldList = e.getValue();
                }

                if (e.getKey().startsWith(KEY) && e.getKey().endsWith("NEW")) {
                    newList = e.getValue();
                }
            }

            if (newList.size() > 0 && oldList.size() > 0) {
                if (isUpdate) {
                    if (oldList.indexOf(NUMBER) > -1) {
                        newStartNumber = newList.get(oldList.indexOf(NUMBER));
                    }
                } else {
                    if (newList.indexOf(NUMBER) > -1) {
                        newStartNumber = oldList.get(newList.indexOf(NUMBER));
                    }
                }
                if (!newStartNumber.equals("")) {
                    return newStartNumber + phoneNumber.substring(NUMBER.length(), phoneNumber.length());
                }
            }
        }

        return phoneNumber;
    }

    private String formatPhoneNumberToStartWithZero(String phoneNumber) {

        char[] s = phoneNumber.toCharArray();
        StringBuilder pBuilder = new StringBuilder();
        for (char value : s) {
            if (value >= '0' && value <= '9') {
                pBuilder.append(value);
            }
        }
        phoneNumber = pBuilder.toString();

        if (phoneNumber.startsWith("84")) {
            return "0" + phoneNumber.substring(2, phoneNumber.length());
        }
        return phoneNumber;
    }

    public static String formatPhoneNumber(String phoneNumber) {
        // Format phone number to: xxx xxx xx xx

        if (phoneNumber.length() != 10) {
            // Don't format with phone number has length different 10
            return phoneNumber;
        }

        StringBuilder sb = new StringBuilder(phoneNumber);

        // Insert white space to after 3 element
        sb.insert(3, " ");

        // Insert white space to after 3 element
        sb.insert(7, " ");

        // Insert white space to after 2 element
        sb.insert(10, " ");

        return sb.toString();
    }

    public static String formatPhoneNumberWithoutWhiteSpace(String phoneNumber) {

        // Create string buffer from string phone number
        StringBuilder sb = new StringBuilder(phoneNumber);

        // Create variable to storage index of while space
        int i;

        // Remove white space from phone number util it hasn't white space
        while ((i = sb.indexOf(" ")) != -1) {
            sb.deleteCharAt(i);
        }

        return sb.toString();
    }

    public static String changeStartNumberPhone(String phoneNumber, String o, String n) {
        return n + phoneNumber.substring(o.length(), phoneNumber.length());
    }
}
