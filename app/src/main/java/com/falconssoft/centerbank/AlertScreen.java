package com.falconssoft.centerbank;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.falconssoft.centerbank.Models.notification;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;
import static com.falconssoft.centerbank.MainActivity.STOP_ACTION;
import static com.falconssoft.centerbank.MainActivity.YES_ACTION;

public class AlertScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList <notification> notificationArrayList;
    LinearLayoutManager layoutManager;
    NotificationManager notificationManager;
    static int id=1;
    TextView mainText;
    String stateIntent="";
    String Main_URL="";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_main_screen);
        recyclerView = findViewById(R.id.recycler);
        mainText=findViewById(R.id.textView);
        Main_URL="http://10.0.0.16:8081/GetCheckTemp?ACCCODE=1014569990011000&IBANNO=&SERIALNO=&BANKNO=004&BRANCHNO=0099&CHECKNO=390144";

                mainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute(Main_URL);
            }
        });
        notificationArrayList=new ArrayList<>();
//        notificationArrayList=getNotification();
        fillListNotification();
        fillListNotification();
        fillListNotification();
        layoutManager = new LinearLayoutManager(AlertScreen.this);
        layoutManager.setOrientation(VERTICAL);
        runAnimation(recyclerView,0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final NotificatioAdapter notificationAdapter = new NotificatioAdapter(AlertScreen.this, notificationArrayList);
        recyclerView.setAdapter(notificationAdapter);


        Toast.makeText(AlertScreen.this, "Saved", Toast.LENGTH_SHORT).show();
    }

//    private ArrayList<notification> getNotification() {
//
//    }
    // ******************************************** CHECK QR VALIDATION *************************************
    private class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
//            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
               // http://10.0.0.16:8081/GetCheckTemp?ACCCODE=1014569990011000&IBANNO=&SERIALNO=&BANKNO=004&BRANCHNO=0099&CHECKNO=390144
//                 http://10.0.0.16:8081/VerifyCheck?CHECKNO=390144&BANKNO=004&BTANCHNO=0099&ACCCODE=1014569990011000&IBANNO=""&CUSTOMERNM=""
//                request.setURI(new URI("http://" + generalSettings.getIpAddress() + "/export.php"));//import 10.0.0.214
                request.setURI(new URI("http://10.0.0.16:8081/GetCheckTemp?ACCCODE=1014569990011000&IBANNO=&SERIALNO=&BANKNO=004&BRANCHNO=0099&CHECKNO=390144"));


//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//                Log.e("addToInventory/", "" + jsonArrayBundles.toString());
//                nameValuePairs.add(new BasicNameValuePair("UPDATE_RAW_INFO", "1"));// list

//                nameValuePairs.add(new BasicNameValuePair("TRUCK", oldTruck));//oldTruck
//                nameValuePairs.add(new BasicNameValuePair("RAW_INFO_DETAILS", jsonArray.toString().trim()));// list
//                nameValuePairs.add(new BasicNameValuePair("RAW_INFO_MASTER", masterData.toString().trim())); // json object
//                Log.e("addNewRow/", "update" + masterData.toString().trim() + " ///oldTruck" + oldTruck);

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
                Log.e("AlertScreen/", "verify" + JsonResponse);

                return JsonResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("tag of update row info", s);
//            progressDialog.dismiss();
            if (s != null) {
                if (s.contains("UPDATE RAWS SUCCESS")) {
//                    showSweetDialog(true);

                    Log.e("tag", "update Success");
                } else {
//                    showSweetDialog(false);
                    Log.e("tag", "****Failed to export data");
//                    Toast.makeText(AddToInventory.this, "Failed to export data Please check internet connection", Toast.LENGTH_LONG).show();
                }
            } else {
                Log.e("tag", "****Failed to export data Please check internet connection");
                Toast.makeText(AlertScreen.this, "Failed to export data Please check internet connection", Toast.LENGTH_LONG).show();
            }
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

    private void fillListNotification() {

        notification one=new notification();
        one.setAmount_check("1500 JD");
        one.setDate("21-6-2020");
        one.setSource("Companey Source");
        notificationArrayList.add(one);
        notificationArrayList.add(one);
        notificationArrayList.add(one);


    }
    private void runAnimation(RecyclerView recyclerView, int type) {
        Context context=recyclerView.getContext();
        LayoutAnimationController controller=null;
        if(type==0)
        {
            controller= AnimationUtils.loadLayoutAnimation(context,R.anim.layout_filldown);
            NotificatioAdapter notificationAdapter = new NotificatioAdapter(AlertScreen.this, notificationArrayList);
            recyclerView.setAdapter(notificationAdapter);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(String detail){

        Intent intent=new Intent(getApplicationContext(),AlertScreen.class);
        String CHANNEL_ID="MYCHANNEL";

        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_HIGH);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,intent,0);
        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText("POINT APP Notification ......")
                .setContentTitle("Point Gift From Point App")
                .setStyle(new Notification.BigTextStyle()
                        .bigText(detail)
                        .setBigContentTitle(" ")
                        .setSummaryText(""))
//                .setContentIntent(pendingIntent)
//                .addAction(android.R.drawable.sym_action_chat,"Title",pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_add)
                .build();


        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);


    }
    private void notification (String detail){// this to use
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
/*
Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
mBuilder.setSound(alarmSound);
*/
