package com.base.lib.net.cancel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class DisposableS {

    public HashMap<String, Disposable> map;

    public DisposableS() {
        map = new HashMap<>();
    }

    public HashMap<String, Disposable> getMap() {
        return map;
    }

    public Disposable get(String key) {
        return map.get(key);
    }

    public void add(String key, final Disposable disposable) {
        //添加新的
        map.put(key, disposable);
    }

    public void remove(String key) {
        if(map.containsKey(key)) {
            map.get(key).dispose();
            map.remove(key);
        }
    }

    public void clear() {
        Iterator<Map.Entry<String, Disposable>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Disposable> entry = it.next();
            entry.getValue().dispose();
        }
        map.clear();
    }

}