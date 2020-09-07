package com.falconssoft.centerbank;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.falconssoft.centerbank.Models.ChequeInfo;
import com.falconssoft.centerbank.Models.LoginINFO;
import com.falconssoft.centerbank.Models.NewAccount;
import com.falconssoft.centerbank.Models.notification;
import com.falconssoft.centerbank.Models.requestModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.falconssoft.centerbank.AlertScreen.ROW_ID_PREFERENCE;
import static com.falconssoft.centerbank.AlertScreen.checkInfoNotification;
import static com.falconssoft.centerbank.AlertScreen.editor;
import static com.falconssoft.centerbank.AlertScreen.sharedPreferences;
import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;
import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;
import static com.falconssoft.centerbank.ShowNotifications.showNotification;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String CHANNEL_ID = "2";
    CircleImageView imageView;
    private ProgressDialog progressDialog;

    private TextView addAccount, generateCheque, logHistory, Editing, request, cashierCheque, jerro, wallet, usernameNavigation, barCodTextTemp, scanBarcode, signout;
    //    @SuppressLint("WrongConstant")
//    private LinearLayout addAccount, chooseAccount, generateCheque, logHistory,Editing;
    private TextView closeDialog, message, usernameTv;
    private SearchView searchAccount;
    private RecyclerView recyclerViewSearchAccount, recyclerViews;
    private CarouselLayoutManager layoutManagerd;
    private List<NewAccount> picforbar;
    private Toolbar toolbar;
    private Timer timer;
    private TextInputEditText inputEditTextTemp;
    private NotificationManager notificationManager;
    static int id = 1;
    public static final String YES_ACTION = "YES";
    public static final String Request_ACTION = "Request";
    public static final String STOP_ACTION = "STOP";
    private ArrayList<String> arrayListRow = new ArrayList<>();
    private ArrayList<String> arrayListRowFirst = new ArrayList<>();
    private ArrayList<notification> notifiList1;
    private ArrayList<requestModel> requestList;
    private ArrayList<requestModel> requestList_Tow;
    public ArrayList<notification> notifiList;
    public ArrayList<ChequeInfo> checkInfoList;

    private DatabaseHandler dbHandler;
    SQLiteDatabase database;
    static String watch;
    private String accCode = "", serverLink = "", CHECKNO = "", ACCCODE = "", IBANNO = "", CUSTOMERNM = "", QRCODE = "", SERIALNO = "", BANKNO = "", BRANCHNO = "", language, userNo, username, AccountNoDelete = "", phoneNo = "", fullUsername, bankNo = "";
    private JSONObject addAccountOb;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Dialog barcodeDialog, addAccountDialog;
    private String[] arr;
    private boolean isAdd = false, isNewData = false;
    private TextView bankNameTV, chequeWriterTV, chequeNoTV, accountNoTV, okTV, cancelTV, check, amountTV;
    public static final String LOGIN_FLAG = "LOGIN_FLAG";
    String countryCode = "962";
    public static TextView notification_btn, button_request;
    RelativeLayout notifyLayout, requestLayout;
    LoginINFO infoUser;
    int transtype = -1;
    int orderMobil = 0;
    String statuseJoin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(ROW_ID_PREFERENCE, Context.MODE_PRIVATE);

        SharedPreferences prefs = getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE);
        language = prefs.getString("language", "en");

        SharedPreferences loginPrefs = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        userNo = loginPrefs.getString("mobile", "");
        username = loginPrefs.getString("name", "");
        serverLink = loginPrefs.getString("link", "");

        init();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_waiting));

        Log.e("editingmain ", "" + isNetworkAvailable());
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isNetworkAvailable()) {
                    new GetNotification_JSONTask().execute();
                }


//                new GetAllRequestFromUser_JSONTask().execute();
//
//
            }

        }, 0, 5000);
        addAccountOb = new JSONObject();
        picforbar = new ArrayList<>();
//        picforbar.add("01365574861","");
//        picforbar.add("0136557486");
//        picforbar.add("01365574861");
//        picforbar.add("01365574861");
//        picforbar.add("01365574861");
//        picforbar.add("01365574861");

//        picforbar=dbHandler.getAllAcCount();

//        layoutManagerd = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);
//        recyclerViews = (RecyclerView) findViewById(R.id.res);
//        recyclerViews.setLayoutManager(layoutManagerd);
//        recyclerViews.setHasFixedSize(true);
//        recyclerViews.addOnScrollListener(new CenterScrollListener());
//        layoutManagerd.setPostLayoutListener(new CarouselZoomPostLayoutListener());
//        recyclerViews.setAdapter(new TestAdapterForbar(this, picforbar));
//        recyclerViews.requestFocus();
//        recyclerViews.scrollToPosition(2);
//        recyclerViews.requestFocus();

//        showAllDataAccount();
        new GetAllAccount().execute();
//        message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f);
        checkLanguage();

        logHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LogHistoryIntent = new Intent(MainActivity.this, LogHistoryActivity.class);
                LogHistoryIntent.putExtra("AccountNo", "00000");
                watch = "1";
                startActivity(LogHistoryIntent);
            }
        });

        Editing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditerCheackActivity.class);
                startActivity(intent);

            }
        });

        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AlertScreen.class);
                startActivity(i);
            }
        });
        button_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RequestCheque.class);
                startActivity(i);
            }
        });

        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAdd=true;
                addAccountButton();
            }
        });

//        chooseAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Dialog dialog = new Dialog(MainActivity.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////                dialog.setCancelable(false);
//                dialog.setContentView(R.layout.dialog_choose_account);
//
//                closeDialog = findViewById(R.id.dialog_search_close);
//                searchAccount = findViewById(R.id.dialog_search_tool);
//                recyclerViewSearchAccount = findViewById(R.id.dialog_search_recycler);
//
//
//                //TODO add dialog function
//                dialog.show();
//
//            }
//        });


//        gib.setImageResource(R.drawable.rscananimation);
//        final MediaController mc = new MediaController(MainActivity.this);
//        mc.setMediaPlayer((GifDrawable) gib.getDrawable());
//        mc.setAnchorView(gib);
//        mc.show();

