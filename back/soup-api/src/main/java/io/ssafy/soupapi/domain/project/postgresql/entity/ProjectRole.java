package io.ssafy.soupapi.domain.project.postgresql.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public enum ProjectRole {
    ADMIN, MAINTAINER, DEVELOPER
}
