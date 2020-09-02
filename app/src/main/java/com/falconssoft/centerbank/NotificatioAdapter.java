package com.falconssoft.centerbank;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.falconssoft.centerbank.Models.ChequeInfo;
import com.falconssoft.centerbank.Models.LoginINFO;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

import static android.view.View.LAYOUT_DIRECTION_LTR;
import static android.view.View.LAYOUT_DIRECTION_RTL;
import static com.falconssoft.centerbank.AlertScreen.checkInfoNotification;
import static com.falconssoft.centerbank.AlertScreen.editor;
import static com.falconssoft.centerbank.AlertScreen.language;
import static com.falconssoft.centerbank.AlertScreen.serverLink;
import static com.falconssoft.centerbank.AlertScreen.sharedPreferences;
import static com.falconssoft.centerbank.AlertScreen.textCheckstateChanger;
import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;
import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;
import static com.falconssoft.centerbank.Requestadapter.mobileNo;

public class NotificatioAdapter extends RecyclerView.Adapter<NotificatioAdapter.ViewHolder> {
    Context context;
    List<notification> notificationList;
    public PhotoView photoView, photoDetail;
    CircleImageView circleImageView, circleGeroImg;
    Bitmap serverPicBitmap, geroBitmap;
    int row_index = -1;
    String checkState = "0";
    public static String languagelocalApp = "";
    public static String acc = "", bankN = "", branch = "", cheNo = "", mobile_No = "", isJoin = "";
    EditText resonText;
    String reson_reject = "";
    private ProgressDialog progressDialog;
    AlertScreen contextAlert;
    public static String amountArabic = "", stateJoin = "", phonCurentReject = "", resonReject = "";


