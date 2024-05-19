
package io.ssafy.soupapi.domain.jira.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.ssafy.soupapi.global.util.StringParserUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountId",
        "accountType",
        "active",
        "avatarUrls",
        "displayName",
        "key",
        "name",
        "self"
})
public class JiraUserDatum {

    @JsonProperty("accountId")
    public String accountId;
    @JsonProperty("accountType")
    public String accountType;
    @JsonProperty("active")
    public Boolean active;
    @JsonProperty("avatarUrls")
    public AvatarUrls avatarUrls;
    @JsonProperty("displayName")
    public String displayName;
    @JsonProperty("key")
    public String key;
    @JsonProperty("name")
    public String name;
    @JsonProperty("self")
    public String self;

    @Builder
    public JiraUserDatum(String accountId, String accountType, Boolean active, AvatarUrls avatarUrls, String displayName, String key, String name, String self) {
        this.accountId = StringParserUtil.parseNullToEmpty(accountId);
        this.accountType = StringParserUtil.parseNullToEmpty(accountType);
        this.active = Objects.nonNull(active) ? active : false;
        this.avatarUrls = avatarUrls;
        this.displayName = StringParserUtil.parseNullToEmpty(displayName);
        this.key = StringParserUtil.parseNullToEmpty(key);
        this.name = StringParserUtil.parseNullToEmpty(name);
        this.self = StringParserUtil.parseNullToEmpty(self);
    }
}