//        scanBarcode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                readBarCode();
//            }
//        });
        navigationView.setNavigationItemSelectedListener(this);

        jerro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentJeros = new Intent(MainActivity.this, JeroActivity.class);
                startActivity(intentJeros);

            }
        });


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void showAllDataAccount() {
//        picforbar = dbHandler.getAllAcCount();
//        new GetAllAccount().execute();
        layoutManagerd = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);

        recyclerViews.setLayoutManager(layoutManagerd);
        recyclerViews.setHasFixedSize(true);
        recyclerViews.addOnScrollListener(new CenterScrollListener());
        layoutManagerd.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerViews.setAdapter(new TestAdapterForbar(this, picforbar));
        recyclerViews.requestFocus();
        recyclerViews.scrollToPosition(2);
        recyclerViews.requestFocus();


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ChanelNotification";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void notificationShow(String title) {
        Log.e("notificationShow", "" + title);
        String actionType = "";
        Notification.Builder notif;
        NotificationManager nm;
        notif = new Notification.Builder(getApplicationContext());
        notif.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        notif.setContentTitle(title);
        notif.setAutoCancel(true);
        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notif.setSound(path);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//        context.sendBroadcast(it);
        if (title.contains("Request")) {
            Log.e("notificationShow", "" + title.contains("Request"));
            Intent yesReceive = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);// test
            yesReceive.setAction(Request_ACTION);
            PendingIntent pendingIntentYes = PendingIntent.getBroadcast(this, 1234, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
            notif.addAction(R.drawable.ic_local_phone_black_24dp, "show Detail", pendingIntentYes);
        } else {
            Intent yesReceive = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);// test
            yesReceive.setAction(YES_ACTION);
            PendingIntent pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
            notif.addAction(R.drawable.ic_local_phone_black_24dp, "show Detail", pendingIntentYes);
        }


        Intent yesReceive2 = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        yesReceive2.setAction(STOP_ACTION);
        PendingIntent pendingIntentYes2 = PendingIntent.getBroadcast(this, 12345, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.addAction(R.drawable.ic_access_time_black_24dp, "cancel", pendingIntentYes2);


        nm.notify(10, notif.getNotification());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(String detail) {
        Log.e("show_Notification", "" + detail);
        String actionType = "";
        if (detail.contains("Request")) {
            actionType = "Request";
        } else {
            actionType = "YES";
        }

        Intent intent = new Intent(MainActivity.this, notificationReciver.class);
        intent.putExtra("action", actionType);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String CHANNEL_ID = "MYCHANNEL";

        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_HIGH);
        Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentText(detail)
                .setContentTitle("App Check")
                .setStyle(new Notification.BigTextStyle()
                        .bigText(detail)
                        .setBigContentTitle(" ")
                        .setSummaryText(""))
                .setContentIntent(pendingIntent).setAutoCancel(true)
                .addAction(android.R.drawable.sym_action_chat, "Show detail", pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_add)
                .setOngoing(true)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1, notification);


    }

    void addAccountButton() {
        addAccountDialog = new Dialog(MainActivity.this);
        addAccountDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addAccountDialog.setContentView(R.layout.dialog_add_account);
        addAccountDialog.setCancelable(false);

        final TextInputEditText inputEditText = addAccountDialog.findViewById(R.id.dialog_addAccount_account);
        TextView close = addAccountDialog.findViewById(R.id.dialog_add_close);
        TextView add = addAccountDialog.findViewById(R.id.dialog_addAccount_add);
        TextView scan = addAccountDialog.findViewById(R.id.dialog_addAccount_scan);
        LinearLayout linearLayout = addAccountDialog.findViewById(R.id.dialog_addAccount_linear);
        if (language.equals("ar")) {
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//addAccountOb

                if (!TextUtils.isEmpty(inputEditText.getText().toString())) {
                    // TODO add account

                    NewAccount acc = new NewAccount("jj", inputEditText.getText().toString(), bankNo, "0");
                    addAccountOb = acc.getJSONObject(userNo);
                    new AddAccount().execute();

                } else
                    Toast.makeText(MainActivity.this, "Please add account first or scan cheque QR barcode!", Toast.LENGTH_SHORT).show();
            }
        });


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputEditTextTemp = inputEditText;
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAdd = false;
                addAccountDialog.dismiss();
            }
        });
        //TODO add dialog function
        addAccountDialog.show();
    }

    void init() {
        imageView = findViewById(R.id.profile_image);
        scanBarcode = findViewById(R.id.scanBarcode);
        notifyLayout = findViewById(R.id.notifyLayout);
        requestLayout = findViewById(R.id.requestLayout);

        notifyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, AlertScreen.class);
                startActivity(in);

            }
        });
        notification_btn = findViewById(R.id.button_notification);
        notification_btn.setVisibility(View.INVISIBLE);
        button_request = findViewById(R.id.button_request);
        button_request.setVisibility(View.INVISIBLE);


        toolbar = findViewById(R.id.main_toolbar);
        request = findViewById(R.id.main_request);
        requestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, RequestCheque.class);
                startActivity(in);

            }
        });

        dbHandler = new DatabaseHandler(this);
        database = dbHandler.getWritableDatabase();
        usernameTv = findViewById(R.id.main_username);
        usernameTv.setText(MainActivity.this.getResources().getString(R.string.welcome) + "  " + username);
        cashierCheque = findViewById(R.id.main_cashier);
        jerro = findViewById(R.id.main_jero);
        wallet = findViewById(R.id.main_wallet);
        arrayListRow = new ArrayList<>();
        arrayListRowFirst = new ArrayList<>();
        requestList = new ArrayList<>();
        requestList_Tow = new ArrayList<>();
        notifiList1 = new ArrayList<>();
        notifiList = new ArrayList<>();
        checkInfoList = new ArrayList<>();

        recyclerViews = (RecyclerView) findViewById(R.id.res);
        setSupportActionBar(toolbar);
        setTitle("");
        message = findViewById(R.id.messages);

        addAccount = findViewById(R.id.main_addAccount);
        logHistory = findViewById(R.id.main_history);
        Editing = findViewById(R.id.Editing);

        drawerLayout = findViewById(R.id.main_drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameNavigation = navigationView.getHeaderView(0).findViewById(R.id.nav_header_textView);
//        View headerView = navigationView.getHeaderView(0);
//        TextView navUsername = (TextView) headerView.findViewById(R.id.navUsername);
//        navUsername.setText("Your Text Here");

        infoUser = dbHandler.getActiveUserInfo();
        phoneNo = infoUser.getUsername();
        fullUsername = infoUser.getFirstName() + " " + infoUser.getFourthName();
        usernameNavigation.setText(fullUsername);

    }

    void checkLanguage() {
        if (language.equals("ar")) {
            Editing.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_send_black_24dp), null);
            logHistory.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_access_time_black_24dp), null);
            request.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_grain), null);
            cashierCheque.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_grain), null);
            jerro.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_autorenew_black_24dp), null);
            wallet.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_account_balance_wallet_black_24dp), null);

        } else {
            Editing.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_send_black_24dp), null
                    , null, null);
            logHistory.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_access_time_black_24dp), null
                    , null, null);
            request.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_grain), null, null, null);
            cashierCheque.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_grain), null, null, null);
            jerro.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_autorenew_black_24dp), null, null, null);
            wallet.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_account_balance_wallet_black_24dp), null, null, null);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    //TextView itemCodeText, int swBarcode
    public void readBarCode() {

//        barCodTextTemp = itemCodeText;
        Log.e("barcode_099", "in");
        IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();


    }

//    void checkLanguage() {
//        if (language.equals("ar")) {
//            .setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_person_black_24dp), null);
//            phoneNo.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_local_phone_black_24dp), null);
//            address.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_location_on_black_24dp), null);
//
//
//        } else {
//            natonalNo.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_person_black_24dp), null
//                    , null, null);
//            phoneNo.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_local_phone_black_24dp), null
//                    , null, null);
//            address.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_location_on_black_24dp), null
//                    , null, null);
//
//        }
//
//        animation = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.move_to_right);
//        natonalNo.startAnimation(animation);
//
//        animation = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.move_to_right);
//        linearLayout.startAnimation(animation);
//
//        animation = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.move_to_right);
//        date_text.startAnimation(animation);
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Log.d("MainActivity", "cancelled scan");
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.cancel));
            } else {
                if (barcodeDialog != null)
                    barcodeDialog.dismiss();
                Log.d("MainActivity", "Scanned");
                Log.e("resultcontent", "" + Result.getContents());
                Toast.makeText(this, "Scan ___" + Result.getContents(), Toast.LENGTH_SHORT).show();

                String ST = Result.getContents();
                arr = ST.split(";");

                bankNo = arr[1];
                accCode = arr[3];

                if (isAdd)
                    inputEditTextTemp.setText(accCode);
                else
                    new JSONTask().execute();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void openEditerCheck() {

        Intent intent = new Intent(MainActivity.this, EditerCheackActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.e("id", " " + id);
        switch (id) {
            case R.id.menu_verification: {
                isAdd = false;
                checkChequeValidation();
            }
            break;
            case R.id.menu_request: {
                Intent in = new Intent(MainActivity.this, RequestCheque.class);
                startActivity(in);


            }
            break;
            case R.id.menu_wallet: {
                Intent intentWallet = new Intent(MainActivity.this, JeroActivity.class);
                intentWallet.putExtra("wallet", "wallet");
                startActivity(intentWallet);
            }
            break;
            case R.id.menu_giro: {
                Intent intentGiro = new Intent(MainActivity.this, JeroActivity.class);
                startActivity(intentGiro);

            }
            break;
            case R.id.menu_cashier_cheque: {
                Intent intentCashier = new Intent(MainActivity.this, CashierCheque.class);
                startActivity(intentCashier);

            }
            break;
            case R.id.menu_profile: {
                Intent intent = new Intent(MainActivity.this, ProfilePage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            break;
            case R.id.menu_signout: {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }
            break;
            case R.id.menu_about: {
                Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.about_page);
                dialog.show();

                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            }
            break;
            case R.id.menu_tracking: {
                Intent intent = new Intent(MainActivity.this, OwnerCheques.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
            break;

            case R.id.menu_lost_steal: {
                Intent intent = new Intent(MainActivity.this, LostAndStealing.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
            break;
        }

        return true;
    }

    void checkChequeValidation() {
        barcodeDialog = new Dialog(MainActivity.this);
        barcodeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        barcodeDialog.setContentView(R.layout.check_validation_layout);

        TextView scan = barcodeDialog.findViewById(R.id.checkValidation_scanBarcode);
        ImageView close = barcodeDialog.findViewById(R.id.checkValidation_close);
        LinearLayout headerLinear = barcodeDialog.findViewById(R.id.checkValidation_headerLinear);
        TextView haveAProblem = barcodeDialog.findViewById(R.id.checkValidation_help);
        TextView scanTV = barcodeDialog.findViewById(R.id.checkValidation_scanLinear);

        final LinearLayout serialLinear = barcodeDialog.findViewById(R.id.checkValidation_serial_linear);
        final TextInputEditText serial = barcodeDialog.findViewById(R.id.checkValidation_serial);
        TextView check = barcodeDialog.findViewById(R.id.checkValidation_check);
        serialLinear.setVisibility(View.GONE);

        haveAProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serialLinear.getVisibility() == View.VISIBLE) {
                    serialLinear.setVisibility(View.GONE);

                } else {
                    serialLinear.setVisibility(View.VISIBLE);
                    serial.setError(null);
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(serial.getText().toString())) {
                    serial.setError(null);
                    new Presenter(MainActivity.this).checkBySerial(serial.getText().toString().toUpperCase(), null, MainActivity.this, null);
                    barcodeDialog.dismiss();
                } else {
                    serial.setError("Required");
                }

            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setPrompt("SCAN");
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barcodeDialog.dismiss();
            }
        });

        Log.e("checkLang", language);
        if (language.equals("ar")) {
            headerLinear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            check.setGravity(Gravity.RIGHT);

            haveAProblem.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_help), null);
            scanTV.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_phone), null);

        } else {
            headerLinear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            check.setGravity(Gravity.LEFT);

            haveAProblem.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_help), null
                    , null, null);
            scanTV.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_phone), null
                    , null, null);

        }

        barcodeDialog.show();
    }

    public void showValidationDialog(boolean check, String customerName, String BankNo, String accountNo, String chequeNo) {
        if (check) {
            final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
            new LocaleAppUtils().changeLayot(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_after_validation);
            dialog.setCancelable(false);

            bankNameTV = dialog.findViewById(R.id.dialog_validation_bankName);
            chequeWriterTV = dialog.findViewById(R.id.dialog_validation_chequeWriter);
            chequeNoTV = dialog.findViewById(R.id.dialog_validation_chequeNo);
            accountNoTV = dialog.findViewById(R.id.dialog_validation_accountNo);
            okTV = dialog.findViewById(R.id.dialog_validation_ok);
            cancelTV = dialog.findViewById(R.id.dialog_validation_cancel);


            if (language.trim().equals("ar")) {
                LocaleAppUtils.setLocale(new Locale("ar"));
                LocaleAppUtils.setConfigChange(MainActivity.this);
                chequeWriterTV.setText(customerName);
                accountNoTV.setText(convertToArabic(accountNo));
                chequeNoTV.setText(convertToArabic(chequeNo));
            } else {
                LocaleAppUtils.setLocale(new Locale("en"));
                LocaleAppUtils.setConfigChange(MainActivity.this);
                chequeWriterTV.setText(customerName);
                accountNoTV.setText(accountNo);
                chequeNoTV.setText(chequeNo);
            }
            okTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkLanguage();
                    dialog.dismiss();

                }
            });

            cancelTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        } else {
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("WARNING")
                    .setContentText("Invalidate cheque!")
                    .setCancelText("Close").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();

                }
            }).show();

        }

    }


    public String convertToArabic(String value) {
        String newValue = (((((((((((value + "").replaceAll("1", "١")).replaceAll("2", "٢")).replaceAll("3", "٣")).replaceAll("4", "٤")).replaceAll("5", "٥")).replaceAll("6", "٦")).replaceAll("7", "٧")).replaceAll("8", "٨")).replaceAll("9", "٩")).replaceAll("0", "٠"));
        Log.e("convertToArabic", value + "      " + newValue);
        return newValue;
    }


    void showSweetDialog(boolean check, String customerName, String BankNo, String accountNo) {
        if (check) {
            String message = "Cheque is validate \n" + "Customer Name :" + customerName + " \n" + "Bank Name : " + "بنك الاردن " + "\n" + "Account No : " + accountNo + "\n";
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Successful")
                    .setContentText(message)
                    .setConfirmText("Next")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    }).setCancelText("Cancel").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();

                }
            })
                    .show();
        } else {
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("WARNING")
                    .setContentText("Invalidate cheque!")
                    .setConfirmText("Ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })

                    .show();

        }
    }

    static class CViewHolderForbar extends RecyclerView.ViewHolder {

        TextView ItemName,AccType;
        ImageView itemImage;
        LinearLayout layBar;

        public CViewHolderForbar(@NonNull View itemView) {
            super(itemView);
            ItemName = itemView.findViewById(R.id.textbar);
            layBar = itemView.findViewById(R.id.layBar);
            itemImage = itemView.findViewById(R.id.imgbar);
            AccType=itemView.findViewById(R.id.AccType);
        }
    }

    class TestAdapterForbar extends RecyclerView.Adapter<MainActivity.CViewHolderForbar> {
        Context context;
        List<NewAccount> list;
//DatabaseHandler db;

        public TestAdapterForbar(Context context, List<NewAccount> list) {
            this.context = context;
            this.list = list;
//        db=new DatabaseHandler(this.context);
        }

        @NonNull
        @Override
        public MainActivity.CViewHolderForbar onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.bar_item, viewGroup, false);
            return new MainActivity.CViewHolderForbar(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final MainActivity.CViewHolderForbar cViewHolder, final int i) {
            cViewHolder.ItemName.setText(list.get(i).getAccountNo().substring(1));
            switch (list.get(i).getBank()) {

                case "004":
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.jordan_bank));
                    break;
                case "009":
                    cViewHolder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.cairo_amman_bank));
                    break;
            }
            String aacType="";
            try {
                 aacType = list.get(i).getAccountNo().substring( list.get(i).getAccountNo().length()-2, list.get(i).getAccountNo().length()-1);
           Log.e("aacType",""+aacType);
            }catch (Exception e){
                aacType="0";
            }

            switch (aacType){

                case "2":
                    cViewHolder.AccType.setText(context.getResources().getString(R.string.Join));
                    break;
                case "0":
                    cViewHolder.AccType.setText(context.getResources().getString(R.string.individual));
                    break;
            }


