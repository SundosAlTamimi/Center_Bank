package com.falconssoft.centerbank;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.falconssoft.centerbank.Models.LoginINFO;
import com.falconssoft.centerbank.Models.notification;
import com.falconssoft.centerbank.Models.requestModel;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.google.android.material.textfield.TextInputEditText;

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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.LAYOUT_DIRECTION_LTR;
import static android.view.View.LAYOUT_DIRECTION_RTL;
import static com.falconssoft.centerbank.AlertScreen.checkInfoNotification;
import static com.falconssoft.centerbank.AlertScreen.language;
import static com.falconssoft.centerbank.AlertScreen.serverLink;
import static com.falconssoft.centerbank.AlertScreen.textCheckstateChanger;
import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;

public class Requestadapter extends RecyclerView.Adapter<Requestadapter.ViewHolder> {
    //    RecyclerView.Adapter<engineer_adapter.ViewHolder>
    Context context;
    List<notification> notificationList;
    List<requestModel> requestList;
    public PhotoView photoView,photoDetail;
    CircleImageView circleImageView;
    Bitmap serverPicBitmap;
    int row_index = -1;
    String checkState = "0";
    LoginINFO infoUser;
    DatabaseHandler databaseHandler;
    public static String languagelocalApp = "";
    public static String acc="",bankN="",branch="",mobileNo="";


    public Requestadapter(Context context, List<requestModel> notifications) {
        this.context = context;
        this.requestList = notifications;
        databaseHandler=new DatabaseHandler(context);
        Log.e("Requestadapter",""+notifications.size());



    }

    @NonNull
    @Override
    public Requestadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_request, viewGroup, false);


        return new Requestadapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final Requestadapter.ViewHolder viewHolder, final int i) {

        if (language.equals("ar")) {
            viewHolder.mainLinearAdapter.setLayoutDirection(LAYOUT_DIRECTION_RTL);
            viewHolder.lineardetail.setLayoutDirection(LAYOUT_DIRECTION_LTR);
            viewHolder.rowStatus.setLayoutDirection(LAYOUT_DIRECTION_RTL);
//            viewHolder.lineardetail.setBackground(context.getResources().getDrawable(R.drawable.left_background));
            viewHolder.date_check.setLayoutDirection(LAYOUT_DIRECTION_RTL);
            viewHolder.amount_check.setLayoutDirection(LAYOUT_DIRECTION_RTL);
            viewHolder.cust_name.setLayoutDirection(LAYOUT_DIRECTION_RTL);
        } else {
            viewHolder.mainLinearAdapter.setLayoutDirection(LAYOUT_DIRECTION_LTR);
            viewHolder.lineardetail.setLayoutDirection(LAYOUT_DIRECTION_LTR);
//            viewHolder.lineardetail.setBackground(context.getResources().getDrawable(R.drawable.accept_background));

            viewHolder.date_check.setLayoutDirection(LAYOUT_DIRECTION_LTR);
            viewHolder.amount_check.setLayoutDirection(LAYOUT_DIRECTION_LTR);
            viewHolder.cust_name.setLayoutDirection(LAYOUT_DIRECTION_LTR);

        }
        if(requestList.get(i).getTRANSSTATUS().equals("1"))//for me rejected request
        {
            viewHolder.checkimage_state.setImageDrawable(context.getResources().getDrawable(R.drawable.reject_images));
            viewHolder.checkStateText.setText(R.string.requestRejec);

        }
        else {
            viewHolder.checkimage_state.setImageDrawable(context.getResources().getDrawable(R.drawable.request_download));

        }
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String date_=requestList.get(i).getINDATE();
        try {
            String subDate=date_.substring(0,date_.indexOf(" "));
            Log.e("subDate",""+subDate+"\t"+date_);
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date date = format.parse(subDate);
            String dateFormated=sdf.format(date);



            viewHolder.date_check.setText(dateFormated+"");
        }
        catch (Exception e)
        {
            Log.e("date_checkException",""+e.getMessage());
            viewHolder.date_check.setText(requestList.get(i).getINDATE());
        }


        viewHolder.amount_check.setText(requestList.get(i).getAMOUNT()+"\tJD");
        viewHolder.cust_name.setText(requestList.get(i).getFROMUSER_name());


        viewHolder.image_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                viewHolder.showImageOfCheck();

            }
        });
        viewHolder.linearCheckInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = i;
                viewHolder.showDetails();
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cust_name, amount_check, date_check,checkStateText,checkStatuse;
        ImageView image_check;
        LinearLayout linearCheckInfo, mainLinearAdapter,divider,lineardetail,rowStatus;
        CircleImageView acceptImg,rejectImg,reciveNew,checkimage_state;
        SharedPreferences loginPrefs;
        public ViewHolder(View itemView) {
            super(itemView);
             loginPrefs = context.getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
//            mobileNo = loginPrefs.getString("mobile", "");
            cust_name = itemView.findViewById(R.id.cust_name);
            amount_check = itemView.findViewById(R.id.amount_check);
            date_check = itemView.findViewById(R.id.dat_check);
            image_check = itemView.findViewById(R.id.image_check);
            linearCheckInfo = itemView.findViewById(R.id.linearCheckInfo);
            mainLinearAdapter = itemView.findViewById(R.id.mainLinearAdapter);
            lineardetail= itemView.findViewById(R.id.lineardetail);
            rowStatus= itemView.findViewById(R.id.rowStatus);
            divider = itemView.findViewById(R.id.divider);
            checkStateText= itemView.findViewById(R.id.checkState);


            acceptImg=itemView.findViewById(R.id.acceptimage);
            rejectImg=itemView.findViewById(R.id.rejectimage);
            reciveNew=itemView.findViewById(R.id.pendingimage);
            checkimage_state=itemView.findViewById(R.id.checkimage_state);

//


        }

        public void showImageOfCheck(Bitmap bitmap) {
            final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.show_image);
            photoDetail= (PhotoView) dialog.findViewById(R.id.image_check);
