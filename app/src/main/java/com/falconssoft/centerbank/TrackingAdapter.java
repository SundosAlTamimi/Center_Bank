package com.falconssoft.centerbank;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.falconssoft.centerbank.viewmodel.ChequeInfoVM;

import java.util.List;

class TrackingAdapter  extends RecyclerView.Adapter<TrackingAdapter.TrackingViewHolder> {

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tracking_raw_layout, null);
        return new TrackingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackingViewHolder holder, int position) {
//        Item item = items.get(position);
//
//        ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
//        itemViewHolder.bindItem(item);
        holder.chequeNo.setText(list.get(position).getChequeNo());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TrackingViewHolder extends RecyclerView.ViewHolder{
        TextView chequeNo;

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

            chequeNo = itemView.findViewById(R.id.tracking_raw_chequeNo);
        }
    }
}
