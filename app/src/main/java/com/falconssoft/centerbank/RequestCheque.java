package com.falconssoft.centerbank;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.centerbank.Models.ChequeInfo;
import com.falconssoft.centerbank.Models.LoginINFO;
import com.falconssoft.centerbank.Models.notification;
import com.falconssoft.centerbank.Models.requestModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static android.widget.LinearLayout.VERTICAL;
import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;
import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;
import static com.falconssoft.centerbank.MainActivity.Request_ACTION;
import static com.falconssoft.centerbank.MainActivity.STOP_ACTION;
import static com.falconssoft.centerbank.MainActivity.YES_ACTION;
import static com.falconssoft.centerbank.Request.serverLink;

public class RequestCheque extends AppCompatActivity {
    RecyclerView recyclerView;
    public  String acc="",bankN="",branch="",cheNo="";

    ArrayList <notification> notificationArrayList;
    ArrayList <notification> notificationArrayListTest;
    public  static ArrayList<ChequeInfo> checkInfoNotification;
    public ArrayList<notification> notifiList;
    //******************************************************
    ArrayList <requestModel> requestArrayList;
    ArrayList <requestModel> requestArrayListTest;
    public ArrayList<requestModel> requestList;

//    public  static ArrayList<ChequeInfo> checkInfoNotification;
    ArrayList <requestModel> requestListMain;
    ArrayList <requestModel> requestListTestMain;
    //******************************************************
    LinearLayoutManager layoutManager;

    NotificationManager notificationManager;
    SwipeRefreshLayout swipeRefresh;

    static int id=1;
    public  static TextView mainText,textCheckstateChanger;
    public  String userNmae="",Passowrd="";
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    ArrayList<String> arrayListRow=new ArrayList<>();
    ArrayList<String> arrayListRowFirst=new ArrayList<>();
    DatabaseHandler databaseHandler;
    ArrayList<notification> notifiList1;
    ArrayList<requestModel> requestList1;
    String  phoneNo="";
    public  static  String ROW_ID_PREFERENCE="ROW_ID_PREFERENCE";
    LoginINFO user;
    LinearLayout layout;
    Bitmap serverPicBitmap;
    int first=0;
    int flagMain=0;
    boolean foundFirst=false,foundSecond=false;
    Timer timer;
    FloatingActionButton floa_add;
    public  String WHICH="0";
    public  static   String language="", serverLink="http://falconssoft.net/ScanChecks/APIMethods.dll/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_cheque);

        SharedPreferences loginPrefs = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        serverLink = loginPrefs.getString("link", "");

        layout = (LinearLayout)findViewById(R.id.mainlayout);
        first=1;
        flagMain=1;
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


        new GetAllRequestToUser_JSONTask().execute();
        Log.e("flagMainCreat",""+flagMain);

//        if(flagMain==2)
//        {
//            new GetAllRequestFromUser_JSONTask().execute();
//        }
        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                new GetAllCheck_JSONTask().execute();
//
//
//            }
//
//        }, 0, 10000);

    }
    private void initialview() {
        databaseHandler=new DatabaseHandler(RequestCheque.this);
        recyclerView = findViewById(R.id.recycler);
        mainText=findViewById(R.id.textView);
        floa_add=findViewById(R.id.floating_add);
        floa_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RequestCheque.this,Request.class);
                startActivity(i);
            }
        });
        user=new LoginINFO();
        sharedPreferences = getSharedPreferences(ROW_ID_PREFERENCE, Context.MODE_PRIVATE);
        notifiList1=new ArrayList<>();
        requestList1=new ArrayList<>();
        user=databaseHandler.getLoginInfo();
        userNmae=user.getUsername();
        Passowrd=user.getPassword();

        textCheckstateChanger=findViewById(R.id.textCheckstateChanger);

        notificationArrayList=new ArrayList<>();
        notificationArrayListTest=new ArrayList<>();

        requestArrayList=new ArrayList<>();
        requestArrayListTest=new ArrayList<>();
        checkInfoNotification=new ArrayList<>();
        notifiList=new ArrayList<>();
        requestList=new ArrayList<>();
        requestListMain=new ArrayList<>();
        requestListTestMain=new ArrayList<>();
    }
    public class GetAllRequestToUser_JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                WHICH="0";// to user

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
//                http://localhost:8082/GetAllTempCheck?CUSTMOBNO=0798899716&CUSTIDNO=123456
                request.setURI(new URI(serverLink + "GetRequest?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("MOBILENO", phoneNo));

                nameValuePairs.add(new BasicNameValuePair("WHICH", WHICH));// to me witch=1
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));


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
                Log.e("tagGetRequest", "" + JsonResponse);

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

