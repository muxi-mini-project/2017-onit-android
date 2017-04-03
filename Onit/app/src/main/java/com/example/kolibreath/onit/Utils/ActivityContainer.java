package com.example.kolibreath.onit.Utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolibreath on 2017/4/3.
 */

public class ActivityContainer {

    private static ActivityContainer instance = new ActivityContainer();
    private static List<Activity> stack = new ArrayList<Activity>();

    public static ActivityContainer getInstance() {
        return instance;
    }

    public void addActivity(Activity aty) {
        stack.add(aty);
    }

    public void removeActivity(Activity aty) {
        stack.remove(aty);
    }
    public void clearActivityStack(){
        for(int i=0;i<stack.size();i++){
            if(stack.get(i)!=null){
                stack.get(i).finish();
            }
        }
        stack.clear();
    }
}
