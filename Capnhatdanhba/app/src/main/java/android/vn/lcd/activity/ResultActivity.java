package android.vn.lcd.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.lcd.vn.capnhatdanhba.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.vn.lcd.interfaces.IViewConstructor;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ResultActivity extends Activity implements IViewConstructor {

    private Intent intent;
    private Bundle bundle;
    private String message;
    private int total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initParams();

        initLayout();
    }

    @Override
    public void initParams() {
        intent = getIntent();
        if (intent != null) {
            bundle = intent.getBundleExtra("result");
            if (bundle != null) {
                total = bundle.getInt("RESULT_TOTAL", 0);
                message = bundle.getString("RESULT_VALUE", "");
            }
        }
    }

    @Override
    public void initLayout() {
        ScrollView scrollView = findViewById(R.id.scroll_view);
        LinearLayout layout = scrollView.findViewById(R.id.layout);
        TextView txtMessage = findViewById(R.id.text_message);

        LinearLayout.LayoutParams llp;

        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        txtMessage.setLayoutParams(llp);
        txtMessage.setPadding(10, 25, 10, 25);
        txtMessage.setTextSize(16);
        txtMessage.setTextColor(Color.parseColor("#343434"));
        txtMessage.setLineSpacing(1.5f, 1.5f);
        txtMessage.setText("Đã thực hiện chuyển đổi " + total + " số điện thoại. \nChi tiết:");

        TextView txtResultDetails = new TextView(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        txtResultDetails.setLayoutParams(llp);
        txtResultDetails.setPadding(10, 25, 10, 25);
        txtResultDetails.setTextSize(16);
        txtResultDetails.setTextColor(Color.parseColor("#343434"));
        txtResultDetails.setLineSpacing(1.5f, 1.5f);
        txtResultDetails.setText(message);
        layout.addView(txtResultDetails);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
