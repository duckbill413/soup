package io.ssafy.soupapi.global.util;

import java.util.HashMap;
import java.util.Map;

public class MapStringReplace {
    private final String origin;
    private String replaced;
    private final Map<String, String> keyMap;
    private boolean isReplaced;

    public MapStringReplace(String origin) {
        keyMap = new HashMap<>();
        this.origin = origin;
    }

    public void addValue(String key, String value) {
        keyMap.put(key, value);
    }

    public String replace() {
        if (isReplaced) {
            return replaced;
        }
        isReplaced = true;

        replaced = origin;
        for (String key : keyMap.keySet()) {
            replaced = replaced.replaceAll(":" + key, keyMap.get(key));
        }
        return replaced;
    }

    public void addAllValues(Map<String, String> names) {
        keyMap.putAll(names);
    }

    public String get(String key) {
        return keyMap.get(key);
    }
}
