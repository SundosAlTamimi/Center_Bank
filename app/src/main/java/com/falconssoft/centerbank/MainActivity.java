package com.falconssoft.centerbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;

import static android.widget.LinearLayout.VERTICAL;

public class MainActivity extends AppCompatActivity {
    CircleImageView imageView;
    Button notification;
    TextView barCodTextTemp, scanBarcode;
    private TextView addAccount, chooseAccount, generateCheque, logHistory, Editing;
    @SuppressLint("WrongConstant")
//    private LinearLayout addAccount, chooseAccount, generateCheque, logHistory,Editing;
    private TextView closeDialog;
    private SearchView searchAccount;
    private RecyclerView recyclerViewSearchAccount, recyclerViews;
    private CarouselLayoutManager layoutManagerd;
    List<String> picforbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_account);

                TextInputEditText inputEditText = dialog.findViewById(R.id.dialog_addAccount_account);
                //TODO add dialog function
                dialog.show();

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

    void init() {
        imageView = findViewById(R.id.profile_image);
        scanBarcode = findViewById(R.id.scanBarcode);
        notification = findViewById(R.id.button_notification);
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
                Toast.makeText(this, "Scan ___" + Result.getContents(), Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.scan)+Result.getContents());
//                barCodTextTemp.setText(Result.getContents() + "");
                openEditerCheck();
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
