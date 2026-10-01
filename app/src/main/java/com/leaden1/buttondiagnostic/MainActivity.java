package com.leaden1.buttondiagnostic;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView status;
    private TextView lastEvent;
    private TextView history;

    private MediaSessionCompat mediaSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = findViewById(R.id.status);
        lastEvent = findViewById(R.id.lastEvent);
        history = findViewById(R.id.history);
        Button clear = findViewById(R.id.clear);

        clear.setOnClickListener(v ->
                history.setText("HISTORIAL\n────────────────────"));

        setupMediaSession();

        status.setText("● MEDIA SESSION ACTIVA\nEsperando botones de GLASES");
        logEvent("APP_INICIADA");
    }

    private void setupMediaSession() {
        mediaSession = new MediaSessionCompat(this, "LEADEN1ButtonDiagnostic");

        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        );

        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public boolean onMediaButtonEvent(Intent mediaButtonIntent) {
                KeyEvent event = mediaButtonIntent.getParcelableExtra(
                        Intent.EXTRA_KEY_EVENT
                );

                if (event != null) {
                    logKeyEvent(event);
                    return true;
                }

                return super.onMediaButtonEvent(mediaButtonIntent);
            }

            @Override
            public void onPlay() {
                logEvent("MEDIA_PLAY");
            }

            @Override
            public void onPause() {
                logEvent("MEDIA_PAUSE");
            }

            @Override
            public void onSkipToNext() {
                logEvent("MEDIA_NEXT");
            }

            @Override
            public void onSkipToPrevious() {
                logEvent("MEDIA_PREVIOUS");
            }

            @Override
            public void onStop() {
                logEvent("MEDIA_STOP");
            }
        });

        mediaSession.setActive(true);
    }

    private void logKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_DOWN) {
            return;
        }

        int code = event.getKeyCode();
        String name = KeyEvent.keyCodeToString(code);

        if (code == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
            logEvent("MEDIA_PLAY_PAUSE");
        } else if (code == KeyEvent.KEYCODE_MEDIA_PLAY) {
            logEvent("MEDIA_PLAY");
        } else if (code == KeyEvent.KEYCODE_MEDIA_PAUSE) {
            logEvent("MEDIA_PAUSE");
        } else if (code == KeyEvent.KEYCODE_MEDIA_NEXT) {
            logEvent("MEDIA_NEXT");
        } else if (code == KeyEvent.KEYCODE_MEDIA_PREVIOUS) {
            logEvent("MEDIA_PREVIOUS");
        } else if (code == KeyEvent.KEYCODE_VOLUME_UP) {
            logEvent("VOLUME_UP");
        } else if (code == KeyEvent.KEYCODE_VOLUME_DOWN) {
            logEvent("VOLUME_DOWN");
        } else if (code == KeyEvent.KEYCODE_ASSIST) {
            logEvent("ASSIST");
        } else {
            logEvent(name);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            int code = event.getKeyCode();

            if (code == KeyEvent.KEYCODE_VOLUME_UP) {
                logEvent("VOLUME_UP_ACTIVITY");
            } else if (code == KeyEvent.KEYCODE_VOLUME_DOWN) {
                logEvent("VOLUME_DOWN_ACTIVITY");
            }
        }

        return super.dispatchKeyEvent(event);
    }

    private void logEvent(String event) {
        runOnUiThread(() -> {
            String time = new SimpleDateFormat(
                    "HH:mm:ss.SSS",
                    Locale.getDefault()
            ).format(new Date());

            lastEvent.setText("ÚLTIMO EVENTO: " + event);
            history.append("\n" + time + "  " + event);
        });
    }

    @Override
    protected void onDestroy() {
        if (mediaSession != null) {
            mediaSession.setActive(false);
            mediaSession.release();
        }
        super.onDestroy();
    }
}
