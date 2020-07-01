package com.falconssoft.centerbank;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.falconssoft.centerbank.Models.ChequeInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;

public class ListAdapterLogHistory extends BaseAdapter {
    CheckBox checkPriceed;
    private Context context;
    List<ChequeInfo> itemsList;
 String phoneNo;

    public ListAdapterLogHistory(Context context, List<ChequeInfo> itemsList) {
        this.context = context;
        this.itemsList = itemsList;

        SharedPreferences loginPrefs =this.context.getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        phoneNo = loginPrefs.getString("mobile", "");

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
        TextView name,transType,date,detail,from,to,TranseType,bankName,AmountJd,AmountWord,branchNo ;//, price
CircleImageView status;




    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.report_row_log_history, null);


        holder.detailRow =  view.findViewById(R.id.detailRow);
        holder.status =  view.findViewById(R.id.statuts);
        holder.name=  view.findViewById(R.id.name);
//        holder.transType=  view.findViewById(R.id.trans);
        holder.date=  view.findViewById(R.id.date);
        holder.detail=  view.findViewById(R.id.Detail);
        holder.from =  view.findViewById(R.id.from);
        holder.to =  view.findViewById(R.id.to);
        holder.TranseType =  view.findViewById(R.id.status);

        holder.bankName =  view.findViewById(R.id.bankName);
        holder.AmountJd =  view.findViewById(R.id.AmountJd);
        holder.AmountWord =  view.findViewById(R.id.AmountWord);
        holder.branchNo =  view.findViewById(R.id.branchNo);


        holder.detailRow.setVisibility(View.GONE);
//        holder.state.setText("" + itemsList.get(i).getStatus());
        holder.name.setText("" + itemsList.get(i).getCustName());
//        holder.transType.setText("" + itemsList.get(i).getTransType());
        holder.date.setText( itemsList.get(i).getCheckDueDate());
        holder.from.setText("This Cheque from "+ itemsList.get(i).getCustName());
        holder.to.setText("This Cheque to "+itemsList.get(i).getToCustomerName());
        holder.TranseType.setText("This Tran From bank Name  : "+ itemsList.get(i).getTransType());
        holder.bankName .setText("This Tran From bank Name  : "+ itemsList.get(i).getBankName());
        holder.AmountJd .setText("Amount is : "+ itemsList.get(i).getMoneyInDinar()+"."+itemsList.get(i).getMoneyInFils());
        holder.AmountWord .setText(""+itemsList.get(i).getMoneyInWord());
        holder.branchNo .setText("This Tran From bank Name  : "+ itemsList.get(i).getBranchNo());




        if(itemsList.get(i).getStatus().equals("2")){
            holder.status.setBorderColor(context.getResources().getColor(R.color.RealRed));
        }else if(itemsList.get(i).getStatus().equals("1")){
            holder.status.setBorderColor(context.getResources().getColor(R.color.RealGreen));
        }else if(itemsList.get(i).getStatus().equals("0")){
            holder.status.setBorderColor(context.getResources().getColor(R.color.blue));

        }

        if(phoneNo.equals(itemsList.get(i).getUserName())){
            holder.status.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
        }else if(phoneNo.equals(itemsList.get(i).getToCustomerMobel())){
            holder.status.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
        }

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

