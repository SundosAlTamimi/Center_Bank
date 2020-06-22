package com.falconssoft.centerbank;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import static android.icu.text.Normalizer.YES;

public class notificationReciver extends BroadcastReceiver {
    private static final String YES_ACTION = "com.falconssoft.centerbank.YES";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("notificationReciver","onReceive");
        String action = intent.getAction();
        if(action.equals("YES")) {
            Intent i=new Intent(context,AlertScreen.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
            try {
            // Perform the operation associated with our pendingIntent
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
        }
        else{
            Log.e("notificationReciver","else \t "+action);
        }

    }
}
