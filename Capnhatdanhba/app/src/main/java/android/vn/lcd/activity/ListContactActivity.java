package android.vn.lcd.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.lcd.vn.capnhatdanhba.R;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.vn.lcd.utils.ScreenPreferences;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ListContactActivity extends AppCompatActivity implements ViewConstructor {


    private RecyclerView mListContactView;
    private ContactAdapter mAdapter;
    private ContactHelper contactHelper;
    private ArrayList<Contact> mDataList;
    private boolean isUpdate = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list_contact);

        initParams();

        initLayout();

        initListener();

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                loadAutoChangeHeadNumberScreen();
                break;
            }
            case R.id.item2: {
                createDialogConfirmUpdateStartNumber();
                break;
            }
            case R.id.item3: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initParams() {
        contactHelper = ContactHelper.getInstance(getApplicationContext());
    }

    @Override
    public void initLayout() {
        mListContactView = findViewById(R.id.list_contact);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mListContactView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {
        mDataList = contactHelper.getContactList();
        Collections.sort(mDataList, new SortName());
        mAdapter = new ContactAdapter(getApplicationContext(), mDataList);
        mListContactView.setAdapter(mAdapter);
    }

    private void loadAutoChangeHeadNumberScreen() {
        Intent intent = new Intent(this, AutoChangeHeadNumberActivity.class);
        startActivity(intent);
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

    private void createDialogConfirmUpdateStartNumber() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_handmade);
        dialog.setCancelable(true);

        final Button btnYes = (Button) dialog.findViewById(R.id.btn_ok);
        final Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final EditText edtStartNumber = (EditText) dialog.findViewById(R.id.start_number);
        final EditText edtReplaceStartNumber = (EditText) dialog.findViewById(R.id.replace_start_number);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String a = edtStartNumber.getText().toString().trim();
                final String b = edtReplaceStartNumber.getText().toString().trim();
                if (!a.equals("") && !b.equals("")) {

                    String title = getResources().getString(R.string.confirm_title);
                    String message = getResources().getString(R.string.confirm_text);
                    String btnYes = getResources().getString(R.string.btn_yes);
                    String btnNo = getResources().getString(R.string.btn_cancel);


                    AlertDialog alertDialog = new AlertDialog.Builder(ListContactActivity.this)
                            .setTitle(title)
                            .setMessage(message)
                            .setCancelable(true)
                            .setPositiveButton(btnYes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialog.dismiss();
                                    new SyncContactWithStartNumber().execute(a,b);
                                }
                            })
                            .setNegativeButton(btnNo, null)
                            .create();
                    alertDialog.show();
                }
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

        dialog.show();
    }

    class SortName implements Comparator<Contact> {

        @Override
        public int compare(Contact contact, Contact t1) {
            return contact.getName().compareTo(t1.getName());
        }
    }

    class SyncContact extends AsyncTask<Boolean, Void, HashMap<String, String>> {

        @Override
        protected HashMap<String, String> doInBackground(Boolean... booleans) {
            return null;
        }
    }

    class SyncContactWithStartNumber extends AsyncTask<String, Void, HashMap<String, String>> {

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
        protected HashMap<String, String> doInBackground(String... strings) {

            String oldStartNumber = strings[0];
            String newStartNumber = strings[1];
            int cnt = 0;
            HashMap<String, String> hashMap = new HashMap<>();
            StringBuilder sb = new StringBuilder();

            List<HashMap<String, HashMap<String, String>>> resultSet = contactHelper.updateContactListWithStartNumber(mDataList, oldStartNumber, newStartNumber);


            for (HashMap<String, HashMap<String, String>> hm : resultSet) {
                Set<String> keys = hm.keySet();

                for (String s : keys) {
                    HashMap<String, String> hmTempt = hm.get(s);
                    cnt = cnt + hmTempt.size();

                    if (hmTempt.size() > 0) {
                        sb.append("[").append(s).append("]");
                        sb.append("\n");

                        List<String> listKey = new ArrayList<>(hmTempt.keySet());

                        for (int i = 0; i < listKey.size(); i = i + 2) {
                            sb.append("\"" + hmTempt.get(listKey.get(i)) + "\"");
                            sb.append(" -> ");
                            sb.append("\"" + hmTempt.get(listKey.get(i + 1)) + "\"");
                            sb.append("\n");
                        }
                    }
                }
            }

            hashMap.put("TOTAL", String.valueOf(cnt / 2));
            hashMap.put("DETAILS", sb.toString());

            return hashMap;
        }

        @Override
        protected void onPostExecute(HashMap<String, String> hm) {
            super.onPostExecute(hm);
            if (mDialog.isShowing()) {
                mDialog.setMessage("Đang load lại dữ liệu...");
            }
            final int resultTotal = Integer.parseInt(hm.get("TOTAL"));
            final String resultDetails = hm.get("DETAILS");
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDataList.clear();
                    mDataList.addAll(contactHelper.getContactList());
                    Collections.sort(mDataList, new SortName());
                    mAdapter.notifyDataSetChanged();
                    mDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListContactActivity.this)
                            .setTitle("Thông báo")
                            .setMessage("Đã cập nhật " + resultTotal + " số điện thoại")
                            .setCancelable(false)
                            .setPositiveButton("Thoát", null);
                    if (resultTotal > 0) {
                        builder.setNegativeButton("Xem chi tiết", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Dialog dialog = new Dialog(ListContactActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialog_details_update);
                                dialog.setCancelable(true);
                                final TextView txtDetails = (TextView) dialog.findViewById(R.id.txt_details);

                                txtDetails.setText(resultDetails);

                                dialog.show();
                            }
                        });
                    }
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }, 1500);
        }
    }
}
