package com.base.lib.net.formatter;

/**
 * JSON格式化
 *
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2017-04-10
 */
public class JSONFormatter {

    static final JSONFormatter FORMATTER = findJSONFormatter();

    public static String formatJSON(String source) {
        try {
            return FORMATTER.format(source);
        } catch (Exception e) {
            return "";
        }
    }

    String format(String source) {
        return "";
    }

    private static JSONFormatter findJSONFormatter() {
        JSONFormatter jsonFormatter = OrgJsonFormatter.buildIfSupported();
        if (jsonFormatter != null) {
            return jsonFormatter;
        }

        JSONFormatter fastjsonFormatter = FastjsonFormatter.buildIfSupported();
        if (fastjsonFormatter != null) {
            return fastjsonFormatter;
        }

        return new JSONFormatter();
    }
}

