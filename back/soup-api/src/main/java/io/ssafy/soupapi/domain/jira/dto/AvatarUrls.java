
package io.ssafy.soupapi.domain.jira.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.ssafy.soupapi.global.util.StringParserUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "16x16",
        "24x24",
        "32x32",
        "48x48"
})
public class AvatarUrls {

    @JsonProperty("16x16")
    public String _16x16;
    @JsonProperty("24x24")
    public String _24x24;
    @JsonProperty("32x32")
    public String _32x32;
    @JsonProperty("48x48")
    public String _48x48;

    @Builder
    public AvatarUrls(String _16x16, String _24x24, String _32x32, String _48x48) {
        this._16x16 = StringParserUtil.parseNullToEmpty(_16x16);
        this._24x24 = StringParserUtil.parseNullToEmpty(_24x24);
        this._32x32 = StringParserUtil.parseNullToEmpty(_32x32);
        this._48x48 = StringParserUtil.parseNullToEmpty(_48x48);
    }
}
