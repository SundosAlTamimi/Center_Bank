package com.falconssoft.centerbank;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.Base64;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;

import static android.widget.LinearLayout.VERTICAL;
import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID ="2" ;
    CircleImageView imageView;
    private Button notification, menuButton;
    TextView barCodTextTemp, scanBarcode, signout;
    private TextView addAccount, chooseAccount, generateCheque, logHistory, Editing, request;
    //    @SuppressLint("WrongConstant")
//    private LinearLayout addAccount, chooseAccount, generateCheque, logHistory,Editing;
    private TextView closeDialog,message;
    private SearchView searchAccount;
    private RecyclerView recyclerViewSearchAccount, recyclerViews;
    private CarouselLayoutManager layoutManagerd;
    List<String> picforbar;
    private Toolbar toolbar;
    Timer timer;
    TextInputEditText inputEditTextTemp;
    NotificationManager notificationManager;
    static int id=1;
    public static final String YES_ACTION = "YES";
    public static final String STOP_ACTION = "STOP";
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE);
        language = prefs.getString("language", "en");
        Log.e("editing,main ", language);

        picforbar = new ArrayList<>();
        picforbar.add("01365574861");
        picforbar.add("0136557486");
        picforbar.add("01365574861");
        picforbar.add("01365574861");
        picforbar.add("01365574861");
        picforbar.add("01365574861");

        layoutManagerd = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);
        recyclerViews = (RecyclerView) findViewById(R.id.res);
        recyclerViews.setLayoutManager(layoutManagerd);
        recyclerViews.setHasFixedSize(true);
        recyclerViews.addOnScrollListener(new CenterScrollListener());
        layoutManagerd.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerViews.setAdapter(new TestAdapterForbar(this, picforbar));
        recyclerViews.requestFocus();
        recyclerViews.scrollToPosition(2);
        recyclerViews.requestFocus();

        init();
//        message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f);
        checkLanguage();

        logHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LogHistoryIntent=new Intent(MainActivity.this,LogHistoryActivity.class);
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
        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notif.setSound(path);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent yesReceive = new Intent();
        yesReceive.setAction(YES_ACTION);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.addAction(R.drawable.ic_local_phone_black_24dp, "show Detail", pendingIntentYes);


        Intent yesReceive2 = new Intent();
        yesReceive2.setAction(STOP_ACTION);
        PendingIntent pendingIntentYes2 = PendingIntent.getBroadcast(this, 12345, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.addAction(R.drawable.ic_access_time_black_24dp, "cancel", pendingIntentYes2);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(String detail){

        Intent intent=new Intent(MainActivity.this,notificationReciver.class);
        intent.putExtra("action","YES");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
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
                .build();


        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);


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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(inputEditText.getText().toString())) {
                    // TODO add account
                    Toast.makeText(MainActivity.this, "Save Success", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                } else
                    Toast.makeText(MainActivity.this, "Please add account first or scan cheque QR barcode!", Toast.LENGTH_SHORT).show();
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputEditTextTemp=inputEditText;
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
//        signout = findViewById(R.id.main_signout);
        imageView = findViewById(R.id.profile_image);
        scanBarcode = findViewById(R.id.scanBarcode);
        notification = findViewById(R.id.button_notification);
        toolbar = findViewById(R.id.main_toolbar);
        request = findViewById(R.id.main_request);

        setSupportActionBar(toolbar);
        setTitle("");
        message=findViewById(R.id.messages);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AlertScreen.class);
                startActivity(i);
            }
        });

//    imageView = findViewById(R.id.profile_image);
//    scanBarcode=findViewById(R.id.scanBarcode);
//
        addAccount = findViewById(R.id.main_addAccount);
//    chooseAccount = findViewById(R.id.main_chooseAccount);
//    generateCheque = findViewById(R.id.main_send);
        logHistory = findViewById(R.id.main_history);
        Editing = findViewById(R.id.Editing);
    }

    void checkLanguage() {
        if (language.equals("ar")) {
            Editing.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_send_black_24dp), null);
            logHistory.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_access_time_black_24dp), null);
            request.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_grain), null);

        } else {
            Editing.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_send_black_24dp), null
                    , null, null);
            logHistory.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_access_time_black_24dp), null
                    , null, null);
            request.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_grain), null
                    , null, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_signout:
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                break;
        }
        return true;
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
//                TostMesage(getResources().getString(R.string.scan)+Result.getContents());
//                barCodTextTemp.setText(Result.getContents() + "");
//                openEditerCheck();
                String ST=Result.getContents();
               String []arr =ST.split(";");

//                    String checkNo = arr[0];
//                    String bankNo = arr[1];
//                    String branchNo = arr[2];
                      String accCode = arr[3];
//                    String ibanNo = arr[4];
//                    String custName= "";
                inputEditTextTemp .setText(accCode);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void openEditerCheck() {

        Intent intent = new Intent(MainActivity.this, EditerCheackActivity.class);
        startActivity(intent);
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
        List<String> list;
//DatabaseHandler db;

        public TestAdapterForbar(Context context, List<String> list) {
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
            cViewHolder.ItemName.setText(list.get(i));
//            cViewHolder.itemImage.setBackgroundResource(getImage(pic2.get(i)));
            cViewHolder.layBar.setTag("" + i);

            cViewHolder.layBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "id = " + v.getTag(), Toast.LENGTH_SHORT).show();

//                    switch (Integer.parseInt(v.getTag().toString())) {
////                        case 0:
////                            Intent intents = new Intent(CategoryActivity.this, RewardActivity.class);
////                            startActivity(intents);
////                            break;
//                        case 0:
//                            Intent intents = new Intent(CategoryActivity.this, ProfileActivity.class);
//                            startActivity(intents);
//                            break;
//                        case 1:
//                            Intent intentN = new Intent(CategoryActivity.this, NotificationActivity.class);
//                            startActivity(intentN);
//                            break;
//                        case 2:
//                            Intent intent = new Intent(CategoryActivity.this, PointViewActivity.class);
//                            startActivity(intent);
//                            break;
//                        case 3:
//                            BarcodeDialog();
//                            break;
//                        case 4:
//                            BranchesDialog();
////                            sendSMS("0786812709","point app 12234");
//                            break;
//
//                        case 5:
//                            Intent addNewIntent = new Intent(CategoryActivity.this, AddNewActivity.class);
//                            startActivity(addNewIntent);
//                            finish();
//                            break;
//                    }

                }
            });

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        }

        @Override
        public int getItemCount() {
            return list.size();
//            return Integer.MAX_VALUE;
        }
    }


}
