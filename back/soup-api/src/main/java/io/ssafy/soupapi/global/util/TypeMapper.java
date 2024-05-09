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
    }

    public static String mapToJavaType(String mysqlType) {
        if (mysqlType == null) {
            return null;
        }

        String type = mysqlType.toUpperCase().trim();
        for (Map.Entry<String, String> entry : TYPE_MAPPINGS.entrySet()) {
            if (type.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }

        return null;
    }
}