//          final ImageView imageView = (ImageView) dialog.findViewById(R.id.image_check);
            photoDetail.setImageBitmap(bitmap);
            dialog.show();
        }

        public void showDetails() {
            Log.e("checkState", "" + checkState);

            final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.request_detail);
            dialog.show();
            LinearLayout linearresone,linear_buttons;
            LinearLayout linearLayout = dialog.findViewById(R.id.mainLinearDetail);
            linearresone = dialog.findViewById(R.id.linearresone);
            linear_buttons  = dialog.findViewById(R.id.linearButtons);
            if (language.equals("ar")) {
                linearLayout.setLayoutDirection(LAYOUT_DIRECTION_RTL);
            } else {
                if (languagelocalApp.equals("en")) {
                    linearLayout.setLayoutDirection(LAYOUT_DIRECTION_LTR);
                }

            }
            TextView textAmouWord, checkStatuseReson,
                    textToOrder, texDate,  binificary,  textCompanyname, note,textCo;
            ImageView mImageView;
            PhotoViewAttacher mAttacher;

            TableRow rowNote,rowcompany;
//            texDate = dialog.findViewById(R.id.texDate);
//            texDate.setText(checkInfoNotification.get(row_index).getChequeData());

//
            rowcompany=dialog.findViewById(R.id.rowcompany);
            checkStatuseReson=dialog.findViewById(R.id.checkStatuseReson);
            textCompanyname=dialog.findViewById(R.id.textComp);
            if(!requestList.get(row_index).getCOMPNAME().equals(""))
            {
                textCompanyname.setText(requestList.get(row_index).getCOMPNAME());
            }
            else {
                rowcompany.setVisibility(View.GONE);

            }
            if(requestList.get(row_index).getWitch().equals("1"))
            {
                linear_buttons.setVisibility(View.GONE);
                linearresone.setVisibility(View.VISIBLE);
//                checkStateText.setText(requestList.get(row_index).getREASON());
                checkStatuseReson.setText(requestList.get(row_index).getREASON());
            }


            binificary= dialog.findViewById(R.id.binificary);
            binificary.setText(requestList.get(row_index).getTOUSER_name());

