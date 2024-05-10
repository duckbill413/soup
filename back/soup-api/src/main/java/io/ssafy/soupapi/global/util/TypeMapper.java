package io.ssafy.soupapi.global.util;

import java.util.HashMap;
import java.util.Map;

public class TypeMapper {
    private static final Map<String, String> TYPE_MAPPINGS = new HashMap<>();

    static {
        TYPE_MAPPINGS.put("VARCHAR", "String");
        TYPE_MAPPINGS.put("CHAR", "String");
        TYPE_MAPPINGS.put("TEXT", "String");
        TYPE_MAPPINGS.put("INT", "Integer");
        TYPE_MAPPINGS.put("DOUBLE", "Double");
        TYPE_MAPPINGS.put("FLOAT", "Float");
        TYPE_MAPPINGS.put("DECIMAL", "BigDecimal");
        TYPE_MAPPINGS.put("DATE", "LocalDate");
        TYPE_MAPPINGS.put("DATETIME", "LocalDateTime");
        TYPE_MAPPINGS.put("TIMESTAMP", "LocalDateTime");
        TYPE_MAPPINGS.put("BOOLEAN", "Boolean");
        TYPE_MAPPINGS.put("BIT", "Boolean");
        TYPE_MAPPINGS.put("LONG", "Long");
        TYPE_MAPPINGS.put("STRING", "String");
        TYPE_MAPPINGS.put("BIGSERIAL", "Long");
        TYPE_MAPPINGS.put("UUID", "UUID");
        TYPE_MAPPINGS.put("INTEGER", "Integer");
        TYPE_MAPPINGS.put("REAL", "Double");
        TYPE_MAPPINGS.put("BIGINT", "Long");
    }

    public static String mapToJavaType(String dbType) {
        if (dbType == null) {
            return null;
        }

        String type = dbType.toUpperCase().trim();
        for (Map.Entry<String, String> entry : TYPE_MAPPINGS.entrySet()) {
            if (type.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }

        return "Object";
    }
}
