package android.vn.lcd.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.lcd.vn.capnhatdanhba.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.vn.lcd.data.Contact;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {


    private ArrayList<Contact> mList;
    private Context mContext;

    public ContactAdapter(Context context, ArrayList<Contact> list) {
        this.mContext = context;
        this.mList = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Contact contact = mList.get(position);

        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );

        holder.frameMain.setLayoutParams(flp);
        holder.frameMain.setBackgroundColor(Color.rgb(255, 255, 255));

        flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        holder.itemRow.setLayoutParams(flp);


        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.contact);
        flp = new FrameLayout.LayoutParams(
                100 * TypedValue.COMPLEX_UNIT_DIP,
                100 * TypedValue.COMPLEX_UNIT_DIP
        );
        flp.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
        flp.topMargin = 15 * TypedValue.COMPLEX_UNIT_DIP;
        flp.bottomMargin = 15 * TypedValue.COMPLEX_UNIT_DIP;
        flp.leftMargin = 10 * TypedValue.COMPLEX_UNIT_DIP;
        holder.imgContactIcon.setLayoutParams(flp);
        holder.imgContactIcon.setImageBitmap(bmp);

        flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        flp.leftMargin = 120 * TypedValue.COMPLEX_UNIT_DIP;
        flp.rightMargin = 75 * TypedValue.COMPLEX_UNIT_DIP;
        holder.frameContactInfo.setLayoutParams(flp);
        holder.frameContactInfo.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        holder.txtContactName.setLayoutParams(llp);
        holder.txtContactName.setText(contact.getName());
        holder.txtContactName.setTextColor(Color.BLACK);
        holder.txtContactName.setTextSize(14);

        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        holder.txtContactPhoneNumber.setLayoutParams(llp);
        holder.txtContactPhoneNumber.setText(contact.getMobilePhone());
        holder.txtContactPhoneNumber.setTextColor(Color.BLACK);
        holder.txtContactPhoneNumber.setTextSize(14);

        flp = new FrameLayout.LayoutParams(
                50 * TypedValue.COMPLEX_UNIT_DIP,
                50 * TypedValue.COMPLEX_UNIT_DIP
        );
        flp.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
        flp.rightMargin = 15 * TypedValue.COMPLEX_UNIT_DIP;
        holder.imgContactOption.setLayoutParams(flp);
        bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.option_menu);
        holder.imgContactOption.setImageBitmap(bmp);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout frameMain;
        CardView itemRow;
        LinearLayout frameContactInfo;
        TextView txtContactName;
        TextView txtContactPhoneNumber;
        ImageView imgContactIcon;
        ImageView imgContactOption;


        ViewHolder(View itemView) {
            super(itemView);
            frameMain = (FrameLayout) itemView.findViewById(R.id.frame_main);
            itemRow = (CardView) itemView.findViewById(R.id.item_row);
            frameContactInfo = (LinearLayout) itemView.findViewById(R.id.frame_contact_info);
            txtContactName = (TextView) itemView.findViewById(R.id.contact_name);
            txtContactPhoneNumber = (TextView) itemView.findViewById(R.id.contact_phone_number);
            imgContactIcon = (ImageView) itemView.findViewById(R.id.contact_icon);
            imgContactOption = (ImageView) itemView.findViewById(R.id.btn_option);
        }
    }


}
