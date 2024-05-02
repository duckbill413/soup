package io.ssafy.soupapi.domain.project.mongodb.entity.vuerd;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Column{
    public String id;
    public String name;
    public String comment;
    public String dataType;
    @JsonProperty("default") 
    public String mydefault;
    public Option option;
    public Ui ui;
}