    public NotificatioAdapter(Context context, List<notification> notifications) {
        this.context = context;
        this.notificationList = notifications;
//        progressDialog = new ProgressDialog(context);
        this.contextAlert = (AlertScreen) context;
        Log.e("NotificatioAdapter", "" + notificationList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        new LocaleAppUtils().changeLayot(context);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_notification, viewGroup, false);

        return new NotificatioAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        stateJoin = "";
        phonCurentReject = "";
        resonReject = "";
        language = new LocaleAppUtils().getLocale();
        if (language.equals("ar")) {

            viewHolder.date_check.setText(viewHolder.convertToArabic(notificationList.get(i).getDate()));
        } else {
            viewHolder.date_check.setText(notificationList.get(i).getDate());

        }



        if (!checkInfoNotification.get(i).getMoneyInFils().equals("0")) {
            if (language.equals("ar")) {
                amountArabic = viewHolder.convertToArabic("\tد.أ\t" + notificationList.get(i).getAmount_check()) + "." + viewHolder.convertToArabic(checkInfoNotification.get(i).getMoneyInFils());
                Log.e("amountArabic", "" + amountArabic);
                viewHolder.amount_Filis.setText(amountArabic);

            } else {
                viewHolder.amount_check.setText(notificationList.get(i).getAmount_check() + ".");
                viewHolder.amount_Filis.setText(checkInfoNotification.get(i).getMoneyInFils() + "\tJD");
            }

        } else {
            if (language.equals("ar")) {
                viewHolder.amount_check.setText("\tد.أ\t" + viewHolder.convertToArabic(notificationList.get(i).getAmount_check()));
            } else {
                viewHolder.amount_check.setText(notificationList.get(i).getAmount_check() + "\tJD");
            }


        }
        viewHolder.source_check.setText(notificationList.get(i).getSource());


        viewHolder.image_check.setImageBitmap(notificationList.get(i).getCheck_photo());
        Log.e("getWICHEUSER", "" + checkInfoNotification.get(i).getWICHEUSER());
        if (checkInfoNotification.get(i).getWICHEUSER().equals("1")) {
            phonCurentReject = checkInfoNotification.get(i).getJOIN_FirstMOB();
            if (checkInfoNotification.get(i).getJOIN_F_STATUS().equals("1")) {
                stateJoin = "1";
                resonReject = "";
            } else {
                stateJoin = "2";
                resonReject = checkInfoNotification.get(i).getJOIN_F_REASON();
            }


        } else if (checkInfoNotification.get(i).getWICHEUSER().equals("2")) {
            phonCurentReject = checkInfoNotification.get(i).getJOIN_SecondSMOB();
            if (checkInfoNotification.get(i).getJOIN_S_STATUS().equals("1")) {
                stateJoin = "1";
                resonReject = "";
            } else {
                stateJoin = "2";
                resonReject = checkInfoNotification.get(i).getJOIN_S_REASON();
            }
        } else if (checkInfoNotification.get(i).getWICHEUSER().equals("3")) {
            phonCurentReject = checkInfoNotification.get(i).getJOIN_TheredMOB();
            if (checkInfoNotification.get(i).getJOIN_T_STATUS().equals("1")) {
                stateJoin = "1";
                resonReject = "";
            } else {
                stateJoin = "2";
                resonReject = checkInfoNotification.get(i).getJOIN_T_REASON();
            }
        }


//        if (checkInfoNotification.get(i).getTransType().equals("0") && checkInfoNotification.get(i).getIsJoin().equals("1") && !checkInfoNotification.get(i).getToCustomerMobel().equals(mobile_No)) {
//            viewHolder.mainLinearAdapter.setVisibility(View.GONE);
//        }
//

        Log.e("stateJoin", "" + stateJoin + resonReject);
        if (checkInfoNotification.get(i).getTransSendOrGero().equals("0"))// normal cheque Send
        {
            viewHolder.geroLinear_pending.setVisibility(View.GONE);
            viewHolder.geroLinear_accep.setVisibility(View.GONE);
            viewHolder.geroLinear_reject.setVisibility(View.GONE);
            //****************************************************
            if(checkInfoNotification.get(i).getTransType().equals("0")&&checkInfoNotification.get(i).getToCustomerMobel().equals(mobile_No))// reciver
            {
                viewHolder.reciveNew.setVisibility(View.VISIBLE);
            }

            if (checkInfoNotification.get(i).getTransType().equals("1") || ((checkInfoNotification.get(i).getTransType().equals("100")) && stateJoin.equals("1")) || stateJoin.equals("1"))// accepted
            {
                if(stateJoin.equals("1"))
                {
                  viewHolder.joined_Acceptimage.setVisibility(View.VISIBLE);
                }
                else {
//                    viewHolder.rejectImg.setVisibility(View.GONE);
//                    viewHolder.reciveNew.setVisibility(View.GONE);
//                    viewHolder.joined_Requestimage.setVisibility(View.GONE);
                    viewHolder.acceptImg.setVisibility(View.VISIBLE);
                }



            } else {// rejected
                if (checkInfoNotification.get(i).getTransType().equals("2") || stateJoin.equals("2")) {
                    if(stateJoin.equals("2"))
                    {
                        viewHolder.joined_Rejectimage.setVisibility(View.VISIBLE);
                    }
                    else {
                        viewHolder.rejectImg.setVisibility(View.VISIBLE);
//                        viewHolder.reciveNew.setVisibility(View.GONE);
//                        viewHolder.acceptImg.setVisibility(View.GONE);
//                        viewHolder.joined_Requestimage.setVisibility(View.GONE);
//                        viewHolder.geroLinear_pending.setVisibility(View.GONE);

                    }

                }
                if (checkInfoNotification.get(i).getTransType().equals("100") && checkInfoNotification.get(i).getWICHEUSER().equals("-1"))// request to accept join cheque
                {
                    viewHolder.joined_Requestimage.setVisibility(View.VISIBLE);
                    viewHolder.rejectImg.setVisibility(View.GONE);
                    viewHolder.reciveNew.setVisibility(View.GONE);
                    viewHolder.acceptImg.setVisibility(View.GONE);
                    viewHolder.geroLinear_pending.setVisibility(View.GONE);
                }
                if (checkInfoNotification.get(i).getTransType().equals("4") )// request to accept join cheque
                {

                    viewHolder.retrive_image.setVisibility(View.VISIBLE);
                }


            }


        } else {// gero chequ


            if(checkInfoNotification.get(i).getTransType().equals("0"))// reciver
            {
                viewHolder.geroLinear_pending.setVisibility(View.VISIBLE);
            }

            if (checkInfoNotification.get(i).getTransType().equals("1") || checkInfoNotification.get(i).getTransType().equals("3"))// accepted
            {

                viewHolder.geroLinear_pending.setVisibility(View.GONE);
                viewHolder.geroLinear_accep.setVisibility(View.VISIBLE);
                viewHolder.geroLinear_reject.setVisibility(View.GONE);

            } else {// rejected
                if (checkInfoNotification.get(i).getTransType().equals("2") || checkInfoNotification.get(i).getTransType().equals("200") || stateJoin.equals("2")) {
                    viewHolder.geroLinear_pending.setVisibility(View.GONE);
                    viewHolder.geroLinear_accep.setVisibility(View.GONE);
                    viewHolder.geroLinear_reject.setVisibility(View.VISIBLE);

                }

            }
            if (checkInfoNotification.get(i).getTransType().equals("4") )// request to accept join cheque
            {

                viewHolder.retrive_image.setVisibility(View.VISIBLE);
            }


            Log.e("getTransSendOrGero", "Gero");

        }

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

                Log.e("getWICHEUSER", "" + checkInfoNotification.get(i).getWICHEUSER());
                if (checkInfoNotification.get(i).getWICHEUSER().equals("1")) {
                    phonCurentReject = checkInfoNotification.get(i).getJOIN_FirstMOB();
                    if (checkInfoNotification.get(i).getJOIN_F_STATUS().equals("1")) {
                        stateJoin = "1";
                        resonReject = "";
                    } else {
                        stateJoin = "2";
                        resonReject = checkInfoNotification.get(i).getJOIN_F_REASON();
                    }


                } else if (checkInfoNotification.get(i).getWICHEUSER().equals("2")) {
                    phonCurentReject = checkInfoNotification.get(i).getJOIN_SecondSMOB();
                    if (checkInfoNotification.get(i).getJOIN_S_STATUS().equals("1")) {
                        stateJoin = "1";
                        resonReject = "";
                    } else {
                        stateJoin = "2";
                        resonReject = checkInfoNotification.get(i).getJOIN_S_REASON();
                    }
                } else if (checkInfoNotification.get(i).getWICHEUSER().equals("3")) {
                    phonCurentReject = checkInfoNotification.get(i).getJOIN_TheredMOB();
                    if (checkInfoNotification.get(i).getJOIN_T_STATUS().equals("1")) {
                        stateJoin = "1";
                        resonReject = "";
                    } else {
                        stateJoin = "2";
                        resonReject = checkInfoNotification.get(i).getJOIN_T_REASON();
                    }
                }

//                isJoin=checkInfoNotification.get(i).getIsJoined();
//                progressDialog.show();
//                progressDialog.setMessage("Please Waiting...");
                viewHolder.showDetails(stateJoin, resonReject,phonCurentReject);
            }
        });
        viewHolder.linearCheckInfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if((checkInfoNotification.get(i).getTransType().equals("0")&&checkInfoNotification.get(i).getToCustomerMobel().equals(mobile_No))||(checkInfoNotification.get(i).getTransType().equals("100")&& checkInfoNotification.get(i).getWICHEUSER().equals("-1")))
                {
                    row_index = i;
                    progressDialog = new ProgressDialog(context);
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(R.string.Confirm)
                            .setContentText(context.getResources().getString(R.string.message_forDelete))
                            .setConfirmText(context.getResources().getString(R.string.ok))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    progressDialog.setMessage(context.getResources().getString(R.string.process));
                                    progressDialog.show();
                                    updateNotificationState();

                                    sDialog.dismissWithAnimation();
                                }
                            }).setCancelText(context.getResources().getString(R.string.dialog_cancel)).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.dismissWithAnimation();

                        }
                    }).show();
                }

                return  true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView source_check, amount_check, date_check, checkStateText, amount_Filis;
        ImageView image_check;
        LinearLayout linearCheckInfo, mainLinearAdapter, divider, lineardetail, rowStatus,joined_Acceptimage,joined_Rejectimage,retrive_image;
        LinearLayout acceptImg, rejectImg, reciveNew, geroLinear_pending, geroLinear_accep, geroLinear_reject, joined_Requestimage;
        SharedPreferences loginPrefs;
        LoginINFO infoUser;

        public ViewHolder(View itemView) {
            super(itemView);
            loginPrefs = context.getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
            DatabaseHandler databaseHandler = new DatabaseHandler(context);
            infoUser = databaseHandler.getActiveUserInfo();
            mobile_No = infoUser.getUsername();
            geroLinear_pending = itemView.findViewById(R.id.geroLinear_pending);
            geroLinear_accep = itemView.findViewById(R.id.geroLinear_accepted);
            geroLinear_reject = itemView.findViewById(R.id.geroLinear_rejected);
            source_check = itemView.findViewById(R.id.source_check);
            amount_check = itemView.findViewById(R.id.amount_check);
            amount_Filis = itemView.findViewById(R.id.amount_Filis);
            date_check = itemView.findViewById(R.id.date_check);
            image_check = itemView.findViewById(R.id.image_check);
            linearCheckInfo = itemView.findViewById(R.id.linearCheckInfo);
            mainLinearAdapter = itemView.findViewById(R.id.mainLinearAdapter);
            lineardetail = itemView.findViewById(R.id.lineardetail);
            rowStatus = itemView.findViewById(R.id.rowStatus);
            divider = itemView.findViewById(R.id.divider);
            checkStateText = itemView.findViewById(R.id.checkState);


            acceptImg = itemView.findViewById(R.id.acceptimage);
            rejectImg = itemView.findViewById(R.id.rejectimage);
            reciveNew = itemView.findViewById(R.id.pendingimage);
            joined_Requestimage = itemView.findViewById(R.id.joined_Requestimage);
            joined_Acceptimage=itemView.findViewById(R.id.joined_Acceptimage);
            joined_Rejectimage=itemView.findViewById(R.id.joined_Rejectimage);
            retrive_image=itemView.findViewById(R.id.retrive_image);

//


        }

        public void showImageOfCheck(Bitmap bitmap) {
            final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.show_image);
            photoDetail = (PhotoView) dialog.findViewById(R.id.image_check);