//            cViewHolder.itemImage.setBackgroundResource(getImage(pic2.get(i)));
            cViewHolder.layBar.setTag("" + i);

            final boolean[] longIsOpen = {false};
            cViewHolder.layBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!longIsOpen[0]) {
//                        Toast.makeText(context, "id = " + v.getTag(), Toast.LENGTH_SHORT).show();
                        Intent LogHistoryIntent = new Intent(MainActivity.this, LogHistoryActivity.class);
                        LogHistoryIntent.putExtra("AccountNo", list.get(i).getAccountNo());
                        watch = "0";
                        startActivity(LogHistoryIntent);
                        longIsOpen[0] = false;
                    }

                }
            });

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            cViewHolder.layBar.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longIsOpen[0] = true;
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING")
                            .setContentText(MainActivity.this.getResources().getString(R.string.deleteAccount) + " ( " + list.get(i).getAccountNo() + " ) !")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    dbHandler.deleteAccount(list.get(i).getAccountNo());
                                    AccountNoDelete = list.get(i).getAccountNo();
                                    new DelAccount().execute();
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })

                            .show();


                    return false;
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
//            return Integer.MAX_VALUE;
        }
    }

    //_________________________________________________getDataFromServer_______________

    private class GetAllAccount extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importstor));

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }

                picforbar.clear();
                String link = serverLink + "GetAccounts";

                //?ACCCODE=4014569990011000&MOBNO=&WHICH=0
                String data = "MOBILENO=" + URLEncoder.encode(userNo, "UTF-8");

                URL url = new URL(link);
                Log.e("getAccount,3 ", serverLink + "   " + link + "   " + data + "   " + userNo);

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
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);


            if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"OK")) {
//                Log.e("GetAccSuccess", "****Success");

//
                try {

                    JSONObject parentArray = new JSONObject(JsonResponse);
                    JSONArray parentInfo = parentArray.getJSONArray("INFO");


                    for (int i = 0; i < parentInfo.length(); i++) {
                        JSONObject finalObject = parentInfo.getJSONObject(i);

                        NewAccount obj = new NewAccount();

                        //[{"ROWID":"AAAp0DAAuAAAAC0AAC","BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000","IBANNO":"","CUSTOMERNM":"الخزينة والاستثمار","QRCODE":"","SERIALNO":"720817C32F164968","CHECKISSUEDATE":"28\/06\/2020 10:33:57","CHECKDUEDATE":"21\/12\/2020","TOCUSTOMERNM":"ALAA SALEM","AMTJD":"100","AMTFILS":"0","AMTWORD":"One Handred JD","TOCUSTOMERMOB":"0798899716","TOCUSTOMERNATID":"123456","CHECKWRITEDATE":"28\/06\/2020 10:33:57","CHECKPICPATH":"E:\\00400991014569990011000390144.png","TRANSSTATUS":""}]}

                        obj.setRowId(finalObject.getString("ROWID"));
//                        obj.setAccountNo(finalObject.getString("MOBILENO"));
                        obj.setAccountNo(finalObject.getString("ACCOUNTNO"));
                        obj.setBank(finalObject.getString("ACCOUNTBANK"));
                        picforbar.add(obj);

                    }

                    showAllDataAccount();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//{"StatusCode" : 18,"StatusDescreption":"Accounts Data not found"}
            } else if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"Accounts Data not found")) {
                showAllDataAccount();

//                if(!isAssetsIn.equals("1")) {
//                    if (pd != null) {
//                        pd.dismiss();
//                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
//                                .setTitleText("Can't Save")
//                                .setContentText(MainActivity.this.getResources().getString(R.string.faildstore))
//                                .show();
//                    }
//                }else{
//                    pd.dismiss();
//                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.ops))
//                            .setContentText(context.getResources().getString(R.string.faildstore))
//                            .show();
//                    new SyncGetAssest().execute();
//                }
            }
//            progressDialog.dismiss();

        }
    }

    private class AddAccount extends AsyncTask<String, String, String> {
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

                String link = serverLink + "AddAcc";

                //?ACCCODE=4014569990011000&MOBNO=&WHICH=0
                String data = "INFO=" + URLEncoder.encode(addAccountOb.toString(), "UTF-8");


                URL url = new URL(link);
                Log.e("getAccount,3 ", serverLink + "   " + link + "   " + data + "   " + userNo);

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
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"OK")) {
                Log.e("GetAccSuccess", "****Success");

                if (addAccountDialog != null)
                    addAccountDialog.dismiss();

                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(MainActivity.this.getResources().getString(R.string.save))
                        .setContentText(MainActivity.this.getResources().getString(R.string.save_success))
                        .show();

                new GetAllAccount().execute();
            } else if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"Account alreay exisit.")) {
//
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(MainActivity.this.getResources().getString(R.string.cantSave))
                        .setContentText(MainActivity.this.getResources().getString(R.string.already_exist))
                        .show();

            } else if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"Error in saving Accounts")) {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(MainActivity.this.getResources().getString(R.string.cantSave))
                        .setContentText(MainActivity.this.getResources().getString(R.string.error_in_save))
                        .show();
            } else if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"This user not own this account.")) {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(MainActivity.this.getResources().getString(R.string.WARNING))
                        .setContentText(MainActivity.this.getResources().getString(R.string.notforYou))//This user not own this account.
                        .show();
            }

        }
    }

    private class DelAccount extends AsyncTask<String, String, String> {
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

                String link = serverLink + "DelAcc";

                //?ACCCODE=4014569990011000&MOBNO=&WHICH=0
                String data = "MOBILENO=" + URLEncoder.encode(userNo, "UTF-8") + "&" +
                        "ACCOUNTNO=" + URLEncoder.encode(AccountNoDelete, "UTF-8");

                URL url = new URL(link);
                Log.e("DelAccount,3 ", serverLink + "   " + link + "   " + data + "   " + userNo);

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
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);


            if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"OK")) {
                Log.e("GetAccSuccess", "****Success");

                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(MainActivity.this.getResources().getString(R.string.success_del))
                        .setContentText(MainActivity.this.getResources().getString(R.string.del_acc_success))
                        .show();

                new GetAllAccount().execute();
            } else if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"Error in deleting account.")) {
//
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(MainActivity.this.getResources().getString(R.string.can_del))
                        .setContentText(MainActivity.this.getResources().getString(R.string.error_del))
                        .show();
            }
