package io.ssafy.soupapi.domain.springinfo.application;

import io.ssafy.soupapi.domain.springinfo.dto.GetDependency;
import io.ssafy.soupapi.domain.springinfo.dto.GetVersion;

import java.util.List;

public interface SpringInfoService {
    List<GetVersion> usableVersions();

    List<GetDependency> usableDependencies();

}
