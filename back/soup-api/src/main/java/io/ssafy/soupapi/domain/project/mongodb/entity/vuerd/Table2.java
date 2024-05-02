package io.ssafy.soupapi.domain.project.mongodb.entity.vuerd;

import java.util.List;

public class Table2{
    public String id;
    public String name;
    public String comment;
    public List<Column> columns;
    public Ui ui;
    public boolean visible;
}
