package com.falconssoft.centerbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.falconssoft.centerbank.Models.ChequeInfo;

import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

//import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_PRIVATE;
import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;
import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;

public class ListAdapterLogHistory extends BaseAdapter {
    CheckBox checkPriceed;
    private LogHistoryActivity context;
    List<ChequeInfo> itemsList;
 String phoneNo,language;

    public ListAdapterLogHistory(LogHistoryActivity context, List<ChequeInfo> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        SharedPreferences prefs = context.getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE);
        language = prefs.getString("language", "en");//"No name defined" is the default value.
        Log.e("editing,3 ", language);

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
        TextView name,transType,StatW,date,from,to,TranseType,bankName,AmountJd,AmountWord,branchNo,cheqNo,chequNo,reSend ,bankName_text,phoneNo;//, price
CircleImageView status;
TableRow detail;




    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        new LocaleAppUtils().changeLayot(context);
        view = View.inflate(context, R.layout.report_row_log_history, null);

        holder.bankName_text= view.findViewById(R.id.bankName_text);

        holder.phoneNo= view.findViewById(R.id.phoneNo);
        holder.detailRow =  view.findViewById(R.id.detailRow);
        holder.reSend =  view.findViewById(R.id.reSend);
        holder.StatW =  view.findViewById(R.id.status);
        holder.name=  view.findViewById(R.id.name);
        holder.cheqNo=  view.findViewById(R.id.cheqNo);
        holder.date=  view.findViewById(R.id.date);
        holder.status=  view.findViewById(R.id.statuts);
        holder.from =  view.findViewById(R.id.from);
        holder.to =  view.findViewById(R.id.to);
        holder.TranseType =  view.findViewById(R.id.status);
        holder.chequNo=  view.findViewById(R.id.chequNo);
        holder.bankName =  view.findViewById(R.id.bankName);
        holder.AmountJd =  view.findViewById(R.id.AmountJd);
        holder.AmountWord =  view.findViewById(R.id.AmountWord);
        holder.branchNo =  view.findViewById(R.id.branchNo);
        holder.detail=  view.findViewById(R.id.detailLog);
        String TStatus="";

        if(itemsList.get(i).getTransType().equals("2")){
            holder.status.setBorderColor(context.getResources().getColor(R.color.RealRed));
            holder.StatW.setTextColor(context.getResources().getColor(R.color.RealRed));
            TStatus=context.getResources().getString(R.string.rej);

        }else if(itemsList.get(i).getTransType().equals("1")){
            holder.status.setBorderColor(context.getResources().getColor(R.color.RealGreen));
            holder.StatW.setTextColor(context.getResources().getColor(R.color.RealGreen));
            TStatus=context.getResources().getString(R.string.acccept);
        }else if(itemsList.get(i).getTransType().equals("0")||itemsList.get(i).getTransType().equals("")){
            holder.status.setBorderColor(context.getResources().getColor(R.color.blue));
            holder.StatW.setTextColor(context.getResources().getColor(R.color.blue));
            TStatus=context.getResources().getString(R.string.pending);

        } else if(itemsList.get(i).getTransType().equals("3")){//OWNERMOBNO
            holder.status.setBorderColor(context.getResources().getColor(R.color.gray_));
            holder.StatW.setTextColor(context.getResources().getColor(R.color.gray_));
            TStatus=context.getResources().getString(R.string.cashed);
        }

        holder.reSend.setVisibility(View.GONE);
//        if(itemsList.get(i).getTransType().equals("2")&&itemsList.get(i).getStatus().equals("0")){
//            holder.reSend.setVisibility(View.VISIBLE);
//        }else {
//            holder.reSend.setVisibility(View.GONE);
//        }