//                        checkInfoNotification.clear();//delete

                        if (first == 1) {
                            requestArrayList.clear();
                            requestListMain.clear();// for first creat
                        }
                        requestArrayListTest.clear();
                        requestListTestMain.clear();


                        arrayListRow.clear();
                        arrayListRowFirst.clear();
                        requestList.clear();



                        jsonObject = new JSONObject(s);

                        JSONArray notificationInfo = jsonObject.getJSONArray("INFO");
                        for (int i = 0; i < notificationInfo.length(); i++) {
                            JSONObject infoDetail = notificationInfo.getJSONObject(i);

                            requestModel chequeInfo = new requestModel();

                            chequeInfo.setTRANSSTATUS(infoDetail.get("TRANSSTATUS").toString());


                            Log.e("setTransType","\t"+chequeInfo.getTRANSSTATUS());

                            if (chequeInfo.getTRANSSTATUS().equals("0") )// reject from mee
                            {
                                chequeInfo.setROWID(infoDetail.getString("ROWID"));
                                chequeInfo.setFROMUSER_No(infoDetail.getString("FROMUSER"));
                                chequeInfo.setWitch(WHICH);
                                chequeInfo.setFROMUSER_name(infoDetail.get("FROMUSERNM").toString());
                                chequeInfo.setTOUSER_No(infoDetail.get("TOUSER").toString());
                                chequeInfo.setTOUSER_name(infoDetail.get("TOUSERNM").toString());
                                chequeInfo.setCOMPNAME(infoDetail.get("COMPNAME").toString());
                                Log.e("getFROMUSER_name",""+chequeInfo.getFROMUSER_name());
                                chequeInfo.setNOTE(infoDetail.get("NOTE").toString());
                                chequeInfo.setAMOUNT(infoDetail.get("AMOUNT").toString());

                                chequeInfo.setTRANSSTATUS(infoDetail.get("TRANSSTATUS").toString());
                                chequeInfo.setINDATE(infoDetail.get("INDATE").toString());
                                chequeInfo.setREASON(infoDetail.getString("REASON"));


                                arrayListRow.add(chequeInfo.getROWID());

//                                checkInfoNotification.add(chequeInfo);
                                if (first == 1) {
                                    requestArrayList.add(chequeInfo);
                                    requestListMain.add(chequeInfo);
                                    Log.e("requestListMain",""+requestListMain.size());
                                }

                                requestArrayListTest.add(chequeInfo);
                                requestListTestMain.add(chequeInfo);
                                Log.e("ToUserArrayListTest",""+requestListTestMain.size());

                            }
                        }
                        Log.e("requestListTestMain",""+requestListTestMain.size());
//
                        if(first==1)
                        {
//                            fillListNotification(requestArrayList);

                        }
                        Set<String> set_tow = new HashSet<String>();
                        set_tow.addAll(arrayListRow);
                        Log.e("Empty",""+arrayListRow.size());



                        Set<String> set = sharedPreferences.getStringSet("REQUEST_ToUser", set_tow);

                        if(set !=null)
                        {
//
                            set = sharedPreferences.getStringSet("REQUEST_ToUser", set_tow);
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
                                    foundFirst=true;
//                                    ShowNotifi();

//                                    fillListNotification(requestArrayListTest);


                                }
                                else {

//                                    fillListNotification(requestArrayListTest);
                                }

                            }//********************************************
                            else {
                                if(arrayListRow.size()>countFirst)// new data
                                {
                                    Log.e("NewGreater","countFirst");
//                                    fillListNotification(requestArrayListTest);
//                                    ShowNotifi();
                                    foundFirst=true;

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
//                                            ShowNotifi();
//
//                                            fillListNotification(requestArrayListTest);
                                            foundFirst=true;

                                        }
                                        else {

//                                                fillListNotification(requestListTestMain);
                                        }
                                    }

                                }

                            }


