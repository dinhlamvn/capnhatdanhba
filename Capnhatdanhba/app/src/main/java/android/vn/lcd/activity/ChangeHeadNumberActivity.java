package android.vn.lcd.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.lcd.vn.capnhatdanhba.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.vn.lcd.adapter.ContactAdapter;
import android.vn.lcd.data.Contact;
import android.vn.lcd.interfaces.IUpdateContactCallback;
import android.vn.lcd.interfaces.IViewConstructor;
import android.vn.lcd.sql.ContactHelper;
import android.vn.lcd.utils.UpdateStartNumberTask;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ChangeHeadNumberActivity extends Activity implements IViewConstructor, IUpdateContactCallback {

    private ContactAdapter mAdapter;
    private ArrayList<Contact> mDataList;
    private RecyclerView mRecyclerView;
    private EditText edtOldHeadNumber;
    private EditText edtNewHeadNumber;
    private Button btnAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_head_number);

        initParams();

        initLayout();

        initListener();

        loadData();
    }

    @Override
    public void initParams() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list_contact);
        edtOldHeadNumber = (EditText) findViewById(R.id.old_head_number);
        edtNewHeadNumber = (EditText) findViewById(R.id.new_head_number);
        btnAction = (Button) findViewById(R.id.btn_action);
        mDataList = ContactHelper.getInstance(getApplicationContext()).getContactList();
        mAdapter = new ContactAdapter(getApplicationContext(), mDataList);
    }

    @Override
    public void initLayout() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {

        edtOldHeadNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText = s.toString();
                mAdapter.filterByPhoneNumber(inputText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String a = edtOldHeadNumber.getText().toString().trim();
                final String b = edtNewHeadNumber.getText().toString().trim();
                if (!a.equals("") && !b.equals("")) {

                    String title = getResources().getString(R.string.confirm_title);
                    String message = getResources().getString(R.string.confirm_text);
                    String btnYes = getResources().getString(R.string.btn_yes);
                    String btnNo = getResources().getString(R.string.btn_cancel);


                    AlertDialog alertDialog = new AlertDialog.Builder(ChangeHeadNumberActivity.this)
                            .setTitle(title)
                            .setMessage(message)
                            .setCancelable(true)
                            .setPositiveButton(btnYes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    new UpdateStartNumberTask(ChangeHeadNumberActivity.this, ChangeHeadNumberActivity.this).execute(a, b);
                                }
                            })
                            .setNegativeButton(btnNo, null)
                            .create();
                    alertDialog.show();
                }
            }
        });

    }

    @Override
    public void loadData() {

    }

    @Override
    public void updateListContactView() {
        this.mDataList = ContactHelper.getInstance(getApplicationContext()).getContactList();
        mAdapter = new ContactAdapter(getApplicationContext(), this.mDataList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.filterByPhoneNumber(edtNewHeadNumber.getText().toString());
    }
}
