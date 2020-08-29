package com.falconssoft.centerbank;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.falconssoft.centerbank.Models.ChequeInfo;
import com.github.chrisbanes.photoview.PhotoView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

//import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_PRIVATE;
import static com.falconssoft.centerbank.AlertScreen.serverLink;
import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;
import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;

public class ListAdapterLogHistory extends BaseAdapter {
    CheckBox checkPriceed;
    private LogHistoryActivity context;
    List<ChequeInfo> itemsList;
    Bitmap serverPicBitmap,geroBitmap;
//    CircleImageView CircleImage;
    String phoneNos,language;
    public static String acc="",bankN="",branch="",cheNo="", mobile_No="",isJoin="";
    ViewHolder holders;
    String serverLink;
    SweetAlertDialog pd,pdGiro;


    public ListAdapterLogHistory(LogHistoryActivity context, List<ChequeInfo> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        SharedPreferences prefs = context.getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE);
        language = prefs.getString("language", "en");//"No name defined" is the default value.
        Log.e("editing,3 ", language);

        SharedPreferences loginPrefs = context.getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        this.phoneNos = loginPrefs.getString("mobile", "");
        this.serverLink = loginPrefs.getString("link", "");

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
       LinearLayout detailRow,resonRejectLinearJoin,resonRejectLinear;
        TextView name,transType,StatW,date,from,to,TranseType,bankName,AmountJd,AmountWord,branchNo,cheqNo,chequNo,reSend ,bankName_text,phoneNo,Retrieval,resonReject,resonRejectJoin1,resonRejectJoin2,resonRejectJoin3;//, price
CircleImageView status,CircleImage,circleGeroImg;
TableRow detail;




    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        new LocaleAppUtils().changeLayot(context);
        view = View.inflate(context, R.layout.report_row_log_history, null);

        holder.bankName_text= view.findViewById(R.id.bankName_text);
        holder.Retrieval= view.findViewById(R.id.Retrieval);
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
        holder.resonRejectLinearJoin =view.findViewById(R.id.resonRejectLinearJoin);
        holder.resonRejectLinear=view.findViewById(R.id.resonRejectLinear);
        holder.resonReject=view.findViewById(R.id.resonReject);
        holder.resonRejectJoin1=view.findViewById(R.id.resonRejectJoin1);
        holder.resonRejectJoin2=view.findViewById(R.id.resonRejectJoin2);
        holder.resonRejectJoin3=view.findViewById(R.id.resonRejectJoin3);

        String TStatus="";
        String T_Type="";

        holder.CircleImage= view.findViewById(R.id.logImage);
        holder.circleGeroImg= view.findViewById(R.id.logImageGiro);


        if(!itemsList.get(i).getIsJoin().equals("1")){

            holder.resonRejectLinearJoin.setVisibility(View.GONE);

            if(itemsList.get(i).getTransType().equals("2")) {
                holder.resonRejectLinear.setVisibility(View.VISIBLE);
                holder.resonReject.setVisibility(View.VISIBLE);
                holder.resonReject.setText(context.getResources().getString(R.string.resonofReject)+"\n"+itemsList.get(i).getResonOfreject());
            }else {
                holder.resonRejectLinear.setVisibility(View.GONE);
                holder.resonReject.setVisibility(View.GONE);
            }

        }else{
            holder.resonRejectLinearJoin.setVisibility(View.VISIBLE);

            if(itemsList.get(i).getTransType().equals("200")||itemsList.get(i).getTransType().equals("2")) {
                holder.resonRejectLinear.setVisibility(View.VISIBLE);
                holder.resonReject.setVisibility(View.VISIBLE);
                if (itemsList.get(i).getTransType().equals("200")){
                    holder.resonReject.setText(context.getResources().getString(R.string.resonofReject)+"\n"+context.getResources().getString(R.string.jointReject));

                }else  if (itemsList.get(i).getTransType().equals("2")){
                    holder.resonReject.setText(context.getResources().getString(R.string.resonofReject)+"\n"+itemsList.get(i).getResonOfreject());

                }
            }else {
                holder.resonRejectLinear.setVisibility(View.GONE);
                holder.resonReject.setVisibility(View.GONE);
            }

            if(itemsList.get(i).getJOIN_F_STATUS().equals("1")){

                holder.resonRejectJoin1.setText("\n"+itemsList.get(i).getJOIN_FirstMOB()+"\n"+context.getResources().getString(R.string.acccept)+"\n"+"__________________________________");


            }else if(itemsList.get(i).getJOIN_F_STATUS().equals("2")){

                holder.resonRejectJoin1.setText(itemsList.get(i).getJOIN_FirstMOB()+"\n"+context.getResources().getString(R.string.Reject)+"\n\n"+context.getResources().getString(R.string.resonofReject)+"\n"+itemsList.get(i).getJOIN_F_REASON()+"\n"+"__________________________________");

            }


            if(itemsList.get(i).getJOIN_S_STATUS().equals("1")){

                holder.resonRejectJoin2.setText(itemsList.get(i).getJOIN_SecondSMOB()+"\n"+context.getResources().getString(R.string.acccept)+"\n"+"__________________________________");

            }else if(itemsList.get(i).getJOIN_S_STATUS().equals("2")){

                holder.resonRejectJoin2.setText(itemsList.get(i).getJOIN_SecondSMOB()+"\n"+context.getResources().getString(R.string.Reject)+"\n\n\n"+context.getResources().getString(R.string.resonofReject)+"\n"+itemsList.get(i).getJOIN_S_REASON()+"\n"+"__________________________________");

            }


            if(itemsList.get(i).getJOIN_T_STATUS().equals("1")){

                holder.resonRejectJoin3.setText(itemsList.get(i).getJOIN_TheredMOB()+"\n"+context.getResources().getString(R.string.acccept)+"\n"+"__________________________________");

            }else if(itemsList.get(i).getJOIN_T_STATUS().equals("2")){
                holder.resonRejectJoin3.setText(itemsList.get(i).getJOIN_TheredMOB()+"\n"+context.getResources().getString(R.string.Reject)+"\n\n\n"+context.getResources().getString(R.string.resonofReject)+"\n"+itemsList.get(i).getJOIN_T_REASON()+"\n"+"__________________________________");
            }


        }