//                            }

                        }
                        else {//empty shared preference
                            if(first!=1)
                            {
//                                fillListNotification(requestArrayList);
//                                ShowNotifi();
                                Log.e("Notfirst",""+first);
                                foundFirst=true;
                            }



                        }

                        editor = sharedPreferences.edit();
                        editor.putStringSet("REQUEST_ToUser", set_tow);
                        editor.apply();
                        Log.e("EndFirstToUser","****************");
                        new GetAllRequestFromUser_JSONTask().execute();




//                        fillListNotification(notificationArrayList);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    INFO
                    Log.e("tag", "****Success"+s.toString());
                } else {
                    Log.e("tag", "****Failed to export data");
                }
            }
            else {

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


                show_Notification("Check  app, Recive new Request");

            }
            else {

            }


        } else {

            notificationShow();
        }
    }
    @SuppressLint("WrongConstant")
    private void fillListNotification(ArrayList<requestModel> notificationsRequest) {
//        notifiList1.clear();
        requestList1.clear();
        requestList1 = notificationsRequest;
        Log.e("requestList1",""+requestList1.size());
        layoutManager = new LinearLayoutManager(RequestCheque.this);
        layoutManager.setOrientation(VERTICAL);
        runAnimation(recyclerView, 0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Toast.makeText(RequestCheque.this, "Saved", Toast.LENGTH_SHORT).show();


    }

    private void runAnimation(RecyclerView recyclerView, int type) {
        Context context=recyclerView.getContext();
        LayoutAnimationController controller=null;
        if(type==0)
        {
            controller= AnimationUtils.loadLayoutAnimation(context,R.anim.layout_filldown);
            Requestadapter notificationAdapter = new Requestadapter(RequestCheque.this, requestList1);
            recyclerView.setAdapter(notificationAdapter);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(String detail){

        Intent intent=new Intent(RequestCheque.this,notificationReciver.class);
        intent.putExtra("action","YES");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(RequestCheque.this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        String CHANNEL_ID="MYCHANNEL";

        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_HIGH);
        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText("show Detail ......")
                .setContentTitle("Recive new Request, click to show detail")
                .setStyle(new Notification.BigTextStyle()
                        .bigText(detail)
                        .setBigContentTitle(" ")
                        .setSummaryText(""))
                .setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_notifications_black_24dp)
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

    public void notificationShow()
    {

        Notification.Builder notif;
        NotificationManager nm;
        notif = new Notification.Builder(getApplicationContext());
        notif.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        notif.setContentTitle("Recive new Request, click to show detail");
        notif.setAutoCancel(true);
        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notif.setSound(path);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//        context.sendBroadcast(it);

        Intent yesReceive = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS );// test
        yesReceive.setAction(Request_ACTION);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.addAction(R.drawable.ic_local_phone_black_24dp, "show Detail", pendingIntentYes);


        Intent yesReceive2 = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        yesReceive2.setAction(STOP_ACTION);
        PendingIntent pendingIntentYes2 = PendingIntent.getBroadcast(this, 12345, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.addAction(R.drawable.ic_access_time_black_24dp, "cancel", pendingIntentYes2);



        nm.notify(10, notif.getNotification());
    }
    public class GetAllRequestFromUser_JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Log.e("flagMaindoInBackground",""+flagMain);
                WHICH="1";

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
//                http://localhost:8082/GetAllTempCheck?CUSTMOBNO=0798899716&CUSTIDNO=123456
                request.setURI(new URI(serverLink + "GetRequest?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("MOBILENO", phoneNo));

                nameValuePairs.add(new BasicNameValuePair("WHICH", WHICH));// to me witch=1
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));


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
                Log.e("tagGetRequest", "" + JsonResponse);

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
                        Log.e("StartSecondUser","****************");
                        Log.e("beforeListTestMain",""+requestListTestMain.size());

//                        checkInfoNotification.clear();//delete

//                        if (first == 1) {
//                            requestArrayList.clear();
//                        }
//                        requestArrayListTest.clear();


                        arrayListRow.clear();
                        arrayListRowFirst.clear();
                        requestList.clear();



                        jsonObject = new JSONObject(s);

                        JSONArray notificationInfo = jsonObject.getJSONArray("INFO");
                        for (int i = 0; i < notificationInfo.length(); i++) {
                            JSONObject infoDetail = notificationInfo.getJSONObject(i);

                            requestModel chequeInfo = new requestModel();

                            chequeInfo.setTRANSSTATUS(infoDetail.get("TRANSSTATUS").toString());


                            Log.e("setTransType","\t"+chequeInfo.getTRANSSTATUS());

                            if (chequeInfo.getTRANSSTATUS().equals("1") )// reject from mee
                            {
                                chequeInfo.setROWID(infoDetail.getString("ROWID"));
                                chequeInfo.setFROMUSER_No(infoDetail.getString("FROMUSER"));
                                chequeInfo.setWitch(WHICH);
                                chequeInfo.setFROMUSER_name(infoDetail.get("FROMUSERNM").toString());
                                chequeInfo.setTOUSER_No(infoDetail.get("TOUSER").toString());
                                chequeInfo.setTOUSER_name(infoDetail.get("TOUSERNM").toString());
                                chequeInfo.setCOMPNAME(infoDetail.get("COMPNAME").toString());
                                Log.e("getFROMUSER_name",""+chequeInfo.getFROMUSER_name());
                                chequeInfo.setNOTE(infoDetail.get("NOTE").toString());
                                chequeInfo.setAMOUNT(infoDetail.get("AMOUNT").toString());

                                chequeInfo.setTRANSSTATUS(infoDetail.get("TRANSSTATUS").toString());
                                chequeInfo.setINDATE(infoDetail.get("INDATE").toString());
                                chequeInfo.setREASON(infoDetail.getString("REASON"));


                                arrayListRow.add(chequeInfo.getROWID());

                                if(first==1){
                                    requestListMain.add(chequeInfo);
                                }


                                requestArrayListTest.add(chequeInfo);
                                requestListTestMain.add(chequeInfo);
                                Log.e("ToUserArrayListTest",""+requestListTestMain.size());

                            }
                        }
//
                        if(first==1)
                        {
                            Log.e("first",""+first);


                        }
                        fillListNotification(requestListMain);
                        Set<String> set_t = new HashSet<String>();
                        set_t.addAll(arrayListRow);
                        Log.e("Empty",""+arrayListRow.size());



                        Set<String> set = sharedPreferences.getStringSet("REQUEST_LIST", set_t);

                        if(set !=null)
                        {
//
                            set = sharedPreferences.getStringSet("REQUEST_LIST", set_t);
                            arrayListRowFirst.addAll(set);

                            int countFirst=arrayListRowFirst.size();
                            if(arrayListRow.size()<countFirst)//there are update new data is less than old data
                            {
                                Log.e("olddataGreater","countFirst"+countFirst);
                                Log.e("olddataGreater","arrayListRow"+arrayListRow.size());

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
                                    Log.e("NewGreater","new data");
//                                    ShowNotifi();
//                                    fillListNotification(requestListTestMain);
                                    foundSecond=true;


                                }
                                else {

//                                    fillListNotification(requestListMain);
                                }

                            }//********************************************
                            else {
                                if(arrayListRow.size()>countFirst)// new data
                                {
                                    Log.e("NewGreater","countFirst");
//                                    fillListNotification(requestListTestMain);
//                                    ShowNotifi();
                                    foundSecond=true;

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
//                                            ShowNotifi();

//                                            fillListNotification(requestListTestMain);
                                            foundSecond=true;

                                        }
                                        else {

//                                                fillListNotification(requestListMain);
                                        }
                                    }

                                }

                            }


//                            }

                        }
                        else {//empty shared preference
                                                   }



                        editor = sharedPreferences.edit();
                        editor.putStringSet("REQUEST_LIST", set_t);
                        editor.apply();
                        Log.e("foundSecond",""+foundSecond+"\t foundFirst"+foundFirst);


                       // fillList();
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
            }
            else {

                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

    private void fillList() {
        Log.e("fillList",""+foundSecond+requestListTestMain.size());
        if(foundSecond||foundFirst)
        {
            fillListNotification(requestListTestMain);
            ShowNotifi();

        }
        foundSecond=false;
        foundFirst=false;
    }

}