//            } else if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"Error in saving Accounts")) {
//                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText("Can't Save")
//                        .setContentText("Error in saving Accounts!")
//                        .show();
//            }

        }
    }

    //    public class GetAllCheck_JSONTask extends AsyncTask<String, String, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                infoUser=dbHandler.getActiveUserInfo();
//                phoneNo=infoUser.getUsername();
//
//                String JsonResponse = null;
//                HttpClient client = new DefaultHttpClient();
//                HttpPost request = new HttpPost();
////                http://localhost:8082/GetAllTempCheck?CUSTMOBNO=0798899716&CUSTIDNO=123456
//                request.setURI(new URI(serverLink + "GetLog?"));
//
//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//                nameValuePairs.add(new BasicNameValuePair("ACCCODE", "0"));
//
//                nameValuePairs.add(new BasicNameValuePair("MOBNO", phoneNo));// test
//                Log.e("editingmain ", phoneNo);
//                nameValuePairs.add(new BasicNameValuePair("WHICH", "1"));//  wich =1 =====> based on phone number //wich =0 =====> based on account number
//                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
//
//                HttpResponse response = client.execute(request);
//
//                BufferedReader in = new BufferedReader(new
//                        InputStreamReader(response.getEntity().getContent()));
//
//                StringBuffer sb = new StringBuffer("");
//                String line = "";
//
//                while ((line = in.readLine()) != null) {
//                    sb.append(line);
//                }
//
//                in.close();
//
//                JsonResponse = sb.toString();
////                Log.e("tagMainActivityNotifi", "" + JsonResponse);
//
//                return JsonResponse;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            if (s != null) {
//                if (s.contains("\"StatusDescreption\":\"OK\"")) {
//                    JSONObject jsonObject = null;
//                    try {
//
//                        arrayListRow.clear();
//                        arrayListRowFirst.clear();
//                        notifiList.clear();
//                        checkInfoList.clear();
//                        jsonObject = new JSONObject(s);
//
//                        JSONArray notificationInfo = jsonObject.getJSONArray("INFO");
//                        for (int i = 0; i < notificationInfo.length(); i++) {
//
//                            JSONObject infoDetail = notificationInfo.getJSONObject(i);
//                            ChequeInfo chequeInfo = new ChequeInfo();
//                            chequeInfo.setRowId(infoDetail.get("ROWID1").toString());
////                           if( !dbHandler.getRowID(chequeInfo.getRowId()).equals(""))
//////                           {
//
//
//                            chequeInfo.setIsJoin(infoDetail.getString("ISJOIN"));
//                            chequeInfo.setTransType(infoDetail.getString("TRANSSTATUS"));
//
//
//                            chequeInfo.setChequeNo(infoDetail.get("CHECKNO").toString());
//                            int chNo=Integer.parseInt(chequeInfo.getChequeNo());
//
//                            chequeInfo.setUserName(infoDetail.getString("USERNO"));
//                            chequeInfo.setToCustomerMobel(infoDetail.get("TOCUSTOMERMOB").toString());
////
//
//                            chequeInfo.setStatus(infoDetail.getString("STATUS"));// Recive=== 1
//                            Log.e("setTransType", "\t" + chequeInfo.getTransType() + "\t setStatus" + chequeInfo.getStatus());
//                            if ((chequeInfo.getTransType().equals("0") && chequeInfo.getStatus().equals("1")) ||
//                                    (chequeInfo.getStatus().equals("0") && !chequeInfo.getTransType().equals("0")&&(chequeInfo.getIsJoin().equals("0")))
//                                    ||(chequeInfo.getIsJoin().equals("1")&&chequeInfo.getTransType().equals("100")&& !chequeInfo.getToCustomerMobel().equals(phoneNo)&&!chequeInfo.getUserName().equals(phoneNo))
//                                    ||(chequeInfo.getIsJoin().equals("1")&& !chequeInfo.getTransType().equals("100")&& !chequeInfo.getToCustomerMobel().equals(phoneNo) ))// Pending and Reciver
//                            {
//
//
//                                notification notifi = new notification();
//                                notifi.setSource(infoDetail.get("CUSTOMERNM").toString());
//                                notifi.setDate(infoDetail.get("CHECKDUEDATE").toString());
//                                notifi.setAmount_check(infoDetail.get("AMTJD").toString());
//                                //**********************************************************************
//                                chequeInfo.setRowId(infoDetail.get("ROWID1").toString());
//                                chequeInfo.setToCustomerNationalId(infoDetail.get("TOCUSTOMERNATID").toString());
//
//                                chequeInfo.setCustName(infoDetail.get("CUSTOMERNM").toString());
//                                chequeInfo.setChequeData(infoDetail.get("CHECKDUEDATE").toString());
//                                chequeInfo.setToCustomerName(infoDetail.get("TOCUSTOMERNM").toString());
//                                chequeInfo.setQrCode(infoDetail.get("QRCODE").toString());
//                                chequeInfo.setMoneyInDinar(infoDetail.get("AMTJD").toString());
//                                chequeInfo.setCustomerWriteDate(infoDetail.get("CHECKWRITEDATE").toString());
//                                chequeInfo.setMoneyInWord(infoDetail.get("AMTWORD").toString());
//                                chequeInfo.setMoneyInFils(infoDetail.getString("AMTFILS"));
//                                chequeInfo.setBankName(infoDetail.get("BANKNM").toString());
//                                chequeInfo.setChequeNo(infoDetail.get("CHECKNO").toString());
//                                chequeInfo.setCustName(infoDetail.get("CUSTOMERNM").toString());
//                                chequeInfo.setSerialNo(infoDetail.get("SERIALNO").toString());
//                                chequeInfo.setBranchNo(infoDetail.get("BRANCHNO").toString());
//                                chequeInfo.setAccCode(infoDetail.get("ACCCODE").toString());
//                                chequeInfo.setIbanNo(infoDetail.get("IBANNO").toString());
//                                chequeInfo.setBankNo(infoDetail.get("BANKNO").toString());
//                                chequeInfo.setCheckIsSueDate(infoDetail.get("CHECKISSUEDATE").toString());
//                                chequeInfo.setCheckDueDate(infoDetail.get("CHECKDUEDATE").toString());
//                                chequeInfo.setTransType(infoDetail.getString("TRANSSTATUS"));
//                                chequeInfo.setStatus(infoDetail.getString("STATUS"));
//
//                                chequeInfo.setISBF(infoDetail.getString("ISFB"));
//                                chequeInfo.setISCO(infoDetail.getString("ISCO"));
//                                chequeInfo.setNoteCheck(infoDetail.getString("NOTE"));
//                                chequeInfo.setCompanyName(infoDetail.getString("COMPANY"));
//                                chequeInfo.setResonOfreject(infoDetail.getString("RJCTREASON"));
//                                chequeInfo.setToCustName(infoDetail.getString("CUSTNAME"));
//                                chequeInfo.setToCustFName(infoDetail.getString("CUSTFNAME"));
//                                chequeInfo.setToCustGName(infoDetail.getString("CUSTGNAME"));
//                                chequeInfo.setToCustFamalyName(infoDetail.getString("CUSTFAMNAME"));
//
//                                chequeInfo.setTransSendOrGero(infoDetail.getString("TRANSTYPE"));// 0-----> send  // 1-------> gero
//
//                                chequeInfo.setIsJoin(infoDetail.getString("ISJOIN"));
//                                chequeInfo.setJOIN_FirstMOB   (infoDetail.getString("JOINFMOB"));
//                                chequeInfo.setJOIN_SecondSMOB  (infoDetail.getString("JOINSMOB"));
//                                chequeInfo.setJOIN_TheredMOB  (infoDetail.getString("JOINTMOB"));
//
//                                chequeInfo.setJOIN_F_STATUS(infoDetail.getString("JOINFSTATUS"));
//                                chequeInfo.setJOIN_F_REASON(infoDetail.getString("JOINFREASON"));
//                                chequeInfo.setJOIN_S_STATUS(infoDetail.getString("JOINSSTATUS"));
//                                chequeInfo.setJOIN_S_REASON(infoDetail.getString("JOINSREASON"));
//
//
//
//                                chequeInfo.setJOIN_T_STATUS(infoDetail.getString("JOINTSTATUS"));
//                                chequeInfo.setJOIN_T_REASON(infoDetail.getString("JOINTREASON"));
//                                if(!dbHandler.getLastTransTypeByChequeNo(chNo).equals( chequeInfo.getTransType()))
//                                {
//                                    Log.e("YES",""+dbHandler.getLastTransTypeByChequeNo(chNo));
//                                    dbHandler.addNotificationInfo(chequeInfo);
//                                    notification_btn.setVisibility(View.VISIBLE);
//                                    ShowNotifi_detail("check",Integer.parseInt(chequeInfo.getTransType()),chNo+"");
//
//                                }
////                                else {
//
//                                   if(chequeInfo.getIsJoin().equals("1"))
//                                   {
//
//
//                                       if( chequeInfo.getJOIN_FirstMOB().equals(phoneNo))
//                                       {
//                                           orderMobil=1;
//                                           statuseJoin=chequeInfo.getJOIN_F_STATUS();
//                                           if(!dbHandler.getLastStateByChequeNo(chNo,2).equals( chequeInfo.getJOIN_S_STATUS())||
//                                                   !dbHandler.getLastStateByChequeNo(chNo,3).equals( chequeInfo.getJOIN_T_STATUS()))
//                                           {
//                                               dbHandler.addNotificationInfo(chequeInfo);
//                                               notification_btn.setVisibility(View.VISIBLE);
//                                               ShowNotifi_detail("check",Integer.parseInt(chequeInfo.getTransType()),chNo+"");
//                                           }
//                                       }
//
//                                       if( chequeInfo.getJOIN_SecondSMOB().equals(phoneNo))
//                                       {
//                                           orderMobil=2;
//                                           statuseJoin=chequeInfo.getJOIN_S_STATUS();
//                                           if(!dbHandler.getLastStateByChequeNo(chNo,1).equals( chequeInfo.getJOIN_F_STATUS())||
//                                                   !dbHandler.getLastStateByChequeNo(chNo,3).equals( chequeInfo.getJOIN_T_STATUS()))
//                                           {
//                                               dbHandler.addNotificationInfo(chequeInfo);
//                                               notification_btn.setVisibility(View.VISIBLE);
//                                               ShowNotifi_detail("check",Integer.parseInt(chequeInfo.getTransType()),chNo+"");
//                                           }
//                                       }
//
//
//                                       if( chequeInfo.getJOIN_TheredMOB().equals(phoneNo))
//                                       {
//                                           orderMobil=3;
//                                           statuseJoin=chequeInfo.getJOIN_T_STATUS();
//                                           if(!dbHandler.getLastStateByChequeNo(chNo,1).equals( chequeInfo.getJOIN_F_STATUS())||
//                                                   !dbHandler.getLastStateByChequeNo(chNo,2).equals( chequeInfo.getJOIN_S_STATUS()))
//                                           {
//                                               dbHandler.addNotificationInfo(chequeInfo);
//                                               notification_btn.setVisibility(View.VISIBLE);
//                                               ShowNotifi_detail("check",Integer.parseInt(chequeInfo.getTransType()),chNo+"");
//                                           }
//                                       }
//
//                                       String state= dbHandler.getLastStateByChequeNo(chNo,orderMobil);
//                                       Log.e("getLastStateByChequeNo",""+state);
////                                       if(!dbHandler.getLastStateByChequeNo(chNo,orderMobil).equals( statuseJoin))
////                                       {
////                                           dbHandler.addNotificationInfo(chequeInfo);
////                                           notification_btn.setVisibility(View.VISIBLE);
////                                           ShowNotifi_detail("check",Integer.parseInt(chequeInfo.getTransType()),chNo+"");
////                                       }
////
////
//
//                                   }
//
//
//                                checkInfoList.add(chequeInfo);
//                                arrayListRow.add(chequeInfo.getRowId());
//
//
//                            }
////                           }
//                        }
//                        Log.e("arrayListRow",""+arrayListRow.size());
//
//
//                        Set<String> set_tow = new HashSet<String>();
//                        set_tow.addAll(arrayListRow);
//                        Set<String> set = null;
//                        try {
//
//                          set = sharedPreferences.getStringSet("DATE_LIST", null);
//                            Log.e("sharedPreferences",""+set.size()+set.toString());
//                        }
//                        catch (Exception e)
//                        {
//
//                        }
//
//
//
//                        if (set != null) {
////
////                            set = sharedPreferences.getStringSet("DATE_LIST", set_tow);
//                            arrayListRowFirst.addAll(set);
//
//                            int countFirst = arrayListRowFirst.size();
//                            if (arrayListRow.size() < countFirst)//there are update new data is less than old data
//                            {
//                                Log.e("olddataGreater", "countFirst" + countFirst);
//
//                                for (int h = 0; h < arrayListRow.size(); h++) {
//                                    int index = arrayListRowFirst.indexOf(arrayListRow.get(h));
//                                    if (index == -1) {
//                                        arrayListRowFirst.add(arrayListRow.get(h));
//                                        Log.e("arrayListRowYES", "" + arrayListRow.get(h));
//
//                                    }
//
//                                }
//
//                                if (countFirst < arrayListRowFirst.size())// new data
//                                {
//                                    Log.e("getTransType-new",""+checkInfoList.get(0).getTransType().equals("100"));
////                                    ShowNotifi("check");
//
//                                    int stat=-1;
//                                    if(checkInfoList.get(0).getStatus().equals("0")) {
//                                        if (checkInfoList.get(0).getTransType().equals("1")) {
//                                            stat=1;
//
//                                        }
//                                        else {
//                                            if((checkInfoList.get(0).getTransType().equals("2"))||(checkInfoList.get(0).getTransType().equals("200")))
//                                            {
//                                                stat=2;
//                                            }
//                                            if(checkInfoList.get(0).getTransType().equals("100"))
//                                            {
//
//                                                stat=100;
//                                            }
//
//                                        }
//                                    }
//                                    else {
//                                        stat=0;
//
//                                    }
//                                    notification_btn.setVisibility(View.VISIBLE);
//                                    ShowNotifi_detail("check",stat,checkInfoList.get(0).getChequeNo());
//
//
//
//
//                                } else {
//
//
//
//                                }
//
//                            }//********************************************
//                            else {
//                                if (arrayListRow.size() > countFirst)// new data
//                                {
//                                    Log.e("NewGreater", "countFirst" + countFirst);
//                                    Log.e("getTransType",""+checkInfoList.get(0).getTransType().equals("100"));
////                                    ShowNotifi("check");
//                                    int stat=-1;
//                                    if(checkInfoList.get(0).getStatus().equals("0")) {
//                                        if (checkInfoList.get(0).getTransType().equals("1")) {
//                                            stat=1;
//
//                                        }
//                                        else {
//                                            if((checkInfoList.get(0).getTransType().equals("2"))||(checkInfoList.get(0).getTransType().equals("200")))
//                                            {
//                                                stat=2;
//                                            }
//                                            if(checkInfoList.get(0).getTransType().equals("100"))
//                                            {
//                                                stat=100;
//                                            }
//
//                                        }
//                                    }
//                                    else {
//                                        stat=0;
//
//                                    }
//                                    notification_btn.setVisibility(View.VISIBLE);
//                                    ShowNotifi_detail("check",stat,checkInfoList.get(0).getChequeNo());
//
//
//
//
//
//                                } else {
//                                    if (arrayListRow.size() == countFirst)// equal size
//                                    {
//                                        Log.e("arrayListRow", "== hereeee");
//
//                                        for (int h = 0; h < arrayListRow.size(); h++) {
//                                            int index = arrayListRowFirst.indexOf(arrayListRow.get(h));
//                                            if (index == -1) {
//                                                arrayListRowFirst.add(arrayListRow.get(h));
//
//
//                                            }
//
//                                        }
//
//                                        if (countFirst < arrayListRowFirst.size())// new data
//                                        {
////                                            ShowNotifi("check");
//
//
//                                            int stat = -1;
//                                            if (checkInfoList.get(0).getStatus().equals("0")) {
//                                                if (checkInfoList.get(0).getTransType().equals("1")) {
//                                                    stat = 1;
//
//                                                } else {
//                                                    if((checkInfoList.get(0).getTransType().equals("2"))||(checkInfoList.get(0).getTransType().equals("200")))
//                                                    {
//                                                        stat=2;
//                                                    }
//                                                    if(checkInfoList.get(0).getTransType().equals("100"))
//                                                    {
//                                                        stat=100;
//                                                    }
//
//                                                }
//                                            } else {
//                                                stat = 0;
//
//                                            }
//                                            notification_btn.setVisibility(View.VISIBLE);
//                                            ShowNotifi_detail("check", stat, checkInfoList.get(0).getChequeNo());
//
//                                        }
//
//
//                                    } else {
//
//
////                                                fillListNotification(notificationArrayListTest);
//
//                                    }
//
//                                }
//
//                            }
//
//
//                        } else {//empty shared preference
//
//                        notification_btn.setVisibility(View.VISIBLE);
//                        try {
//                            transtype=Integer.parseInt(checkInfoList.get(0).getTransType());
//                        }
//                        catch (Exception e)
//                        {}
//                        ShowNotifi_detail("check",transtype,checkInfoList.get(0).getChequeNo());
//                        }
//
//
////                        Set<String> set_tow = new HashSet<String>();
////                        set_tow.addAll(arrayListRow);
////                        Log.e("Empty", "" + arrayListRow.size());
//                        editor = sharedPreferences.edit();
//                        editor.putStringSet("DATE_LIST", set_tow);
//                        editor.apply();
//
//
//                        new  GetAllRequestToUser_JSONTask().execute();
////                        new GetAllRequestFromUser_JSONTask().execute();
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
////                    INFO
////                    Log.e("tag", "****Success" + s.toString());
//                } else {
//                    new  GetAllRequestToUser_JSONTask().execute();
//                    Log.e("tagMain", "****Failed to export data"+s.toString());
//                }
//            } else {
//                new  GetAllRequestToUser_JSONTask().execute();
//                Log.e("tagMain", "****Failed to export data Please check internet connection");
//            }
//        }
//    }
    public class GetNotification_JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                infoUser = dbHandler.getActiveUserInfo();
                phoneNo = infoUser.getUsername();

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
//                http://localhost:8082/GetAllTempCheck?CUSTMOBNO=0798899716&CUSTIDNO=123456
                // http://localhost:8082/GetNotifications?MOBNO=??
                request.setURI(new URI(serverLink + "GetNotifications?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);


                nameValuePairs.add(new BasicNameValuePair("MOBNO", phoneNo));// test
                nameValuePairs.add(new BasicNameValuePair("ISREQ", "0"));// test

                Log.e("editingmain ", phoneNo);
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
                Log.e("tagMainActivityNotifi", "" + JsonResponse);

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

                        arrayListRow.clear();
                        arrayListRowFirst.clear();
                        notifiList.clear();
                        checkInfoList.clear();
                        jsonObject = new JSONObject(s);

                        JSONArray notificationInfo = jsonObject.getJSONArray("INFO");
                        for (int i = 0; i < notificationInfo.length(); i++) {

                            JSONObject infoDetail = notificationInfo.getJSONObject(i);
                            ChequeInfo chequeInfo = new ChequeInfo();
                            chequeInfo.setRowId(infoDetail.get("ROWID1").toString());
//                           if( !dbHandler.getRowID(chequeInfo.getRowId()).equals(""))
////                           {


                            chequeInfo.setIsJoin(infoDetail.getString("ISJOIN"));
                            chequeInfo.setTransType(infoDetail.getString("TRANSSTATUS"));


                            chequeInfo.setChequeNo(infoDetail.get("CHECKNO").toString());
                            int chNo = Integer.parseInt(chequeInfo.getChequeNo());

                            chequeInfo.setUserName(infoDetail.getString("USERNO"));
                            chequeInfo.setToCustomerMobel(infoDetail.get("TOCUSTOMERMOB").toString());
                            Log.e("setTransType", "\t" + chequeInfo.getTransType() + "\t" + chequeInfo.getUserName());


                            notification notifi = new notification();
                            notifi.setSource(infoDetail.get("CUSTOMERNM").toString());
                            notifi.setDate(infoDetail.get("CHECKDUEDATE").toString());
                            notifi.setAmount_check(infoDetail.get("AMTJD").toString());
                            //**********************************************************************
                            chequeInfo.setRowId(infoDetail.get("ROWID1").toString());
                            chequeInfo.setToCustomerNationalId(infoDetail.get("TOCUSTOMERNATID").toString());

                            chequeInfo.setCustName(infoDetail.get("CUSTOMERNM").toString());
                            chequeInfo.setChequeData(infoDetail.get("CHECKDUEDATE").toString());
                            chequeInfo.setToCustomerName(infoDetail.get("TOCUSTOMERNM").toString());
                            chequeInfo.setQrCode(infoDetail.get("QRCODE").toString());
                            chequeInfo.setMoneyInDinar(infoDetail.get("AMTJD").toString());
                            chequeInfo.setCustomerWriteDate(infoDetail.get("CHECKWRITEDATE").toString());
                            chequeInfo.setMoneyInWord(infoDetail.get("AMTWORD").toString());
                            chequeInfo.setMoneyInFils(infoDetail.getString("AMTFILS"));
                            chequeInfo.setBankName(infoDetail.get("BANKNM").toString());
                            chequeInfo.setChequeNo(infoDetail.get("CHECKNO").toString());
                            chequeInfo.setCustName(infoDetail.get("CUSTOMERNM").toString());
                            chequeInfo.setSerialNo(infoDetail.get("SERIALNO").toString());
                            chequeInfo.setBranchNo(infoDetail.get("BRANCHNO").toString());
                            chequeInfo.setAccCode(infoDetail.get("ACCCODE").toString());
                            chequeInfo.setIbanNo(infoDetail.get("IBANNO").toString());
                            chequeInfo.setBankNo(infoDetail.get("BANKNO").toString());
                            chequeInfo.setCheckIsSueDate(infoDetail.get("CHECKISSUEDATE").toString());
                            chequeInfo.setCheckDueDate(infoDetail.get("CHECKDUEDATE").toString());
                            chequeInfo.setTransType(infoDetail.getString("TRANSSTATUS"));

                            chequeInfo.setISBF(infoDetail.getString("ISFB"));
                            chequeInfo.setISCO(infoDetail.getString("ISCO"));
                            chequeInfo.setNoteCheck(infoDetail.getString("NOTE"));
                            chequeInfo.setCompanyName(infoDetail.getString("COMPANY"));
                            chequeInfo.setResonOfreject(infoDetail.getString("RJCTREASON"));
                            chequeInfo.setToCustName(infoDetail.getString("CUSTNAME"));
                            chequeInfo.setToCustFName(infoDetail.getString("CUSTFNAME"));
                            chequeInfo.setToCustGName(infoDetail.getString("CUSTGNAME"));
                            chequeInfo.setToCustFamalyName(infoDetail.getString("CUSTFAMNAME"));

                            chequeInfo.setTransSendOrGero(infoDetail.getString("TRANSTYPE"));// 0-----> send  // 1-------> gero

                            chequeInfo.setIsJoin(infoDetail.getString("ISJOIN"));
                            chequeInfo.setJOIN_FirstMOB(infoDetail.getString("JOINFMOB"));
                            chequeInfo.setJOIN_SecondSMOB(infoDetail.getString("JOINSMOB"));
                            chequeInfo.setJOIN_TheredMOB(infoDetail.getString("JOINTMOB"));

                            chequeInfo.setJOIN_F_STATUS(infoDetail.getString("JOINFSTATUS"));
                            chequeInfo.setJOIN_F_REASON(infoDetail.getString("JOINFREASON"));
                            chequeInfo.setJOIN_S_STATUS(infoDetail.getString("JOINSSTATUS"));
                            chequeInfo.setJOIN_S_REASON(infoDetail.getString("JOINSREASON"));


                            chequeInfo.setJOIN_T_STATUS(infoDetail.getString("JOINTSTATUS"));
                            chequeInfo.setJOIN_T_REASON(infoDetail.getString("JOINTREASON"));
                            chequeInfo.setNOTFROWID(infoDetail.getString("NOTFROWID"));
                            chequeInfo.setWICHEUSER(infoDetail.getString("WICHEUSER"));


                            checkInfoList.add(chequeInfo);
                            arrayListRow.add(chequeInfo.getNOTFROWID());


                        }
                        Log.e("arrayListRow", "" + arrayListRow.size());


                        Set<String> set_tow = new HashSet<String>();
                        set_tow.addAll(arrayListRow);
                        Log.e("Empty", "" + arrayListRow.size());
//                        editor = sharedPreferences.edit();
//                        editor.putStringSet("DATE_LIST", set_tow);
//                        editor.apply();

                        Set<String> set = null;
                        try {

                            set = sharedPreferences.getStringSet("DATE_LIST", null);
                            Log.e("sharedPreferences", "" + set.size() + set.toString());
                        } catch (Exception e) {

                        }


                        if (set != null) {
//
//                            set = sharedPreferences.getStringSet("DATE_LIST", set_tow);
                            arrayListRowFirst.addAll(set);

                            int countFirst = arrayListRowFirst.size();
                            if (arrayListRow.size() < countFirst)//there are update new data is less than old data
                            {
                                Log.e("olddataGreater", "countFirst" + countFirst);

                                for (int h = 0; h < arrayListRow.size(); h++) {
                                    int index = arrayListRowFirst.indexOf(arrayListRow.get(h));
                                    if (index == -1) {
                                        arrayListRowFirst.add(arrayListRow.get(h));
                                        Log.e("arrayListRowYES", "" + arrayListRow.get(h));

                                    }

                                }

                                if (countFirst < arrayListRowFirst.size())// new data
                                {
                                    Log.e("getTransType-new", "" + checkInfoList.get(0).getTransType().equals("100"));
//                                    ShowNotifi("check");

                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {

                                            notification_btn.setVisibility(View.VISIBLE);

                                        }
                                    });
                                    int stat = -1;
//                                    if(checkInfoList.get(0).getStatus().equals("0")) {
                                    if (checkInfoList.get(0).getTransType().equals("1")) {
                                        stat = 1;

                                    } else {
                                        if ((checkInfoList.get(0).getTransType().equals("2")) || (checkInfoList.get(0).getTransType().equals("200"))) {
                                            stat = 2;
                                        }
                                        if (checkInfoList.get(0).getTransType().equals("100")) {

                                            stat = 100;
                                        }

                                    }
//                                    }
//                                    else {
//                                        stat=0;
//
//                                    }
                                    try {
                                        notification_btn.setVisibility(View.VISIBLE);
                                        ShowNotifi_detail("check", stat, checkInfoList.get(0).getChequeNo());
                                    } catch (Exception e) {
                                        Log.e("ShowNotifi_detail", "" + e.getMessage());
                                    }


                                } else {

                                }

                            }//********************************************
                            else {
                                if (arrayListRow.size() > countFirst)// new data
                                {
                                    Log.e("NewGreater", "countFirst" + countFirst);
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {

                                            notification_btn.setVisibility(View.VISIBLE);

                                        }
                                    });
//                                    ShowNotifi("check");
                                    int stat = -1;
//                                    if(checkInfoList.get(0).getStatus().equals("0")) {
                                    if (checkInfoList.get(0).getTransType().equals("1")) {
                                        stat = 1;

                                    } else {
                                        if ((checkInfoList.get(0).getTransType().equals("2")) || (checkInfoList.get(0).getTransType().equals("200"))) {
                                            stat = 2;
                                        }
                                        if (checkInfoList.get(0).getTransType().equals("100")) {
                                            stat = 100;
                                        }

                                    }
//                                    }
//                                    else {
//                                        stat=0;
//
//                                    }
                                    notification_btn.setVisibility(View.VISIBLE);
                                    ShowNotifi_detail("check", stat, checkInfoList.get(0).getChequeNo());


                                } else {
                                    if (arrayListRow.size() == countFirst)// equal size
                                    {
                                        Log.e("arrayListRow", "== hereeee");

                                        for (int h = 0; h < arrayListRow.size(); h++) {
                                            int index = arrayListRowFirst.indexOf(arrayListRow.get(h));
                                            if (index == -1) {
                                                arrayListRowFirst.add(arrayListRow.get(h));


                                            }

                                        }

                                        if (countFirst < arrayListRowFirst.size())// new data
                                        {
//                                            ShowNotifi("check");

                                            new Handler().post(new Runnable() {
                                                @Override
                                                public void run() {

                                                    notification_btn.setVisibility(View.VISIBLE);

                                                }
                                            });
                                            int stat = -1;
//                                            if (checkInfoList.get(0).getStatus().equals("0")) {
                                            if (checkInfoList.get(0).getTransType().equals("1")) {
                                                stat = 1;

                                            } else {
                                                if ((checkInfoList.get(0).getTransType().equals("2")) || (checkInfoList.get(0).getTransType().equals("200"))) {
                                                    stat = 2;
                                                }
                                                if (checkInfoList.get(0).getTransType().equals("100")) {
                                                    stat = 100;
                                                }

                                            }
//                                            }
//                                            else {
//                                                stat = 0;
//
//                                            }
                                            notification_btn.setVisibility(View.VISIBLE);
                                            ShowNotifi_detail("check", stat, checkInfoList.get(0).getChequeNo());

                                        }


                                    } else {


//                                                fillListNotification(notificationArrayListTest);

                                    }

                                }

                            }


                        } else {//empty shared preference

                            notification_btn.setVisibility(View.VISIBLE);
                            try {
                                transtype = Integer.parseInt(checkInfoList.get(0).getTransType());
                            } catch (Exception e) {
                            }
                            ShowNotifi_detail("check", transtype, checkInfoList.get(0).getChequeNo());
                        }


