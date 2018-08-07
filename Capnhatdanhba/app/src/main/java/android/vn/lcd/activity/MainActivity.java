package android.vn.lcd.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.lcd.vn.capnhatdanhba.R;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.vn.lcd.adapter.ContactAdapter;
import android.vn.lcd.data.Contact;
import android.vn.lcd.sql.ContactHelper;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Button btnLoadContact;
    ContactHelper contactHelper;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactHelper = new ContactHelper(getApplicationContext());
        btnLoadContact = (Button) findViewById(R.id.load_contact);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);


        btnLoadContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Contact> lists = contactHelper.getContactList();

                ContactAdapter mAdapter = new ContactAdapter(getApplicationContext(), lists);
                recyclerView.setAdapter(mAdapter);
            }
        });
    }


}
