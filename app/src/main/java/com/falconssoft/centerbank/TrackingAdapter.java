package com.falconssoft.centerbank;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.falconssoft.centerbank.viewmodel.ChequeInfoVM;

import java.util.List;

class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.TrackingViewHolder> {

    private List<ChequeInfoVM> list;
    private Activity activity;

    public TrackingAdapter(Activity activity, List<ChequeInfoVM> list) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TrackingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout and retrieve binding
//        ListItemBinding binding = DataBindingUtil.inflate(host.getLayoutInflater(),
//                R.layout.list_item, parent, false);
//
//        return new ItemViewHolder(binding);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tracking_raw_layout, parent, false);
        return new TrackingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackingViewHolder holder, int position) {
//        Item item = items.get(position);
//
//        ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
//        itemViewHolder.bindItem(item);
//        holder.chequeNo.setText("Cheque No: " + list.get(position).getChequeNo());
//        holder.accountNo.setText("Account No: " + list.get(position).getAccCode());

//        if (position % 2 == 0) {
//            holder.date.setTextColor(Color.parseColor("#D5A000"));
//            holder.beneficiary.setTextColor(Color.parseColor("#D5A000"));
//            holder.nationalNo.setTextColor(Color.parseColor("#D5A000"));
//            holder.phone.setTextColor(Color.parseColor("#D5A000"));
//            holder.transactionType.setTextColor(Color.parseColor("#D5A000"));
//            holder.status.setTextColor(Color.parseColor("#D5A000"));
//        } else {
//            holder.date.setTextColor(Color.WHITE);
//            holder.beneficiary.setTextColor(Color.WHITE);
//            holder.nationalNo.setTextColor(Color.WHITE);
//            holder.phone.setTextColor(Color.WHITE);
//            holder.transactionType.setTextColor(Color.WHITE);
//            holder.status.setTextColor(Color.WHITE);
//        }

        holder.date.setText(list.get(position).getCheckDueDate());
        holder.beneficiary.setText(list.get(position).getReceiverName());
        holder.nationalNo.setText(list.get(position).getReceiverNationalID());
        holder.phone.setText(list.get(position).getReceiverMobileNo());
        if (!TextUtils.isEmpty(list.get(position).getTransType()))
            if (list.get(position).getTransType().equals("0"))
                holder.transactionType.setText("Send");
            else
                holder.transactionType.setText("Giro");

//        if (!TextUtils.isEmpty(list.get(position).getStatus()))
//            if (list.get(position).getStatus().equals("0"))
//                holder.status.setText("Pending");
//            else if (list.get(position).getStatus().equals("1"))
//                holder.status.setText("Accepted");
//            else if (list.get(position).getStatus().equals("2"))
//                holder.status.setText("Rejected");
//            else if (list.get(position).getStatus().equals("3"))
//                holder.status.setText("Cashed");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        Log.e("showallinfo", list.get(position).getCheckDueDate()
//                + list.get(position).getReceiverName()
//                + list.get(position).getReceiverNationalID()
//                + list.get(position).getReceiverMobileNo()
//                + list.get(position).getTransType()
//                + list.get(position).getStatus()

//        );

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TrackingViewHolder extends RecyclerView.ViewHolder {
        TextView chequeNo, date, beneficiary, nationalNo, phone, transactionType;//, status, accountNo;
        LinearLayout trackingLinear;

//        ListItemBinding binding;
//
//        ItemViewHolder(ListItemBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//
//        void bindItem(Item item) {
//            binding.setItem(item);
//            binding.executePendingBindings();
//        }

        public TrackingViewHolder(@NonNull View itemView) {
            super(itemView);

//            chequeNo = itemView.findViewById(R.id.tracking_raw_chequeNo);
//            accountNo = itemView.findViewById(R.id.tracking_raw_accountNo);
            trackingLinear = itemView.findViewById(R.id.tracking_raw_linear);
            date = itemView.findViewById(R.id.tracking_raw_date);
            beneficiary = itemView.findViewById(R.id.tracking_raw_beneficiary);
            nationalNo = itemView.findViewById(R.id.tracking_raw_nationalID);
            phone = itemView.findViewById(R.id.tracking_raw_phone);
            transactionType = itemView.findViewById(R.id.tracking_raw_transactionType);
//            status = itemView.findViewById(R.id.tracking_raw_status);

        }
    }
}
