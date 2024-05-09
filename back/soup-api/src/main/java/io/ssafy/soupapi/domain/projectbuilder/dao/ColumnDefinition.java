package io.ssafy.soupapi.domain.projectbuilder.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.util.TypeMapper;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColumnDefinition {
    private String id;
    private String tableId;
    private String name;
    private String comment;
    private String dataType;
    @JsonProperty("default")
    private String mydefault;
    private int options;

    public String getColumnVariable() {
        // @Column(name = "project_builder_file_path", length = 1111, nullable = false, unique = true)
        if (name.isBlank()) {
            throw new BaseExceptionHandler(ErrorCode.NEED_MORE_PROJECT_BUILD_DATA);
        }
        StringBuilder sb = new StringBuilder(String.format("@Column(name = \"%s\"", name));

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

        // Primary Key
        // @Id
        if ((options & 2) == 2) {
            // AUTO INCREMENT
            // @GeneratedValue(strategy = GenerationType.IDENTITY)
            if ((options & 1) == 1) {
                sb.insert(0, "@GeneratedValue(strategy = GenerationType.IDENTITY)\n");
            }

            sb.insert(0, "@Id\n");
        }

        return sb.toString();
    }
    public String mapToJavaType() {
        return TypeMapper.mapToJavaType(dataType);
    }
}
