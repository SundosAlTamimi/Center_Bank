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
    private String language;

    public TrackingAdapter(Activity activity, List<ChequeInfoVM> list, String language) {
        this.list = list;
        this.activity = activity;
        this.language = language;
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

        holder.date.setText(list.get(position).getCheckDueDate());
        holder.beneficiary.setText(list.get(position).getReceiverName());
//        holder.nationalNo.setText(list.get(position).getReceiverNationalID());
        holder.phone.setText("+" + list.get(position).getReceiverMobileNo());

        if (!TextUtils.isEmpty(list.get(position).getTransSendOrGero()))
            if (list.get(position).getTransSendOrGero().equals("0"))
                holder.transactionType.setText("Send");
            else
                holder.transactionType.setText("Giro");

        if (!TextUtils.isEmpty(list.get(position).getTransType()))
            if (list.get(position).getTransType().equals("0")) {
                holder.status.setText("Pending");
                holder.status.setTextColor(activity.getResources().getColor(R.color.blue));
            } else if (list.get(position).getTransType().equals("1")) {
                holder.status.setText("Accepted");
                holder.status.setTextColor(activity.getResources().getColor(R.color.RealGreen));
            } else if (list.get(position).getTransType().equals("2")){
                holder.status.setText("Rejected");
                holder.status.setTextColor(activity.getResources().getColor(R.color.RealRed));}
            else if (list.get(position).getTransType().equals("3")){
                holder.status.setText("Cashed");
                holder.status.setTextColor(activity.getResources().getColor(R.color.colorBlack));}

//        if(itemsList.get(i).getTransType().equals("2")){
//            holder.status.setBorderColor(context.getResources().getColor(R.color.RealRed));
//            TStatus=context.getResources().getString(R.string.rej);
//
//        }else if(itemsList.get(i).getTransType().equals("1")){
//            holder.status.setBorderColor(context.getResources().getColor(R.color.RealGreen));
//            TStatus=context.getResources().getString(R.string.acccept);
//        }else if(itemsList.get(i).getTransType().equals("0")||itemsList.get(i).getTransType().equals("")){
//            holder.status.setBorderColor(context.getResources().getColor(R.color.blue));
//            TStatus=context.getResources().getString(R.string.pending);
//
//        } else if(itemsList.get(i).getTransType().equals("3")){//OWNERMOBNO
//            holder.status.setBorderColor(context.getResources().getColor(R.color.gray_));
//            TStatus=context.getResources().getString(R.string.cashed);
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (language.equals("ar")) {
            holder.dateLinear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            holder.transTypeLinear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
//            holder.nationalLinear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            holder.phoneLinear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            holder.beneficiaryLinear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            holder.dateLinear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            holder.transTypeLinear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//            holder.nationalLinear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            holder.phoneLinear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            holder.beneficiaryLinear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TrackingViewHolder extends RecyclerView.ViewHolder {
        TextView chequeNo, date, beneficiary, phone, transactionType, status;//, nationalNo, accountNo;
        LinearLayout trackingLinear, dateLinear, beneficiaryLinear, phoneLinear, transTypeLinear;//, nationalLinear

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
//            nationalNo = itemView.findViewById(R.id.tracking_raw_nationalID);
            phone = itemView.findViewById(R.id.tracking_raw_phone);
            transactionType = itemView.findViewById(R.id.tracking_raw_transactionType);

            dateLinear = itemView.findViewById(R.id.tracking_raw_linear_date);
            beneficiaryLinear = itemView.findViewById(R.id.tracking_raw_linear_beneficiary);
            phoneLinear = itemView.findViewById(R.id.tracking_raw_linear_phone);
//            nationalLinear = itemView.findViewById(R.id.tracking_raw_linear_national);
            transTypeLinear = itemView.findViewById(R.id.tracking_raw_linear_transType);

            status = itemView.findViewById(R.id.tracking_raw_status);

        }
    }
}
