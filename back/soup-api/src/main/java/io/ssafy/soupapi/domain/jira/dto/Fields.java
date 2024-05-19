package io.ssafy.soupapi.domain.jira.dto;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Fields {

    public String statuscategorychangedate;
    public Issuetype issuetype;
    public Parent parent;
    public Object timespent;
    public Object customfield_10030;
    public Project project;
    public Long customfield_10031; // 지라 스토리 포인트
    @Builder.Default
    public List<Object> fixVersions = new ArrayList<>();
    public Object aggregatetimespent;
    @Builder.Default
    public List<Object> customfield_10035 = new ArrayList<>();
    public Object resolution;
    public Object customfield_10036;
    public Object customfield_10037;
    public Object customfield_10027;
    public Object customfield_10028;
    public Object customfield_10029;
    public Object resolutiondate;
    public Long workratio;
    public String lastViewed;
    public Watches watches;
    public String created;
    @Builder.Default
    public List<Customfield_10020> customfield_10020 = new ArrayList<Customfield_10020>();
    public Object customfield_10021;
    public Object customfield_10022;
    public Object customfield_10023;
    public Priority priority;
    public Object customfield_10024;
    public Object customfield_10025;
    public Object customfield_10026;
    @Builder.Default
    public List<Object> labels = new ArrayList<>();
    public Object customfield_10016;
    public Object customfield_10017;
    public Customfield_10018 customfield_10018;
    public String customfield_10019;
    public Object timeestimate;
    public Object aggregatetimeoriginalestimate;
    @Builder.Default
    public List<Object> versions = new ArrayList<>();
    @Builder.Default
    public List<Object> issuelinks = new ArrayList<>();
    public Assignee assignee;
    public String updated;
    public Status status;
    @Builder.Default
    public List<Object> components = new ArrayList<>();
    public Object timeoriginalestimate;
    public String description;
    public Object customfield_10010;
    public String customfield_10014;
    public Object customfield_10015;
    public Object customfield_10005;
    public Object customfield_10006;
    public Object security;
    public Object customfield_10007;
    public Object customfield_10008;
    public Object aggregatetimeestimate;
    public Object customfield_10009;
    public String summary;
    public Creator creator;
    @Builder.Default
    public List<Object> subtasks = new ArrayList<>();
    public Reporter reporter;
    public String customfield_10000;
    public Object customfield_10044;
    public Aggregateprogress aggregateprogress;
    public Object customfield_10001;
    public Object customfield_10045;
    public Object customfield_10002;
    public Object customfield_10003;
    public Object customfield_10004;
    public Object customfield_10038;
    public Object customfield_10039;
    public Object environment;
    public Object duedate;
    public Progress progress;
    public Votes votes;

}
