package com.falconssoft.centerbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.falconssoft.centerbank.Models.ChequeInfo;

import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.falconssoft.centerbank.JeroActivity.getTrial;
import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;

//import static android.content.Context.MODE_PRIVATE;

public class ListAdapterGiro extends BaseAdapter {
    CheckBox checkPriceed;
    private JeroActivity context;
    List<ChequeInfo> itemsList;
    String phoneNo, language;
    LinearLayout liner;
    int index;
    boolean isWallet=false;

    public ListAdapterGiro(JeroActivity context, List<ChequeInfo> itemsList, LinearLayout liner,boolean isWallet) {
        this.context = context;
        this.itemsList = itemsList;
        SharedPreferences prefs = context.getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE);
        language = prefs.getString("language", "en");//"No name defined" is the default value.
        Log.e("editing,3 ", language);
        this.liner = liner;
        this.isWallet=isWallet;
        Log.e("sizeLog", "" + itemsList.size());
    }

    public ListAdapterGiro() {

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
        TextView name,status, transType, date, from, to, TranseType, bankName, AmountJd, AmountWord, branchNo, cheqNo, chequNo,bankName_text,phoneNo;//, price
//CircleImageView status;

        TableRow detail;

        Button send,lostSteal;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        new LocaleAppUtils().changeLayot(context);

        view = View.inflate(context, R.layout.report_row_giro, null);

        holder.phoneNo= view.findViewById(R.id.phoneNo);
        holder.bankName_text= view.findViewById(R.id.bankName_text);
        holder.detailRow = view.findViewById(R.id.detailRow);
        holder.status =  view.findViewById(R.id.status);
        holder.detail =  view.findViewById(R.id.detail);
        holder.name = view.findViewById(R.id.name);
        holder.cheqNo = view.findViewById(R.id.cheqNo);
        holder.date = view.findViewById(R.id.date);
        holder.from = view.findViewById(R.id.from);
        holder.to = view.findViewById(R.id.to);
        holder.TranseType = view.findViewById(R.id.status);
        holder.chequNo = view.findViewById(R.id.chequNo);
        holder.bankName = view.findViewById(R.id.bankName);
        holder.AmountJd = view.findViewById(R.id.AmountJd);
        holder.AmountWord = view.findViewById(R.id.AmountWord);
        holder.branchNo = view.findViewById(R.id.branchNo);
        holder.send = view.findViewById(R.id.sendGiro);
        holder.lostSteal= view.findViewById(R.id.lostSteal);

        String TStatus = "";


        if(isWallet){
            holder.send.setVisibility(View.GONE);
            holder.lostSteal.setVisibility(View.GONE);
        }else {
            holder.send.setVisibility(View.VISIBLE);
            holder.lostSteal.setVisibility(View.VISIBLE);
        }




        if(itemsList.get(i).getTransType().equals("2")){
//            holder.status.setBorderColor(context.getResources().getColor(R.color.RealRed));
            holder.status.setTextColor(context.getResources().getColor(R.color.RealRed));
            TStatus=context.getResources().getString(R.string.rej);
        }else if(itemsList.get(i).getTransType().equals("1")){
            holder.status.setTextColor(context.getResources().getColor(R.color.RealGreen));
            TStatus=context.getResources().getString(R.string.acccept);
        }else if(itemsList.get(i).getTransType().equals("0")||itemsList.get(i).getTransType().equals("")){
            holder.status.setTextColor(context.getResources().getColor(R.color.blue));
            TStatus=context.getResources().getString(R.string.pending);

        }


        holder.detailRow.setVisibility(View.GONE);
        holder.branchNo.setVisibility(View.GONE);
        holder.bankName_text.setVisibility(View.GONE);
//        holder.state.setText("" + itemsList.get(i).getStatus());

        holder.TranseType.setText( TStatus);
        holder.chequNo.setText(itemsList.get(i).getChequeNo());
        holder.phoneNo.setText("+"+itemsList.get(i).getToCustomerMobel());

        holder.name.setText("" +itemsList.get(i).getCustName());
//        holder.transType.setText("" + itemsList.get(i).getTransType());
        holder.date.setText(itemsList.get(i).getCheckDueDate());
        holder.from.setText( itemsList.get(i).getCustName());
        holder.to.setText( itemsList.get(i).getToCustomerName());
        holder.bankName.setText(  itemsList.get(i).getBankName());
        holder.AmountJd.setText(context.getResources().getString(R.string.amount_word) + "  :  " + itemsList.get(i).getMoneyInDinar() + "." + itemsList.get(i).getMoneyInFils() + " JD");
        holder.AmountWord.setText("(" + itemsList.get(i).getMoneyInWord() + ")");
        holder.cheqNo.setText( itemsList.get(i).getChequeNo() + "");


//
//        if(itemsList.get(i).getStatus().equals("0")){
//            holder.status.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
//            holder.branchNo .setText(context.getResources().getString(R.string.account_no_)+ itemsList.get(i).getAccCode().substring(1));
//            holder.branchNo .setVisibility(View.VISIBLE);
//        }else if(itemsList.get(i).getStatus().equals("1")){
//            holder.status.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
//            holder.branchNo .setVisibility(View.GONE);
//
//        }

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemsList.get(i).getISOpen().equals("0")) {
                    holder.detailRow.setVisibility(View.VISIBLE);
                    itemsList.get(i).setISOpen("1");
                } else {
                    holder.detailRow.setVisibility(View.GONE);
                    itemsList.get(i).setISOpen("0");
                }


            }
        });


        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index = i;
                getTrial.setTag("" + i);
                getTrial.setText("1");
                context.checkIfBending();
//                holder.send.setEnabled(false);


            }
        });


        holder.lostSteal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intLost=new Intent(context,LostAndStealing.class);
                context.startActivity(intLost);
                Toast.makeText(context, "lost/stealing", Toast.LENGTH_SHORT).show();

            }
        });

        holder.phoneNo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                {
                    new SharedClass(context).showPhoneOptions(holder.phoneNo.getText().toString());
                    return true;
                }
            }
        });


        return view;
    }


    public int returnIndex() {

        return index;
    }

//    void checkLanguage(ViewHolder holder) {
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
////                    , ContextCompat.getDrawable(context, R.drawable.ic_attach_money_black_24dp), null);
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
////
////            holder.AmountJd.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_attach_money_black_24dp), null
////                    , null, null);
////
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

