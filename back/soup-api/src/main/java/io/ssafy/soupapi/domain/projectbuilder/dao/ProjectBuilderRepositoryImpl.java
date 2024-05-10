package io.ssafy.soupapi.domain.projectbuilder.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariable;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderInfo;
import io.ssafy.soupapi.domain.projectbuilder.entity.MainPath;
import io.ssafy.soupapi.global.util.MapStringReplace;
import io.ssafy.soupapi.global.util.StringParserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

import static io.ssafy.soupapi.global.util.StringParserUtil.*;

@Log4j2
@Repository
@RequiredArgsConstructor
public class ProjectBuilderRepositoryImpl implements ProjectBuilderRepository {
    final String sourcePath = "src/main/resources/templates/springboot-default-project";
    final String domainPath = "src/main/resources/templates/springboot-default-project-domain";
    final String globalPath = "src/main/resources/templates/springboot-default-project-global";
    final String saveRootPath = "C:\\util\\%s\\%s"; // TODO: 환경 변수를 이용하여 경로 변경
    private final ObjectMapper objectMapper;
    final String[] domainSubNames = {"entity", "dao", "application", "api", "dto"};

    @Override
    public void packageBuilder(Project project) throws IOException {
        String destination = getDestination(project);
        // package name을 기반으로 폴더 생성
        createDefaultPackage(destination, project.getProjectBuilderInfo());
    }

    private String getDestination(Project project) {
        return String.format(saveRootPath, project.getId(), project.getProjectBuilderInfo().getName());
    }

    private void createDefaultPackage(String destination, ProjectBuilderInfo projectBuilderInfo) throws IOException {
        String basicPath = destination + "\\src\\main\\java";
        String[] path = projectBuilderInfo.getPackageName().split("\\.");
        StringBuilder folderPath = new StringBuilder(basicPath);

        for (String p : path) {
            folderPath.append(File.separator).append(p);
            File folder = new File(folderPath.toString());
            if (!folder.exists()) {
                folder.mkdir();
            }
        }

        // start class 생성
        String application = """
                package :springboot-project_package;
                                
                import org.springframework.boot.SpringApplication;
                import org.springframework.boot.autoconfigure.SpringBootApplication;
                                
                @SpringBootApplication
                public class :springboot-project-name {
                                
                	public static void main(String[] args) {
                		SpringApplication.run(:springboot-project-name.class, args);
                	}
                                
                }
                """;

        folderPath.append(File.separator).append(projectBuilderInfo.getName()).append("Application.java");
        var mapUtil = new MapStringReplace(application);
        mapUtil.addValue("springboot-project_package", projectBuilderInfo.getPackageName());
        mapUtil.addValue("springboot-project-name", projectBuilderInfo.getName() + "Application");
        String replaced = mapUtil.replace();

        File defaultClass = new File(folderPath.toString());
        FileWriter writer = new FileWriter(defaultClass);
        try (BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(replaced);
            bw.flush();
        }
    }

    @Override
    public String controllerBuilder(ApiDoc apiDoc) {
        String controller = """
                @Operation(summary = ":description")
                :methodType(":apiUriPath")
                public ResponseEntity<BaseResponse<:responseBodyName>> :methodName(
                    :pathVariables
                    :queryParameters
                    :requestBody
                ) {
                    return :serviceClassName.:serviceMethodName
                }
                """;


        var mapUtil = new MapStringReplace(controller);

        mapUtil.addValue("description", apiDoc.getDescription());
        mapUtil.addValue("methodType", switch (apiDoc.getMethodType()) {
            case GET -> "@GetMapping";
            case POST -> "@PostMapping";
            case PUT -> "@PutMapping";
            case DELETE -> "@DeleteMapping";
            case PATCH -> "@PatchMapping";
        });
        mapUtil.addValue("apiUriPath", apiDoc.getApiUriPath());
        mapUtil.addValue("responseBodyName", StringParserUtil.upperFirstAndLowerElse(apiDoc.getResponseBodyName()));
        mapUtil.addValue("methodName", convertToCamelCase(apiDoc.getMethodName()));
        mapUtil.addValue("pathVariables", addPathVariableBuilder(apiDoc.getPathVariables()));
        mapUtil.addValue("queryParameters", addQueryParameters(apiDoc.getQueryParameters()));
        mapUtil.addValue("requestBody", addRequestBody(apiDoc.getRequestBodyName()));
        mapUtil.addValue("serviceClassName", StringParserUtil.convertToPascalCase(apiDoc.getDomain()) + "Service");
        mapUtil.addValue("serviceMethodName", addServiceMethod(apiDoc));

        return mapUtil.replace();
    }

