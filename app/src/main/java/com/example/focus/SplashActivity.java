package com.example.focus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends Activity {

    private Thread splashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageView logoImage = findViewById(R.id.logoImage);
        Animation logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        logoImage.startAnimation(logoAnimation);

        splashThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final Thread currentThread = Thread.currentThread();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (splashThread != currentThread || isFinishing() || isDestroyed()) {
                            return;
                        }
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
        splashThread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (splashThread != null) {
            splashThread.interrupt();
            splashThread = null;
        }
        ImageView logoImage = findViewById(R.id.logoImage);
        logoImage.clearAnimation();
    }
}
