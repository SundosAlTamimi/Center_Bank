package com.falconssoft.centerbank;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.falconssoft.centerbank.Models.ChequeInfo;
import com.falconssoft.centerbank.Models.LoginINFO;
import com.falconssoft.centerbank.Models.notification;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.widget.LinearLayout.VERTICAL;
import static com.falconssoft.centerbank.EditerCheackActivity.localNationlNo;
import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;
import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;
import static com.falconssoft.centerbank.MainActivity.STOP_ACTION;
import static com.falconssoft.centerbank.MainActivity.YES_ACTION;
import static com.falconssoft.centerbank.MainActivity.notification_btn;


public class AlertScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    public  String acc="",bankN="",branch="",cheNo="";

    ArrayList <notification> notificationArrayList;
    ArrayList <notification> notificationArrayListTest;
    LinearLayoutManager layoutManager;
    NotificationManager notificationManager;
    SwipeRefreshLayout swipeRefresh;

    static int id=1;
    public  static TextView mainText,textCheckstateChanger;
    public  String userNmae="",Passowrd="";
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public  static   String language="", serverLink;
    ArrayList<String> arrayListRow=new ArrayList<>();
    ArrayList<String> arrayListRowFirst=new ArrayList<>();
    DatabaseHandler databaseHandler;
    ArrayList<notification> notifiList1;
    String  phoneNo="";
    public  static  String ROW_ID_PREFERENCE="ROW_ID_PREFERENCE";
    LoginINFO user;
    LinearLayout layout;
    Bitmap serverPicBitmap;
    int first=0;
    Timer timer;
    public  static ArrayList<ChequeInfo> checkInfoNotification;
    public ArrayList<notification> notifiList;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_main_screen);

        SharedPreferences loginPrefs = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        serverLink = loginPrefs.getString("link", "");

        layout = (LinearLayout)findViewById(R.id.mainlayout);
        first=1;
        SharedPreferences prefs = getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE);
        language = prefs.getString("language", "en");//"No name defined" is the default value.
        Log.e("editing,3 ", language);
        if(language.equals("ar"))
        {
            layout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        else {
            layout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        }

        initialview();
      //  dataForTest();

        editor = sharedPreferences.edit();
        editor.clear();// just for test
        phoneNo = loginPrefs.getString("mobile", "");

        new GetAllCheck_JSONTask().execute();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new GetAllCheck_JSONTask().execute();


            }

        }, 0, 10000);


        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

