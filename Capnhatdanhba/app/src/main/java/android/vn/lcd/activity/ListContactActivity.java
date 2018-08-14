package android.vn.lcd.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.lcd.vn.capnhatdanhba.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.vn.lcd.data.Contact;
import android.vn.lcd.interfaces.ViewConstructor;
import android.vn.lcd.adapter.ContactAdapter;
import android.vn.lcd.sql.ContactHelper;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListContactActivity extends AppCompatActivity implements ViewConstructor{


    private RecyclerView mListContactView;
    private ContactAdapter mAdapter;
    private ContactHelper contactHelper;
    private ArrayList<Contact> mDataList;
    Button btnAutoSync;
    private LinearLayout layoutBackgroundButton;
    private boolean isUpdate = true;
    private int idMenu = R.id.item1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        initParams();

        initLayout();

        initListener();

        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1: {
                idMenu = id;
                item.setChecked(true);
                break;
            }
            case R.id.item2: {
                idMenu = id;
                item.setChecked(true);
                break;
            }
            case R.id.item3: {
                idMenu = id;
                item.setChecked(true);
                break;
            }
            case R.id.item4: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initParams() {
        contactHelper = new ContactHelper(getApplicationContext());
    }

    @Override
    public void initLayout() {
        btnAutoSync = (Button) findViewById(R.id.btn_auto);
        mListContactView = findViewById(R.id.list_contact);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mListContactView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void initListener() {

        btnAutoSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (idMenu) {
                    case R.id.item1: {
                        createDialogConfirmAutoSync();
                        break;
                    }
                    case R.id.item2: {
                        createDialogConfirmUpdatePhoneByName();
                        break;
                    }
                    case R.id.item3: {
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void loadData() {
        mDataList = contactHelper.getContactList();
        Collections.sort(mDataList, new SortName());
        mAdapter = new ContactAdapter(getApplicationContext(), mDataList);
        mListContactView.setAdapter(mAdapter);
    }


    private void createDialogConfirmAutoSync() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_auto_sync);
        dialog.setCancelable(true);

        final Button btnYes = (Button) dialog.findViewById(R.id.btn_ok);
        final Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final RadioButton rdbUpdate = (RadioButton) dialog.findViewById(R.id.update_selector);
        final RadioButton rdbRestore = (RadioButton) dialog.findViewById(R.id.restore_selector);
        final TextView txtDetails = (TextView) dialog.findViewById(R.id.text_details);

        rdbUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isUpdate = false;
            }
        });

        rdbRestore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isUpdate = true;
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                new SyncContact().execute(isUpdate);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        txtDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog details = new Dialog(ListContactActivity.this);
                details.requestWindowFeature(Window.FEATURE_NO_TITLE);
                details.setContentView(R.layout.dialog_details);
                details.setCancelable(true);
                details.show();
            }
        });

        dialog.show();
    }

    private void createDialogConfirmUpdatePhoneByName() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_phone_by_name);
        dialog.setCancelable(true);
        dialog.show();
    }

    class SortName implements Comparator<Contact> {

        @Override
        public int compare(Contact contact, Contact t1) {
            return contact.getName().compareTo(t1.getName());
        }
    }

    class SyncContact extends AsyncTask<Boolean, Void, Void> {

        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(ListContactActivity.this);
            mDialog.setTitle("");
            mDialog.setMessage("Đang cập nhật...");
            mDialog.setCancelable(false);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Boolean... booleans) {

            boolean isUpdate = booleans[0];

            contactHelper.updateContactList(mDataList, isUpdate);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mDialog.isShowing()) {
                mDialog.setMessage("Đang load lại dữ liệu...");
            }

            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDataList.clear();
                    mDataList.addAll(contactHelper.getContactList());
                    Collections.sort(mDataList, new SortName());
                    mAdapter.notifyDataSetChanged();
                    mDialog.dismiss();
                }
            }, 1500);
        }
    }
}
