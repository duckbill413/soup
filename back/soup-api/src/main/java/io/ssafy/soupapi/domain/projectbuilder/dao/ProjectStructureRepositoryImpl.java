package io.ssafy.soupapi.domain.projectbuilder.dao;

import io.ssafy.soupapi.domain.projectbuilder.dto.response.ClassFileInfo;
import io.ssafy.soupapi.domain.projectbuilder.dto.response.GetProjectBuilderInfo;
import io.ssafy.soupapi.global.util.BuildFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Log4j2
@Repository
@RequiredArgsConstructor
public class ProjectStructureRepositoryImpl implements ProjectStructureRepository {
    @Value("${springbuilder.build-path}")
    private String saveRootPath;

    @Override
    public Map<String, Object> findProjectStructure(String projectId, GetProjectBuilderInfo builderInfo) {
        String domainPath = getAbsoluteDomainPath(projectId, builderInfo.name(), builderInfo.packageName());

        Map<String, Object> structure = new TreeMap<>();
        // domain folder 에서 시작
        File domainFolder = new File(domainPath);
        searchStructure(domainFolder, structure);
        return structure;
    }

    private void searchStructure(File current, Object structure) {
        if (structure instanceof Map<?, ?>) {
            var folders = current.listFiles(File::isDirectory);
            if (folders != null) {
                for (File folder : folders) {
                    var folderName = folder.getName();
                    // request, response 폴더 인지 확인
                    if (List.of("request", "response").contains(folderName)) {
                        List<ClassFileInfo> subStructure = new ArrayList<>();
                        ((Map<String, Object>) structure).put(folder.getName(), subStructure);
                        searchStructure(folder, subStructure);
                        Collections.sort(subStructure);
                    } else if (List.of("api", "application", "dao", "entity").contains(folderName)) {
                        File[] subFiles = folder.listFiles(File::isFile);
                        assert subFiles != null;
                        for (File subFile : subFiles) {
                            // java 파일이 아닌 경우 읽지 않음
                            if (!BuildFileUtil.getFileExtension(subFile).equals("java")) {
                                continue;
                            }
                            try {
                                var classFileInfo = new ClassFileInfo(subFile.getName(), BuildFileUtil.readAllFile(subFile));
                                ((Map<String, Object>) structure).put(folder.getName(), classFileInfo);
                            } catch (IOException e) {
                                log.info(e.getMessage());
                            }
                        }
                    } else {
                        Map<String, Object> subStructure = new TreeMap<>();
                        ((Map<String, Object>) structure).put(folder.getName(), subStructure);
                        searchStructure(folder, subStructure);
                    }
                }
            }
        } else if (structure instanceof List<?>) {
            var files = current.listFiles(File::isFile);
            if (Objects.isNull(files) || files.length == 0) {
                return;
            }
            ((List<ClassFileInfo>) structure).addAll(Arrays.stream(files).map(file ->
                    {
                        // java 파일이 아닌 경우 읽지 않음
                        if (!BuildFileUtil.getFileExtension(file).equals("java")) {
                            return null;
                        }
                        try {
                            return new ClassFileInfo(file.getName(), BuildFileUtil.readAllFile(file));
                        } catch (IOException e) {
                            log.info(e.getMessage());
                            return null;
                        }
                    }
            ).filter(Objects::nonNull).toList());
        }
    }

    public String getAbsoluteDomainPath(String projectId, String projectName, String packageName) {
        StringBuilder destination = new StringBuilder(String.format(saveRootPath, projectId, projectName));
        String[] path = packageName.split("\\.");
        destination.append(File.separator).append("src").append(File.separator).append("main").append(File.separator).append("java").append(File.separator);
        destination.append(String.join(File.separator, path));
        destination.append(File.separator).append("domain");
        return destination.toString();
    }
}
