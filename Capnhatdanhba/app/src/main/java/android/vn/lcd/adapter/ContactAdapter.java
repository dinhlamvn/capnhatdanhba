package android.vn.lcd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.lcd.vn.capnhatdanhba.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.vn.lcd.data.Contact;
import android.vn.lcd.data.ContactPhoneNumberHelper;
import android.vn.lcd.utils.ValueFactory;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {


    private ArrayList<Contact> mList;
    private Context mContext;
    private String type;

    public ContactAdapter(Context context, ArrayList<Contact> list, String type) {
        this.mContext = context;
        this.mList = list;
        this.type = type;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Contact contact = mList.get(position);

        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        holder.frameMain.setLayoutParams(flp);
        if (position < mList.size()) {
            holder.frameMain.setBackgroundResource(R.drawable.background_contact_item);
        }

        flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        flp.leftMargin = (int)(ValueFactory.getScreenWidth() * 0.02f);
        flp.gravity = Gravity.CENTER_VERTICAL;
        holder.frameContactInfo.setLayoutParams(flp);
        holder.frameContactInfo.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        holder.txtContactName.setLayoutParams(llp);
        holder.txtContactName.setPadding(20, 15, 0, 2);
        holder.txtContactName.setText(contact.getName());
        holder.txtContactName.setTextColor(Color.BLACK);
        holder.txtContactName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        holder.txtContactName.setTypeface(Typeface.DEFAULT_BOLD);

        if (!contact.getHomePhone().equals("")) {
            llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            holder.txtContactHomeNumber.setLayoutParams(llp);
            holder.txtContactHomeNumber.setPadding(20, 2, 0, 15);
            holder.txtContactHomeNumber.setTextColor(Color.parseColor("#808080"));
            holder.txtContactHomeNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            String homeNumberText = "Home: " + contact.getHomePhone();
            if (!type.equals("CUSTOM")) {
                homeNumberText += " (" + ((type.equals("11-TO-10")) ? ContactPhoneNumberHelper.getNetworkNameByPhoneNumber11(mContext, contact.getHomePhone())
                        : ContactPhoneNumberHelper.getNetworkNameByPhoneNumber10(mContext, contact.getHomePhone())) + ")";
            }
            holder.txtContactHomeNumber.setText(homeNumberText);
            holder.txtContactHomeNumber.setVisibility(View.VISIBLE);
        } else {
            holder.txtContactHomeNumber.setVisibility(View.GONE);
        }

        if (!contact.getMobilePhone().equals("")) {
            llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            holder.txtContactPhoneNumber.setLayoutParams(llp);
            holder.txtContactPhoneNumber.setPadding(20, 2, 0, 15);
            holder.txtContactPhoneNumber.setTextColor(Color.parseColor("#808080"));
            holder.txtContactPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            String mobileNumberText = "Mobile: " + contact.getMobilePhone();
            if (!type.equals("CUSTOM")) {
                mobileNumberText += " (" + ((type.equals("11-TO-10")) ? ContactPhoneNumberHelper.getNetworkNameByPhoneNumber11(mContext, contact.getMobilePhone())
                        : ContactPhoneNumberHelper.getNetworkNameByPhoneNumber10(mContext, contact.getMobilePhone())) + ")";
            }
            holder.txtContactPhoneNumber.setText(mobileNumberText);
            holder.txtContactPhoneNumber.setVisibility(View.VISIBLE);
        } else {
            holder.txtContactPhoneNumber.setVisibility(View.GONE);
        }

        if (!contact.getWorkPhone().equals("")) {
            llp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            holder.txtContactWorkNumber.setLayoutParams(llp);
            holder.txtContactWorkNumber.setPadding(20, 2, 0, 15);
            holder.txtContactWorkNumber.setTextColor(Color.parseColor("#808080"));
            holder.txtContactWorkNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            String workNumberText = "Work: " + contact.getWorkPhone();
            if (!type.equals("CUSTOM")) {
                workNumberText += " (" + ((type.equals("11-TO-10")) ? ContactPhoneNumberHelper.getNetworkNameByPhoneNumber11(mContext, contact.getWorkPhone())
                        : ContactPhoneNumberHelper.getNetworkNameByPhoneNumber10(mContext, contact.getWorkPhone())) + ")";
            }
            holder.txtContactWorkNumber.setText(workNumberText);
            holder.txtContactWorkNumber.setVisibility(View.VISIBLE);
        } else {
            holder.txtContactWorkNumber.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout frameMain;
        LinearLayout frameContactInfo;
        TextView txtContactName;
        TextView txtContactHomeNumber;
        TextView txtContactPhoneNumber;
        TextView txtContactWorkNumber;


        ViewHolder(View itemView) {
            super(itemView);
            frameMain = (FrameLayout) itemView.findViewById(R.id.frame_main);
            frameContactInfo = (LinearLayout) itemView.findViewById(R.id.frame_contact_info);
            txtContactName = (TextView) itemView.findViewById(R.id.contact_name);
            txtContactHomeNumber = (TextView) itemView.findViewById(R.id.contact_home_number);
            txtContactPhoneNumber = (TextView) itemView.findViewById(R.id.contact_phone_number);
            txtContactWorkNumber = (TextView) itemView.findViewById(R.id.contact_work_number);
        }
    }
}
