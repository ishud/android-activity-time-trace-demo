package com.test.timecalculete;

import java.util.HashMap;

public class Application extends android.app.Application {

    private HashMap<String,Long> activityStartTime;

    @Override
    public void onCreate() {
        super.onCreate();
        activityStartTime = new HashMap<>();
    }

    public void setActivityStartTime(String key,long time){
        activityStartTime.put(key,time);
    }

    public long getActivityStartTime(String key){
        return activityStartTime.containsKey(key)?
                activityStartTime.get(key):-1;

    }


}
