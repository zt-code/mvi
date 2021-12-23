package com.base.lib.base;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class IntentData implements Serializable {

    private Class act;
    private Class frg;
    private JSONObject actParams;
    private JSONObject frgParams;

    public IntentData() {

    }

    public Class getAct() {
        return act;
    }

    public void setAct(Class act) {
        this.act = act;
    }

    public Class getFrg() {
        return frg;
    }

    public void setFrg(Class frg) {
        this.frg = frg;
    }

    public JSONObject getActParams() {
        return actParams;
    }

    public void setActParams(JSONObject actParams) {
        this.actParams = actParams;
    }

    public JSONObject getFrgParams() {
        return frgParams;
    }

    public void setFrgParams(JSONObject frgParams) {
        this.frgParams = frgParams;
    }

    @Override
    public String toString() {
        return "UI{" +
                "act=" + act +
                ", frg=" + frg +
                ", actParams=" + actParams +
                ", frgParams=" + frgParams +
                '}';
    }

}
