package android.vn.lcd.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.lcd.vn.capnhatdanhba.R;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.vn.lcd.data.Contact;
import android.vn.lcd.data.ContactPhoneNumberHelper;
import android.vn.lcd.interfaces.IViewConstructor;
import android.vn.lcd.adapter.ContactAdapter;
import android.vn.lcd.sql.ContactHelper;
import android.vn.lcd.utils.UpdateContactTask;
import android.vn.lcd.utils.UpdateStartNumberTask;
import android.vn.lcd.utils.ValueFactory;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListContactActivity extends AppCompatActivity implements IViewConstructor {


    private ContactAdapter mAdapter;
    private ArrayList<Contact> mDataList;
    private String typeValue;
    private ArrayList<Contact> mUpdateList;
    private ActionBar mActionbar;
    private ProgressDialog mDialog;
    private EditText edtOldPhoneNumber;
    private EditText edtNewPhoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list_contact);

        initParams();

        loadData();

        initLayout();

        initListener();

        setUpTitle();

        setUpSubTitleActionBar(mUpdateList.size());
    }

    private void setUpTitle() {
        switch (typeValue) {
            case "11-TO-10":
                setUpTitleActionBar(getResources().getString(R.string.option_1));
                break;
            case "10-TO-11":
                setUpTitleActionBar(getResources().getString(R.string.option_2));
                break;
            case "CUSTOM":
                setUpTitleActionBar(getResources().getString(R.string.option_3));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setUpTitleActionBar(String title) {
        if (mActionbar != null) {
            mActionbar.setTitle(title);
        }
    }

    private void setUpSubTitleActionBar(int size) {
        if (mActionbar != null) {
            mActionbar.setSubtitle("Tìm được " + size + " số");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!typeValue.equals("CUSTOM")) {
            MenuInflater menuInflater = getMenuInflater();

            menuInflater.inflate(R.menu.main_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.select_all:{
                item.setChecked(true);
                refreshList("ALL");
                break;
            }
            case R.id.select_viettle: {
                item.setChecked(true);
                refreshList("VIETTLE");
                break;
            }
            case R.id.select_mobi: {
                item.setChecked(true);
                refreshList("MOBIPHONE");
                break;
            }
            case R.id.select_vina: {
                item.setChecked(true);
                refreshList("VINAPHONE");
                break;
            }
            case R.id.select_vietnam: {
                item.setChecked(true);
                refreshList("VIETNAMOBILE");
                break;
            }
            case R.id.select_gmobile: {
                item.setChecked(true);
                refreshList("GMOBILLE");
                break;
            }
        }

        mAdapter.notifyDataSetChanged();
        setUpSubTitleActionBar(mUpdateList.size());

        return true;
    }

    @Override
    public void initParams() {
        mActionbar = getSupportActionBar();
        Bundle bundle = getIntent().getBundleExtra("DATA");
        typeValue = bundle.getString("TYPE_VALUE");
    }

    @Override
    public void initLayout() {

        LinearLayout layout = findViewById(R.id.layout);
        layout.setBackgroundColor(Color.rgb(255, 255, 255));

        LinearLayout.LayoutParams llp;


        if (typeValue.equals("CUSTOM")) {
            edtOldPhoneNumber = new EditText(this);
            llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            llp.topMargin = 15;
            llp.leftMargin = 15;
            llp.rightMargin = 15;
            edtOldPhoneNumber.setLayoutParams(llp);
            edtOldPhoneNumber.setHint(getResources().getString(R.string.edt_start_number_hint));
            edtOldPhoneNumber.setTextSize(14f);
            edtOldPhoneNumber.setTextColor(Color.rgb(34,34,34));
            edtOldPhoneNumber.setInputType(InputType.TYPE_CLASS_PHONE);
            edtOldPhoneNumber.setBackgroundResource(android.R.drawable.edit_text);
            edtOldPhoneNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String startNumber = s.toString();
                    refreshListStartNumber(startNumber);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            layout.addView(edtOldPhoneNumber);

            edtNewPhoneNumber = new EditText(this);
            llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            llp.leftMargin = 15;
            llp.rightMargin = 15;
            edtNewPhoneNumber.setLayoutParams(llp);
            edtNewPhoneNumber.setHint(getResources().getString(R.string.edt_replace_start_number_hint));
            edtNewPhoneNumber.setTextSize(14f);
            edtNewPhoneNumber.setTextColor(Color.rgb(34,34,34));
            edtNewPhoneNumber.setInputType(InputType.TYPE_CLASS_PHONE);
            edtNewPhoneNumber.setBackgroundResource(android.R.drawable.edit_text);
            layout.addView(edtNewPhoneNumber);
        }

        FrameLayout frameList = new FrameLayout(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );
        frameList.setLayoutParams(llp);

        RecyclerView mRecycleView = new RecyclerView(this);
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        mRecycleView.setLayoutParams(flp);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(mAdapter);
        frameList.addView(mRecycleView);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.save_icon);
        ImageView btnSync = new ImageView(this);
        flp = new FrameLayout.LayoutParams(
                (int)(bitmap.getWidth() * 0.51),
                (int)(bitmap.getHeight() * 0.51)
        );
        flp.gravity = Gravity.BOTTOM | Gravity.END;
        flp.rightMargin = (int)(ValueFactory.getScreenWidth() * 0.025);
        flp.bottomMargin = (int)(ValueFactory.getScreenWidth() * 0.11);
        btnSync.setLayoutParams(flp);
        btnSync.setImageBitmap(bitmap);
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (typeValue.equals("CUSTOM")) {
                    if (edtOldPhoneNumber != null && edtNewPhoneNumber != null && (edtOldPhoneNumber.getText().toString().equals(""))
                            ||edtNewPhoneNumber.getText().toString().equals("")) {
                        Toast.makeText(ListContactActivity.this, "Chưa nhập dữ liệu vào", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (mUpdateList.size() > 0) {
                    String title = getResources().getString(R.string.confirm_title);
                    String btnYes = getResources().getString(R.string.btn_yes);
                    String btnNo = getResources().getString(R.string.btn_cancel);
                    String message = "";

                    switch (typeValue) {
                        case "CUSTOM":
                            message = "Bạn muốn chuyển đổi <b>" + mUpdateList.size() + "</b> số có đầu số <b>"
                                    + edtOldPhoneNumber.getText().toString()
                                    + "</b> sang đầu số <b>"
                                    + edtNewPhoneNumber.getText().toString()
                                    + "</b>?";
                            break;
                        case "11-TO-10":
                            message = "Bạn muốn chuyển đổi <b>" + mUpdateList.size() + "</b> số có <b>11 số</b> sang số có <b>10 số</b>?";
                            break;
                        case "10-TO-11":
                            message = "Bạn muốn chuyển đổi <b>" + mUpdateList.size() + "</b> số có <b>10 số</b> sang số có <b>11 số</b>?";
                            break;
                    }

                    AlertDialog alertDialog = new AlertDialog.Builder(ListContactActivity.this)
                            .setTitle(title)
                            .setMessage(Html.fromHtml(message))
                            .setCancelable(true)
                            .setPositiveButton(btnYes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (typeValue) {
                                        case "11-TO-10":
                                            new UpdateContactTask(ListContactActivity.this, mUpdateList).execute(true);
                                            break;
                                        case "10-TO-11":
                                            new UpdateContactTask(ListContactActivity.this, mUpdateList).execute(false);
                                            break;
                                        case "CUSTOM":
                                            String a = edtOldPhoneNumber.getText().toString();
                                            String b = edtNewPhoneNumber.getText().toString();
                                            new UpdateStartNumberTask(ListContactActivity.this).execute(a, b);
                                            break;
                                    }
                                }
                            })
                            .setNegativeButton(btnNo, null)
                            .create();
                    alertDialog.show();
                } else {
                    Toast.makeText(ListContactActivity.this, getResources().getString(R.string.do_empty_list), Toast.LENGTH_SHORT).show();
                }

            }
        });

        frameList.addView(btnSync);

        layout.addView(frameList);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {
        ContactHelper.getInstance(getApplicationContext()).loadContact();
        if (typeValue.equals("11-TO-10")) {
            ContactHelper.getInstance(getApplicationContext()).filterList11();
        } else if (typeValue.equals("10-TO-11")) {
            ContactHelper.getInstance(getApplicationContext()).filterList10();
        } else if (typeValue.equals("CUSTOM")) {
            ContactHelper.getInstance(getApplicationContext()).filterListCustom();
        }
        mDataList = new ArrayList<>(ContactHelper.getInstance(getApplicationContext()).getContacts());
        mUpdateList = new ArrayList<>(ContactHelper.getInstance(getApplicationContext()).getContacts());
        mAdapter = new ContactAdapter(this, mUpdateList, typeValue);
    }

    private void refreshList(String networkName) {
        mUpdateList.clear();
        if (networkName.toLowerCase().equals("all")) {
            mUpdateList.addAll(mDataList);
            return;
        }

        for (Contact contact : mDataList) {

            if (typeValue.equals("11-TO-10")) {
                if (ContactPhoneNumberHelper
                        .getNetworkNameByPhoneNumber11(this, contact.getMobilePhone()).equals(networkName)) {
                    mUpdateList.add(contact);
                }
            } else if (typeValue.equals("10-TO-11")) {
                if (ContactPhoneNumberHelper
                        .getNetworkNameByPhoneNumber10(this, contact.getMobilePhone()).equals(networkName)) {
                    mUpdateList.add(contact);
                }
            }
        }
    }

    private void refreshListStartNumber(String startNumber) {
        mUpdateList.clear();

        for (Contact contact : mDataList) {

            String pn = ContactPhoneNumberHelper.formatPhoneNumberWithoutWhiteSpace(contact.getMobilePhone());
            if (pn.startsWith(startNumber)) {
                mUpdateList.add(contact);
            }
        }
        mAdapter.notifyDataSetChanged();
        setUpSubTitleActionBar(mUpdateList.size());
    }
}
