package android.vn.lcd.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.lcd.vn.capnhatdanhba.R;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.vn.lcd.interfaces.IViewConstructor;
import android.vn.lcd.utils.ScreenPreferences;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements IViewConstructor {


    Button btnLoadContact;

    private View.OnClickListener listener1, listener2;

    private final int MY_REQUEST_PERMISSION_READ_CONTACT = 1111;
    private boolean isFirstRunApp = false;

    private final int LOAD_SCREEN_CHANGE_HEAD_NUMBER_AUTO = 1;
    private final int LOAD_SCREEN_RESTORE_HEAD_NUMBER_AUTO = 2;
    private final int LOAD_SCREEN_CHANGE_HEAD_NUMBER_NOT_AUTO = 3;
    private final int LOAD_SCREEN_HELP = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initParams();
        //startApp();
        initLayout();
    }

    private void startApp() {
        if (isFirstRunApp) {
            initLayout();
            initListener();
        } else {
            loadScreenContactList();
        }
    }

    private void loadScreenContactList() {


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_REQUEST_PERMISSION_READ_CONTACT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // DO NO THING
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void initParams() {
        isFirstRunApp = ScreenPreferences.getInstance(getApplicationContext())
                .getFirstRunApp();
    }

    @Override
    public void initLayout() {

        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

        LinearLayout.LayoutParams llp = null;

        ImageView imgMain = new ImageView(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        llp.topMargin = 50;
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        imgMain.setLayoutParams(llp);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.edit_contact_icon);
        imgMain.setImageBitmap(bitmap);
        layout.addView(imgMain);

        Button btnOption1 = new Button(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        llp.topMargin = 75;
        llp.leftMargin = 20;
        llp.rightMargin = 20;
        btnOption1.setLayoutParams(llp);
        btnOption1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        btnOption1.setTypeface(Typeface.DEFAULT_BOLD);
        btnOption1.setGravity(Gravity.CENTER);
        btnOption1.setText(getResources().getString(R.string.option_1));
        btnOption1.setAllCaps(true);
        btnOption1.setTextColor(Color.parseColor("#343434"));
        btnOption1.setBackgroundResource(R.drawable.button_selector);
        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadScreen(LOAD_SCREEN_CHANGE_HEAD_NUMBER_AUTO);
            }
        });
        layout.addView(btnOption1);

        Button btnOption2 = new Button(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        llp.topMargin = 25;
        llp.leftMargin = 20;
        llp.rightMargin = 20;
        btnOption2.setLayoutParams(llp);
        btnOption2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        btnOption2.setTypeface(Typeface.DEFAULT_BOLD);
        btnOption2.setGravity(Gravity.CENTER);
        btnOption2.setText(getResources().getString(R.string.option_2));
        btnOption2.setAllCaps(true);
        btnOption2.setTextColor(Color.parseColor("#343434"));
        btnOption2.setBackgroundResource(R.drawable.button_selector);
        btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadScreen(LOAD_SCREEN_RESTORE_HEAD_NUMBER_AUTO);
            }
        });
        layout.addView(btnOption2);

        Button btnOption3 = new Button(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        llp.topMargin = 25;
        llp.leftMargin = 20;
        llp.rightMargin = 20;
        btnOption3.setLayoutParams(llp);
        btnOption3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        btnOption3.setTypeface(Typeface.DEFAULT_BOLD);
        btnOption3.setGravity(Gravity.CENTER);
        btnOption3.setText(getResources().getString(R.string.option_3));
        btnOption3.setAllCaps(true);
        btnOption3.setTextColor(Color.parseColor("#343434"));
        btnOption3.setBackgroundResource(R.drawable.button_selector);
        btnOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadScreen(LOAD_SCREEN_CHANGE_HEAD_NUMBER_NOT_AUTO);
            }
        });
        layout.addView(btnOption3);

        Button btnOption4 = new Button(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        llp.topMargin = 25;
        llp.leftMargin = 20;
        llp.rightMargin = 20;
        llp.bottomMargin = 15;
        btnOption4.setLayoutParams(llp);
        btnOption4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        btnOption4.setTypeface(Typeface.DEFAULT_BOLD);
        btnOption4.setGravity(Gravity.CENTER);
        btnOption4.setText(getResources().getString(R.string.option_4));
        btnOption4.setAllCaps(true);
        btnOption4.setTextColor(Color.parseColor("#343434"));
        btnOption4.setBackgroundResource(R.drawable.button_selector);
        btnOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadScreen(LOAD_SCREEN_HELP);
            }
        });
        layout.addView(btnOption4);
    }

    @Override
    public void initListener() {
        btnLoadContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadScreenContactList();
            }
        });
    }

    @Override
    public void loadData() {

    }

    private void loadScreen(int screenIndex) {

        if (screenIndex == LOAD_SCREEN_HELP) {

            Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();

        } else if (isHasContactPermission()) {

            switch (screenIndex) {
                case LOAD_SCREEN_CHANGE_HEAD_NUMBER_AUTO: {
                    loadScreen("CHANGE_HEAD_NUMBER_AUTO");
                    break;
                }
                case LOAD_SCREEN_RESTORE_HEAD_NUMBER_AUTO: {
                    loadScreen("CHANGE_HEAD_NUMBER_AUTO");
                    break;
                }
                case LOAD_SCREEN_CHANGE_HEAD_NUMBER_NOT_AUTO: {
                    loadScreen("CHANGE_HEAD_NUMBER_NOT_AUTO");
                    break;
                }
            }
        }
    }

    private boolean isHasContactPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_CONTACTS},
                    MY_REQUEST_PERMISSION_READ_CONTACT);
        }
        return false;
    }

    private void loadScreen(String screenName) {
        Intent intent = null;
        if (screenName.equals("CHANGE_HEAD_NUMBER_AUTO")) {
            intent = new Intent(MainActivity.this, AutoChangeHeadNumberActivity.class);
        } else {
            intent = new Intent(MainActivity.this, ChangeHeadNumberActivity.class);
        }
        startActivity(intent);
    }
}
