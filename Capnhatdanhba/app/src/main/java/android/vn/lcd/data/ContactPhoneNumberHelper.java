package android.vn.lcd.data;

import android.content.Context;
import android.lcd.vn.capnhatdanhba.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class ContactPhoneNumberHelper {

    private Context mContext;

    public ContactPhoneNumberHelper(Context context) {
        this.mContext = context;
    }

    protected String changePhoneNumberWithFormat(String phoneNumber, String[] format, boolean isUpdate) {
        if (changPhoneNumber(phoneNumber).length() <= 10 && isUpdate) {
            return phoneNumber;
        }
        return changePhone(phoneNumber, format, isUpdate);
    }

    protected String[] getFormatPhoneNumber(String phoneNumber, boolean isUpdate) {
        String[] result = new String[] {"", ""};
        phoneNumber = changPhoneNumber(phoneNumber);

        List<String> vts, mbs, vinas, vns, gms;

        if (isUpdate) {
            vts = Arrays.asList(mContext.getResources().getStringArray(R.array.viettels));
            mbs = Arrays.asList(mContext.getResources().getStringArray(R.array.mobifones));
            vinas = Arrays.asList(mContext.getResources().getStringArray(R.array.vinaphones));
            vns = Arrays.asList(mContext.getResources().getStringArray(R.array.vietnammobile));
            gms = Arrays.asList(mContext.getResources().getStringArray(R.array.gmobile));
        } else {
            vts = Arrays.asList(mContext.getResources().getStringArray(R.array.viettels_new));
            mbs = Arrays.asList(mContext.getResources().getStringArray(R.array.mobifones_new));
            vinas = Arrays.asList(mContext.getResources().getStringArray(R.array.vinaphones_new));
            vns = Arrays.asList(mContext.getResources().getStringArray(R.array.vietnammobile_new));
            gms = Arrays.asList(mContext.getResources().getStringArray(R.array.gmobile_new));
        }


        for (String startNumber : vts) {
            if (phoneNumber.startsWith(startNumber)) {
                result[0] = "vt";
                result[1] = startNumber;
                return result;
            }
        }

        for (String startNumber : mbs) {
            if (phoneNumber.startsWith(startNumber)) {
                result[0] = "mb";
                result[1] = startNumber;
                return result;
            }
        }

        for (String startNumber : vinas) {
            if (phoneNumber.startsWith(startNumber)) {
                result[0] = "vina";
                result[1] = startNumber;
                return result;
            }
        }

        for (String startNumber : vns) {
            if (phoneNumber.startsWith(startNumber)) {
                result[0] = "vn";
                result[1] = startNumber;
                return result;
            }
        }

        for (String startNumber : gms) {
            if (phoneNumber.startsWith(startNumber)) {
                result[0] = "gm";
                result[1] = startNumber;
                return result;
            }
        }

        return result;
    }

    private String changePhone(String p, String[] f, boolean isUpdate) {

        String result = p;
        String key = f[0];
        String number = f[1];
        int index;
        String newStartNumber;

        if (key.equals("vt")) {
            List<String> old_list = Arrays.asList(mContext.getResources().getStringArray(R.array.viettels));
            List<String> new_list = Arrays.asList(mContext.getResources().getStringArray(R.array.viettels_new));
            if (isUpdate) {
                index = old_list.indexOf(number);
                newStartNumber = new_list.get(index);
                result = newStartNumber + p.substring(4, p.length());
            } else {
                index = new_list.indexOf(number);
                newStartNumber = old_list.get(index);
                result = newStartNumber + p.substring(3, p.length());
            }
        } else if (key.equals("mb")) {
            List<String> old_list = Arrays.asList(mContext.getResources().getStringArray(R.array.mobifones));
            List<String> new_list = Arrays.asList(mContext.getResources().getStringArray(R.array.mobifones_new));
            if (isUpdate) {
                index = old_list.indexOf(number);
                newStartNumber = new_list.get(index);
                result = newStartNumber + p.substring(4, p.length());
            } else {
                index = new_list.indexOf(number);
                newStartNumber = old_list.get(index);
                result = newStartNumber + p.substring(3, p.length());
            }
        } else if (key.equals("vina")) {
            List<String> old_list = Arrays.asList(mContext.getResources().getStringArray(R.array.vinaphones));
            List<String> new_list = Arrays.asList(mContext.getResources().getStringArray(R.array.vinaphones_new));
            if (isUpdate) {
                index = old_list.indexOf(number);
                newStartNumber = new_list.get(index);
                result = newStartNumber + p.substring(4, p.length());
            } else {
                index = new_list.indexOf(number);
                newStartNumber = old_list.get(index);
                result = newStartNumber + p.substring(3, p.length());
            }
        } else if (key.equals("vn")) {
            List<String> old_list = Arrays.asList(mContext.getResources().getStringArray(R.array.vietnammobile));
            List<String> new_list = Arrays.asList(mContext.getResources().getStringArray(R.array.vietnammobile_new));
            if (isUpdate) {
                index = old_list.indexOf(number);
                newStartNumber = new_list.get(index);
                result = newStartNumber + p.substring(4, p.length());
            } else {
                index = new_list.indexOf(number);
                newStartNumber = old_list.get(index);
                result = newStartNumber + p.substring(3, p.length());
            }
        } else if (key.equals("gm")) {
            List<String> old_list = Arrays.asList(mContext.getResources().getStringArray(R.array.gmobile));
            List<String> new_list = Arrays.asList(mContext.getResources().getStringArray(R.array.gmobile_new));
            if (isUpdate) {
                index = old_list.indexOf(number);
                newStartNumber = new_list.get(index);
                result = newStartNumber + p.substring(4, p.length());
            } else {
                index = new_list.indexOf(number);
                newStartNumber = old_list.get(index);
                result = newStartNumber + p.substring(3, p.length());
            }
        }

        return result;
    }

    private String changPhoneNumber(String p) {
        char[] s = p.toCharArray();
        StringBuilder pBuilder = new StringBuilder();
        for (char value : s) {
            if (value >= '0' && value <= '9') {
                pBuilder.append(value);
            }
        }
        p = pBuilder.toString();

        if (p.startsWith("84")) {
            return "0" + p.substring(2, p.length());
        }
        return p;
    }
}