        holder.reSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(itemsList.get(i).getTransType().equals("2")&&itemsList.get(i).getStatus().equals("0")){

//                    Intent EditeIntent=new Intent(context,EditerCheackActivity.class);
                   context.startEditerForReSend(itemsList.get(i));
                    Toast.makeText(context, "Resend "+itemsList.get(i).getChequeNo(), Toast.LENGTH_SHORT).show();

                }else {
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ReSend Error!")
                            .setContentText("This Cheque Can't Resend States is not Reject")
                            .show();
                }

            }
        });


        holder.detailRow.setVisibility(View.GONE);
//        holder.state.setText("" + itemsList.get(i).getStatus());

        holder.TranseType.setText(context.getResources().getString(R.string.ch_status)+" \n " +TStatus);
        holder.chequNo.setText(itemsList.get(i).getChequeNo());
        holder.phoneNo.setText("+"+itemsList.get(i).getToCustomerMobel());

        holder.name.setText(" " + itemsList.get(i).getCustName());
//        holder.transType.setText("" + itemsList.get(i).getTransType());
        holder.date.setText( itemsList.get(i).getCheckDueDate());
        holder.from.setText( itemsList.get(i).getCustName());
        holder.to.setText(itemsList.get(i).getToCustomerName());
        holder.bankName .setText(itemsList.get(i).getBankName());
        holder.AmountJd .setText(context.getResources().getString(R.string.amount_word)+" : " + itemsList.get(i).getMoneyInDinar()+"."+itemsList.get(i).getMoneyInFils()+" JD");
        holder.AmountWord .setText("("+itemsList.get(i).getMoneyInWord()+")");
        holder.cheqNo. setText(itemsList.get(i).getChequeNo()+"");




        if(itemsList.get(i).getStatus().equals("0")){
            holder.status.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
            holder.branchNo .setText(context.getResources().getString(R.string.account_no_) +"\n "+ itemsList.get(i).getAccCode().substring(1));
            holder.branchNo .setVisibility(View.VISIBLE);
            holder.bankName_text.setVisibility(View.VISIBLE);
        }else if(itemsList.get(i).getStatus().equals("1")){
            holder.status.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
            holder.branchNo .setVisibility(View.GONE);
            holder.bankName_text.setVisibility(View.GONE);
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

//    void checkLanguage(ViewHolder holder){
//        if (language.trim().equals("ar")) {
//            LocaleAppUtils.setLocale(new Locale("ar"));
//            LocaleAppUtils.setConfigChange(context);
//
//            holder.TranseType.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(context, R.drawable.ic_person_black_24dp), null);
//
//            holder.cheqNo.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(context, R.drawable.ic_border_color_black_24dp), null);
//            holder.from.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(context, R.drawable.ic_border_color_black_24dp), null);
//            holder.to.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(context, R.drawable.ic_date_range_black_24dp), null);
//            holder.bankName.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(context, R.drawable.ic_account_balance_black_24dp), null);
////            holder.AmountJd.setCompoundDrawablesWithIntrinsicBounds(null, null
////                    , ContextCompat.getDrawable(context, R.drawable.ic_account_balance_black_24dp), null);
//            holder.AmountWord.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(context, R.drawable.ic_notes), null);
//            holder.branchNo.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(context, R.drawable.ic_account_circle_black_24dp), null);
//
//        } else {
//            LocaleAppUtils.setLocale(new Locale("en"));
//            LocaleAppUtils.setConfigChange(context);
//
//            holder.TranseType.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_person_black_24dp), null
//                    , null, null);
//            holder.cheqNo.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_border_color_black_24dp), null
//                    , null, null);
//
//            holder.from.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_border_color_black_24dp), null
//                    , null, null);
//            holder.to.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_date_range_black_24dp), null
//                    , null, null);
//            holder.bankName.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_account_balance_black_24dp), null
//                    , null, null);
//
////            holder.AmountJd.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_attach_money_black_24dp), null
////                    , null, null);
//
//
//
//            holder.AmountWord.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_notes), null
//                    , null, null);
//
//
//            holder.branchNo.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_account_circle_black_24dp), null
//                    , null, null);
//
//
//        }
//
//    }
    public static boolean isProbablyArabic(String s) {
        for (int i = 0; i < s.length();) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

}

