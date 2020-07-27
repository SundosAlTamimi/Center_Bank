package com.falconssoft.centerbank;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.CLIPBOARD_SERVICE;

public class SharedClass {
    private Context context;

    public SharedClass(Context context) {
        this.context = context;
    }

    public void showPhoneOptions(final String phoneNo) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_dialer_copy_paste);

        ImageView call = dialog.findViewById(R.id.option_dialog_call);
        TextView copy = dialog.findViewById(R.id.option_dialog_copy);
        TextView paste = dialog.findViewById(R.id.option_dialog_paste);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    String mobile = "tel:+" + phoneNo;
                    Log.e("mobile", mobile);
                    intent.setData(Uri.parse(mobile));
                    try {
                        context.startActivity(intent);

                    } catch (Exception e) {
                        Toast.makeText(context, "No Dialer Found!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ClipboardManager myClipboard= (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);;
                 ClipData myClip;

                myClip = ClipData.newPlainText("text", "+" + phoneNo);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(context, "Text Copied",
                        Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dialog.show();
    }
}
