package io.ssafy.soupapi.global.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RecordClassGenerator {
    private final ObjectMapper objectMapper;

    public void generateRecordClasses(String json, String dtoName, String packagePath, String destination) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        generateRecordClass(rootNode, dtoName, packagePath, destination);
    }

    private void generateRecordClass(JsonNode node, String className, String packagePath, String destination) throws IOException {
        StringBuilder sb = new StringBuilder();
        // 패키지 선언
        sb.append(String.format("package %s;", packagePath)).append("\n\n");
        // Util 생성
        sb.append("import java.util.*;").append("\n");
        sb.append("import io.swagger.v3.oas.annotations.media.Schema;").append("\n");
        sb.append("import jakarta.validation.constraints.*;").append("\n\n");
        // Record 클래스 생성
        sb.append("public record ").append(className).append("(\n");

        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String fieldName = field.getKey();
            JsonNode fieldValue = field.getValue();

            if (fieldValue.isObject()) {
                String nestedClassName = capitalize(fieldName);
                generateRecordClass(fieldValue, nestedClassName, packagePath, destination);
                sb.append("\t").append(nestedClassName).append(" ").append(fieldName);
            } else {
                String fieldType = getFieldType(fieldValue);
                sb.append("\t").append(fieldType).append(" ").append(fieldName);
            }

            if (fields.hasNext()) {
                sb.append(",\n");
            } else {
                sb.append("\n");
            }
        }

        sb.append(") {}\n");

        File file = new File(destination + File.separator + className + ".java");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(sb.toString());
            bw.flush();
        }
    }

    private String getFieldType(JsonNode fieldValue) {
        if (fieldValue.isTextual()) {
            return "String";
        } else if (fieldValue.isInt()) {
            return "Integer";
        } else if (fieldValue.isLong()) {
            return "Long";
        } else if (fieldValue.isDouble()) {
            return "Double";
        } else if (fieldValue.isBoolean()) {
            return "Boolean";
        } else {
            return "Object";
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}