        if(itemsList.get(i).getTransType().equals("2")){
            holder.status.setBorderColor(context.getResources().getColor(R.color.RealRed));
            holder.StatW.setTextColor(context.getResources().getColor(R.color.RealRed));
            TStatus=context.getResources().getString(R.string.Reject);

        }else if(itemsList.get(i).getTransType().equals("1")){
            holder.status.setBorderColor(context.getResources().getColor(R.color.RealGreen));
            holder.StatW.setTextColor(context.getResources().getColor(R.color.RealGreen));
            TStatus=context.getResources().getString(R.string.acccept);
        }else if(itemsList.get(i).getTransType().equals("0")||itemsList.get(i).getTransType().equals("")||itemsList.get(i).getTransType().equals("100")){
            holder.status.setBorderColor(context.getResources().getColor(R.color.blue));
            holder.StatW.setTextColor(context.getResources().getColor(R.color.blue));
            if(!itemsList.get(i).getTransType().equals("100")&&itemsList.get(i).getIsJoin().equals("0")){
                TStatus=context.getResources().getString(R.string.pending);
            }else{
                TStatus=context.getResources().getString(R.string.Join)+" / "+context.getResources().getString(R.string.pending);
            }


        } else if(itemsList.get(i).getTransType().equals("3")){//OWNERMOBNO
            holder.status.setBorderColor(context.getResources().getColor(R.color.gray_));
            holder.StatW.setTextColor(context.getResources().getColor(R.color.gray_));
            TStatus=context.getResources().getString(R.string.cashed);
        }else if(itemsList.get(i).getTransType().equals("4")){//OWNERMOBNO
            holder.status.setBorderColor(context.getResources().getColor(R.color.dark_yellow));
            holder.StatW.setTextColor(context.getResources().getColor(R.color.dark_yellow));
            TStatus=context.getResources().getString(R.string.retrieval);
        }else if(itemsList.get(i).getTransType().equals("200")){//OWNERMOBNO
            holder.status.setBorderColor(context.getResources().getColor(R.color.RealRed));
            holder.StatW.setTextColor(context.getResources().getColor(R.color.RealRed));
            TStatus=context.getResources().getString(R.string.Reject);
        }

        holder.reSend.setVisibility(View.GONE);

        holder.Retrieval.setVisibility(View.GONE);
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


        if (itemsList.get(i).getTransSendOrGero().equals("1")) {
            T_Type=context.getResources().getString(R.string.giro);
//            holder.status.setBackground(context.getResources().getDrawable(R.drawable.ic_swap));
        } else if (itemsList.get(i).getTransSendOrGero().equals("0")) {
//            holder.status.setBackground(null);
            T_Type=context.getResources().getString(R.string.Issue);
        }


