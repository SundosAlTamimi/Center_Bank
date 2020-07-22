package com.falconssoft.centerbank;

import android.widget.EditText;

import androidx.databinding.BindingAdapter;

public class LoginMethods {

    @BindingAdapter("seterrormessage")
    public static void bindingError(EditText editText, String error) {
        editText.setError(error);

    }
}
