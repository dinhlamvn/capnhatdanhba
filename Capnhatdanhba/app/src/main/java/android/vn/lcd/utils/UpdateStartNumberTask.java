package android.vn.lcd.utils;

import android.annotation.SuppressLint;
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
import android.vn.lcd.activity.ResultActivity;
import android.vn.lcd.data.Contact;
import android.vn.lcd.interfaces.IUpdateContactCallback;
import android.vn.lcd.sql.ContactHelper;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@SuppressLint("StaticFieldLeak")
public class UpdateStartNumberTask extends AsyncTask<String, Void, HashMap<String, String>> {

    private ProgressDialog mDialog;
    private Context mContext;
    private ArrayList<Contact> mDataList;

    public UpdateStartNumberTask(Context context) {
        this.mContext = context;
        this.mDataList = ContactHelper.getInstance(context).getContactList();
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
    protected HashMap<String, String> doInBackground(String... strings) {

        String oldStartNumber = strings[0];
        String newStartNumber = strings[1];
        int cnt = 0;
        HashMap<String, String> hashMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        List<HashMap<String, HashMap<String, String>>> resultSet =
                ContactHelper.getInstance(mContext).updateContactListWithStartNumber(mDataList, oldStartNumber, newStartNumber);


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
        final int resultTotal = Integer.parseInt(hm.get("TOTAL"));
        final String resultDetails = hm.get("DETAILS");
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }

                Intent intent = new Intent(mContext, ResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("RESULT_TOTAL", resultTotal);
                bundle.putString("RESULT_VALUE", resultDetails);
                intent.putExtra("result", bundle);
                mContext.startActivity(intent);
            }
        }, 1500);
    }
}