        holder.detailRow.setVisibility(View.GONE);
//        holder.state.setText("" + itemsList.get(i).getStatus());

        holder.TranseType.setText(context.getResources().getString(R.string.ch_status)+" \n " +T_Type+" / "+TStatus);
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


        holder.CircleImage.setVisibility(View.GONE);
        holder.circleGeroImg.setVisibility(View.GONE);

        if(!itemsList.get(i).getTransType().equals("200")) {
            if (itemsList.get(i).getStatus().equals("0")) {
                holder.status.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
                holder.branchNo.setText(context.getResources().getString(R.string.account_no_) + "\n " + itemsList.get(i).getAccCode().substring(1));
                holder.branchNo.setVisibility(View.VISIBLE);
                holder.bankName_text.setVisibility(View.VISIBLE);
            } else if (itemsList.get(i).getStatus().equals("1")) {
                holder.status.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
                holder.branchNo.setVisibility(View.GONE);
                holder.bankName_text.setVisibility(View.GONE);
            }
        }else   if(itemsList.get(i).getTransType().equals("200")) {

            if (itemsList.get(i).getStatus().equals("0")) {
                holder.status.setImageResource(R.drawable.ic_merge_type_black_24dp);
                holder.branchNo.setText(context.getResources().getString(R.string.account_no_) + "\n " + itemsList.get(i).getAccCode().substring(1));
                holder.branchNo.setVisibility(View.VISIBLE);
                holder.bankName_text.setVisibility(View.VISIBLE);
            } else if (itemsList.get(i).getStatus().equals("1")) {
                holder.status.setRotation(180);
                holder.status.setImageResource(R.drawable.ic_merge_type_black_24dp);
                holder.branchNo.setVisibility(View.GONE);
                holder.bankName_text.setVisibility(View.GONE);
            }

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
//String type,String accCode, String bankName, String branchNo, String chequeNo
                if(!itemsList.get(i).getTransSendOrGero().equals("1")) {
                    getPicture(itemsList.get(i).getTransSendOrGero(), itemsList.get(i).getAccCode(), itemsList.get(i).getBankNo(),
                            itemsList.get(i).getBranchNo(), itemsList.get(i).getChequeNo(), holder);
                    holder.CircleImage.setVisibility(View.VISIBLE);
                    holder.circleGeroImg.setVisibility(View.GONE);
                }else{
                    getPicture(itemsList.get(i).getTransSendOrGero(), itemsList.get(i).getAccCode(), itemsList.get(i).getBankNo(),
                            itemsList.get(i).getBranchNo(), itemsList.get(i).getChequeNo(), holder);
                    holder.CircleImage.setVisibility(View.VISIBLE);
                    holder.circleGeroImg.setVisibility(View.VISIBLE);

                }



            }
        });



       holder.CircleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageOfCheck(serverPicBitmap);
            }
        });

        holder.circleGeroImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageOfCheck(geroBitmap);
            }
        });


            if(itemsList.get(i).getStatus().equals("0")&&(itemsList.get(i).getTransType().equals("0")||itemsList.get(i).getTransType().equals(""))&&itemsList.get(i).getUserName().equals(phoneNos)){
                holder.Retrieval.setVisibility(View.VISIBLE);
            }else {
                holder.Retrieval.setVisibility(View.GONE);
            }


        holder.phoneNo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                {
                    new SharedClass(context).showPhoneOptions(holder.phoneNo.getText().toString());
                    return true;
                }
            }
        });

        holder.Retrieval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.Retrive(itemsList.get(i));
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
    public static boolean isProbablyArabic(String s) {//know if char is arabic or eng
        for (int i = 0; i < s.length();) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

    public void showImageOfCheck(Bitmap bitmap) {//for show image and zoom this image in dialog
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.show_image);

        PhotoView  photoDetail= (PhotoView) dialog.findViewById(R.id.image_check);
//          final ImageView imageView = (ImageView) dialog.findViewById(R.id.image_check);
        photoDetail.setImageBitmap(bitmap);
        dialog.show();
    }

    private void getPicture(String type,String accCode, String bankName, String branchNo, String chequeNo,ViewHolder holder) {
        SharedPreferences loginPrefs = context.getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        serverLink = loginPrefs.getString("link", "");
        acc    =accCode;
        bankN  =bankName;
        branch = branchNo;
        cheNo  =chequeNo;
//        if(type.equals("0"))
//        {
            new Image(holder,type).execute();
//        }
//        else {
//            new ImageGero(holder).execute();
//        }


    }

    protected class Image extends AsyncTask<String, String, String> {//get Image from server
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        public  String typeImg="0";
        ViewHolder holder;

        public Image(ViewHolder holder,String typeImg) {
            this.holder = holder;
            this. typeImg=typeImg;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText(context.getResources().getString(R.string.save_success));
            pd.setCancelable(false);
            pd.show();


            serverPicBitmap=null;
                    geroBitmap=null;

        }

        @Override
        protected String doInBackground(String... params) {
            try{
            String link = serverLink + "GetCheckPic";

            String data = "ACCCODE=" + URLEncoder.encode(acc, "UTF-8")+ "&"
                    + "BANKNO=" + URLEncoder.encode(bankN, "UTF-8")+ "&"
                    +"BRANCHNO=" + URLEncoder.encode(branch, "UTF-8")+ "&"
                    +"CHECKNO=" + URLEncoder.encode(cheNo, "UTF-8");
//
            URL url = new URL(link);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(data);
            wr.flush();
            wr.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer stringBuffer = new StringBuffer();

            while ((JsonResponse = bufferedReader.readLine()) != null) {
                stringBuffer.append(JsonResponse + "\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());

            Log.e("tag", "dataSave  -->" + data);

            return stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("tag", "Error closing stream", e);
                }
            }
        }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("notificationadpter/", "saved//" + s);
            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {


                    Log.e("tag", "****saved Success In Edit");

                    JSONObject jsonObject = null;


                    try {
                        jsonObject = new JSONObject(s);
//                        saveJSONData(EditerCheackActivity.this,jsonObject.getString("CHECKPIC"));
//
//                       serverPicBitmap= StringToBitMap(getJSONData(EditerCheackActivity.this));
//
//
                        serverPicBitmap = StringToBitMap(jsonObject.getString("CHECKPIC"));
                        Log.e("serverPicBitmap",""+serverPicBitmap);


                        holder.CircleImage.setImageBitmap(serverPicBitmap);

                        pd.dismissWithAnimation();

                        if(!typeImg.equals("0")){
                            new ImageGero(holder).execute();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.e("tagImage", "****Failed to export data");
                    if(!typeImg.equals("0")){
                        new ImageGero(holder).execute();
                    }
                    pd.dismissWithAnimation();
                }
            } else {
                Log.e("tag", "****Failed to export data Please check internet connection");
                if(!typeImg.equals("0")){
                    new ImageGero(holder).execute();
                }
                pd.dismissWithAnimation();
            }
        }
    }

    protected class ImageGero extends AsyncTask<String, String, String> {//get Giro Image from server
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        ViewHolder holder;

        public ImageGero(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdGiro = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pdGiro.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdGiro.setTitleText(context.getResources().getString(R.string.save_success));
            pdGiro.setCancelable(false);
            pdGiro.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                String link = serverLink + "GetGeroPic";

                String data = "ACCCODE=" + URLEncoder.encode(acc, "UTF-8")+ "&"
                        + "BANKNO=" + URLEncoder.encode(bankN, "UTF-8")+ "&"
                        +"BRANCHNO=" + URLEncoder.encode(branch, "UTF-8")+ "&"
                        +"CHECKNO=" + URLEncoder.encode(cheNo, "UTF-8");
//
                URL url = new URL(link);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());

                Log.e("tag", "dataSave  -->" + data);

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("notificationadpter/", "saved//" + s);
            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
                    pdGiro.dismissWithAnimation();
                    Log.e("tag", "****saved Success In Adapter");

                    JSONObject jsonObject = null;


                    try {
                        jsonObject = new JSONObject(s);

                        geroBitmap = StringToBitMap(jsonObject.getString("CHECKPIC"));

                            holder.circleGeroImg.setImageBitmap(geroBitmap);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    pdGiro.dismissWithAnimation();
                    Log.e("tagNotificatioAdapter", "****Failed to export data");
                }
            } else {
                pdGiro.dismissWithAnimation();
                Log.e("tagNotificatioAdapter", "****Failed to export data Please check internet connection");
            }
        }
    }

    public Bitmap StringToBitMap(String image) {
        try {

            byte[] encodeByte = Base64.decode(image, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
//            String decoded = new String(encodeByte);

//            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap=null;
            try {
                bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            }
            catch (Exception e)
            {
//                R.drawable.check_bank

                Toast.makeText(context, "memorY is full can't display image ", Toast.LENGTH_SHORT).show();
            }

            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}

