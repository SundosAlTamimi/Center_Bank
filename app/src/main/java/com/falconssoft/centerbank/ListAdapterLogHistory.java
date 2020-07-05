package com.falconssoft.centerbank;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.falconssoft.centerbank.Models.ChequeInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//import static android.content.Context.MODE_PRIVATE;
import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;

public class ListAdapterLogHistory extends BaseAdapter {
    CheckBox checkPriceed;
    private Context context;
    List<ChequeInfo> itemsList;
 String phoneNo;

    public ListAdapterLogHistory(Context context, List<ChequeInfo> itemsList) {
        this.context = context;
        this.itemsList = itemsList;

//        SharedPreferences loginPrefs =this.context.getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
//        phoneNo = loginPrefs.getString("mobile", "");

        Log.e("sizeLog",""+itemsList.size());
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
        TextView name,transType,date,detail,from,to,TranseType,bankName,AmountJd,AmountWord,branchNo,cheqNo,chequNo ;//, price
CircleImageView status;




    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.report_row_log_history, null);


        holder.detailRow =  view.findViewById(R.id.detailRow);
        holder.status =  view.findViewById(R.id.statuts);
        holder.name=  view.findViewById(R.id.name);
        holder.cheqNo=  view.findViewById(R.id.cheqNo);
        holder.date=  view.findViewById(R.id.date);
        holder.detail=  view.findViewById(R.id.Detail);
        holder.from =  view.findViewById(R.id.from);
        holder.to =  view.findViewById(R.id.to);
        holder.TranseType =  view.findViewById(R.id.status);
        holder.chequNo=  view.findViewById(R.id.chequNo);
        holder.bankName =  view.findViewById(R.id.bankName);
        holder.AmountJd =  view.findViewById(R.id.AmountJd);
        holder.AmountWord =  view.findViewById(R.id.AmountWord);
        holder.branchNo =  view.findViewById(R.id.branchNo);
        String TStatus="";

        if(itemsList.get(i).getTransType().equals("2")){
            holder.status.setBorderColor(context.getResources().getColor(R.color.RealRed));
            TStatus="Reject";
        }else if(itemsList.get(i).getTransType().equals("1")){
            holder.status.setBorderColor(context.getResources().getColor(R.color.RealGreen));
            TStatus="Accept";
        }else if(itemsList.get(i).getTransType().equals("0")||itemsList.get(i).getTransType().equals("")){
            holder.status.setBorderColor(context.getResources().getColor(R.color.blue));
            TStatus="pending";

        }


        holder.detailRow.setVisibility(View.GONE);
//        holder.state.setText("" + itemsList.get(i).getStatus());

        holder.TranseType.setText("Cheque Status : "+TStatus);
        holder.chequNo.setText(itemsList.get(i).getChequeNo());

        holder.name.setText("" + itemsList.get(i).getCustName());
//        holder.transType.setText("" + itemsList.get(i).getTransType());
        holder.date.setText( itemsList.get(i).getCheckDueDate());
        holder.from.setText(" Cheque Writer : "+ itemsList.get(i).getCustName());
        holder.to.setText(" Cheque Beneficiary : "+itemsList.get(i).getToCustomerName());
        holder.bankName .setText("Bank Name  : "+ itemsList.get(i).getBankName());
        holder.AmountJd .setText("Amount is : "+ itemsList.get(i).getMoneyInDinar()+"."+itemsList.get(i).getMoneyInFils()+" JD");
        holder.AmountWord .setText("("+itemsList.get(i).getMoneyInWord()+")");
        holder.cheqNo. setText("Cheque No : "+itemsList.get(i).getChequeNo()+"");




        if(itemsList.get(i).getStatus().equals("0")){
            holder.status.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
            holder.branchNo .setText(" Account No  : "+ itemsList.get(i).getAccCode().substring(1));
            holder.branchNo .setVisibility(View.VISIBLE);
        }else if(itemsList.get(i).getStatus().equals("1")){
            holder.status.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
            holder.branchNo .setVisibility(View.GONE);

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

