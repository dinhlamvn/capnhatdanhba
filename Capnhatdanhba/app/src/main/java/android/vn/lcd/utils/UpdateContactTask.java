package android.vn.lcd.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.lcd.vn.capnhatdanhba.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.vn.lcd.activity.ListContactActivity;
import android.vn.lcd.activity.ResultActivity;
import android.vn.lcd.data.Contact;
import android.vn.lcd.data.ResultContact;
import android.vn.lcd.sql.ContactHelper;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@SuppressWarnings("StaticFieldLeak")
public class UpdateContactTask extends AsyncTask<Boolean, Void, ResultContact> {

    private ProgressDialog mDialog;
    private Context mContext;
    private ArrayList<Contact> mUpdateList;

    public UpdateContactTask(Context context, ArrayList<Contact> updateList) {
        this.mContext = context;
        this.mUpdateList = new ArrayList<>(updateList);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setTitle("");
        mDialog.setMessage("Đang cập nhật...");
        mDialog.setCancelable(false);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.show();
    }

    @Override
    protected ResultContact doInBackground(Boolean... booleans) {

        boolean isUpdate = booleans[0];

        int cnt = 0;
        ResultContact resultContact = new ResultContact();

        List<HashMap<String, HashMap<String, String>>> resultSet =
                ContactHelper.getInstance(mContext).updateContactList(mUpdateList, isUpdate);


        for (HashMap<String, HashMap<String, String>> hm : resultSet) {
            Set<String> keys = hm.keySet();

            for (String s : keys) {
                ResultContact item = new ResultContact();
                HashMap<String, String> hmTempt = hm.get(s);
                cnt = cnt + hmTempt.size();

                if (hmTempt.size() > 0) {

                    item.setContactName(s);

                    List<String> listKey = new ArrayList<>(hmTempt.keySet());

                    for (int i = 0; i < listKey.size(); i = i + 2) {
                        item.setOldPhoneNumber(hmTempt.get(listKey.get(i)));
                        item.setNewPhoneNumber(hmTempt.get(listKey.get(i + 1)));
                    }
                    resultContact.add(item);
                }
            }
        }
        resultContact.setTotalResult(cnt / 2);

        return resultContact;

    }

    @Override
    protected void onPostExecute(final ResultContact resultContact) {
        super.onPostExecute(resultContact);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }

                Intent intent = new Intent(mContext, ResultActivity.class);
                intent.putExtra("RESULTS", resultContact);
                mContext.startActivity(intent);
                ((Activity)mContext).finish();
            }
        }, 200);
    }
}
