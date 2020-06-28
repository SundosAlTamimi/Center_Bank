package com.falconssoft.centerbank;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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

        TextView state,name,transType,date,detail;//, price
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.report_row_log_history, null);



        holder.state =  view.findViewById(R.id.statuts);
        holder.name=  view.findViewById(R.id.name);
        holder.transType=  view.findViewById(R.id.trans);
        holder.date=  view.findViewById(R.id.date);
        holder.detail=  view.findViewById(R.id.Detail);


        holder.state.setText("" + itemsList.get(i).getStatus());
        holder.name.setText("" + itemsList.get(i).getCustName());
        holder.transType.setText("" + itemsList.get(i).getTransType());
        holder.date.setText("" + itemsList.get(i).getDate());


        return view;
    }




}

