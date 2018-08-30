package android.vn.lcd.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.lcd.vn.capnhatdanhba.R;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.vn.lcd.adapter.ContactAdapter;
import android.vn.lcd.data.Contact;
import android.vn.lcd.interfaces.ViewConstructor;
import android.vn.lcd.sql.ContactHelper;
import android.vn.lcd.utils.ScreenPreferences;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements ViewConstructor {


    Button btnLoadContact;
    ImageView imgUpdateDetails;
    TextView txtDescription;

    private final int MY_REQUEST_PERMISSION_READ_CONTACT = 1111;
    private boolean isFirstRunApp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initParams();
        startApp();
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            ScreenPreferences.getInstance(getApplicationContext()).setFirstRunApp(false);
            Intent intent = new Intent(MainActivity.this, ListContactActivity.class);
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_CONTACTS},
                    MY_REQUEST_PERMISSION_READ_CONTACT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_REQUEST_PERMISSION_READ_CONTACT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ScreenPreferences.getInstance(getApplicationContext()).setFirstRunApp(false);
                Intent intent = new Intent(MainActivity.this, ListContactActivity.class);
                startActivity(intent);
            } else {
                finish();
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

        txtDescription = findViewById(R.id.text_description);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        txtDescription.setLayoutParams(llp);
        txtDescription.setTextColor(Color.parseColor("#343434"));
        txtDescription.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        txtDescription.setText(getResources().getString(R.string.des_main));
        txtDescription.setBackgroundColor(Color.parseColor("#ffffff"));
        txtDescription.setPadding(15, 15, 15, 15);
        txtDescription.setLineSpacing(1.0f, 1.2f);


        imgUpdateDetails = findViewById(R.id.update_details);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0
        );
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        llp.weight = 1.0f;
        imgUpdateDetails.setLayoutParams(llp);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.details);
        imgUpdateDetails.setImageBitmap(bmp);

        btnLoadContact = findViewById(R.id.btn_load);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        llp.gravity = Gravity.BOTTOM;
        llp.bottomMargin = 10;
        llp.leftMargin = 5;
        llp.rightMargin = 5;
        btnLoadContact.setLayoutParams(llp);
        btnLoadContact.setText(getResources().getString(R.string.btn_load_contact_text));
        btnLoadContact.setTextColor(Color.parseColor("#ffffff"));
        btnLoadContact.setBackgroundResource(R.drawable.btn_load_background);
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
}
