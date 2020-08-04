package com.falconssoft.centerbank;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;

public class LocaleAppUtils {

    private static Locale locale;
    public static String language;

    public static void setLocale(Locale localeIn) {
        locale = localeIn;
        Log.e("locale", locale.getCountry());
        if (locale != null) {
            Locale.setDefault(locale);
        }
    }

    public static void setConfigChange(Context ctx) {
        if (locale != null) {
            Locale.setDefault(locale);

            Configuration configuration = ctx.getResources().getConfiguration();
            DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
            configuration.locale = locale;

            ctx.getResources().updateConfiguration(configuration, displayMetrics);
        }
    }
    public  static  String  getLocale(){
        return String.valueOf(locale);

    }

    public static void changeLayot(Context context){


        if (language.equals("ar")) {
            LocaleAppUtils.setLocale(new Locale("ar"));
            LocaleAppUtils.setConfigChange(context);
        } else {
            LocaleAppUtils.setLocale(new Locale("en"));
            LocaleAppUtils.setConfigChange(context);
        }

    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }

    public String convertToArabic(String value) {
        String newValue = (((((((((((value + "").replaceAll("1", "١")).replaceAll("2", "٢")).replaceAll("3", "٣")).replaceAll("4", "٤")).replaceAll("5", "٥")).replaceAll("6", "٦")).replaceAll("7", "٧")).replaceAll("8", "٨")).replaceAll("9", "٩")).replaceAll("0", "٠"));
        Log.e("convertToArabic", value + "      " + newValue);
        return newValue;
    }

}