//          final ImageView imageView = (ImageView) dialog.findViewById(R.id.image_check);
            photoDetail.setImageBitmap(bitmap);
            dialog.show();
        }

        public String convertToArabic(String value) {
            String newValue = (((((((((((value + "").replaceAll("1", "١")).replaceAll("2", "٢")).replaceAll("3", "٣")).replaceAll("4", "٤")).replaceAll("5", "٥")).replaceAll("6", "٦")).replaceAll("7", "٧")).replaceAll("8", "٨")).replaceAll("9", "٩")).replaceAll("0", "٠"));
            Log.e("convertToArabic", value + "      " + newValue);
            return newValue;
        }

        public void showDetails(String state_Join, String reson_Reject,String phonCurent) {
            progressDialog = new ProgressDialog(context);
            final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.show_check_detail);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setAttributes(lp);
            dialog.show();
            LinearLayout linearLayout = dialog.findViewById(R.id.mainLinearDetail);
            if (language.equals("ar")) {
                linearLayout.setLayoutDirection(LAYOUT_DIRECTION_RTL);
            } else {
                if (languagelocalApp.equals("en")) {
                    linearLayout.setLayoutDirection(LAYOUT_DIRECTION_LTR);
                }

            }
            TextView textAmouWord, textAmountNo, textViewMain, textResonReject,textPhoneAccept,
                    rejectPhone, textToOrder, texChequNo, amountPhilis, textPhoneNo, texDate, binificary, textCompanyname, note, textFirstPinificry, textCo, reSend, textCompany;
            ImageView mImageView;
            PhotoViewAttacher mAttacher;
            LinearLayout resonLayout, linearButn, rowNote, rowCompany,rowJointAccptedPhone;


            final Button reject = (Button) dialog.findViewById(R.id.RejectButton);
            final Button accept = (Button) dialog.findViewById(R.id.AcceptButton);
            reSend = dialog.findViewById(R.id.reSend);
            linearButn = dialog.findViewById(R.id.linearButn);

            resonLayout = dialog.findViewById(R.id.resonLayout);
            textPhoneAccept = dialog.findViewById(R.id.textPhoneAccept);
            rowJointAccptedPhone = dialog.findViewById(R.id.rowJointAccptedPhone);


            TableRow rowFirst;
            texDate = dialog.findViewById(R.id.texDate);
            textViewMain = dialog.findViewById(R.id.textViewMain);
            textFirstPinificry = dialog.findViewById(R.id.textFirstPinificry);
            textResonReject = dialog.findViewById(R.id.textResonReject);
            rowNote = dialog.findViewById(R.id.rowNote);
            rowFirst = dialog.findViewById(R.id.rowFirst);
            textCo = dialog.findViewById(R.id.textCo);
            textToOrder = dialog.findViewById(R.id.textToOrder);
            rowCompany = dialog.findViewById(R.id.rowcompany);
            textCompany = dialog.findViewById(R.id.Textcompany);
            amountPhilis = dialog.findViewById(R.id.amountPhilis);
            rejectPhone = dialog.findViewById(R.id.rejectPhone);

            textPhoneNo = dialog.findViewById(R.id.textPhoneNo);

            circleImageView = (CircleImageView) dialog.findViewById(R.id.profile_image2);
            circleGeroImg = (CircleImageView) dialog.findViewById(R.id.profile_gero);
            textAmouWord = dialog.findViewById(R.id.textAmouWord);

            textAmountNo = dialog.findViewById(R.id.textAmountNo);

            note = dialog.findViewById(R.id.textnote);
            reSend.setVisibility(View.GONE);
            binificary = dialog.findViewById(R.id.binificary);
            String fullName = checkInfoNotification.get(row_index).getToCustomerName();
            binificary.setText(fullName);
            texChequNo = dialog.findViewById(R.id.texChequNo);


            if (language.equals("ar")) {
                texChequNo.setText(convertToArabic(checkInfoNotification.get(row_index).getChequeNo()));
                texDate.setText(convertToArabic(checkInfoNotification.get(row_index).getChequeData()));
                textAmountNo.setText(convertToArabic(checkInfoNotification.get(row_index).getMoneyInDinar()));
                amountPhilis.setText(convertToArabic(checkInfoNotification.get(row_index).getMoneyInFils()));
            } else {
                texChequNo.setText(checkInfoNotification.get(row_index).getChequeNo());
                texDate.setText(checkInfoNotification.get(row_index).getChequeData());
                textAmountNo.setText(checkInfoNotification.get(row_index).getMoneyInDinar());
                amountPhilis.setText(checkInfoNotification.get(row_index).getMoneyInFils());
            }


            textResonReject.setMovementMethod(ScrollingMovementMethod.getInstance());

            if (checkInfoNotification.get(row_index).getISCO().equals("1")) {

                textCo.setVisibility(View.VISIBLE);


            } else {
                textCo.setVisibility(View.GONE);
            }

            if (checkInfoNotification.get(row_index).getISBF().equals("1")) {
                textFirstPinificry.setVisibility(View.VISIBLE);

            } else {
                textFirstPinificry.setVisibility(View.GONE);
            }
            if (checkInfoNotification.get(row_index).getISBF().equals("0") && checkInfoNotification.get(row_index).getISBF().equals("0")) {
                rowFirst.setVisibility(View.GONE);
            }


            if (checkInfoNotification.get(row_index).getTransSendOrGero().equals("0"))// normal cheque
            {
//                if(checkInfoNotification.get(row_index).getStatus().equals("0")) {
                if (checkInfoNotification.get(row_index).getTransType().equals("1") || (state_Join.equals("1")&&checkInfoNotification.get(row_index).getIsJoin().equals("1"))) {
                    textViewMain.setText(context.getResources().getString(R.string.CheckAccpted));
                    resonLayout.setVisibility(View.GONE);
                    linearButn.setVisibility(View.GONE);
                    if((state_Join.equals("1")&&checkInfoNotification.get(row_index).getIsJoin().equals("1")))
                    {
                        rowJointAccptedPhone.setVisibility(View.VISIBLE);
                        textPhoneAccept.setText(phonCurent);

                    }


                }
                if (checkInfoNotification.get(row_index).getTransType().equals("2") || (checkInfoNotification.get(row_index).getTransType().equals("200") && !state_Join.equals("1")) ||( state_Join.equals("2")&&checkInfoNotification.get(row_index).getIsJoin().equals("1")&&!checkInfoNotification.get(row_index).getWICHEUSER().equals("-1"))) {
                    textViewMain.setText(context.getResources().getString(R.string.checkReject));
                    resonLayout.setVisibility(View.VISIBLE);
                    linearButn.setVisibility(View.GONE);
                    if (state_Join.equals("2")&&!checkInfoNotification.get(row_index).getWICHEUSER().equals("-1")) {
                        textResonReject.setText(reson_Reject);
                    } else {
                        textResonReject.setText(checkInfoNotification.get(row_index).getResonOfreject());
                    }


                    rejectPhone.setText(phonCurent);


                    if (!checkInfoNotification.get(row_index).getTransSendOrGero().equals("1")) {//0
                        reSend.setVisibility(View.VISIBLE);
                        linearButn.setVisibility(View.GONE);
                    }


                }
                if (checkInfoNotification.get(row_index).getTransType().equals("100") && checkInfoNotification.get(row_index).getWICHEUSER().equals("-1")) {
                    linearButn.setVisibility(View.VISIBLE);
                    textViewMain.setText(context.getResources().getString(R.string.requestToJoinCheque));

                }
                if(checkInfoNotification.get(row_index).getTransType().equals("0") &&checkInfoNotification.get(row_index).getWICHEUSER().equals("-1"))
                {
                    linearButn.setVisibility(View.VISIBLE);
//                    textViewMain.setText(context.getResources().getString(R.string));
                }


            } else {// gero cheque

//                if(checkInfoNotification.get(row_index).getStatus().equals("0")) {
                if (checkInfoNotification.get(row_index).getTransType().equals("1") || checkInfoNotification.get(row_index).getTransType().equals("3")) {
                    resonLayout.setVisibility(View.GONE);
                    linearButn.setVisibility(View.GONE);
                    textViewMain.setText(context.getResources().getString(R.string.gerocheque));
                    textViewMain.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_swap_calls_green_24dp), null
                            , null, null);

                }
                if (checkInfoNotification.get(row_index).getTransType().equals("2") || checkInfoNotification.get(row_index).getTransType().equals("200")) {
                    resonLayout.setVisibility(View.VISIBLE);
                    linearButn.setVisibility(View.GONE);
                    textResonReject.setText(checkInfoNotification.get(row_index).getResonOfreject());
                    textViewMain.setCompoundDrawablesWithIntrinsicBounds(null, null
                            , ContextCompat.getDrawable(context, R.drawable.ic_swap_calls_red_24dp), null);
                    textViewMain.setText(context.getResources().getString(R.string.rejectedGereo));
                        if(!checkInfoNotification.get(row_index).getTransSendOrGero().equals("1")){
                            reSend.setVisibility(View.VISIBLE);
                        }


                }
