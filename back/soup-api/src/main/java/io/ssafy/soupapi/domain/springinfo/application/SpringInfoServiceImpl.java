package io.ssafy.soupapi.domain.springinfo.application;

import io.ssafy.soupapi.domain.springinfo.dao.SpringDependencyRepository;
import io.ssafy.soupapi.domain.springinfo.dao.SpringVersionRepository;
import io.ssafy.soupapi.domain.springinfo.dto.GetDependency;
import io.ssafy.soupapi.domain.springinfo.dto.GetVersion;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class SpringInfoServiceImpl implements SpringInfoService {
    private final SpringDependencyRepository dependencyRepository;
    private final SpringVersionRepository versionRepository;

    @Override
    public List<GetVersion> usableVersions() {
        var versions = versionRepository.findAll();
        return versions.stream().map(v -> new GetVersion(v.getVersion())).toList();
    }

    @Override
    public List<GetDependency> usableDependencies() {
        var dependencies = dependencyRepository.findAll();
        return dependencies.stream().map(GetDependency::of).sorted((a, b) -> {
            if (a.basic() == b.basic()) return 0;
            return a.basic() ? -1 : 1;
        }).toList();
    }
}
