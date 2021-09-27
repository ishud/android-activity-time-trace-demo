package com.test.timecalculete;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class ActivityTimeSaveHelper {

    private static  ActivityTimeSaveHelper instance;
    private String TIME_FORMAT = "HH : mm : ss";

    public static ActivityTimeSaveHelper getInstance(){
        if(instance == null)
            instance = new ActivityTimeSaveHelper();

        return instance;
    }

    private ActivityTimeSaveHelper(){
    }

    public void exportActivityTime(String activity, long startTime){
        final Thread thread = new Thread() {
            @Override
            public void run() {
                saveActivityTime(activity,startTime);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveActivityTime(String activity, long startTime){

        if(startTime == -1)
            return;

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"activity_data.csv");
        if(!file.exists()) {
            try {
                boolean isCreate = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT,Locale.getDefault());

        final String formatStartTime = simpleDateFormat.format(new Date(startTime));
        final String formatEndTime = simpleDateFormat.format(new Date(endTime));

//        long mnt = 0;
//        long hours = 0;
//        final long seconds = duration / 1000;
//
//        if(seconds > 60)
//           mnt = seconds / 60;
//
//        if(mnt > 24)
//            hours = mnt / 24;
//
//        String formatDuration = hours +" : " + mnt +" : " + seconds;


        try {
            FileInputStream inputStream = new FileInputStream(Environment.getExternalStorageDirectory()+"/"+"activity_data.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder readValue = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                readValue.append(line +"\n");
            }

            if(readValue.length() == 0){
                readValue.append("activity,start_time,end_time,duration \n");
            }
            readValue.append(activity +","+formatStartTime+","+formatEndTime+","+duration+" \n");

            FileWriter fileWriter = new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"activity_data.csv");
            fileWriter.write(readValue.toString());
            inputStream.close();
            reader.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
