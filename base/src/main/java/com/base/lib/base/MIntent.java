package com.base.lib.base;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class MIntent implements Serializable {

    private String act;
    private String frg;
    private JSONObject actParams;
    private JSONObject frgParams;

    public MIntent() {

    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getFrg() {
        return frg;
    }

    public void setFrg(String frg) {
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
