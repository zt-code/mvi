package com.base.lib.base.base_act;

import android.app.Activity;

import java.util.Iterator;
import java.util.LinkedList;

public class ActManager {

    private static LinkedList<Activity> actStack = new LinkedList<>();

    public static LinkedList<Activity> getAllActivity(){
        return actStack;
    }

    public static Activity getFirstActivity(){
        Activity act = null;
        if(actStack != null && actStack.size() > 0) {
            act = actStack.getFirst();
        }
        return act;
    }

    public static Activity getLastActivity(){
        Activity act = null;
        if(actStack != null && actStack.size() > 0) {
            act = actStack.getLast();
        }
        return act;
    }

    public static Activity getActivity(Class name){
        for (int j = 0; j < actStack.size(); j++) {
            Activity act = actStack.get(j);
            if(act.getClass().equals(name)){
                return act;
            }
        }
        return null;
    }

    public static Activity getActivity(int index){
        if(actStack.size() >= index + 1) {
            return actStack.get(index);
        }
        return null;
    }

    public static void add(Activity activity) {
        if(!actStack.contains(activity)) {
            actStack.add(activity);
        }
    }

    public static void remove(Activity activity) {
        if(actStack != null) {
            actStack.remove(activity);
        }
    }

    /**
     * 关闭所有Activity
     */
    public static void closeApp() {
        if(actStack != null && actStack.size() > 0) {
            Iterator<Activity> iterator = actStack.iterator();
            while(iterator.hasNext()){
                iterator.next().finish();
            }
            System.exit(0);
        }
    }

}