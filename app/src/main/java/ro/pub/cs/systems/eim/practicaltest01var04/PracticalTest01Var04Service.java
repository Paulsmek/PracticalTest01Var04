package ro.pub.cs.systems.eim.practicaltest01var04;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class PracticalTest01Var04Service extends Service {




    // intervalul de 10 secunde (în ms)
    public static final long INTERVAL_MS = 5_000L;

    // un Random ca să alegem una din cele 3 acțiuni

    private String name, group;

    private Handler handler;
    private Runnable broadcastRunnable;
    private boolean isRunning = false, ok = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // e un service "started", nu "bound", deci returnăm null
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // putem fi porniți din nou cu alte numere
        if (intent != null) {
            name = intent.getStringExtra("name");
            group = intent.getStringExtra("group");
        }

        // pornim runnable-ul doar o dată
        if (!isRunning) {
            isRunning = true;
            handler = new Handler();
            broadcastRunnable = new Runnable() {
                @Override
                public void run() {
                    sendRandomBroadcast();
                    // reprogramăm peste 10 secunde
                    handler.postDelayed(this, INTERVAL_MS);
                }
            };
            // pornim prima execuție imediat
            handler.post(broadcastRunnable);
        }

        // dacă service-ul e ucis, să fie repornit cu ultimul intent
        return START_REDELIVER_INTENT;
    }

    private void sendRandomBroadcast() {
        // 1. alegem acțiunea
        String s;

        if (ok) {
            s = name;
            ok = false;
        }
        else {
            s = group;
            ok = true;
        }


        // 4. construim intentul de broadcast
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(s);
        broadcastIntent.putExtra("s", s);

        broadcastIntent.setPackage(getApplicationContext().getPackageName());

        // 5. îl trimitem
        sendBroadcast(broadcastIntent);

    }

    @Override
    public void onDestroy() {
        // oprim runnable-ul ca să nu mai trimită mesaje
        if (handler != null && broadcastRunnable != null) {
            handler.removeCallbacks(broadcastRunnable);
        }
        isRunning = false;
        super.onDestroy();
    }
}