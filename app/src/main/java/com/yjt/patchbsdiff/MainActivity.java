package com.yjt.patchbsdiff;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    boolean hasAuth = false;
    volatile boolean doing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            hasAuth = true;
        }

        findViewById(R.id.split).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                if (doing) {
                    return;
                }
                doing = true;
                new Thread() {
                    @Override
                    public void run() {
                        BsdiffUtils.startPatch("/storage/emulated/0/old.txt", "/storage/emulated" +
                                "/0/new.txt", "/storage/emulated/0/patch.txt");
                        doing = false;
                    }
                }.start();

            }
        });

        findViewById(R.id.combine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                if (doing) {
                    return;
                }
                doing = true;
                new Thread() {
                    @Override
                    public void run() {
                        BsdiffUtils.startCombine("/storage/emulated/0/old.txt", "/storage" +
                                "/emulated/0/combine.txt", "/storage/emulated/0/patch.txt");
                        doing = false;
                    }
                }.start();
            }
        });

    }
}
