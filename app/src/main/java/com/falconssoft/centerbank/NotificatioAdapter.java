package com.falconssoft.centerbank;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.falconssoft.centerbank.Models.ChequeInfo;
import com.falconssoft.centerbank.Models.notification;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.falconssoft.centerbank.AlertScreen.checkInfoNotification;
import static com.falconssoft.centerbank.AlertScreen.editor;
import static com.falconssoft.centerbank.AlertScreen.language;
import static com.falconssoft.centerbank.AlertScreen.sharedPreferences;
import static com.falconssoft.centerbank.AlertScreen.textCheckstateChanger;
import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;

public class NotificatioAdapter  extends  RecyclerView.Adapter<NotificatioAdapter.ViewHolder> {
    //    RecyclerView.Adapter<engineer_adapter.ViewHolder>
    Context context;
    List<notification> notificationList;
    int row_index=-1;
     String checkState="0";
    public static String languagelocalApp="";


    public NotificatioAdapter(Context context, List<notification> notifications) {
        this.context = context;
        this.notificationList = notifications;
        Log.e("notificationList",""+notificationList.size());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_notification, viewGroup, false);

        return new NotificatioAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        if(language.equals("ar"))
        {
            viewHolder.mainLinearAdapter.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            viewHolder.date_check.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            viewHolder.amount_check.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            viewHolder.source_check.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        else {
            viewHolder.mainLinearAdapter.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            viewHolder.date_check.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            viewHolder.amount_check.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            viewHolder.source_check.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        }
        viewHolder.date_check.setText(notificationList.get(i).getDate());
        viewHolder.amount_check.setText(notificationList.get(i).getAmount_check());
        viewHolder.source_check.setText(notificationList.get(i).getSource());
//        viewHolder.image_check.setImageDrawable(R.drawable.check);
        viewHolder.image_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.showImageOfCheck();

            }
        });
        viewHolder.linearCheckInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=i;
                Log.e("row_index",""+row_index);
                viewHolder.showDetails();
            }
        });
//        viewHolder.linear_companey.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                row_index=i;
//                notifyDataSetChanged();
//
//
////                clickedcom=new CompaneyInfo();
////                clickedcom.setCompanyName(viewHolder.hold_company_name.getText().toString());
////                clickedcom.setPhoneNo(viewHolder.companyTel.getText().toString());
////                companey_name.setText(viewHolder.hold_company_name.getText().toString());
////                telephone_no.setText(viewHolder.companyTel.getText().toString());
////                customer_name.setText(companey.get(i).getCustomerName());
////                systype.setText(companey.get(i).getSystemName());
////                Log.e("systemSelected",""+companey.get(i).getSystemName());
////                Log.e("text_delet_id",""+i);
////                text_delet_id.setText(i+"");
////                //companey.remove(i);
////                // Log.e("companysize",""+companey.size());
////                isInHold=true;
////                companeyInfos.add(clickedcom);
//            }
//
//        });
//        if(row_index==i)
//        {
//            viewHolder.linear_companey.setBackgroundColor(Color.parseColor("#e5e4e2"));
//
//        }
//        else {
//            viewHolder.linear_companey.setBackgroundColor(Color.parseColor("#FFFFFF"));
//
//        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView source_check, amount_check,date_check;
        CircleImageView image_check;
        LinearLayout linearCheckInfo,mainLinearAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            source_check = itemView.findViewById(R.id.source_check);
            amount_check=itemView.findViewById(R.id.amount_check);
            date_check=itemView.findViewById(R.id.date_check);
            image_check=itemView.findViewById(R.id.image_check);
            linearCheckInfo=itemView.findViewById(R.id.linearCheckInfo);
            mainLinearAdapter=itemView.findViewById(R.id.mainLinearAdapter);