//                }
//                else {
//                    linearButn.setVisibility(View.VISIBLE);
//                    resonLayout.setVisibility(View.GONE);
//                    textViewMain.setText(context.getResources().getString(R.string.newGeroChecue));
//                    textViewMain.setCompoundDrawablesWithIntrinsicBounds(null, null
//                            , ContextCompat.getDrawable(context, R.drawable.ic_swap_calls_yelow_24dp), null);
//
//                }


            }
if(checkInfoNotification.get(row_index).getTransType().equals("4")&&checkInfoNotification.get(row_index).getTransSendOrGero().equals("0"))
{
    reSend.setVisibility(View.VISIBLE);
    linearButn.setVisibility(View.GONE);
}

            textAmouWord.setText(checkInfoNotification.get(row_index).getMoneyInWord());
            if (!checkInfoNotification.get(row_index).getNoteCheck().equals("")) {
                note.setVisibility(View.VISIBLE);
                rowNote.setVisibility(View.VISIBLE);
                note.setText(checkInfoNotification.get(row_index).getNoteCheck());
            } else {
                note.setVisibility(View.INVISIBLE);
                rowNote.setVisibility(View.GONE);
            }


            if (!checkInfoNotification.get(row_index).getCompanyName().equals("")) {
                textCompany.setVisibility(View.VISIBLE);
                rowCompany.setVisibility(View.VISIBLE);
                textCompany.setText(checkInfoNotification.get(row_index).getCompanyName());
            } else {
                textCompany.setVisibility(View.INVISIBLE);
                rowCompany.setVisibility(View.GONE);
            }

            textPhoneNo.setText(checkInfoNotification.get(row_index).getToCustomerMobel());
            textToOrder.setText(checkInfoNotification.get(row_index).getCustName());
            if (checkInfoNotification.get(row_index).getTransSendOrGero().equals("0")) {// send image
                circleGeroImg.setVisibility(View.GONE);
                getPicture("0", checkInfoNotification.get(row_index).getAccCode(), checkInfoNotification.get(row_index).getBankNo(), checkInfoNotification.get(row_index).getBranchNo(), checkInfoNotification.get(row_index).getChequeNo());
                Log.e("getTransSendOrGero", "Send");

            } else {// gero image
                getPicture("0", checkInfoNotification.get(row_index).getAccCode(), checkInfoNotification.get(row_index).getBankNo(), checkInfoNotification.get(row_index).getBranchNo(), checkInfoNotification.get(row_index).getChequeNo());

                getPicture("1", checkInfoNotification.get(row_index).getAccCode(), checkInfoNotification.get(row_index).getBankNo(), checkInfoNotification.get(row_index).getBranchNo(), checkInfoNotification.get(row_index).getChequeNo());

            }


            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showImageOfCheck(serverPicBitmap);

                }
            });
            circleGeroImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showImageOfCheck(geroBitmap);

                }
            });


            //**********************************************************************************

            reSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    if((checkInfoNotification.get(row_index).getTransType().equals("200")||checkInfoNotification.get(row_index).getTransType().equals("2"))&&checkInfoNotification.get(row_index).getStatus().equals("0"))
                    if ((checkInfoNotification.get(row_index).getTransType().equals("200") || checkInfoNotification.get(row_index).getTransType().equals("2"))) {

//                    Intent EditeIntent=new Intent(context,EditerCheackActivity.class);
                        contextAlert.startEditerForReSendAlert(checkInfoNotification.get(row_index));
                        Toast.makeText(contextAlert, "Resend " + checkInfoNotification.get(row_index).getChequeNo(), Toast.LENGTH_SHORT).show();


                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {

                                dialog.dismiss();

                            }
                        });

                    }else {
                        new SweetAlertDialog(contextAlert, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("ReSend Error!")
                                .setContentText("This Cheque Can't Resend States is not Reject")
                                .show();
                    }

                }
            });