    public void createDefaultProject(Project project) throws IOException {
        // Step1. 폴더 복사 하기
        File destinationFolder = copyDefaultProject(project);

        // Step2. 폴더 변수 설정
        setDefaultProjectVariables(project, destinationFolder);
    }

    private void setDefaultProjectVariables(Project project, File destinationFolder) throws IOException {
        // 기본 변수 설정
        Map<String, String> variables = new HashMap<>();
        variables.put("springboot-project-name", project.getProjectBuilderInfo().getName());
        variables.put("springboot-version", project.getProjectBuilderInfo().getVersion());
        variables.put("springboot-group", project.getProjectBuilderInfo().getGroup());
        variables.put("springboot-java-version", project.getProjectBuilderInfo().getLanguageVersion());

        // dependency 변수 설정
        var dependencies = project.getProjectBuilderInfo().getDependencies();
        StringBuilder sb = new StringBuilder();
        dependencies.forEach(v -> sb.append("\t").append(v.getCode()).append("\n"));
        variables.put("springboot-dependencies", sb.toString());


        List<File> leafFiles = getLeafFiles(destinationFolder);
        for (File file : leafFiles) {
            replaceFileVariables(file, variables);
        }
    }

    private File copyDefaultProject(Project project) throws IOException {
        String destination = getDestination(project);
        File sourceFolder = new File(sourcePath);
        File destinationFolder = new File(destination);

        if (destinationFolder.exists()) {
            FileUtils.deleteDirectory(destinationFolder);
        }

        FileUtils.copyDirectory(sourceFolder, destinationFolder);
        log.info("Default Folder copied successfully!");
        return destinationFolder;
    }

    @Override
    public void createGlobalGroup(Project project) throws IOException {
        String destination = getProjectMainPath(project, MainPath.global);
        // Step 1. copy global folder
        copyGlobalGroup(destination);
        // Step 2. replace global folder variables
        replaceGlobalGroupVariables(project, destination);
    }

    private String getProjectMainPath(Project project, MainPath mainPath) {
        StringBuilder destination = new StringBuilder(getDestination(project));
        String[] path = project.getProjectBuilderInfo().getPackageName().split("\\.");
        destination.append("\\src\\main\\java\\");
        destination.append(String.join("\\", path));
        destination.append(File.separator).append(mainPath.name());
        return destination.toString();
    }

    @Override
    public void createDomainPackages(Project project) throws IOException {
        // 도메인 폴더 생성
        String destination = getProjectMainPath(project, MainPath.domain);

        File destinationFolder = new File(destination);
        if (!destinationFolder.exists()) {
            destinationFolder.mkdir();
        }

        // 도메인 리스트에 대한 각각의 폴더 생성 및 하위 폴더 생성
        var domains = project.getApiDocs().getDomains();
        Map<String, List<File>> domainLeafFiles = new HashMap<>();
        for (String domain : domains) {
            createDomainSubPackages(domain, destination, domainLeafFiles);
        }

        // 파일 변수 치환
        for (String domain : domainLeafFiles.keySet()) {
            replaceDomainSubFiles(domain, domainLeafFiles.get(domain), project);
        }
    }

    private void replaceDomainSubFiles(String domain, List<File> subFiles, Project project) throws IOException {
        Map<String, String> variables = new HashMap<>();
        variables.put("domain-package-name", project.getProjectBuilderInfo().getPackageName() + ".domain");
        variables.put("domain-class-name", StringParserUtil.convertToPascalCase(domain));
        variables.put("domain-method-name", convertToCamelCase(domain));
        variables.put("entity-name", StringParserUtil.convertToPascalCase(domain));
        variables.put("entity-table-name", StringParserUtil.convertToSnakeCase(domain));

        for (File subFile : subFiles) {
            replaceFileVariables(subFile, variables);
        }
    }

