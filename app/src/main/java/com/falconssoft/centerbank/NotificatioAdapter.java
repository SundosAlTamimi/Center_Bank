package com.falconssoft.centerbank;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.falconssoft.centerbank.Models.notification;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificatioAdapter  extends  RecyclerView.Adapter<NotificatioAdapter.ViewHolder> {
    //    RecyclerView.Adapter<engineer_adapter.ViewHolder>
    Context context;
    List<notification> notificationList;
//    CompaneyInfo clickedcom;
//    List<CompaneyInfo> companeyInfos=new ArrayList<>();
    int row_index=-1;

    public NotificatioAdapter(Context context, List<notification> notifications) {
        this.context = context;
        this.notificationList = notifications;
        Log.e("notificationList",""+notificationList.size());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_notification, viewGroup, false);
        return new NotificatioAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.date_check.setText(notificationList.get(i).getDate());
        viewHolder.amount_check.setText(notificationList.get(i).getAmount_check());
        viewHolder.source_check.setText(notificationList.get(i).getSource());
        viewHolder.image_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.showImageOfCheck();

            }
        });
        viewHolder.linearCheckInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.showDetails();
            }
        });
//        viewHolder.linear_companey.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                row_index=i;
//                notifyDataSetChanged();
//
//
////                clickedcom=new CompaneyInfo();
////                clickedcom.setCompanyName(viewHolder.hold_company_name.getText().toString());
////                clickedcom.setPhoneNo(viewHolder.companyTel.getText().toString());
////                companey_name.setText(viewHolder.hold_company_name.getText().toString());
////                telephone_no.setText(viewHolder.companyTel.getText().toString());
////                customer_name.setText(companey.get(i).getCustomerName());
////                systype.setText(companey.get(i).getSystemName());
////                Log.e("systemSelected",""+companey.get(i).getSystemName());
////                Log.e("text_delet_id",""+i);
////                text_delet_id.setText(i+"");
////                //companey.remove(i);
////                // Log.e("companysize",""+companey.size());
////                isInHold=true;
////                companeyInfos.add(clickedcom);
//            }
//
//        });
//        if(row_index==i)
//        {
//            viewHolder.linear_companey.setBackgroundColor(Color.parseColor("#e5e4e2"));
//
//        }
//        else {
//            viewHolder.linear_companey.setBackgroundColor(Color.parseColor("#FFFFFF"));
//
//        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
//    //public List<CompaneyInfo> getCheckedItems() {
//        return companeyInfos;
//    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView source_check, amount_check,date_check;
        CircleImageView image_check;
        LinearLayout linearCheckInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            source_check = itemView.findViewById(R.id.source_check);
            amount_check=itemView.findViewById(R.id.amount_check);
            date_check=itemView.findViewById(R.id.date_check);
            image_check=itemView.findViewById(R.id.image_check);
            linearCheckInfo=itemView.findViewById(R.id.linearCheckInfo);
//


        }
        public void showImageOfCheck() {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.show_image);
            dialog.show();

            final ImageView imageView = (ImageView) dialog.findViewById(R.id.image_check);
//            imageView.setImageBitmap();



        }
        public void showDetails() {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.show_check_detail);
            dialog.show();

            final ImageView imageView = (ImageView) dialog.findViewById(R.id.image_check);
//            imageView.setImageBitmap();



        }
    }
}