//            if(checkInfoNotification.get(row_index).getStatus().equals("0"))
//            {
//                linearButn.setVisibility(View.GONE);
//                accept.setVisibility(View.GONE);
//                reject.setVisibility(View.GONE);
//            }

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);
                    view.startAnimation(buttonClick);
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(R.string.Confirm)
                            .setContentText(context.getResources().getString(R.string.message_forAccept))
                            .setConfirmText(context.getResources().getString(R.string.ok))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    checkState = "1";
                                    if (!checkInfoNotification.get(row_index).getTransType().equals("100")) {
                                        isJoin = "0";
                                    } else {
                                        isJoin = checkInfoNotification.get(row_index).getIsJoin();
                                    }
                                    progressDialog.setMessage(context.getResources().getString(R.string.process));
                                    progressDialog.show();
                                    updateCheckState();

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

                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);
                    view.startAnimation(buttonClick);
                    // alertDialog + reson

                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(R.string.Confirm)
                            .setContentText(context.getResources().getString(R.string.message_reject))
                            .setConfirmText(context.getResources().getString(R.string.yes))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {

                                            dialog.dismiss();

                                        }
                                    });


                                    showDialogreson();


                                }
                            }).setCancelText(context.getResources().getString(R.string.no)).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            dialog.dismiss();
                            sweetAlertDialog.dismissWithAnimation();

                        }
                    }).show();

                }
            });
