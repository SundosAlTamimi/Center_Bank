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
import java.util.ArrayList;
import java.util.List;

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
    public static String languagelocalApp = "";
    public static String acc="",bankN="",branch="",cheNo="";


    public Requestadapter(Context context, List<requestModel> notifications) {
        this.context = context;
        this.requestList = notifications;


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

        viewHolder.date_check.setText(requestList.get(i).getINDATE());
        viewHolder.amount_check.setText(requestList.get(i).getAMOUNT()+"\tJD");
        viewHolder.cust_name.setText(requestList.get(i).getFROMUSER_name());
//        viewHolder.image_check.setImageBitmap(requestList.get(i).getCheck_photo());
        Log.e("getStatus",""+requestList.get(i).getAMOUNT());
//        if(checkInfoNotification.get(i).getStatus().equals("0"))
//        {
//
//            if(checkInfoNotification.get(i).getTransType().equals("1"))
//            {
//                viewHolder.checkStateText.setText(R.string.CheckAccpted);
////                viewHolder.divider.setBackgroundColor(R.color.RealGreen);
//                viewHolder.rejectImg.setVisibility(View.INVISIBLE);
//                viewHolder.reciveNew.setVisibility(View.INVISIBLE);
//                viewHolder.acceptImg.setVisibility(View.VISIBLE);
//
//            }
//            else {
////                viewHolder.divider.setBackgroundColor(R.color.RealRed);
//                viewHolder.rejectImg.setVisibility(View.VISIBLE);
//                viewHolder.checkStateText.setText(R.string.checkReject);
//                viewHolder.reciveNew.setVisibility(View.INVISIBLE);
//                viewHolder.acceptImg.setVisibility(View.INVISIBLE);
//
//            }
//
//        }
//        else {
//
////            viewHolder.divider.setBackgroundColor(R.color.white);
//            viewHolder.reciveNew.setVisibility(View.VISIBLE);
//            viewHolder.checkStateText.setText(R.string.NewCheck);
//            viewHolder.rejectImg.setVisibility(View.INVISIBLE);
//            viewHolder.acceptImg.setVisibility(View.INVISIBLE);
//
//        }

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
                Log.e("row_index", "" + row_index);
                viewHolder.showDetails();
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cust_name, amount_check, date_check,checkStateText;
        ImageView image_check;
        LinearLayout linearCheckInfo, mainLinearAdapter,divider,lineardetail,rowStatus;
        CircleImageView acceptImg,rejectImg,reciveNew;

        public ViewHolder(View itemView) {
            super(itemView);
            cust_name = itemView.findViewById(R.id.cust_name);
            amount_check = itemView.findViewById(R.id.amount_check);
            date_check = itemView.findViewById(R.id.date_check);
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
            LinearLayout linearLayout = dialog.findViewById(R.id.mainLinearDetail);
            if (language.equals("ar")) {
                linearLayout.setLayoutDirection(LAYOUT_DIRECTION_RTL);
            } else {
                if (languagelocalApp.equals("en")) {
                    linearLayout.setLayoutDirection(LAYOUT_DIRECTION_LTR);
                }

            }
            TextView textAmouWord, textAmountNo,
                    textToOrder, texDate,  binificary,  textCompanyname, note,textCo;
            ImageView mImageView;
            PhotoViewAttacher mAttacher;

            TableRow rowNote,rowcompany;
//            texDate = dialog.findViewById(R.id.texDate);
//            texDate.setText(checkInfoNotification.get(row_index).getChequeData());

//
            rowcompany=dialog.findViewById(R.id.rowcompany);
            textCompanyname=dialog.findViewById(R.id.textComp);
            if(!requestList.get(row_index).getCOMPNAME().equals(""))
            {
                textCompanyname.setText(requestList.get(row_index).getCOMPNAME());
            }
            else {
                rowcompany.setVisibility(View.GONE);

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

    private String getFullName(String toCustomerName) {
        String first,second,third,fourth,full;
        int indexSecond=toCustomerName.indexOf("sName");
        first=toCustomerName.substring(0,indexSecond);
        int indexTherd=toCustomerName.indexOf("tName");
        second=toCustomerName.substring(indexSecond+5,indexTherd);
        int indexFourth=toCustomerName.indexOf("fName");
        third=toCustomerName.substring(indexTherd+5,indexFourth);
        fourth=toCustomerName.substring(indexFourth+5);
        Log.e("full",""+first+"\t"+second+"\t"+third+"\t"+fourth);
        return full=first+"\t"+second+"\t"+third+"\t"+fourth;

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