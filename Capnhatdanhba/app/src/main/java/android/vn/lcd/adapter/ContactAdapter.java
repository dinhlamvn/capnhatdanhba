package android.vn.lcd.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.lcd.vn.capnhatdanhba.R;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.vn.lcd.data.Contact;
import android.vn.lcd.data.ContactPhoneNumberHelper;
import android.vn.lcd.sql.ContactHelper;
import android.vn.lcd.utils.AlphabetSort;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {


    private ArrayList<Contact> mList;
    private ArrayList<Contact> parentList;
    private Context mContext;

    public ContactAdapter(Context context, ArrayList<Contact> list) {
        this.mContext = context;
        this.mList = new ArrayList<>(list);
        this.parentList = new ArrayList<>(list);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Contact contact = mList.get(position);

        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        holder.frameMain.setLayoutParams(flp);
        if (position % 2 == 0) {
            holder.frameMain.setBackgroundColor(Color.rgb(255, 255, 255));
        } else {
            holder.frameMain.setBackgroundColor(Color.rgb(242, 242, 242));
        }

        holder.frameMain.setPadding(0, 5, 0, 5);


        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.contact_avatar);
        flp = new FrameLayout.LayoutParams(
                (int)(bmp.getWidth() * 0.4),
                (int)(bmp.getWidth() * 0.4)
        );
        flp.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
        flp.topMargin = 5 * TypedValue.COMPLEX_UNIT_DIP;
        flp.bottomMargin = 5 * TypedValue.COMPLEX_UNIT_DIP;
        flp.leftMargin = 10 * TypedValue.COMPLEX_UNIT_DIP;
        holder.imgContactIcon.setLayoutParams(flp);
        holder.imgContactIcon.setImageBitmap(bmp);

        flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        flp.leftMargin = 130 * TypedValue.COMPLEX_UNIT_DIP;
        flp.rightMargin = 75 * TypedValue.COMPLEX_UNIT_DIP;
        flp.gravity = Gravity.CENTER_VERTICAL;
        holder.frameContactInfo.setLayoutParams(flp);
        holder.frameContactInfo.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        holder.txtContactName.setLayoutParams(llp);
        holder.txtContactName.setText(contact.getName());
        holder.txtContactName.setTextColor(Color.BLACK);
        holder.txtContactName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        holder.txtContactName.setTypeface(Typeface.DEFAULT_BOLD);

        llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        holder.txtContactPhoneNumber.setLayoutParams(llp);
        holder.txtContactPhoneNumber.setText(contact.getMobilePhone());
        holder.txtContactPhoneNumber.setTextColor(Color.BLACK);
        holder.txtContactPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);

        bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.call);
        flp = new FrameLayout.LayoutParams(
                (int)(bmp.getWidth() * 0.8),
                (int)(bmp.getWidth() * 0.8)
        );
        flp.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        flp.rightMargin = TypedValue.COMPLEX_UNIT_DIP * 30;
        holder.btnCall.setLayoutParams(flp);
        holder.btnCall.setImageBitmap(bmp);
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.getMobilePhone()));
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(callIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void filter(String text) {
        mList.clear();

        if (text.trim().equals("")) {
            this.mList.addAll(this.parentList);
        } else {
            for (Contact item : this.parentList) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    this.mList.add(item);
                }
            }
        }
        Collections.sort(this.mList, new AlphabetSort());
        notifyDataSetChanged();
    }

    public void filterByPhoneNumber(String p) {
        mList.clear();

        if (p.trim().equals("")) {
            this.mList.addAll(this.parentList);
        } else {
            for (Contact item : this.parentList) {
                String phoneNumber = ContactPhoneNumberHelper
                        .formatPhoneNumberWithoutWhiteSpace(item.getMobilePhone());
                if (phoneNumber.toLowerCase().startsWith(p.toLowerCase())) {
                    this.mList.add(item);
                }
            }
        }
        Collections.sort(this.mList, new AlphabetSort());
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout frameMain;
        LinearLayout frameContactInfo;
        TextView txtContactName;
        TextView txtContactPhoneNumber;
        ImageView imgContactIcon;
        ImageView btnCall;


        ViewHolder(View itemView) {
            super(itemView);
            frameMain = (FrameLayout) itemView.findViewById(R.id.frame_main);
            frameContactInfo = (LinearLayout) itemView.findViewById(R.id.frame_contact_info);
            txtContactName = (TextView) itemView.findViewById(R.id.contact_name);
            txtContactPhoneNumber = (TextView) itemView.findViewById(R.id.contact_phone_number);
            imgContactIcon = (ImageView) itemView.findViewById(R.id.contact_icon);
            btnCall = (ImageView) itemView.findViewById(R.id.btn_call);
        }
    }
}
