package android.vn.lcd.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.lcd.vn.capnhatdanhba.BuildConfig;
import android.lcd.vn.capnhatdanhba.R;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.vn.lcd.data.ResultContact;
import android.vn.lcd.interfaces.IViewConstructor;
import android.vn.lcd.utils.ValueFactory;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements IViewConstructor {

    private ResultContact resultContact;
    private ActionBar mActionbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initParams();

        initLayout();

        setUpTitle();

        setUpSubTitle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.result_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.rate: {
                openPlayStore();
                break;
            }
            case R.id.share: {
                saveShareText();
                openFacebook();
                break;
            }
            case R.id.info: {
                openInfoAppDialog();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void openPlayStore() {
        String playStoreAppUrl = "market://details?id=com.facebook.katana";
        String playStoreWebUrl = "https://play.google.com/store/apps/details?id=com.facebook.katana";
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreAppUrl));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreWebUrl));
            startActivity(intent);
        }
    }

    private void saveShareText() {
        ClipboardManager clipboardManager = (ClipboardManager)getApplicationContext()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        String shareText = "https://play.google.com/store/apps/details?id=com.facebook.katana"
                + "\nCác bạn vào tải app để đổi đầu số nhé.";
        ClipData clipData = ClipData.newPlainText("", shareText);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clipData);
        }
        Toast.makeText(ResultActivity.this, "Đã sao chép link tải ứng dụng", Toast.LENGTH_LONG).show();
    }

    private void openFacebook() {
        String facebookUrl = "fb://page/";
        String facebookWebUrl = "https://www.facebook.com/";
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookWebUrl));
            startActivity(intent);
        }
    }

    private void openInfoAppDialog() {
        String message = getResources().getString(R.string.info_app);
        message = message.replace("xxxx", getResources().getString(R.string.app_name));
        message = message.replace("yyyy", BuildConfig.VERSION_NAME);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(null)
                .setMessage(message)
                .setCancelable(true)
                .create();
        alertDialog.show();
    }

    @Override
    public void initParams() {
        Intent intent = getIntent();
        if (intent != null && intent.getSerializableExtra("RESULTS") != null) {
            resultContact = (ResultContact) intent.getSerializableExtra("RESULTS");
        }
        mActionbar = getSupportActionBar();
    }

    private void setUpTitle() {
        if (mActionbar != null) {
            mActionbar.setTitle("Kết quả");
        }
    }

    private void setUpSubTitle() {
        if (mActionbar != null) {
            mActionbar.setSubtitle("Đã cập nhật " + resultContact.getTotalResult() + " số");
        }
    }

    @Override
    public void initLayout() {
        LinearLayout layout = findViewById(R.id.layout);

        LinearLayout.LayoutParams llp;

        FrameLayout frameList = new FrameLayout(this);
        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        frameList.setLayoutParams(llp);

        RecyclerView mRecycleView = new RecyclerView(this);
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        mRecycleView.setLayoutParams(flp);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);
        ResultContactAdapter mAdapter = new ResultContactAdapter(this, resultContact.getResultSet());
        mRecycleView.setAdapter(mAdapter);
        frameList.addView(mRecycleView);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_home);
        ImageView btnHome = new ImageView(this);
        flp = new FrameLayout.LayoutParams(
                (int)(bitmap.getWidth() * 0.51),
                (int)(bitmap.getHeight() * 0.51)
        );
        flp.gravity = Gravity.BOTTOM | Gravity.END;
        flp.rightMargin = (int)(ValueFactory.getScreenWidth() * 0.025);
        flp.bottomMargin = (int)(ValueFactory.getScreenWidth() * 0.11);
        btnHome.setLayoutParams(flp);
        btnHome.setImageBitmap(bitmap);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        frameList.addView(btnHome);

        layout.addView(frameList);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {

    }

    class ResultContactAdapter extends RecyclerView.Adapter<ResultContactAdapter.ViewHolder> {

        private Context mContext;
        private ArrayList<ResultContact> mDataList;

        ResultContactAdapter(Context context, ArrayList<ResultContact> dataList) {
            this.mContext = context;
            this.mDataList = dataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.result_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            LinearLayout.LayoutParams llp;
            FrameLayout.LayoutParams flp;
            final ResultContact data = mDataList.get(position);

            flp = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            holder.frameMain.setLayoutParams(flp);
            if (position < mDataList.size()) {
                holder.frameMain.setBackgroundResource(R.drawable.background_contact_item);
            }

            flp = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            flp.leftMargin = (int)(ValueFactory.getScreenWidth() * 0.015);
            holder.frameInfo.setLayoutParams(flp);
            holder.frameInfo.setOrientation(LinearLayout.VERTICAL);

            llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            holder.txtContactName.setLayoutParams(llp);
            holder.txtContactName.setPadding(20, 15, 0, 2);
            holder.txtContactName.setText(data.getContactName());
            holder.txtContactName.setTextColor(Color.BLACK);
            holder.txtContactName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
            holder.txtContactName.setTypeface(Typeface.DEFAULT_BOLD);

            llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            holder.framePhoneInfo.setLayoutParams(llp);
            holder.framePhoneInfo.setOrientation(LinearLayout.HORIZONTAL);

            llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            holder.txtOldPhoneNumber.setLayoutParams(llp);
            holder.txtOldPhoneNumber.setPadding(20, 2, 0, 15);
            holder.txtOldPhoneNumber.setText(data.getOldPhoneNumber());
            holder.txtOldPhoneNumber.setTextColor(Color.rgb(255, 0, 0));
            holder.txtOldPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);

            llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            holder.txtDescription.setLayoutParams(llp);
            holder.txtDescription.setPadding(20, 2, 0, 15);
            holder.txtDescription.setText(mContext.getResources().getString(R.string.text_descript));
            holder.txtDescription.setTextColor(Color.rgb(0, 0, 0));
            holder.txtDescription.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);

            llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            holder.txtNewPhoneNumber.setLayoutParams(llp);
            holder.txtNewPhoneNumber.setPadding(20, 2, 0, 15);
            holder.txtNewPhoneNumber.setText(data.getNewPhoneNumber());
            holder.txtNewPhoneNumber.setTextColor(Color.rgb(0, 0, 255));
            holder.txtNewPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            FrameLayout frameMain;
            LinearLayout frameInfo;
            TextView txtContactName;
            LinearLayout framePhoneInfo;
            TextView txtOldPhoneNumber;
            TextView txtDescription;
            TextView txtNewPhoneNumber;


            ViewHolder(View itemView) {
                super(itemView);
                frameMain = (FrameLayout) itemView.findViewById(R.id.frameMain);
                frameInfo = (LinearLayout) itemView.findViewById(R.id.frameInfo);
                txtContactName = (TextView) itemView.findViewById(R.id.txtContactName);
                framePhoneInfo = (LinearLayout) itemView.findViewById(R.id.framePhone);
                txtOldPhoneNumber = (TextView) itemView.findViewById(R.id.txtOldPhoneNumber);
                txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
                txtNewPhoneNumber = (TextView) itemView.findViewById(R.id.txtNewPhoneNumber);
            }
        }
    }
}
