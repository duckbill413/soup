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

    public static String upperFirstAndLowerElse(String data) {
        if (data.isBlank()) {
            return "";
        }
        if (data.length() == 1) {
            return data.toUpperCase();
        }
        data = data.toLowerCase();
        return Character.toUpperCase(data.charAt(0)) + data.substring(1);
    }

    public static String convertToSnakeCase(String data) {
        String[] parts = data.split("(?=[A-Z])|_|-");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].isEmpty()) {
                continue; // 빈 문자열인 경우 건너뜁니다.
            }
            String part = parts[i].toLowerCase();
            if (i > 0) {
                result.append("_");
            }
            result.append(part);
        }

        return result.toString();
    }

    public static String convertToPascalCase(String data) {
        String[] parts = data.split("(?=[A-Z])|_|-");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].isEmpty()) {
                continue; // 빈 문자열인 경우 건너뜁니다.
            }

            result.append(upperFirstAndLowerElse(parts[i]));
        }

        return result.toString();
    }

    public static String convertToCamelCase(String data) {
        String[] parts = data.split("(?=[A-Z])|_|-");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].isEmpty()) {
                continue; // 빈 문자열인 경우 건너뜁니다.
            }
            // 첫번쨰 문자는 첫글자 내림
            if (i == 0) {
                result.append(parts[i].toLowerCase());
                continue;
            }

            result.append(upperFirstAndLowerElse(parts[i]));
        }

        return result.toString();
    }

    public static String renameFile(String filePath, String oldString, String newString) {
        int index = filePath.lastIndexOf(oldString);
        if (index == -1) return filePath;

        StringBuilder sb = new StringBuilder(filePath);
        sb.replace(index, index + oldString.length(), newString);
        return sb.toString();
    }

    public static boolean isNullOrEmpty(String str) {
        if (Objects.isNull(str)) {
            return true;
        }

        return str.isEmpty();
    }

    public static boolean isJsonNullOrEmpty(String json) {
        if (isNullOrEmpty(json)) {
            return true;
        }
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(json);

        return matcher.find();
    }
}
