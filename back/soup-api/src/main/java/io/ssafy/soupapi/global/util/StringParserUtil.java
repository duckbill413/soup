package io.ssafy.soupapi.global.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParserUtil {
    public static String parseNullToEmpty(String data) {
        if (Objects.isNull(data)) {
            return "";
        }
        return data;
    }

    public static List<String> extractBracketsContent(String data) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return result;
    }
}
