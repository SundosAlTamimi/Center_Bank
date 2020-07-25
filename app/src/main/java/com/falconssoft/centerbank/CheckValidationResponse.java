package com.falconssoft.centerbank;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CheckValidationResponse {
    Context context;

    public CheckValidationResponse(Context context) {
        this.context = context;
    }

    public CheckValidationResponse() {
    }

    void showSnackbar(String text, boolean showImage, View coordinatorLayout) {
        Snackbar snackbar;
        if (showImage) {
             snackbar = Snackbar.make(coordinatorLayout, Html.fromHtml("<font color=\"#3167F0\">" + text + "</font>"), Snackbar.LENGTH_SHORT);//Updated Successfully
            View snackbarLayout = snackbar.getView();
            TextView textViewSnackbar = (TextView) snackbarLayout.findViewById(R.id.snackbar_text);//android.support.design.R.id.snackbar_text
            textViewSnackbar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_24dp, 0, 0, 0);
        } else {
            snackbar = Snackbar.make(coordinatorLayout, Html.fromHtml("<font color=\"#D11616\">" + text + "</font>"), Snackbar.LENGTH_SHORT);//Updated Successfully
            View snackbarLayout = snackbar.getView();
            TextView textViewSnackbar = (TextView) snackbarLayout.findViewById(R.id.snackbar_text);//android.support.design.R.id.snackbar_text
            textViewSnackbar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_error, 0, 0, 0);
        }
        snackbar.show();
    }

    public void showValidationDialog(boolean check, String customerName, String BankNo, String accountNo, String chequeNo) {
        if (check) {
            final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_after_validation);
            dialog.setCancelable(false);

            TextView bankNameTV = dialog.findViewById(R.id.dialog_validation_bankName);
            TextView chequeWriterTV = dialog.findViewById(R.id.dialog_validation_chequeWriter);
            TextView chequeNoTV = dialog.findViewById(R.id.dialog_validation_chequeNo);
            TextView accountNoTV = dialog.findViewById(R.id.dialog_validation_accountNo);
            TextView okTV = dialog.findViewById(R.id.dialog_validation_ok);
            TextView cancelTV = dialog.findViewById(R.id.dialog_validation_cancel);

            chequeWriterTV.setText(customerName);
            accountNoTV.setText(accountNo);
            chequeNoTV.setText(chequeNo);
            okTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    checkLanguage();
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
            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
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

}
