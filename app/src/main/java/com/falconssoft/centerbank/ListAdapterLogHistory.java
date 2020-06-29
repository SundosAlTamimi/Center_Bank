package com.falconssoft.centerbank;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.falconssoft.centerbank.Models.ChequeInfo;

import java.util.List;

public class ListAdapterLogHistory extends BaseAdapter {
    CheckBox checkPriceed;
    private Context context;
    List<ChequeInfo> itemsList;

    public ListAdapterLogHistory(Context context, List<ChequeInfo> itemsList) {
        this.context = context;
        this.itemsList = itemsList;

    }

    public ListAdapterLogHistory() {

    }

    public void setItemsList(List<ChequeInfo> itemsList) {
        this.itemsList = itemsList;

    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
       LinearLayout detailRow;
        TextView state,name,transType,date,detail,from,to,TranseType ;//, price

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.report_row_log_history, null);

        itemsList.get(i).setISOpen("0");
        holder.detailRow =  view.findViewById(R.id.detailRow);
        holder.state =  view.findViewById(R.id.statuts);
        holder.name=  view.findViewById(R.id.name);
//        holder.transType=  view.findViewById(R.id.trans);
        holder.date=  view.findViewById(R.id.date);
        holder.detail=  view.findViewById(R.id.Detail);
        holder.from =  view.findViewById(R.id.from);
        holder.to =  view.findViewById(R.id.to);
        holder.TranseType =  view.findViewById(R.id.status);

        holder.detailRow.setVisibility(View.GONE);
        holder.state.setText("" + itemsList.get(i).getStatus());
        holder.name.setText("" + itemsList.get(i).getCustName());
//        holder.transType.setText("" + itemsList.get(i).getTransType());
        holder.date.setText("" + itemsList.get(i).getDate());
        holder.from.setText(""+ itemsList.get(i).getToCustomerName());
        holder.to.setText("Amount is : "+ itemsList.get(i).getMoneyInDinar()+itemsList.get(i).getMoneyInFils()+"\n"+itemsList.get(i).getMoneyInWord());
        holder.TranseType.setText("This Tran From bank Name  : "+ itemsList.get(i).getBankName());


        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemsList.get(i).getISOpen().equals("0")){
                    holder.detailRow.setVisibility(View.VISIBLE);
                itemsList.get(i).setISOpen("1");
            }else {
                    holder.detailRow.setVisibility(View.GONE);
                    itemsList.get(i).setISOpen("0");
                }
            }
        });

        return view;
    }




}

