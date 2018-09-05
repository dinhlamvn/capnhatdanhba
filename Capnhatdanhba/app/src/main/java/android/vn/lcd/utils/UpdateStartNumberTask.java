package android.vn.lcd.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.lcd.vn.capnhatdanhba.R;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.vn.lcd.activity.ChangeHeadNumberActivity;
import android.vn.lcd.activity.ListContactActivity;
import android.vn.lcd.adapter.ContactAdapter;
import android.vn.lcd.data.Contact;
import android.vn.lcd.interfaces.IUpdateContactCallback;
import android.vn.lcd.sql.ContactHelper;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@SuppressLint("StaticFieldLeak")
public class UpdateStartNumberTask extends AsyncTask<String, Void, HashMap<String, String>> {

    private ProgressDialog mDialog;
    private Context mContext;
    private ArrayList<Contact> mDataList;
    private IUpdateContactCallback callback;

    public UpdateStartNumberTask(Context context, IUpdateContactCallback callback) {
        this.mContext = context;
        this.mDataList = ContactHelper.getInstance(context).getContactList();
        this.callback = callback;
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
        if (mDialog.isShowing()) {
            mDialog.setMessage("Đang load lại dữ liệu...");
        }
        final int resultTotal = Integer.parseInt(hm.get("TOTAL"));
        final String resultDetails = hm.get("DETAILS");
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                callback.updateListContactView();

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                        .setTitle("Thông báo")
                        .setMessage("Đã cập nhật " + resultTotal + " số điện thoại")
                        .setCancelable(false)
                        .setPositiveButton("Thoát", null);
                if (resultTotal > 0) {
                    builder.setNegativeButton("Xem chi tiết", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Dialog dialog = new Dialog(mContext);
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