//


        }
        public void showImageOfCheck() {
            final Dialog dialog = new Dialog(context,R.style.Theme_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.show_image);
            dialog.show();

            final ImageView imageView = (ImageView) dialog.findViewById(R.id.image_check);
//            imageView.setImageBitmap();



        }

        public void showDetails() {
            Log.e("checkState",""+checkState);

            final Dialog dialog = new Dialog(context,R.style.Theme_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.show_check_detail);
            dialog.show();
            LinearLayout linearLayout=dialog.findViewById(R.id.mainLinearDetail);
            if (language.equals("ar")) {
                linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            } else {
                if (languagelocalApp.equals("en")) {
                    linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }

            }
            TextView textAmouWord,textAmountNo,textToOrder,textSourceCheck,textPhoneNo,texDate,textCompanyname,note;
           ImageView mImageView;PhotoViewAttacher mAttacher;
            texDate =  dialog.findViewById(R.id.texDate);
            texDate.setText(checkInfoNotification.get(row_index).getChequeData());

            textAmouWord =  dialog.findViewById(R.id.textAmouWord);
            textAmouWord.setText(checkInfoNotification.get(row_index).getMoneyInWord());
            textAmountNo =  dialog.findViewById(R.id.textAmountNo);
            textAmountNo.setText(checkInfoNotification.get(row_index).getMoneyInDinar());
            Log.e("Dinar",""+checkInfoNotification.get(row_index).getMoneyInDinar());
            note =  dialog.findViewById(R.id.textnote);
            note.setText("Note");

            textToOrder =  dialog.findViewById(R.id.textToOrder);
            textToOrder.setText(checkInfoNotification.get(row_index).getToCustomerName());

            textSourceCheck =  dialog.findViewById(R.id.textSourceCheck);
            textSourceCheck.setText(checkInfoNotification.get(row_index).getCustName());
            textCompanyname=dialog.findViewById(R.id.textSourceCheck);
            textCompanyname.setText("company Name");
//            textCompanyname.setText(checkInfoNotification.get(row_index).getCustName());


            textPhoneNo =  dialog.findViewById(R.id.textPhoneNo);
            textPhoneNo.setText(checkInfoNotification.get(row_index).getRecieverMobileNo());
//             Any implementation of ImageView can be used!
//            mImageView = (ImageView)dialog.findViewById(R.id.profile_image2);
//
//            // Set the Drawable displayed
//            Drawable bitmap = dialog.getContext().getResources().getDrawable(R.drawable.check);
//            mImageView.setImageDrawable(bitmap);
//
//            // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
//            mAttacher = new PhotoViewAttacher(mImageView);
//            mAttacher.update();
            PhotoView photoView = (PhotoView)dialog.findViewById(R.id.profile_image2);
            photoView.setImageResource(R.drawable.check);


            final Button accept = (Button) dialog.findViewById(R.id.AcceptButton);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkState="1";
                    updateCheckState();
                    dialog.dismiss();
                }
            });
            final Button reject = (Button) dialog.findViewById(R.id.RejectButton);
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkState="2";
                    updateCheckState();
                    dialog.dismiss();
                }
            });
//            imageView.setImageBitmap();



        }
    }

    private void updateCheckState() {
        new JSONTask().execute();
        //http://localhost:8082/UpdateCheckStatus?CHECKNO=390144&BANKNO=004&BRANCHNO=0099&ACCCODE=1014569990011000&IBANNO=""&ROWID=AAAp0DAAuAAAAC2AAA&STATUS=1

    }
    class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI("http://10.0.0.16:8081/UpdateCheckStatus?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("CHECKNO", checkInfoNotification.get(row_index).getChequeNo()));
                nameValuePairs.add(new BasicNameValuePair("BANKNO",  checkInfoNotification.get(row_index).getBankNo()));
                nameValuePairs.add(new BasicNameValuePair("BRANCHNO",  checkInfoNotification.get(row_index).getBranchNo()));
                nameValuePairs.add(new BasicNameValuePair("ACCCODE",  checkInfoNotification.get(row_index).getAccCode()));

                nameValuePairs.add(new BasicNameValuePair("IBANNO",  checkInfoNotification.get(row_index).getIbanNo()));
                nameValuePairs.add(new BasicNameValuePair("ROWID", checkInfoNotification.get(row_index).getRowId()));
                nameValuePairs.add(new BasicNameValuePair("STATUS", checkState));
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));


//                HttpResponse response = client.execute(request);
//                request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = client.execute(request);

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();

                JsonResponse = sb.toString();
                Log.e("tagAlertScreen", "" + JsonResponse);

                return JsonResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
                    Log.e("onPostExecute","OK");
                    refreshScreen();
                    Log.e("tag", "****Success"+s.toString());
                } else {
                    Log.e("tag", "****Failed to Savedata");
                }
            } else {

                Log.e("tag", "****Failed  Please check internet connection");
            }
        }
    }

//    private void refreshScreen() {
//       String id= checkInfoNotification.get(row_index).getRowId();
////        Set<String> set = sharedPreferences.getStringSet("DATE_LIST", null);
////        editor = sharedPreferences.edit();
////        editor.clear();
////
////        arrayListRowFirst.addAll(set);
//    }

    private void refreshScreen() {
        textCheckstateChanger.setText("1");
    }
}