//                Toast.makeText(AlertScreen.this, "refresh ..", Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
//                new GetAllCheck_JSONTask().execute();
            }
        });



    }

    private void dataForTest() {
        notification on=new notification();
        on.setAmount_check("100");
        on.setDate("10/05/321");
        on.setSource("ahmed");
//        fillListNotification(on);
    }

    public void notificationShow()
    {

        Notification.Builder notif;
        NotificationManager nm;
        notif = new Notification.Builder(getApplicationContext());
        notif.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        notif.setContentTitle("Recive new Check, click to show detail");
        notif.setAutoCancel(true);
        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notif.setSound(path);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//        context.sendBroadcast(it);

        Intent yesReceive = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS );// test
        yesReceive.setAction(YES_ACTION);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.addAction(R.drawable.ic_local_phone_black_24dp, "show Detail", pendingIntentYes);


        Intent yesReceive2 = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        yesReceive2.setAction(STOP_ACTION);
        PendingIntent pendingIntentYes2 = PendingIntent.getBroadcast(this, 12345, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.addAction(R.drawable.ic_access_time_black_24dp, "cancel", pendingIntentYes2);



        nm.notify(10, notif.getNotification());
    }

    private void initialview() {
        databaseHandler=new DatabaseHandler(AlertScreen.this);
        recyclerView = findViewById(R.id.recycler);
        mainText=findViewById(R.id.textView);
        user=new LoginINFO();
        sharedPreferences = getSharedPreferences(ROW_ID_PREFERENCE, Context.MODE_PRIVATE);
        notifiList1=new ArrayList<>();
        user=databaseHandler.getLoginInfo();
        userNmae=user.getUsername();
        Passowrd=user.getPassword();
        notification_btn.setVisibility(View.INVISIBLE);


        textCheckstateChanger=findViewById(R.id.textCheckstateChanger);
        textCheckstateChanger.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("2")) {

                    new GetAllCheck_JSONTask().execute();
                    finish();
                    Intent n = new Intent(AlertScreen.this, AlertScreen.class);
                    startActivity(n);
//

                }



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        notificationArrayList=new ArrayList<>();
        notificationArrayListTest=new ArrayList<>();
        checkInfoNotification=new ArrayList<>();
        notifiList=new ArrayList<>();
    }
    // ******************************************** GET NOTIFICATION *************************************
    public class GetAllCheck_JSONTask extends AsyncTask<String, String, String> {

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
//                http://localhost:8082/GetAllTempCheck?CUSTMOBNO=0798899716&CUSTIDNO=123456
                request.setURI(new URI(serverLink + "GetLog?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("ACCCODE", "0"));
//
                nameValuePairs.add(new BasicNameValuePair("MOBNO", phoneNo));// test
                nameValuePairs.add(new BasicNameValuePair("WHICH", "1"));
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
                Log.e("tagAlertScreenGetLog", "" + JsonResponse);

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
                    JSONObject jsonObject = null;
                    try {

                        checkInfoNotification.clear();

                        if (first == 1) {
                            notificationArrayList.clear();
                        }
                        notificationArrayListTest.clear();


                        arrayListRow.clear();
                        arrayListRowFirst.clear();
                        notifiList.clear();
                        jsonObject = new JSONObject(s);

                        JSONArray notificationInfo = jsonObject.getJSONArray("INFO");
                        for (int i = 0; i < notificationInfo.length(); i++) {
                            JSONObject infoDetail = notificationInfo.getJSONObject(i);
//                            serverPicBitmap=null;
                            ChequeInfo chequeInfo = new ChequeInfo();
                            chequeInfo.setTransType(infoDetail.getString("TRANSSTATUS"));
                            chequeInfo.setStatus(infoDetail.getString("STATUS"));// Recive=== 1
                            Log.e("setTransType","\t"+chequeInfo.getTransType()+"\t setStatus"+chequeInfo.getStatus());
                            if ((chequeInfo.getTransType().equals("0") && chequeInfo.getStatus().equals("1")) ||
                                    (chequeInfo.getStatus().equals("0") && !chequeInfo.getTransType().equals("0")))// Pending and Reciver
                            {



                            notification notifi = new notification();
                            notifi.setSource(infoDetail.get("CUSTOMERNM").toString());
                            notifi.setDate(infoDetail.get("CHECKDUEDATE").toString());
                            notifi.setAmount_check(infoDetail.get("AMTJD").toString());
                            //**********************************************************************

                            chequeInfo.setRowId(infoDetail.get("ROWID1").toString());
                            chequeInfo.setRecieverNationalID(infoDetail.get("TOCUSTOMERNATID").toString());
                            chequeInfo.setRecieverMobileNo(infoDetail.get("TOCUSTOMERMOB").toString());
                            chequeInfo.setCustName(infoDetail.get("CUSTOMERNM").toString());
                            Log.e("setCustName",""+chequeInfo.getCustName());
                            chequeInfo.setChequeData(infoDetail.get("CHECKDUEDATE").toString());
                            chequeInfo.setToCustomerName(infoDetail.get("TOCUSTOMERNM").toString());

                            chequeInfo.setMoneyInDinar(infoDetail.get("AMTJD").toString());
                            chequeInfo.setMoneyInWord(infoDetail.get("AMTWORD").toString());
                            chequeInfo.setMoneyInFils(infoDetail.getString("AMTFILS"));
                            chequeInfo.setBankName(infoDetail.get("BANKNM").toString());
                            chequeInfo.setChequeNo(infoDetail.get("CHECKNO").toString());


                            chequeInfo.setBranchNo(infoDetail.get("BRANCHNO").toString());
                            chequeInfo.setAccCode(infoDetail.get("ACCCODE").toString());
                            chequeInfo.setIbanNo(infoDetail.get("IBANNO").toString());
                            chequeInfo.setBankNo(infoDetail.get("BANKNO").toString());

                            chequeInfo.setTransType(infoDetail.getString("TRANSSTATUS"));
                            chequeInfo.setStatus(infoDetail.getString("STATUS"));

                            chequeInfo.setUserName(infoDetail.getString("USERNO"));

                            chequeInfo.setISBF(infoDetail.getString("ISFB"));
                            chequeInfo.setISCO(infoDetail.getString("ISCO"));

                                chequeInfo.setNoteCheck(infoDetail.getString("NOTE"));
                                chequeInfo.setCompanyName(infoDetail.getString("COMPANY"));
//                            Log.e("chequeInfo",""+chequeInfo.getAccCode()+chequeInfo.getBankNo()+chequeInfo.getBranchNo()+"\t"+chequeInfo.getChequeNo());

                            arrayListRow.add(chequeInfo.getRowId());

                            checkInfoNotification.add(chequeInfo);
                            if (first == 1) {
                                notificationArrayList.add(notifi);
                            }

                            notificationArrayListTest.add(notifi);
                                Log.e("notificatListTest",""+notificationArrayListTest.size());

                        }
                    }
//
                        if(first==1)
                        {
                            fillListNotification(notificationArrayList);

                        }



                        Set<String> set = sharedPreferences.getStringSet("DATE_LIST", null);

                        if(set !=null)
                        {
//
                            set = sharedPreferences.getStringSet("DATE_LIST", null);
                            arrayListRowFirst.addAll(set);

                            int countFirst=arrayListRowFirst.size();
                                if(arrayListRow.size()<countFirst)//there are update new data is less than old data
                                {Log.e("olddataGreater","countFirst"+countFirst);

                                    for( int h=0;h<arrayListRow.size();h++){
                                        int index= arrayListRowFirst.indexOf(arrayListRow.get(h));
                                        if(index==-1)
                                        {
                                            arrayListRowFirst.add(arrayListRow.get(h));
                                            Log.e("arrayListRowYES",""+arrayListRow.get(h));

                                        }

                                    }

                                    if (countFirst < arrayListRowFirst.size())// new data
                                    {
                                        ShowNotifi();

                                        fillListNotification(notificationArrayListTest);


                                    }
                                    else {

                                        fillListNotification(notificationArrayListTest);
                                    }

                                }//********************************************
                                else {
                                    if(arrayListRow.size()>countFirst)// new data
                                    {
                                        Log.e("NewGreater","countFirst");
                                        fillListNotification(notificationArrayListTest);
                                        ShowNotifi();

                                    }
                                    else{
                                        if(arrayListRow.size()==countFirst)// equal size
                                        {
                                            Log.e("arrayListRow","== hereeee");

                                            for( int h=0;h<arrayListRow.size();h++){
                                                int index= arrayListRowFirst.indexOf(arrayListRow.get(h));
                                                if(index==-1)
                                                {
                                                    arrayListRowFirst.add(arrayListRow.get(h));


                                                }

                                            }

                                            if (countFirst < arrayListRowFirst.size())// new data
                                            {
                                                ShowNotifi();

                                                fillListNotification(notificationArrayListTest);

                                            }
                                            else {

//                                                fillListNotification(notificationArrayListTest);
                                            }
                                        }

                                    }

                                   }


//                            }

                        }
                        else {//empty shared preference
                            if(first!=1)
                            {
                                fillListNotification(notificationArrayList);
                                ShowNotifi();
                                Log.e("Notfirst",""+first);
                            }



                        }


                        Set<String> set_tow = new HashSet<String>();
                        set_tow.addAll(arrayListRow);
                        Log.e("Empty",""+arrayListRow.size());
                        editor = sharedPreferences.edit();
                        editor.putStringSet("DATE_LIST", set_tow);
                        editor.apply();

                        first=2;
//                        fillListNotification(notificationArrayList);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    INFO
                    Log.e("tag", "****Success"+s.toString());
                } else {
                    Log.e("tag", "****Failed to export data");
                }
            } else {

                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

    private void ShowNotifi() {
        String currentapiVersion = Build.VERSION.RELEASE;
//
        if (Double.parseDouble(currentapiVersion.substring(0,1) )>=8) {
            // Do something for 14 and above versions

//                                show_Notification("Thank you for downloading the Points app, so we'd like to add 30 free points to your account");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                show_Notification("Check  app, Recive new Check");

            }
            else {

            }


        } else {

            notificationShow();
        }
    }

    public void noto2() // paste in activity
    {
        Notification.Builder notif;
        NotificationManager nm;
        notif = new Notification.Builder(getApplicationContext());
        notif.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        notif.setContentTitle("Recive new Check, click to show detail");
        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notif.setSound(path);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent yesReceive = new Intent( );
        yesReceive.setAction(YES_ACTION);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.addAction(R.drawable.second_check, "show Detail", pendingIntentYes);


        Intent yesReceive2 = new Intent();
        yesReceive2.setAction(STOP_ACTION);
        PendingIntent pendingIntentYes2 = PendingIntent.getBroadcast(this, 12345, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.addAction(R.drawable.ic_access_time_black_24dp, "cancel", pendingIntentYes2);



        nm.notify(10, notif.getNotification());
    }

    @SuppressLint("WrongConstant")
    private void fillListNotification(ArrayList<notification> notifications) {
        notifiList1.clear();
        notifiList1 = notifications;
        layoutManager = new LinearLayoutManager(AlertScreen.this);
        layoutManager.setOrientation(VERTICAL);
        runAnimation(recyclerView, 0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Toast.makeText(AlertScreen.this, "Saved", Toast.LENGTH_SHORT).show();


    }

    private void runAnimation(RecyclerView recyclerView, int type) {
        Context context=recyclerView.getContext();
        LayoutAnimationController controller=null;
        if(type==0)
        {
            controller= AnimationUtils.loadLayoutAnimation(context,R.anim.layout_filldown);
            NotificatioAdapter notificationAdapter = new NotificatioAdapter(AlertScreen.this, notifiList1);
            recyclerView.setAdapter(notificationAdapter);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(String detail){

        Intent intent=new Intent(AlertScreen.this,notificationReciver.class);
        intent.putExtra("action","YES");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AlertScreen.this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        String CHANNEL_ID="MYCHANNEL";

        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_HIGH);
        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText("show Detail ......")
                .setContentTitle("Recive new Check, click to show detail")
                .setStyle(new Notification.BigTextStyle()
                        .bigText(detail)
                        .setBigContentTitle(" ")
                        .setSummaryText(""))
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat,"Show detail",pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_add)
                .setOngoing(true)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);


    }

    private void notification_show (String detail){// this to use
//        final Intent intent = new Intent(this, MainActivity.class);
//        intent.setData(Uri.parse("data"));
//        intent.putExtra("key", "clicked");
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
////        final PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent), 0);
//        try {
//            // Perform the operation associated with our pendingIntent
//            pendingIntent.send();
//        } catch (PendingIntent.CanceledException e) {
//            e.printStackTrace();
//        }
        NotificationCompat.Builder nbuilder=new NotificationCompat.Builder(AlertScreen.this)
                .setContentTitle("Check APP Notification ......")
                .setContentText("New Check... click to show details ")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(detail)
                        .setBigContentTitle(" New Check ").setSummaryText(""))
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);

        notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id,nbuilder.build());


    }

}