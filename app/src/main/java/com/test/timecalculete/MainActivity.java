package com.test.timecalculete;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_REQUEST_CODE =1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStoragePermission();

    }

    private void checkStoragePermission() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == STORAGE_REQUEST_CODE && grantResults.length>0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        ((Application)getApplication()).setActivityStartTime(MainActivity.class.getSimpleName(),System.currentTimeMillis());
        super.onStart();
    }
    

    @Override
    protected void onStop() {
        ActivityTimeSaveHelper.getInstance().exportActivityTime(
                MainActivity.class.getSimpleName(),
                ((Application)getApplication()).getActivityStartTime( MainActivity.class.getSimpleName())
        );

        super.onStop();
    }
}