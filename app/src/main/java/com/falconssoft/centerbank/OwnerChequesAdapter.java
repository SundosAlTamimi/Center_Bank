package com.falconssoft.centerbank;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.falconssoft.centerbank.viewmodel.ChequeInfoVM;

import java.util.List;

class OwnerChequesAdapter extends RecyclerView.Adapter<OwnerChequesAdapter.OwnerChequeViewHolder> {

    private List<ChequeInfoVM> list;
    private Activity activity;

    public OwnerChequesAdapter(Activity activity, List<ChequeInfoVM> list) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OwnerChequesAdapter.OwnerChequeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout and retrieve binding
//        ListItemBinding binding = DataBindingUtil.inflate(host.getLayoutInflater(),
//                R.layout.list_item, parent, false);
//
//        return new ItemViewHolder(binding);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.owner_cheque_raw_layout, parent, false);
        return new OwnerChequesAdapter.OwnerChequeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerChequesAdapter.OwnerChequeViewHolder holder, final int position) {
//        Item item = items.get(position);
//
//        ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
//        itemViewHolder.bindItem(item);
        holder.chequeNo.setText(list.get(position).getChequeNo());
        holder.accountNo.setText(list.get(position).getAccCode());

//        holder.date.setText(""+list.get(position).getBankNo());
//        holder.beneficiary.setText(list.get(position).getReceiverName());
//        holder.nationalNo.setText(list.get(position).getReceiverNationalID());
//        holder.phone.setText(list.get(position).getReceiverMobileNo());
//        holder.transactionType.setText(list.get(position).getTransType());
//        holder.status.setText(list.get(position).getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TrackingCheque().getChequeData(list.get(position)); //////// intent

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

    class OwnerChequeViewHolder extends RecyclerView.ViewHolder {
        TextView chequeNo, accountNo;

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

        public OwnerChequeViewHolder(@NonNull View itemView) {
            super(itemView);

            chequeNo = itemView.findViewById(R.id.owner_raw_chequeNo);
            accountNo = itemView.findViewById(R.id.owner_raw_accountNo);
//            trackingLinear = itemView.findViewById(R.id.tracking_raw_linear);
//            date = itemView.findViewById(R.id.tracking_raw_date);
//            beneficiary = itemView.findViewById(R.id.tracking_raw_beneficiary);
//            nationalNo = itemView.findViewById(R.id.tracking_raw_nationalID);
//            phone = itemView.findViewById(R.id.tracking_raw_phone);
//            transactionType = itemView.findViewById(R.id.tracking_raw_transactionType);
//            status = itemView.findViewById(R.id.tracking_raw_status);

        }
    }
}
