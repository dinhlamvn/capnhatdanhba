package android.vn.lcd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.lcd.vn.capnhatdanhba.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.vn.lcd.data.Contact;
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

        holder.txtContactName.setText(contact.getName());
        holder.txtContactName.setTextColor(Color.BLACK);
        holder.txtContactName.setTextSize(14);

        holder.txtContactPhoneNumber.setText(contact.getMobilePhone());
        holder.txtContactPhoneNumber.setTextColor(Color.BLACK);
        holder.txtContactPhoneNumber.setTextSize(14);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtContactName;
        TextView txtContactPhoneNumber;


        ViewHolder(View itemView) {
            super(itemView);
            txtContactName = (TextView) itemView.findViewById(R.id.contact_name);
            txtContactPhoneNumber = (TextView) itemView.findViewById(R.id.contact_phone_number);
        }
    }


}