    @Override
    public void replaceClassesVariables(Project project) throws IOException {
        String domainFolder = getProjectMainPath(project, MainPath.domain);
        var schema = getProjectSchemaFromERD(project);

        for (TableDefinition tableDefinition : schema.getTables().values()) {
            // Entity 파일 수정
            System.out.println(tableDefinition.getName());
            var entityFilePath = domainFolder + File.separator + tableDefinition.getName();

            for (String domainSubName : domainSubNames) {
                String domainSubPath = entityFilePath + File.separator + domainSubName;
                switch (domainSubName) {
                    case "entity" -> replaceEntityVariables(domainSubPath, tableDefinition);
                    case "dao" -> replaceRepositoryVariables(domainSubPath, tableDefinition);
                    case "application" -> replaceServiceVariables(domainSubPath, tableDefinition);
                }
            }
        }
    }

    private void replaceServiceVariables(String domainSubPath, TableDefinition tableDefinition) {

    }

    private void replaceRepositoryVariables(String domainSubPath, TableDefinition tableDefinition) throws IOException {
        List<File> files = getLeafFiles(new File(domainSubPath));

        Map<String, String> variables = new HashMap<>();
        variables.put("entity-id-type", tableDefinition.getTableIdType());

        for (File file : files) {
            replaceFileVariables(file, variables);
        }
    }

    private void replaceEntityVariables(String domainSubPath, TableDefinition table) throws IOException {
        List<File> files = getLeafFiles(new File(domainSubPath));
        StringBuilder sb = new StringBuilder();

        for (ColumnDefinition column : table.getColumns().values()) {
            try {
                sb.append(column.getColumnVariable()).append('\n');
            } catch (Exception e) {
                log.info("[빌드] 칼럼명: " + column.getName() + " 생성 실패");
            }
        }

        Map<String, String> variables = new HashMap<>();
        variables.put("domain-columns", sb.toString());

        for (File file : files) {
            replaceFileVariables(file, variables);
        }
    }

    private void createDomainSubPackages(String domain, String destination, Map<String, List<File>> domainLeafFiles) throws IOException {
        domain = StringParserUtil.convertToSnakeCase(domain);

        // 도메인 폴더 생성
        File domainFolder = new File(destination + File.separator + domain
                // 도메인 폴더 생성
        );
        if (!domainFolder.exists()) {
            domainFolder.mkdir();
        }

        // 도메인 하위 폴더 생성
        // api, application, dao, dto, entity
        File domainSourceFolder = new File(domainPath);
        FileUtils.copyDirectory(domainSourceFolder, domainFolder);

        List<File> leafFiles = getLeafFiles(domainFolder);
        for (File leafFile : leafFiles) {
            String filePath = leafFile.getAbsolutePath();
            String newFilePath = renameFile(filePath, "Domain", convertToPascalCase(domain));

            File newFile = new File(newFilePath);
            if (leafFile.renameTo(newFile)) {
                if (!domainLeafFiles.containsKey(domain)) {
                    domainLeafFiles.put(domain, new ArrayList<>());
                }
                domainLeafFiles.get(domain).add(newFile);
            }
        }
    }

    private void replaceGlobalGroupVariables(Project project, String destination) throws IOException {
        File destinationFolder = new File(destination);

        // 최하위 노드의 파일 목록을 얻기 위해 재귀 함수 호출
        List<File> leafFiles = getLeafFiles(destinationFolder);

        Map<String, String> variables = new HashMap<>();
        variables.put("springboot-project_package", project.getProjectBuilderInfo().getPackageName());

        // 최하위 노드의 파일 목록 출력
        for (File file : leafFiles) {
            replaceFileVariables(file, variables);
        }
    }

