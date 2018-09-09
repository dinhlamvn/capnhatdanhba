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
import android.vn.lcd.data.Contact;
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

        LinearLayout layout = findViewById(R.id.layout);

        LinearLayout.LayoutParams llp;

        TextView txtMessage = new TextView(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        txtMessage.setLayoutParams(llp);
        txtMessage.setBackgroundColor(Color.rgb(255, 255, 255));
        txtMessage.setTextSize(12);
        txtMessage.setTextColor(Color.parseColor("#343434"));
        txtMessage.setPadding(15, 15, 15, 15);
        txtMessage.setText(getResources().getString(R.string.des_main));
        txtMessage.setLineSpacing(1.2f, 1.2f);
        layout.addView(txtMessage);

        ImageView imgMain = new ImageView(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1
        );
        imgMain.setLayoutParams(llp);
        imgMain.setBackgroundResource(R.drawable.details);
        layout.addView(imgMain);


        LinearLayout btnLayout1 = new LinearLayout(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnLayout1.setLayoutParams(llp);
        btnLayout1.setOrientation(LinearLayout.HORIZONTAL);

        Button btnOption1 = new Button(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        llp.leftMargin = 5;
        llp.rightMargin = 3;
        btnOption1.setLayoutParams(llp);
        btnOption1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        btnOption1.setTypeface(Typeface.DEFAULT_BOLD);
        btnOption1.setGravity(Gravity.CENTER);
        btnOption1.setText(getResources().getString(R.string.option_1));
        btnOption1.setTextColor(Color.parseColor("#343434"));
        btnOption1.setBackgroundResource(R.drawable.button_selector);
        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadScreen(LOAD_SCREEN_CHANGE_HEAD_NUMBER_AUTO);
            }
        });
        btnLayout1.addView(btnOption1);

        Button btnOption2 = new Button(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        llp.rightMargin = 5;
        llp.leftMargin = 3;
        btnOption2.setLayoutParams(llp);
        btnOption2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        btnOption2.setTypeface(Typeface.DEFAULT_BOLD);
        btnOption2.setGravity(Gravity.CENTER);
        btnOption2.setText(getResources().getString(R.string.option_2));
        btnOption2.setTextColor(Color.parseColor("#343434"));
        btnOption2.setBackgroundResource(R.drawable.button_selector);
        btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadScreen(LOAD_SCREEN_RESTORE_HEAD_NUMBER_AUTO);
            }
        });
        btnLayout1.addView(btnOption2);

        layout.addView(btnLayout1);

        Button btnOption3 = new Button(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        llp.topMargin = 6;
        llp.leftMargin = 5;
        llp.rightMargin = 5;
        btnOption3.setLayoutParams(llp);
        btnOption3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        btnOption3.setTypeface(Typeface.DEFAULT_BOLD);
        btnOption3.setGravity(Gravity.CENTER);
        btnOption3.setText(getResources().getString(R.string.option_3));
        btnOption3.setTextColor(Color.parseColor("#343434"));
        btnOption3.setBackgroundResource(R.drawable.button_selector);
        btnOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadScreen(LOAD_SCREEN_CHANGE_HEAD_NUMBER_NOT_AUTO);
            }
        });
        layout.addView(btnOption3);

        TextView txtDescription = new TextView(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        llp.leftMargin = 5;
        llp.rightMargin = 5;
        llp.bottomMargin = 5;
        txtDescription.setLayoutParams(llp);
        txtDescription.setGravity(Gravity.CENTER);
        txtDescription.setTextSize(13);
        txtDescription.setTypeface(Typeface.DEFAULT_BOLD);
        txtDescription.setTextColor(Color.parseColor("#B6B6B6"));
        txtDescription.setText("Phát triển bởi LCD");

        layout.addView(txtDescription);
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

        if (isHasContactPermission()) {
            switch (screenIndex) {
                case LOAD_SCREEN_CHANGE_HEAD_NUMBER_AUTO: {
                    loadScreen("11-TO-10");
                    break;
                }
                case LOAD_SCREEN_RESTORE_HEAD_NUMBER_AUTO: {
                    loadScreen("10-TO-11");
                    break;
                }
                case LOAD_SCREEN_CHANGE_HEAD_NUMBER_NOT_AUTO: {
                    loadScreen("CUSTOM");
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

    private void loadScreen(String typeValue) {
        Intent intent = new Intent(MainActivity.this, ListContactActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("TYPE_VALUE", typeValue);
        intent.putExtra("DATA", bundle);
        startActivity(intent);
    }
}
