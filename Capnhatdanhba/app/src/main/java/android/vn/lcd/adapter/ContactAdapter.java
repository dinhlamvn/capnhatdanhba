package android.vn.lcd.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {


    private ArrayList<Contact> mList;
    private Context mContext;
    private int[] avatars = new int[] {
            R.drawable.contact_avatar_1,
            R.drawable.contact_avatar_2,
            R.drawable.contact_avatar_3,
            R.drawable.contact_avatar_4,
            R.drawable.contact_avatar_5
    };
    private int positionAvatar = 0;
    Random random = new Random();

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

        final Contact contact = mList.get(position);

        positionAvatar = random.nextInt(5);
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

        flp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        flp.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
        flp.topMargin = 5 * TypedValue.COMPLEX_UNIT_DIP;
        flp.bottomMargin = 5 * TypedValue.COMPLEX_UNIT_DIP;
        flp.leftMargin = 10 * TypedValue.COMPLEX_UNIT_DIP;
        holder.imgContactIcon.setLayoutParams(flp);
        holder.imgContactIcon.setBackgroundResource(avatars[positionAvatar]);
        holder.imgContactIcon.setText(String.valueOf(contact.getName().charAt(0)));
        holder.imgContactIcon.setGravity(Gravity.CENTER);
        holder.imgContactIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        holder.imgContactIcon.setTextColor(Color.rgb(255, 255, 255));

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

        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.call);
        flp = new FrameLayout.LayoutParams(
                (int)(bmp.getWidth() * 0.5),
                (int)(bmp.getWidth() * 0.5)
        );
        flp.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        flp.rightMargin = TypedValue.COMPLEX_UNIT_DIP * 30;
        holder.btnCall.setLayoutParams(flp);
        holder.btnCall.setImageBitmap(bmp);
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.getMobilePhone()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(callIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout frameMain;
        LinearLayout frameContactInfo;
        TextView txtContactName;
        TextView txtContactPhoneNumber;
        TextView imgContactIcon;
        ImageView btnCall;


        ViewHolder(View itemView) {
            super(itemView);
            frameMain = (FrameLayout) itemView.findViewById(R.id.frame_main);
            frameContactInfo = (LinearLayout) itemView.findViewById(R.id.frame_contact_info);
            txtContactName = (TextView) itemView.findViewById(R.id.contact_name);
            txtContactPhoneNumber = (TextView) itemView.findViewById(R.id.contact_phone_number);
            imgContactIcon = (TextView) itemView.findViewById(R.id.contact_icon);
            btnCall = (ImageView) itemView.findViewById(R.id.btn_call);
        }
    }


}
