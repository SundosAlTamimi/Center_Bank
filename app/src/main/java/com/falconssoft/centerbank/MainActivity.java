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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.falconssoft.centerbank.Models.ChequeInfo;
import com.falconssoft.centerbank.Models.NewAccount;
import com.falconssoft.centerbank.Models.notification;
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
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.falconssoft.centerbank.AlertScreen.ROW_ID_PREFERENCE;
import static com.falconssoft.centerbank.AlertScreen.editor;
import static com.falconssoft.centerbank.AlertScreen.sharedPreferences;
import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;
import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String CHANNEL_ID = "2";
    CircleImageView imageView;
    private Button notification, requestButton;
    private TextView addAccount, chooseAccount, generateCheque, logHistory, Editing, request, cashierCheque, jerro, wallet, barCodTextTemp, scanBarcode, signout;
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
    public static final String STOP_ACTION = "STOP";
    private ArrayList<String> arrayListRow = new ArrayList<>();
    private ArrayList<String> arrayListRowFirst = new ArrayList<>();
    private ArrayList<notification> notifiList1;
    public ArrayList<notification> notifiList;
    private DatabaseHandler dbHandler;
    static String watch;
    private String accCode = "", serverLink = "", CHECKNO = "", ACCCODE = "", IBANNO = ""
            , CUSTOMERNM = "", QRCODE = "", SERIALNO = "", BANKNO = ""
            , BRANCHNO = "", language, userNo, username, AccountNoDelete = "", phoneNo = "";
    private JSONObject addAccountOb;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Dialog barcodeDialog;
    private String[] arr;
    private boolean isAdd = false;
    private TextView bankNameTV, chequeWriterTV, chequeNoTV, accountNoTV, okTV, cancelTV, check, amountTV;

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

        Log.e("editing,main ", language);
        init();
        phoneNo = loginPrefs.getString("mobile", "");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new GetAllCheck_JSONTask().execute();


            }

        }, 0, 20000);
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

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AlertScreen.class);
                startActivity(i);
            }
        });
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RequestCheque.class);
                startActivity(i);
            }
        });

        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public void notificationShow() {

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

        Intent yesReceive = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);// test
        yesReceive.setAction(YES_ACTION);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.addAction(R.drawable.ic_local_phone_black_24dp, "show Detail", pendingIntentYes);


        Intent yesReceive2 = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        yesReceive2.setAction(STOP_ACTION);
        PendingIntent pendingIntentYes2 = PendingIntent.getBroadcast(this, 12345, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.addAction(R.drawable.ic_access_time_black_24dp, "cancel", pendingIntentYes2);


        nm.notify(10, notif.getNotification());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(String detail) {

        Intent intent = new Intent(MainActivity.this, notificationReciver.class);
        intent.putExtra("action", "YES");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String CHANNEL_ID = "MYCHANNEL";

        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_HIGH);
        Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentText("show Detail ......")
                .setContentTitle("Recive new Check, click to show detail")
                .setStyle(new Notification.BigTextStyle()
                        .bigText(detail)
                        .setBigContentTitle(" ")
                        .setSummaryText(""))
                .setContentIntent(pendingIntent)
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
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_account);
        dialog.setCancelable(false);

        final TextInputEditText inputEditText = dialog.findViewById(R.id.dialog_addAccount_account);
        TextView close = dialog.findViewById(R.id.dialog_add_close);
        TextView add = dialog.findViewById(R.id.dialog_addAccount_add);
        TextView scan = dialog.findViewById(R.id.dialog_addAccount_scan);
        LinearLayout linearLayout = dialog.findViewById(R.id.dialog_addAccount_linear);

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

                    NewAccount acc = new NewAccount("jj", inputEditText.getText().toString(), "Jordan Bank", "0");
                    addAccountOb = acc.getJSONObject(userNo);
                    new AddAccount().execute();

                } else
                    Toast.makeText(MainActivity.this, "Please add account first or scan cheque QR barcode!", Toast.LENGTH_SHORT).show();
            }
        });

        isAdd = true;
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
                dialog.dismiss();
            }
        });
        //TODO add dialog function
        dialog.show();
    }

    void init() {
        imageView = findViewById(R.id.profile_image);
        scanBarcode = findViewById(R.id.scanBarcode);
        notification = findViewById(R.id.button_notification);
        requestButton= findViewById(R.id.button_request);
        toolbar = findViewById(R.id.main_toolbar);
        request = findViewById(R.id.main_request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,Request.class);
                startActivity(in);

            }
        });
        dbHandler = new DatabaseHandler(MainActivity.this);
        usernameTv = findViewById(R.id.main_username);
        usernameTv.setText("Welcome " + username);
        cashierCheque = findViewById(R.id.main_cashier);
        jerro = findViewById(R.id.main_jero);
        wallet = findViewById(R.id.main_wallet);
        arrayListRow = new ArrayList<>();
        arrayListRowFirst = new ArrayList<>();
        notifiList1 = new ArrayList<>();
        notifiList = new ArrayList<>();

        dbHandler = new DatabaseHandler(MainActivity.this);
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
                Log.d("MainActivity", "Scanned");
                Log.e("resultcontent", "" + Result.getContents());
                Toast.makeText(this, "Scan ___" + Result.getContents(), Toast.LENGTH_SHORT).show();

                String ST = Result.getContents();
                String[] arr = ST.split(";");

                accCode = arr[3];

                if (isAdd)
                    inputEditTextTemp.setText(accCode);
                else
                    new JSONTask().execute();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        isAdd = false;

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
                checkChequeValidation();
            }
            break;
            case R.id.menu_request: {
                Intent in=new Intent(MainActivity.this,Request.class);
                startActivity(in);


            }
            break;
            case R.id.menu_wallet: {

            }
            case R.id.menu_giro: {

            }
            break;
            case R.id.menu_cashier_cheque: {

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

    void showValidationDialog(boolean check, String customerName, String BankNo, String accountNo, String chequeNo) {
        if (check) {
            final Dialog dialog = new Dialog(this,R.style.Theme_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_after_validation);
            dialog.setCancelable(false);

            bankNameTV = dialog.findViewById(R.id.dialog_validation_bankName);
            chequeWriterTV = dialog.findViewById(R.id.dialog_validation_chequeWriter);
            chequeNoTV = dialog.findViewById(R.id.dialog_validation_chequeNo);
            accountNoTV = dialog.findViewById(R.id.dialog_validation_accountNo);
            okTV = dialog.findViewById(R.id.dialog_validation_ok);
            cancelTV = dialog.findViewById(R.id.dialog_validation_cancel);

            chequeWriterTV.setText(customerName);
            accountNoTV.setText(accountNo);
            chequeNoTV.setText(chequeNo);
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

        TextView ItemName;
        ImageView itemImage;
        LinearLayout layBar;

        public CViewHolderForbar(@NonNull View itemView) {
            super(itemView);
            ItemName = itemView.findViewById(R.id.textbar);
            layBar = itemView.findViewById(R.id.layBar);
            itemImage = itemView.findViewById(R.id.imgbar);
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
//            cViewHolder.itemImage.setBackgroundResource(getImage(pic2.get(i)));
            cViewHolder.layBar.setTag("" + i);

            final boolean[] longIsOpen = {false};
            cViewHolder.layBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!longIsOpen[0]) {
                        Toast.makeText(context, "id = " + v.getTag(), Toast.LENGTH_SHORT).show();
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
                            .setContentText("You want to Delete This Account No =   ( " + list.get(i).getAccountNo() + " ) !")
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
                Log.e("GetAccSuccess", "****Success");

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

                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(MainActivity.this.getResources().getString(R.string.save_success))
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

                nameValuePairs.add(new BasicNameValuePair("MOBNO", phoneNo));// test
                nameValuePairs.add(new BasicNameValuePair("WHICH", "1"));
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
                        jsonObject = new JSONObject(s);

                        JSONArray notificationInfo = jsonObject.getJSONArray("INFO");
                        for (int i = 0; i < notificationInfo.length(); i++) {
                            JSONObject infoDetail = notificationInfo.getJSONObject(i);
//                            serverPicBitmap=null;
                            ChequeInfo chequeInfo = new ChequeInfo();
                            chequeInfo.setTransType(infoDetail.getString("TRANSSTATUS"));
                            chequeInfo.setStatus(infoDetail.getString("STATUS"));// Recive=== 1
                            Log.e("setTransType", "\t" + chequeInfo.getTransType() + "\t setStatus" + chequeInfo.getStatus());
                            if ((chequeInfo.getTransType().equals("0") && chequeInfo.getStatus().equals("1")) ||
                                    (chequeInfo.getStatus().equals("0") && !chequeInfo.getTransType().equals("0")))// Pending and Reciver
                            {


                                com.falconssoft.centerbank.Models.notification notifi = new notification();
                                notifi.setSource(infoDetail.get("CUSTOMERNM").toString());
                                notifi.setDate(infoDetail.get("CHECKDUEDATE").toString());
                                notifi.setAmount_check(infoDetail.get("AMTJD").toString());
                                //**********************************************************************

                                chequeInfo.setRowId(infoDetail.get("ROWID1").toString());


                                arrayListRow.add(chequeInfo.getRowId());


                            }
                        }

                        Set<String> set_tow = new HashSet<String>();
                        set_tow.addAll(arrayListRow);
                        Log.e("Empty", "" + arrayListRow.size());
//                        editor = sharedPreferences.edit();
//                        editor.putStringSet("DATE_LIST", set_tow);
//                        editor.apply();
                        
                        Set<String> set = null;
                        try {

                          set = sharedPreferences.getStringSet("DATE_LIST", set_tow);
                            Log.e("sharedPreferences",""+set.size()+set.toString()); 
                        }
                        catch (Exception e)
                        {
                            
                        }



                        if (set != null) {
//
                            set = sharedPreferences.getStringSet("DATE_LIST", null);
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
                                    ShowNotifi();


                                } else {

                                }

                            }//********************************************
                            else {
                                if (arrayListRow.size() > countFirst)// new data
                                {
                                    Log.e("NewGreater", "countFirst" + countFirst);

                                    ShowNotifi();

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
                                            ShowNotifi();


                                        } else {

//                                                fillListNotification(notificationArrayListTest);
                                        }
                                    }

                                }

                            }


                        } else {//empty shared preference
//
                        }


//                        Set<String> set_tow = new HashSet<String>();
//                        set_tow.addAll(arrayListRow);
//                        Log.e("Empty", "" + arrayListRow.size());
                        editor = sharedPreferences.edit();
                        editor.putStringSet("DATE_LIST", set_tow);
                        editor.apply();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    INFO
                    Log.e("tag", "****Success" + s.toString());
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
        if (Double.parseDouble(currentapiVersion.substring(0, 1)) >= 8) {
            // Do something for 14 and above versions

//                                show_Notification("Thank you for downloading the Points app, so we'd like to add 30 free points to your account");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                show_Notification("Check  app, Recive new Check");

            } else {

            }


        } else {

            notificationShow();
        }
    }

    // ******************************************** CHECK QR VALIDATION *************************************
    private class JSONTask extends AsyncTask<String, String, String> {

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
                Log.e("tag", "" + JsonResponse);

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
                    Log.e("tag", "****Success");
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
                    Log.e("tag", "****Failed to export data");
                }
            } else {
                showSweetDialog(false, "", "", "");
                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

}