//                        Set<String> set_tow = new HashSet<String>();
//                        set_tow.addAll(arrayListRow);
//                        Log.e("Empty", "" + arrayListRow.size());
                        editor = sharedPreferences.edit();
                        editor.putStringSet("DATE_LIST", set_tow);
                        editor.apply();


                        new GetAllRequestToUser_JSONTask().execute();
//                        new GetAllRequestFromUser_JSONTask().execute();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    INFO
//                    Log.e("tag", "****Success" + s.toString());
                } else {
                    new GetAllRequestToUser_JSONTask().execute();
                    Log.e("tagMain", "****Failed to export data" + s.toString());
                }
            } else {
                new GetAllRequestToUser_JSONTask().execute();
                Log.e("tagMain", "****Failed to export data Please check internet connection");
            }
        }
    }

    private void ShowNotifi(String type) {
        String currentapiVersion = Build.VERSION.RELEASE;
        String title = "", content = "";
        switch (type) {
            case "check":
                title = "Recive new Check";
                break;
            case "request":
                title = "Recive new Request";
                break;
        }

//


//                                show_Notification("Thank you for downloading the Points app, so we'd like to add 30 free points to your account");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


//                show_Notification(title);
            showNotification(MainActivity.this, title, "details");

        } else {
            notificationShow(title);
        }


    }

    private void ShowNotifi_detail(String type, int state, String contentMessage) {
        String currentapiVersion = Build.VERSION.RELEASE;
        String title = "", content = "", statuse = "";


        if (type.equals("check")) {
            notification_btn.setVisibility(View.VISIBLE);

        }
        if (type.equals("Request")) {
            button_request.setVisibility(View.VISIBLE);

        }

        switch (type) {
            case "check":
                title = "Check";
                break;
            case "request":
                title = "Request";
                break;
        }
        String messgaeBody = "";
        switch (state) {
            case 0:
                statuse = "new\t" + title + "\t";
                messgaeBody = "you have " + statuse + "From" + "\t" + contentMessage;
                break;
            case 1:
                statuse = "Accepted\t" + title + "\t";
                messgaeBody = "you have " + statuse + "\t" + contentMessage;
                break;
            case 2:
                statuse = "Rejected\t" + title + "\t";
                messgaeBody = "you have " + statuse + "\t" + contentMessage;
                break;
            case 200:
                statuse = "Rejected\t" + title + "\t";
                messgaeBody = "you have " + statuse + "\t" + contentMessage;
                break;
            case 100:
                statuse = "New Joint\t" + title + "\t";
                messgaeBody = "you have " + statuse + "\t" + contentMessage;
                break;
        }

        Log.e("messgaeBody", "" + messgaeBody);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


//                show_Notification(title);
            showNotification(MainActivity.this, title, messgaeBody);

        } else {
            showNotification(MainActivity.this, title, messgaeBody);

//            notificationShow(title);
        }


    }

    // ******************************************** CHECK QR VALIDATION *************************************
    private class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(serverLink + "VerifyCheck?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("CHECKNO", arr[0]));
                nameValuePairs.add(new BasicNameValuePair("BANKNO", arr[1]));
                nameValuePairs.add(new BasicNameValuePair("BTANCHNO", arr[2]));
                nameValuePairs.add(new BasicNameValuePair("ACCCODE", arr[3]));
                nameValuePairs.add(new BasicNameValuePair("IBANNO", ""));
                nameValuePairs.add(new BasicNameValuePair("CUSTOMERNM", ""));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
//                Log.e("tag", "" + JsonResponse);

                return JsonResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
                    Log.e("main/checkValidation/", "Success/" + s);
                    try {
                        JSONObject jsonObject = new JSONObject(s);


                        CHECKNO = jsonObject.get("CHECKNO").toString();
                        ACCCODE = jsonObject.get("ACCCODE").toString();
                        IBANNO = jsonObject.get("IBANNO").toString();
                        CUSTOMERNM = jsonObject.get("CUSTOMERNM").toString();
                        QRCODE = jsonObject.get("QRCODE").toString();
                        SERIALNO = jsonObject.get("SERIALNO").toString();
                        BANKNO = jsonObject.get("BANKNO").toString();
                        BRANCHNO = jsonObject.get("BRANCHNO").toString();

                        showValidationDialog(true, CUSTOMERNM, BANKNO, ACCCODE, CHECKNO);

//                        showSweetDialog(true, jsonObject.get("CUSTOMERNM").toString(), jsonObject.get("BANKNO").toString(), jsonObject.get("ACCCODE").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    showSweetDialog(false, "", "", "");
                    Log.e("tagtagMainJSONTask", "****Failed to export data");
                }
            } else {
                showSweetDialog(false, "", "", "");
                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

    //**********************************************************************************************************
    public class GetAllRequestToUser_JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String WHICH = "0";// to user
                infoUser = dbHandler.getActiveUserInfo();
                phoneNo = infoUser.getUsername();
                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
//                http://localhost:8082/GetAllTempCheck?CUSTMOBNO=0798899716&CUSTIDNO=123456
                request.setURI(new URI(serverLink + "GetNotifications?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("MOBNO", phoneNo));
                nameValuePairs.add(new BasicNameValuePair("ISREQ", "1"));// test

                Log.e("editingmain ", phoneNo);

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

                        arrayListRow.clear();
                        arrayListRowFirst.clear();
                        requestList.clear();

                        jsonObject = new JSONObject(s);

                        JSONArray notificationInfo = jsonObject.getJSONArray("INFO");
                        for (int i = 0; i < notificationInfo.length(); i++) {
                            JSONObject infoDetail = notificationInfo.getJSONObject(i);

                            requestModel chequeInfo = new requestModel();

                            chequeInfo.setTRANSSTATUS(infoDetail.get("TRANSSTATUS").toString());
                            chequeInfo.setKind(infoDetail.getString("KIND"));

                            Log.e("setTransType", "\t" + chequeInfo.getTRANSSTATUS());

                            if ((chequeInfo.getKind().equals("3")) || (chequeInfo.getKind().equals("4")))// reject from mee
                            {
                                chequeInfo.setROWID(infoDetail.getString("ROWID1"));
                                chequeInfo.setFROMUSER_No(infoDetail.getString("FROMUSER"));
                                chequeInfo.setWitch("0");
                                chequeInfo.setFROMUSER_name(infoDetail.get("FROMUSERNM").toString());
                                chequeInfo.setTOUSER_No(infoDetail.get("TOUSER").toString());
                                chequeInfo.setTOUSER_name(infoDetail.get("TOUSERNM").toString());
                                chequeInfo.setCOMPNAME(infoDetail.get("COMPNAME").toString());
//                                Log.e("getFROMUSER_name", "" + chequeInfo.getFROMUSER_name());
                                chequeInfo.setNOTE(infoDetail.get("NOTE").toString());
                                chequeInfo.setAMOUNT(infoDetail.get("AMOUNT").toString());

                                chequeInfo.setTRANSSTATUS(infoDetail.get("TRANSSTATUS").toString());
                                chequeInfo.setINDATE(infoDetail.get("INDATE").toString());
                                chequeInfo.setREASON(infoDetail.getString("REASON"));
                                chequeInfo.setNotif_ROWID(infoDetail.getString("NOTFROWID"));
                                requestList.add(chequeInfo);


                                arrayListRow.add(chequeInfo.getNotif_ROWID());
                                Log.e("arrayListRowTouser", "" + arrayListRow.size());


                            }
                        }

                        Set<String> set_tow = new HashSet<String>();
                        set_tow.addAll(arrayListRow);
                        Log.e("Empty", "" + arrayListRow.size());


                        Set<String> set = sharedPreferences.getStringSet("REQUEST_ToUser", null);

                        if (set != null) {
//
                            set = sharedPreferences.getStringSet("REQUEST_ToUser", null);
                            arrayListRowFirst.addAll(set);

                            int countFirst = arrayListRowFirst.size();
                            if (arrayListRow.size() < countFirst)//there are update new data is less than old data
                            {
                                Log.e("olddataGreater", "countFirst" + countFirst);

                                for (int h = 0; h < arrayListRow.size(); h++) {
                                    int index = arrayListRowFirst.indexOf(arrayListRow.get(h));
                                    if (index == -1) {
                                        arrayListRowFirst.add(arrayListRow.get(h));
                                        Log.e("arrayListRowYES", "" + arrayListRow.get(h));

                                    }

                                }

                                if (countFirst < arrayListRowFirst.size())// comparenew data
                                {

//                                    ShowNotifi("request");
                                    button_request.setVisibility(View.VISIBLE);
                                    ShowNotifi_detail("request", 0, requestList.get(requestList.size() - 1).getTOUSER_name());

                                    Log.e("requestList_comparenew", "" + arrayListRowFirst.size() + "\t" + countFirst);
//                                    isNewData=true;


                                } else {

//
                                }

                            }//********************************************
                            else {
                                if (arrayListRow.size() > countFirst)// new data
                                {
                                    Log.e("newGreater", "countFirst");
                                    button_request.setVisibility(View.VISIBLE);
                                    ShowNotifi_detail("request", 0, requestList.get(requestList.size() - 1).getFROMUSER_name());
                                    Log.e("requestList", "" + requestList.size() + "\t" + requestList.get(requestList.size() - 1).getTOUSER_name());

//                                    isNewData=true;

                                } else {
                                    if (arrayListRow.size() == countFirst)// equal size
                                    {
                                        Log.e("arrayListRow", "== hereeee");

                                        for (int h = 0; h < arrayListRow.size(); h++) {
                                            int index = arrayListRowFirst.indexOf(arrayListRow.get(h));
                                            if (index == -1) {
                                                arrayListRowFirst.add(arrayListRow.get(h));


                                            }

                                        }
                                        Log.e("requestList_equal", "" + arrayListRowFirst.size() + "\t" + arrayListRow.size());

                                        if (countFirst < arrayListRowFirst.size())// new data
                                        {
//                                            ShowNotifi("request");
                                            button_request.setVisibility(View.VISIBLE);
                                            ShowNotifi_detail("request", 0, requestList.get(requestList.size() - 1).getTOUSER_name());

//                                            isNewData=true;
//

                                        } else {

                                        }
                                    }

                                }

                            }


//                            }

                        } else {//empty shared preference

                            button_request.setVisibility(View.VISIBLE);
                            ShowNotifi_detail("request", 0, requestList.get(requestList.size() - 1).getTOUSER_name());

                        }

                        editor = sharedPreferences.edit();
                        editor.putStringSet("REQUEST_ToUser", set_tow);
                        editor.apply();
                        Log.e("EndFirstToUser", "****************");
//                        new GetAllRequestFromUser_JSONTask().execute();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    INFO
                    Log.e("tag", "****Success" + s.toString());
                } else {
                    Log.e("tagRequest", "****Failed to export data" + s.toString());
//                    new GetAllRequestFromUser_JSONTask().execute();

                }
            } else {

                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

    public class GetAllRequestFromUser_JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Log.e("flagMaindoInBackground", "");
                String WHICH = "1";
                infoUser = dbHandler.getActiveUserInfo();
                phoneNo = infoUser.getUsername();
                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
//                http://localhost:8082/GetAllTempCheck?CUSTMOBNO=0798899716&CUSTIDNO=123456
                request.setURI(new URI(serverLink + "GetRequest?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

                nameValuePairs.add(new BasicNameValuePair("MOBILENO", phoneNo));
                Log.e("editingmain ", phoneNo);

                nameValuePairs.add(new BasicNameValuePair("WHICH", WHICH));// to me witch=1
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
                        Log.e("StartSecondUser", "****************");


                        arrayListRow.clear();
                        arrayListRowFirst.clear();
                        requestList_Tow.clear();

                        jsonObject = new JSONObject(s);

                        JSONArray notificationInfo = jsonObject.getJSONArray("INFO");
                        for (int i = 0; i < notificationInfo.length(); i++) {
                            JSONObject infoDetail = notificationInfo.getJSONObject(i);

                            requestModel chequeInfo = new requestModel();

                            chequeInfo.setTRANSSTATUS(infoDetail.get("TRANSSTATUS").toString());


                            if (chequeInfo.getTRANSSTATUS().equals("1"))// reject from mee
                            {
                                chequeInfo.setROWID(infoDetail.getString("ROWID"));
                                chequeInfo.setFROMUSER_No(infoDetail.getString("FROMUSER"));
                                chequeInfo.setWitch("1");
                                chequeInfo.setFROMUSER_name(infoDetail.get("FROMUSERNM").toString());
                                chequeInfo.setTOUSER_No(infoDetail.get("TOUSER").toString());
                                chequeInfo.setTOUSER_name(infoDetail.get("TOUSERNM").toString());
                                chequeInfo.setCOMPNAME(infoDetail.get("COMPNAME").toString());
                                Log.e("getFROMUSER_name", "" + chequeInfo.getFROMUSER_name());
                                chequeInfo.setNOTE(infoDetail.get("NOTE").toString());
                                chequeInfo.setAMOUNT(infoDetail.get("AMOUNT").toString());

                                chequeInfo.setTRANSSTATUS(infoDetail.get("TRANSSTATUS").toString());
                                chequeInfo.setINDATE(infoDetail.get("INDATE").toString());
                                chequeInfo.setREASON(infoDetail.getString("REASON"));
                                requestList_Tow.add(chequeInfo);

                                arrayListRow.add(chequeInfo.getROWID());


                            }
                        }
                        Log.e("requestList_Tow", "" + requestList_Tow.size());
//
                        Set<String> set_Data = new HashSet<String>();
                        set_Data.addAll(arrayListRow);
                        Log.e("Empty", "" + arrayListRow.size());


                        Set<String> set = sharedPreferences.getStringSet("REQUEST_LIST", set_Data);

                        if (set != null) {
//
                            set = sharedPreferences.getStringSet("REQUEST_LIST", set_Data);
                            arrayListRowFirst.addAll(set);

                            int countFirst = arrayListRowFirst.size();
                            Log.e("countFirstT", "" + countFirst);
                            Log.e("arrayListRow9999", "" + arrayListRow.size());

                            if (arrayListRow.size() < countFirst)//there are update new data is less than old data
                            {
                                Log.e("olddataGreater2222T", "countFirst" + countFirst + "arrayListRow" + arrayListRow.size());


                                for (int h = 0; h < arrayListRow.size(); h++) {
                                    int index = arrayListRowFirst.indexOf(arrayListRow.get(h));
                                    if (index == -1) {
                                        arrayListRowFirst.add(arrayListRow.get(h));
                                        Log.e("arrayListRowYEST", "" + arrayListRow.get(h));

                                    }

                                }

                                if (countFirst < arrayListRowFirst.size())// new data
                                {
                                    Log.e("NewGreaterT", "new data");
//                                    ShowNotifi("request");
                                    button_request.setVisibility(View.VISIBLE);
                                    ShowNotifi_detail("request", 2, requestList_Tow.get(requestList_Tow.size() - 1).getTOUSER_name());

//                                    isNewData=true;


                                } else {
                                }

                            }//********************************************
                            else {
                                if (arrayListRow.size() > countFirst)// new data
                                {
                                    Log.e("StatenewdataT", "g");

//                                    ShowNotifi("request");
                                    button_request.setVisibility(View.VISIBLE);
                                    ShowNotifi_detail("request", 2, requestList_Tow.get(requestList_Tow.size() - 1).getTOUSER_name());

//                                    isNewData=true;

                                } else {
                                    if (arrayListRow.size() == countFirst)// equal size
                                    {
                                        Log.e("arrayListRowT", "== hereeee");

                                        for (int h = 0; h < arrayListRow.size(); h++) {
                                            int index = arrayListRowFirst.indexOf(arrayListRow.get(h));
                                            if (index == -1) {
                                                arrayListRowFirst.add(arrayListRow.get(h));


                                            }

                                        }

                                        if (countFirst < arrayListRowFirst.size())// new data
                                        {

                                            Log.e("newdataT", "gCompare");
//                                            ShowNotifi("request");
                                            button_request.setVisibility(View.VISIBLE);
                                            ShowNotifi_detail("request", 2, requestList_Tow.get(requestList_Tow.size() - 1).getTOUSER_name());

//                                            isNewData=true;
                                        } else {

                                        }
                                    }

                                }

                            }


//                            }

                        } else {//empty shared preference
                        }


                        editor = sharedPreferences.edit();
                        editor.putStringSet("REQUEST_LIST", set_Data);
                        editor.apply();
                        Log.e("isNewData", "" + isNewData);
//                        if(isNewData)
//                        {
//                            ShowNotifi("request");
//                        }
//

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    INFO
//                    Log.e("tag", "****Success" + s.toString());
                } else {
                    Log.e("tagFromUser", "****Failed to export data");
                }
            } else {

                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

}
