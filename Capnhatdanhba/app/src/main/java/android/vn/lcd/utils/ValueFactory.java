package android.vn.lcd.utils;

import android.content.Context;
import android.content.res.Resources;
import android.lcd.vn.capnhatdanhba.R;

import java.util.Arrays;
import java.util.List;

public class ValueFactory {

    private Context context;
    private static ValueFactory instance;

    private ValueFactory(Context context) {
        this.context = context;
    }

    public static ValueFactory getInstance(Context context) {
        if (instance == null) {
            instance = new ValueFactory(context);
        }
        return instance;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public List<String> getViettleList() {
        String[] arr = context.getResources().getStringArray(R.array.viettels);
        return Arrays.asList(arr);
    }

    public List<String> getViettleListNew() {
        String[] arr = context.getResources().getStringArray(R.array.viettels_new);
        return Arrays.asList(arr);
    }

    public List<String> getMobiList() {
        String[] arr = context.getResources().getStringArray(R.array.mobifones);
        return Arrays.asList(arr);
    }

    public List<String> getMobiListNew() {
        String[] arr = context.getResources().getStringArray(R.array.mobifones_new);
        return Arrays.asList(arr);
    }

    public List<String> getVinaList() {
        String[] arr = context.getResources().getStringArray(R.array.vinaphones);
        return Arrays.asList(arr);
    }

    public List<String> getVinaListNew() {
        String[] arr = context.getResources().getStringArray(R.array.vinaphones_new);
        return Arrays.asList(arr);
    }

    public List<String> getVietnamList() {
        String[] arr = context.getResources().getStringArray(R.array.vietnammobile);
        return Arrays.asList(arr);
    }

    public List<String> getVietnamListNew() {
        String[] arr = context.getResources().getStringArray(R.array.vietnammobile_new);
        return Arrays.asList(arr);
    }

    public List<String> getGMobileList() {
        String[] arr = context.getResources().getStringArray(R.array.gmobile);
        return Arrays.asList(arr);
    }

    public List<String> getGMobileListNew() {
        String[] arr = context.getResources().getStringArray(R.array.gmobile_new);
        return Arrays.asList(arr);
    }
}
