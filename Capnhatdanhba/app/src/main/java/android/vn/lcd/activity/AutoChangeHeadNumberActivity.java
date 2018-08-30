package android.vn.lcd.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.lcd.vn.capnhatdanhba.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.vn.lcd.interfaces.ViewConstructor;
import android.vn.lcd.utils.UpdateContactTask;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AutoChangeHeadNumberActivity extends Activity implements ViewConstructor {

    private Button btnUpdate, btnRestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_auto_change_head_number);

        initParams();

        initLayout();

        initListener();
    }

    @Override
    public void initParams() {

    }

    @Override
    public void initLayout() {
        final TextView txtDescription = findViewById(R.id.text_description);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        txtDescription.setLayoutParams(llp);
        txtDescription.setTextColor(Color.parseColor("#343434"));
        txtDescription.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        txtDescription.setText(getResources().getString(R.string.des_main));
        txtDescription.setBackgroundColor(Color.parseColor("#ffffff"));
        txtDescription.setPadding(15, 15, 15, 15);
        txtDescription.setLineSpacing(1.0f, 1.2f);


        final ImageView imgUpdateDetails = findViewById(R.id.update_details);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1
        );
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        llp.bottomMargin = 5;
        imgUpdateDetails.setLayoutParams(llp);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.details);
        imgUpdateDetails.setImageBitmap(bmp);

        btnUpdate = findViewById(R.id.btn_update);
        llp = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        llp.bottomMargin = 5;
        llp.leftMargin = 5;
        llp.rightMargin = 5;
        btnUpdate.setLayoutParams(llp);
        btnUpdate.setText(getResources().getString(R.string.btn_auto_sync));
        btnUpdate.setTextColor(Color.parseColor("#ffffff"));
        btnUpdate.setBackgroundResource(R.drawable.btn_radius_background);

        btnRestore = findViewById(R.id.btn_restore);
        llp = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        llp.bottomMargin = 5;
        llp.leftMargin = 5;
        llp.rightMargin = 5;
        btnRestore.setLayoutParams(llp);
        btnRestore.setText(getResources().getString(R.string.btn_auto_restore));
        btnRestore.setTextColor(Color.parseColor("#ffffff"));
        btnRestore.setBackgroundResource(R.drawable.btn_load_background);
    }

    @Override
    public void initListener() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = getResources().getString(R.string.confirm_title);
                String message = getResources().getString(R.string.confirm_text);
                String btnYes = getResources().getString(R.string.btn_yes);
                String btnNo = getResources().getString(R.string.btn_cancel);


                AlertDialog alertDialog = new AlertDialog.Builder(AutoChangeHeadNumberActivity.this)
                        .setTitle(title)
                        .setMessage(message)
                        .setCancelable(true)
                        .setPositiveButton(btnYes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new UpdateContactTask(AutoChangeHeadNumberActivity.this).execute(true);
                            }
                        })
                        .setNegativeButton(btnNo, null)
                        .create();
                alertDialog.show();
            }
        });

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = getResources().getString(R.string.confirm_title);
                String message = getResources().getString(R.string.confirm_text);
                String btnYes = getResources().getString(R.string.btn_yes);
                String btnNo = getResources().getString(R.string.btn_cancel);

                AlertDialog alertDialog = new AlertDialog.Builder(AutoChangeHeadNumberActivity.this)
                        .setTitle(title)
                        .setMessage(message)
                        .setCancelable(true)
                        .setPositiveButton(btnYes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new UpdateContactTask(AutoChangeHeadNumberActivity.this).execute(false);
                            }
                        })
                        .setNegativeButton(btnNo, null)
                        .create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
