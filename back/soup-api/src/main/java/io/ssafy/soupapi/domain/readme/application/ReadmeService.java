package io.ssafy.soupapi.domain.readme.application;

import io.ssafy.soupapi.domain.readme.dao.ReadmeRepository;
import io.ssafy.soupapi.domain.readme.entity.BasicTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadmeService {

    private final ReadmeRepository readmeRepository;

    public BasicTemplate getTemplate(String templateName) {
        System.out.println(readmeRepository.findByTitle(templateName));
        return readmeRepository.findByTitle(templateName);
    }
}
