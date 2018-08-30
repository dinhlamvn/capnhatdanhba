package android.vn.lcd.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ScreenPreferences {

    private final String PREFERENCES_FILE_NAME = "update_contact_app";
    private final SharedPreferences sharedPreferences;
    private static ScreenPreferences instance;


    private ScreenPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static ScreenPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new ScreenPreferences(context);
        }
        return instance;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setFirstRunApp(boolean isFirstRun) {
        sharedPreferences.edit().putBoolean("IS_FIRST_RUN_APP", isFirstRun).apply();
    }

    public boolean getFirstRunApp() {
        return sharedPreferences.getBoolean("IS_FIRST_RUN_APP", true);
    }
}
