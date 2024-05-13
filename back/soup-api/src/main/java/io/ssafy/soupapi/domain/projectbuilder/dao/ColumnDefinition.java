package io.ssafy.soupapi.domain.projectbuilder.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.util.TypeMapper;
import lombok.*;

import java.util.regex.Pattern;

import static io.ssafy.soupapi.global.util.StringParserUtil.convertToCamelCase;
import static io.ssafy.soupapi.global.util.StringParserUtil.convertToSnakeCase;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColumnDefinition {
    @JsonIgnore
    private static final Pattern VALID_CHAR_PATTERN = Pattern.compile("[^a-zA-Z0-9_-]");
    private String id;
    private String tableId;
    private String name;
    private String comment;
    private String dataType;
    @JsonProperty("default")
    private String mydefault;
    private int options;
    private Ui ui;

    public boolean isForeignKey() {
        return ui.keys() == 2;
    }

    public String getColumnVariable() {
        // @Column(name = "project_builder_file_path", length = 1111, nullable = false, unique = true)
        if (name.isBlank()) {
            throw new BaseExceptionHandler(ErrorCode.NEED_MORE_PROJECT_BUILD_DATA);
        }
        StringBuilder sb = new StringBuilder(String.format("\t@Column(name = \"%s\"", getValidColumnName()));

        // Data type이 VARCHAR 이면서 length가 정해진 경우
        if (dataType.toUpperCase().contains("VARCHAR")) {
            String lengthStr = dataType.replaceAll("\\D", "");
            Long length = Long.parseLong(lengthStr.isBlank() ? "0" : lengthStr);
            sb.append(", length = ").append(length);
        }

        // Not Null
        if ((options & 8) == 8) {
            sb.append(", nullable = false");
        }

        // Unique
        if ((options & 4) == 4) {
            sb.append(", unique = true");
        }
        sb.append(")\n");

        // Primary Key
        // @Id
        if ((options & 2) == 2) {
            // AUTO INCREMENT
            // @GeneratedValue(strategy = GenerationType.IDENTITY)
            if ((options & 1) == 1) {
                sb.insert(0, "\t@GeneratedValue(strategy = GenerationType.IDENTITY)\n");
            }

            sb.insert(0, "\t@Id\n");
        }

        sb.append("\t").append("private ").append(mapToJavaType()).append(" ").append(getValidParamName()).append(';');

        return sb.toString();
    }

    public String mapToJavaType() {
        return TypeMapper.mapToJavaType(dataType);
    }

    public boolean isId() {
        return (options & 2) == 2;
    }

    public String getValidParamName() {
        String validName = VALID_CHAR_PATTERN.matcher(name).replaceAll("");
        if (Character.isDigit(validName.charAt(0))) {
            validName = "_" + validName;
        }
        return convertToCamelCase(validName);
    }

    public String getValidColumnName() {
        return convertToSnakeCase(getValidParamName());
    }
}