//
            rowNote=dialog.findViewById(R.id.rowNote);
//
//
            textAmouWord = dialog.findViewById(R.id.textAmountNo);
            textAmouWord.setText(requestList.get(row_index).getAMOUNT());
//
            Log.e("Dinar", "" + requestList.get(row_index).getAMOUNT());
            note = dialog.findViewById(R.id.textnote);
            if(!requestList.get(row_index).getNOTE().equals(""))
            {
                note.setVisibility(View.VISIBLE);
                rowNote.setVisibility(View.VISIBLE);
                note.setText(requestList.get(row_index).getNOTE());
            }
            else {
                note.setVisibility(View.INVISIBLE);
                rowNote.setVisibility(View.GONE);
            }
//
//
//
            textToOrder = dialog.findViewById(R.id.textToOrder);
            textToOrder.setText(requestList.get(row_index).getFROMUSER_name());

            final Button reject = (Button) dialog.findViewById(R.id.RejectButton);
            final Button accept = (Button) dialog.findViewById(R.id.AcceptButton);
            if(!requestList.get(row_index).getWitch().equals("0"))
            {
                accept.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
            }
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Intent i=new Intent(context,EditerCheackActivity.class);
                    context.startActivity(i);


                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkState = "1";
                    dialog.dismiss();
                    openResonDialog();


                }
            });
//            imageView.setImageBitmap();


        }
    }
    EditText resonText;
    String reson="";
    private void openResonDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reson_dialog);
        dialog.setCancelable(false);

         resonText=dialog.findViewById(R.id.edit_reson);
        Button close = dialog.findViewById(R.id.canceltButton);
        Button send = dialog.findViewById(R.id.AcceptButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 reson=resonText.getText().toString();
                 if(!TextUtils.isEmpty(reson))
                 {
                     Log.e("reson",""+reson);
                     requestList.get(row_index).setREASON(reson);
                     updateCheckState();
                     dialog.dismiss();
                 }
                 else {resonText.setError(context.getResources().getString(R.string.required));

                 }

            }
        });

        dialog.show();


    }



    private void updateCheckState() {
        new Requestadapter.JSONTask().execute();
       // http://10.0.0.16:8081/UpdateRequestStatus?ROWID=AAArqWAAuAAAAFNAAA&STATUS=1&REASON=""
    }


    class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
//                infoUser=databaseHandler.getActiveUserInfo();
//                phoneNo=infoUser.getUsername();

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                SharedPreferences loginPrefs1 = context.getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
                String serverLink = loginPrefs1.getString("link", "");
                request.setURI(new URI(serverLink+"UpdateRequestStatus?"));
//                http://10.0.0.16:8081/UpdateRequestStatus?ROWID=AAArqWAAuAAAAFNAAA&STATUS=1&REASON=""

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("ROWID", requestList.get(row_index).getROWID()));
                nameValuePairs.add(new BasicNameValuePair("STATUS", "1"));
                nameValuePairs.add(new BasicNameValuePair("REASON", requestList.get(row_index).getREASON()));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


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
                    Intent i=new Intent(context,RequestCheque.class);
                    context.startActivity(i);
                    Log.e("AdaptRequestExecute", "OK");

                    Log.e("tagAdapter", "****Success" + s.toString());
                } else {
                    Log.e("tagAdapter", "****Failed to Savedata");
                }
            } else {

                Log.e("tagAdapter", "****Failed  Please check internet connection");
            }
        }
    }


/*
    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(R.string.Confirm)
                            .setContentText(context.getResources().getString(R.string.message_forAccept))
                            .setConfirmText(context.getResources().getString(R.string.ok))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    checkState = "1";
//                                    updateCheckState();
                                    dialog.dismiss();
                                    sDialog.dismissWithAnimation();
                                }
                            }).setCancelText(context.getResources().getString(R.string.dialog_cancel)).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            dialog.dismiss();
                            sweetAlertDialog.dismissWithAnimation();

                        }
                    }).show();
                    dialog.dismiss();
                    */


}