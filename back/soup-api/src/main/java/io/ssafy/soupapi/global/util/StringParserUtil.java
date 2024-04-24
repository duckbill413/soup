package io.ssafy.soupapi.global.util;

import java.util.Objects;

public class StringParserUtil {
    public static String parseNullToEmpty(String data) {
        if (Objects.isNull(data)) {
            return "";
        }
        return data;
    }
}