//            imageView.setImageBitmap();


        }
    }

    private void updateNotificationState() {
        new JSONUpdateNotificationTask().execute();
    }

    private void showDialogreson() {
        final Dialog dialog_reson = new Dialog(context);
        dialog_reson.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_reson.setContentView(R.layout.reson_dialog);
        dialog_reson.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog_reson.getWindow().getAttributes());
//            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//            lp.gravity = Gravity.CENTER;
        lp.windowAnimations = R.style.DialogAnimation;
        dialog_reson.getWindow().setAttributes(lp);


        resonText = dialog_reson.findViewById(R.id.edit_reson);
        Button close = dialog_reson.findViewById(R.id.canceltButton);
        Button send = dialog_reson.findViewById(R.id.AcceptButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);
                v.startAnimation(buttonClick);
                dialog_reson.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);
                v.startAnimation(buttonClick);

                reson_reject = resonText.getText().toString();
                if (!TextUtils.isEmpty(reson_reject)) {
                    Log.e("reson_reject", "" + reson_reject);
                    dialog_reson.dismiss();
//                  requestList.get(row_index).setREASON(reson);

                    progressDialog.setMessage(context.getResources().getString(R.string.PleaseWaiting));
                    progressDialog.show();
                    if (!checkInfoNotification.get(row_index).getTransType().equals("100")) {
                        isJoin = "0";
                    } else {
                        isJoin = checkInfoNotification.get(row_index).getIsJoin();
                    }
                    checkState = "2";


//
                    updateCheckState();



                } else {
                    resonText.setError(context.getResources().getString(R.string.required));

                }

            }
        });

        dialog_reson.show();


    }


    public Bitmap StringToBitMap(String image) {
        try {

            byte[] encodeByte = Base64.decode(image, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
//            String decoded = new String(encodeByte);

//            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            } catch (Exception e) {
//                R.drawable.check_bank

                Toast.makeText(context, "memorY is full can't display image ", Toast.LENGTH_SHORT).show();
            }

            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void updateCheckState() {
        new JSONTask().execute();
        //http://localhost:8082/UpdateCheckStatus?CHECKNO=390144&BANKNO=004&BRANCHNO=0099&ACCCODE=1014569990011000&IBANNO=""&ROWID=AAAp0DAAuAAAAC2AAA&STATUS=1

    }

    private void getPicture(String type, String accCode, String bankName, String branchNo, String chequeNo) {
        acc = accCode;
        bankN = bankName;
        branch = branchNo;
        cheNo = chequeNo;
        if (type.equals("0")) {
            new Image().execute();
        } else {
            new ImageGero().execute();
        }


    }

    protected class Image extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        public String typeImg = "0";

//        public Image(String typeImg) {
//            this.typeImg = typeImg;
//        }

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
                try {
                    request.setURI(new URI(serverLink + "GetCheckPic?"));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                Log.e("NameValuePair", "" + acc + bankN + branch + "" + cheNo);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("ACCCODE", acc));
                nameValuePairs.add(new BasicNameValuePair("BANKNO", bankN));
                nameValuePairs.add(new BasicNameValuePair("BRANCHNO", branch));// test
                nameValuePairs.add(new BasicNameValuePair("CHECKNO", cheNo));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
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
//                Log.e("tagAlertScreenImage", "" + JsonResponse);

                return JsonResponse;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

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
                        Log.e("serverPicBitmap", "" + serverPicBitmap);
                        if (serverPicBitmap != null) {
                            circleImageView.setImageBitmap(serverPicBitmap);
                        } else {
                            circleImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.check));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.e("tagImage", "****Failed to export data");
                }
            } else {
                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
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
                request.setURI(new URI(serverLink + "UpdateCheckStatus?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("CHECKNO", checkInfoNotification.get(row_index).getChequeNo()));
                nameValuePairs.add(new BasicNameValuePair("BANKNO", checkInfoNotification.get(row_index).getBankNo()));
                nameValuePairs.add(new BasicNameValuePair("BRANCHNO", checkInfoNotification.get(row_index).getBranchNo()));
                nameValuePairs.add(new BasicNameValuePair("ACCCODE", checkInfoNotification.get(row_index).getAccCode()));

                nameValuePairs.add(new BasicNameValuePair("IBANNO", checkInfoNotification.get(row_index).getIbanNo()));
                nameValuePairs.add(new BasicNameValuePair("ROWID", checkInfoNotification.get(row_index).getRowId()));
                nameValuePairs.add(new BasicNameValuePair("STATUS", checkState));
                nameValuePairs.add(new BasicNameValuePair("RJCTREASON", reson_reject));
                nameValuePairs.add(new BasicNameValuePair("USERNO", mobile_No));

                Log.e("isJoin", "" + isJoin);
                nameValuePairs.add(new BasicNameValuePair("ISJOIN", isJoin));


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
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Log.e("tagAlertScreen", "" + JsonResponse);

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
                    Log.e("AdapteronPostExecute", "OK");
                    updateNotificationState();
//                    progressDialog.dismiss();

//                    Log.e("tagAdapter", "****Success" + s.toString());
                } else {
                    Log.e("tagAdapter", "****Failed to Savedata");
                }
            } else {

                Log.e("tagAdapter", "****Failed  Please check internet connection");
            }
        }
    }

    class JSONUpdateNotificationTask extends AsyncTask<String, String, String> {

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
//                http://localhost:8082/UpdateNotfication?ROWID=&STATUS=1
                request.setURI(new URI(serverLink + "UpdateNotfication?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("ROWID", checkInfoNotification.get(row_index).getNOTFROWID()));
                nameValuePairs.add(new BasicNameValuePair("STATUS", "1"));

                Log.e("STATUS", "" + checkState);

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
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Log.e("tagAlertScreen", "" + JsonResponse);

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
                    Log.e("AdapteronPostExecute", "OK");


                    progressDialog.dismiss();

//                    Log.e("tagAdapter", "****Success" + s.toString());
                } else {
                    Log.e("tagAdapter", "****Failed to Savedata");
                }
            } else {

                Log.e("tagAdapter", "****Failed  Please check internet connection");
            }
        }
    }

    private void refreshScreen() {

        textCheckstateChanger.setText("2");

    }

    protected class ImageGero extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

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
                try {
                    request.setURI(new URI(serverLink + "GetGeroPic?"));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                Log.e("NameValuePair", "" + acc + bankN + branch + "" + cheNo);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("ACCCODE", acc));
                nameValuePairs.add(new BasicNameValuePair("BANKNO", bankN));
                nameValuePairs.add(new BasicNameValuePair("BRANCHNO", branch));// test
                nameValuePairs.add(new BasicNameValuePair("CHECKNO", cheNo));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
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
//                Log.e("tagAlertScreenImage", "" + JsonResponse);

                return JsonResponse;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("notificationadpter/", "saved//" + s);
            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
                    Log.e("tag", "****saved Success In Adapter");

                    JSONObject jsonObject = null;


                    try {
                        jsonObject = new JSONObject(s);

                        geroBitmap = StringToBitMap(jsonObject.getString("CHECKPIC"));
                        if (geroBitmap != null) {
                            circleGeroImg.setImageBitmap(geroBitmap);
                        } else {
                            circleImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.check));
                        }
//                        Log.e("GetGeroPic",""+geroBitmap);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.e("tagNotificatioAdapter", "****Failed to export data");
                }
            } else {
                Log.e("tagNotificatioAdapter", "****Failed to export data Please check internet connection");
            }
        }
    }


}