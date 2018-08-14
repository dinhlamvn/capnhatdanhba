package android.vn.lcd.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.lcd.vn.capnhatdanhba.R;
import android.provider.ContactsContract;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements ViewConstructor {


    Button btnLoadContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        initListener();
    }

    private void loadScreenContactList() {
        Intent intent = new Intent(MainActivity.this, ListContactActivity.class);
        startActivity(intent);
    }


    @Override
    public void initParams() {

    }

    @Override
    public void initLayout() {
        btnLoadContact = findViewById(R.id.btn_load);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        llp.gravity = Gravity.BOTTOM;
        llp.bottomMargin = 20;
        btnLoadContact.setLayoutParams(llp);
        btnLoadContact.setText(getResources().getString(R.string.btn_load_contact_text));
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
