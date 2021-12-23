package com.base.lib.net.formatter;

import org.json.JSONException;
import org.json.JSONObject;

class OrgJsonFormatter extends JSONFormatter {

    private static final int INDENT_SPACES = 4;

    static OrgJsonFormatter buildIfSupported() {
        try {
            Class.forName("org.json.JSONObject");
            return new OrgJsonFormatter();
        } catch (ClassNotFoundException ignore) {
            return null;
        }
    }

    @Override
    String format(String source) {
        String str = null;
        try {
            str = new JSONObject(source).toString(INDENT_SPACES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }

}