    private static void replaceFileVariables(File file, Map<String, String> variables) throws IOException {
        FileReader fileReader = new FileReader(file.getAbsolutePath());
        try (BufferedReader br = new BufferedReader(fileReader)) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            var mapUtil = new MapStringReplace(sb.toString());
            mapUtil.addAllValues(variables);

            File newFile = new File(file.getAbsolutePath());
            FileWriter writer = new FileWriter(newFile);
            try (BufferedWriter bw = new BufferedWriter(writer)) {
                bw.write(mapUtil.replace());
                bw.flush();
            }
        }
    }

    private void copyGlobalGroup(String destination) throws IOException {
        // global 폴더 생성
        File destinationFolder = new File(destination);
        if (!destinationFolder.exists()) {
            destinationFolder.mkdir();
        }

        File sourceFolder = new File(globalPath);

        FileUtils.copyDirectory(sourceFolder, destinationFolder);
    }

    private String addServiceMethod(ApiDoc apiDoc) {
        String serviceMethodName = convertToCamelCase(apiDoc.getMethodName()) + "(:parameters)";
        List<String> values = new ArrayList<>();
        if (Objects.nonNull(apiDoc.getPathVariables()) && !apiDoc.getPathVariables().isEmpty()) {
            for (ApiVariable pathVariable : apiDoc.getPathVariables()) {
                values.add(pathVariable.getName());
            }
        }
        if (Objects.nonNull(apiDoc.getQueryParameters()) && !apiDoc.getQueryParameters().isEmpty()) {
            for (ApiVariable queryParameter : apiDoc.getQueryParameters()) {
                values.add(queryParameter.getName());
            }
        }
        if (Objects.nonNull(apiDoc.getRequestBodyName())) {
            values.add(apiDoc.getRequestBodyName());
        }
        serviceMethodName = serviceMethodName.replaceAll(":parameters", String.join(", ", values));
        return serviceMethodName;
    }

    private String addRequestBody(String requestBodyName) {
        if (Objects.isNull(requestBodyName)) {
            return "";
        }
        String className = Character.toUpperCase(requestBodyName.charAt(0)) + requestBodyName.substring(1);
        String paramName = Character.toLowerCase(requestBodyName.charAt(0)) + requestBodyName.substring(1);
        return String.format("@RequestBody %s %s", className, paramName);
    }

    private String addPathVariableBuilder(List<ApiVariable> apiVariables) {
        if (Objects.isNull(apiVariables) || apiVariables.isEmpty()) {
            return "";
        }
        List<String> strPathVariable = new ArrayList<>();
        for (ApiVariable pathVariable : apiVariables) {
            String strPath = "@PathVariable(name = :name :require) :type :name";
            String replaced = strPath.replaceAll(":name", pathVariable.getName());
            replaced = replaced.replaceAll(":type", pathVariable.getType().name());
            if (pathVariable.isRequire()) {
                replaced = replaced.replaceAll(":replace", ", required = false");
            } else {
                replaced = replaced.replaceAll(":replace", "");
            }
            strPathVariable.add(replaced);
        }
        return String.join(",\n", strPathVariable);
    }

    private String addQueryParameters(List<ApiVariable> apiVariables) {
        if (Objects.isNull(apiVariables) || apiVariables.isEmpty()) {
            return "";
        }
        List<String> strPathVariable = new ArrayList<>();
        for (ApiVariable queryParam : apiVariables) {
            String strQuery = "@RequestParam(name = :name :require :defaultValue) :type :name";
            String replaced = strQuery.replaceAll(":name", queryParam.getName());
            replaced = replaced.replaceAll(":type", queryParam.getType().name());
            if (!queryParam.isRequire()) {
                replaced = replaced.replaceAll(":replace", ", required = false");
            } else {
                replaced = replaced.replaceAll(":replace", "");
            }
            if (Objects.nonNull(queryParam.getDefaultVariable())) {
                replaced = replaced.replaceAll(":defaultValue", String.format(", defaultValue = \"%s\"", queryParam.getDefaultVariable()));
            } else {
                replaced = replaced.replaceAll(":defaultValue", "");
            }
            strPathVariable.add(replaced);
        }
        return String.join(",\n", strPathVariable);
    }

    private List<File> getLeafFiles(File folder) {
        List<File> leafFiles = new ArrayList<>();

        // 폴더 내의 모든 파일과 하위 폴더 얻기
        File[] files = folder.listFiles();
        if (Objects.isNull(files)) {
            return leafFiles;
        }
        // 파일과 하위 폴더 순회
        for (File file : files) {
            if (file.isDirectory()) {
                // 하위 폴더인 경우 재귀 호출
                leafFiles.addAll(getLeafFiles(file));
            } else {
                // 파일인 경우 리스트에 추가
                leafFiles.add(file);
            }
        }

        return leafFiles;
    }

    /**
     * 프로젝트 도메인 리스트 정보
     *
     * @param project 프로젝트 ID
     * @return 프로젝트 도메인 리스트
     */
    private SchemaDefinition getProjectSchemaFromERD(Project project) {
        var vuerdObj = project.getVuerd();
        Set<String> usableNames = new LinkedHashSet<>();
        Map<String, TableDefinition> tables = new HashMap<>();
        Map<String, TableRelationDefinition> relations = new HashMap<>();

        if (vuerdObj instanceof LinkedHashMap<?, ?>) {
            vuerdObj = ((LinkedHashMap<?, ?>) vuerdObj).get("$set");

            if (vuerdObj instanceof LinkedHashMap<?, ?>) {
                var doc = ((LinkedHashMap<?, ?>) vuerdObj).get("doc");
                if (doc instanceof LinkedHashMap<?, ?>) {
                    var tableIdsObj = ((LinkedHashMap<?, ?>) doc).get("tableIds");
                    if (tableIdsObj instanceof List<?>) {
                        ((List<?>) tableIdsObj).forEach(tableId -> usableNames.add(tableId.toString()));
                    }
                    var relationshipIdsObj = ((LinkedHashMap<?, ?>) doc).get("relationshipIds");
                    if (relationshipIdsObj instanceof List<?>) {
                        ((List<?>) relationshipIdsObj).forEach(relationshipId -> usableNames.add(relationshipId.toString()));
                    }
                }

                // collections 파싱
                var collections = ((LinkedHashMap<?, ?>) vuerdObj).get("collections");

                if (collections instanceof LinkedHashMap<?, ?>) {
                    var tableEntities = ((LinkedHashMap<?, ?>) collections).get("tableEntities");
                    var tableColumnEntities = ((LinkedHashMap<?, ?>) collections).get("tableColumnEntities");
                    var relationshipEntities = ((LinkedHashMap<?, ?>) collections).get("relationshipEntities");

                    // 테이블 정보 파싱
                    if (tableEntities instanceof LinkedHashMap<?, ?>) {
                        for (Object key : ((LinkedHashMap<?, ?>) tableEntities).keySet()) {
                            if (!usableNames.contains(key.toString())) {
                                continue;
                            }
                            var tableDefinition = objectMapper.convertValue(((LinkedHashMap<?, ?>) tableEntities).get(key), TableDefinition.class);
                            tables.put(key.toString(), tableDefinition);
                        }
                    }

                    // 칼럼 정보 파싱
                    if (tableColumnEntities instanceof LinkedHashMap<?, ?>) {
                        for (Object key : ((LinkedHashMap<?, ?>) tableColumnEntities).keySet()) {
                            var tableColumnEntity = objectMapper.convertValue(((LinkedHashMap<?, ?>) tableColumnEntities).get(key), ColumnDefinition.class);
                            // 테이블이 존재한다면
                            if (tables.containsKey(tableColumnEntity.getTableId())) {
                                tables.get(tableColumnEntity.getTableId()).getColumns().put(key.toString(), tableColumnEntity);
                            }
                        }
                    }

                    // 릴레이션 정보 파싱
                    if (relationshipEntities instanceof LinkedHashMap<?, ?>) {
                        for (Object key : ((LinkedHashMap<?, ?>) relationshipEntities).keySet()) {
                            if (!usableNames.contains(key.toString())) {
                                continue;
                            }
                            var tableRelationDefinition = objectMapper.convertValue(((LinkedHashMap<?, ?>) relationshipEntities).get(key), TableRelationDefinition.class);
                            relations.put(key.toString(), tableRelationDefinition);
                        }
                    }
                }
            }
        }

        return new SchemaDefinition(tables, relations);
    }
}
