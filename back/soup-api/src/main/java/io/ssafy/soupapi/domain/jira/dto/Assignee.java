package io.ssafy.soupapi.domain.jira.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Assignee {

    public String self;
    public String accountId;
    public String emailAddress;
    public AvatarUrls avatarUrls;
    public String displayName;
    public Boolean active;
    public String timeZone;
    public String accountType;

}
