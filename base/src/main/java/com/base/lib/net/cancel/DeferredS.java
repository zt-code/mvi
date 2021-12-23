package com.base.lib.net.cancel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CancellationException;

import io.reactivex.disposables.Disposable;
import kotlinx.coroutines.Deferred;

public class DeferredS {

    public HashMap<String, Deferred> map;

    public DeferredS() {
        map = new HashMap<>();
    }

    public HashMap<String, Deferred> getMap() {
        return map;
    }

    public Deferred get(String key) {
        return map.get(key);
    }

    public void add(String key, final Deferred deferred) {
        //添加新的
        map.put(key, deferred);
    }

    public void remove(String key) {
        if(map.containsKey(key)) {
            map.get(key).cancel(new CancellationException("取消"));
            map.remove(key);
        }
    }

    public void clear() {
        Iterator<Map.Entry<String, Deferred>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Deferred> entry = it.next();
            entry.getValue().cancel(new CancellationException("取消"));
        }
        map.clear();
    }

}