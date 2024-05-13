package io.ssafy.soupapi.domain.projectbuilder.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDocs;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariable;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderInfo;
import io.ssafy.soupapi.domain.projectbuilder.entity.MainPath;
import io.ssafy.soupapi.global.util.MapStringReplace;
import io.ssafy.soupapi.global.util.RecordClassGenerator;
import io.ssafy.soupapi.global.util.StringParserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

import static io.ssafy.soupapi.global.util.BuildFileUtil.getLeafFiles;
import static io.ssafy.soupapi.global.util.StringParserUtil.*;

@Log4j2
@Repository
@RequiredArgsConstructor
public class ProjectBuilderRepositoryImpl implements ProjectBuilderRepository {
    private final RecordClassGenerator recordClassGenerator;
    private final ObjectMapper objectMapper;
    final String sourcePath = "src/main/resources/templates/springboot-default-project";
    final String domainPath = "src/main/resources/templates/springboot-default-project-domain";
    final String globalPath = "src/main/resources/templates/springboot-default-project-global";
    final Map<String, String> baseDtoPackage = new HashMap<>(Map.of(
            "REQUEST", "import %s.dto.request.*;",
            "RESPONSE", "import %s.dto.response.*;"
    ));
    @Value("${springbuilder.path}")
    private String saveRootPath;

    /**
     * 프로젝트 패키지 구조 설정
     *
     * @param project 프로젝트 정보
     * @throws IOException 파일 쓰기 에러
     */
    @Override
    public void packageBuilder(Project project) throws IOException {
        String destination = getDestination(project);
        // package name을 기반으로 폴더 생성
        createStarterClass(destination, project.getProjectBuilderInfo());
    }

    /**
     * 생성할 프로젝트 저장 경로
     *
     * @param project 프로젝트 정보
     * @return 프로젝트 저장 경로
     */
    private String getDestination(Project project) {
        return String.format(saveRootPath, project.getId(), project.getProjectBuilderInfo().getName());
    }

    /**
     * 프로젝트 기본 클래스 생성
     * 스프링 시작을 위한 기본 클래스 생성
     *
     * @param destination        프로젝트 경로
     * @param projectBuilderInfo 프로젝트 빌드 정보
     * @throws IOException 파일 입출력 에러
     */
    private void createStarterClass(String destination, ProjectBuilderInfo projectBuilderInfo) throws IOException {
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

        folderPath.append(File.separator).append(convertToPascalCase(projectBuilderInfo.getName())).append("Application.java");
        var mapUtil = new MapStringReplace(application);
        mapUtil.addValue("springboot-project_package", projectBuilderInfo.getPackageName());
        mapUtil.addValue("springboot-project-name", convertToPascalCase(projectBuilderInfo.getName()) + "Application");
        String replaced = mapUtil.replace();

        File defaultClass = new File(folderPath.toString());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(defaultClass))) {
            bw.write(replaced);
            bw.flush();
        }
    }

    /**
     * 기본 패키지 구성 생성
     *
     * @param project 프로젝트 정보
     * @return
     * @throws IOException 파일 입출력 에러
     */
    public File createDefaultProject(Project project) throws IOException {
        // Step1. 폴더 복사 하기
        File destinationFolder = copyDefaultProject(project);

        // Step2. 폴더 변수 설정
        setDefaultProjectVariables(project, destinationFolder);

        return destinationFolder;
    }

    /**
     * 생성된 기본 프로젝트 변수 설정
     *
     * @param project           프로젝트 정보
     * @param destinationFolder 프로젝트 경로
     * @throws IOException 파일 입출력 에러
     */
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
        dependencies.forEach(v -> {
            String[] lines = v.getCode().split("\n");
            for (String line : lines) {
                sb.append("\t").append(line).append('\n');
            }
        });
        sb.append('\n');
        variables.put("springboot-dependencies", sb.toString());


        List<File> leafFiles = getLeafFiles(destinationFolder);
        for (File file : leafFiles) {
            replaceFileVariables(file, variables);
        }
    }

    /**
     * 스프링 기본 프로젝트 복사
     *
     * @param project 프로젝트 정보
     * @return 복사 완료된 File
     * @throws IOException 파일 입출력 에러
     */
    private File copyDefaultProject(Project project) throws IOException {
        String destination = getDestination(project);
        File sourceFolder = new File(sourcePath);
        File destinationFolder = new File(destination);

        if (destinationFolder.exists()) {
            FileUtils.deleteDirectory(destinationFolder);
        }

        FileUtils.copyDirectory(sourceFolder, destinationFolder);
        return destinationFolder;
    }

    /**
     * Global 패키지 구조 생성
     *
     * @param project 프로젝트 정보
     * @throws IOException 파일 입출력 에러
     */
    @Override
    public void createGlobalGroup(Project project) throws IOException {
        String destination = getProjectMainAbsolutePath(project, MainPath.global);
        // Step 1. copy global folder
        copyGlobalGroup(destination);
        // Step 2. replace global folder variables
        replaceGlobalGroupVariables(project, destination);
    }

    /**
     * 프로젝트 Global, Domain 패키지 경로
     *
     * @param project  프로젝트 정보
     * @param mainPath global, domain
     * @return 패키지 경로
     */
    private String getProjectMainAbsolutePath(Project project, MainPath mainPath) {
        StringBuilder destination = new StringBuilder(getDestination(project));
        String[] path = project.getProjectBuilderInfo().getPackageName().split("\\.");
        destination.append("\\src\\main\\java\\");
        destination.append(String.join("\\", path));
        destination.append(File.separator).append(mainPath.name());
        return destination.toString();
    }

    /**
     * Domain Package 생성
     *
     * @param project 프로젝트 정보
     * @throws IOException 파일 입출력 에러
     */
    @Override
    public void createDomainPackages(Project project) throws IOException {
        // 도메인 폴더 생성
        String destination = getProjectMainAbsolutePath(project, MainPath.domain);

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

    /**
     * 도메인 서브 파일들의 패키지 경로 메소드 이름 등의 변수 치환
     *
     * @param domain   도메인 이름
     * @param subFiles 서버 파일 리스트
     * @param project  프로젝트 정보
     * @throws IOException 파일 입출력 에러
     */
    private void replaceDomainSubFiles(String domain, List<File> subFiles, Project project) throws IOException {
        Map<String, String> variables = new HashMap<>();
        variables.put("domain-package-name", project.getProjectBuilderInfo().getPackageName() + ".domain." + convertToSnakeCase(domain));
        variables.put("domain-class-name", StringParserUtil.convertToPascalCase(domain));
        variables.put("domain-method-name", convertToCamelCase(domain));
        variables.put("entity-name", StringParserUtil.convertToPascalCase(domain));
        variables.put("entity-table-name", StringParserUtil.convertToSnakeCase(domain));

        for (File subFile : subFiles) {
            replaceFileVariables(subFile, variables);
        }
    }

    /**
     * 클래스 파일들의 변수 치환
     * api, application, dao, entity의 변수 치환
     *
     * @param project 프로젝트 정보
     * @throws IOException 파일 입출력 에러
     */
    @Override
    public void replaceClassesVariables(Project project) throws IOException {
        String domainFolder = getProjectMainAbsolutePath(project, MainPath.domain);
        var schema = getProjectSchemaFromERD(project);

        for (TableDefinition tableDefinition : schema.getTables().values()) {
            // Entity 파일 수정
            var entityFilePath = domainFolder + File.separator + tableDefinition.getName();

            // Entity 변수 치환
            replaceEntityVariables(entityFilePath + File.separator + "entity", tableDefinition);
            // DAO 변수 치환
            replaceRepositoryVariables(entityFilePath + File.separator + "dao", tableDefinition);
        }
    }

    @Override
    public void insertEntityRelationShip(Project project) throws IOException {
        String domainAbsolutePath = getProjectMainAbsolutePath(project, MainPath.domain);
        String domainPackage = project.getProjectBuilderInfo().getPackageName() + ".domain";
        var schema = getProjectSchemaFromERD(project);
        var tables = schema.getTables();

        for (TableRelationDefinition relation : schema.getRelations().values()) {
            var parent = tables.get(relation.getParentTableId());
            var child = tables.get(relation.getChildTableId());
            // 부모 Entity 파일 경로
            var parentEntityPath = domainAbsolutePath + File.separator + convertToSnakeCase(parent.getName()) + File.separator + "entity" + File.separator + convertToPascalCase(parent.getName()) + ".java";
            String parentImportBuilder = schema.getParentImportBuilder(relation.getId(), domainPackage);
            String parentRelationBuilder = schema.getParentRelationBuilder(relation.getId());

            insertImportIntoFile(new File(parentEntityPath), parentImportBuilder);
            insertRelationIntoFile(new File(parentEntityPath), parentRelationBuilder);

            // 자식 Entity 파일 경로
            var childEntityPath = domainAbsolutePath + File.separator + convertToSnakeCase(child.getName()) + File.separator + "entity" + File.separator + convertToPascalCase(child.getName()) + ".java";
            String childImportBuilder = schema.getChildImportBuilder(relation.getId(), domainPackage);
            String childRelationBuilder = schema.getChildRelationBuilder(relation.getId());

            insertImportIntoFile(new File(childEntityPath), childImportBuilder);
            insertRelationIntoFile(new File(childEntityPath), childRelationBuilder);
        }
    }

    @Override
    public void createDtoClassFiles(Project project) throws IOException {
        String domainPackage = project.getProjectBuilderInfo().getPackageName() + ".domain";
        String domainAbsolutePath = getProjectMainAbsolutePath(project, MainPath.domain);
        ApiDocs apiDocs = project.getApiDocs();

        Set<String> domainReqDtoPackage = new HashSet<>();
        Set<String> domainResDtoPackage = new HashSet<>();

        List<ApiDoc> apiDocList = project.getApiDocs().getApiDocList();
        for (ApiDoc apiDoc : apiDocList) {
            String subDomainPackage = domainPackage + String.format(".%s.dto", convertToSnakeCase(apiDoc.getDomain()));
            String subDomainAbsoluteDtoPath = domainAbsolutePath + File.separator + convertToSnakeCase(apiDoc.getDomain()) + File.separator + "dto";

            // Generate RequestBody
            if (apiDoc.getResponseBody() != null && !apiDoc.getRequestBody().isBlank()) {
                recordClassGenerator.generateRecordClasses(
                        apiDoc.getRequestBody(),
                        apiDoc.getRequestBodyName(),
                        subDomainPackage + ".request",
                        subDomainAbsoluteDtoPath + File.separator + "request"
                );

                // Request import 문 삽입
                if (!domainReqDtoPackage.contains(apiDoc.getDomain())) {
                    domainReqDtoPackage.add(apiDoc.getDomain());
                    String importStrPath = domainAbsolutePath + File.separator + convertToSnakeCase(apiDoc.getDomain()) + File.separator;
                    String importStr = String.format(baseDtoPackage.get("REQUEST"), domainPackage + "." + convertToSnakeCase(apiDoc.getDomain()));
                    insertImportIntoFile(new File(importStrPath + String.format("api\\%s", convertToPascalCase(apiDoc.getDomain()) + "Controller.java")), importStr);
                    insertImportIntoFile(new File(importStrPath + String.format("application\\%s", convertToPascalCase(apiDoc.getDomain()) + "Service.java")), importStr);
                }
            }

            // Generate ResponseBody
            if (apiDoc.getResponseBody() != null && !apiDoc.getResponseBody().isBlank()) {
                recordClassGenerator.generateRecordClasses(
                        apiDoc.getResponseBody(),
                        apiDoc.getResponseBodyName(),
                        subDomainPackage + ".response",
                        subDomainAbsoluteDtoPath + File.separator + "response"
                );

                // Response import 문 삽입
                if (!domainResDtoPackage.contains(apiDoc.getDomain())) {
                    domainResDtoPackage.add(apiDoc.getDomain());
                    String importStrPath = domainAbsolutePath + File.separator + convertToSnakeCase(apiDoc.getDomain()) + File.separator;
                    String importStr = String.format(baseDtoPackage.get("RESPONSE"), domainPackage + "." + convertToSnakeCase(apiDoc.getDomain()));
                    insertImportIntoFile(new File(importStrPath + String.format("api\\%s", convertToPascalCase(apiDoc.getDomain()) + "Controller.java")), importStr);
                    insertImportIntoFile(new File(importStrPath + String.format("application\\%s", convertToPascalCase(apiDoc.getDomain()) + "Service.java")), importStr);
                }
            }
        }
    }

    @Override
    public void projectMethodBuilder(Project project) throws IOException {
        String domainPath = getProjectMainAbsolutePath(project, MainPath.domain);
        ApiDocs apiDocs = project.getApiDocs();
        var apiDocList = project.getApiDocs().getApiDocList();
        var usableDomains = new HashSet<>(apiDocs.getDomains().stream().map(String::toUpperCase).toList());

        for (ApiDoc apiDoc : apiDocList) {
            // 유효하지 않은 도메인의 경우 API를 생성하지 않음
            if (!usableDomains.contains(apiDoc.getDomain().toUpperCase())) {
                continue;
            }

            String domainSubPath = domainPath + File.separator + apiDoc.getDomain() + File.separator;

            String controllerMethod = createControllerMethod(apiDoc);
            List<File> cFiles = getLeafFiles(new File(domainSubPath + "api"));
            for (File cFile : cFiles) {
                insertMethodIntoFile(cFile, controllerMethod);
            }

            String serviceMethod = createServiceMethod(apiDoc);
            List<File> sFiles = getLeafFiles(new File(domainSubPath + "application"));
            for (File sFile : sFiles) {
                insertMethodIntoFile(sFile, serviceMethod);
            }
        }
    }


    /**
     * 프로젝트 Controller 메소드
     *
     * @param apiDoc 프로젝트 API 문서
     */
    private String createControllerMethod(ApiDoc apiDoc) {
        String controllerMethod = """
                    @Operation(summary = ":api-summary", description = ":api-description")
                    :api-http-method(":api-uri")
                    public :responsebody-name :controller-method-name(
                    :parameters) {
                        return null;
                    }
                """;

        var mapUtil = new MapStringReplace(controllerMethod);
        mapUtil.addValue("api-summary", apiDoc.getName() != null ? apiDoc.getName() : null);
        mapUtil.addValue("api-description", apiDoc.getDescription() != null ? apiDoc.getDescription() : null);
        mapUtil.addValue("api-http-method", apiDoc.getHttpMethod());
        mapUtil.addValue("api-uri", apiDoc.getApiUriPath() != null ? apiDoc.getApiUriPath().replaceAll(" ", "") : "");
        mapUtil.addValue("responsebody-name", apiDoc.getResponseBodyName());
        mapUtil.addValue("controller-method-name", convertToCamelCase(apiDoc.getMethodName()));

        List<String> parameters = new ArrayList<>();
        // PathVariables
        if (Objects.nonNull(apiDoc.getPathVariables()) && !apiDoc.getPathVariables().isEmpty()) {
            for (ApiVariable pathVariable : apiDoc.getPathVariables()) {
                parameters.add(makeControllerPathVariableAttr(pathVariable));
            }
        }
        // QueryParameters
        if (Objects.nonNull(apiDoc.getQueryParameters()) && !apiDoc.getQueryParameters().isEmpty()) {
            for (ApiVariable queryVariable : apiDoc.getQueryParameters()) {
                parameters.add(makeControllerQueryVariableAttr(queryVariable));
            }
        }
        // RequestBody
        if (Objects.nonNull(apiDoc.getRequestBody()) && !apiDoc.getRequestBody().isBlank()) {
            parameters.add(makeControllerRequestBodyAttr(apiDoc));
        }

        mapUtil.addValue("parameters", String.join(",\n\t\t\t", parameters));
        return mapUtil.replace();
    }

    private String makeControllerRequestBodyAttr(ApiDoc apiDoc) {
        String attr = "@RequestBody %s %s";
        return String.format(attr,
                apiDoc.getRequestBodyName(),
                convertToCamelCase(apiDoc.getRequestBodyName())
        );
    }

    private String makeControllerQueryVariableAttr(ApiVariable queryVariable) {
        String attr = "@RequestParam(name = \"%s\"%s%s) %s %s";
        return String.format(attr,
                queryVariable.getName().replaceAll(" ", ""),
                queryVariable.isRequire() ? "" : ", required = false", // default is true
                isNullOrEmpty(queryVariable.getDefaultVariable()) ? "" : String.format(", defaultValue = \"%s\"", queryVariable.getDefaultVariable()),
                convertToPascalCase(queryVariable.getType().name().replaceAll(" ", "")),
                convertToCamelCase(queryVariable.getName())
        );
    }

    private String makeControllerPathVariableAttr(ApiVariable apiVariable) {
        String attr = "@PathVariable(name = \"%s\"%s) %s %s";
        return String.format(attr,
                apiVariable.getName().replaceAll(" ", ""),
                apiVariable.isRequire() ? "" : ", required = false", // default is true
                convertToPascalCase(apiVariable.getType().name().replaceAll(" ", "")),
                convertToCamelCase(apiVariable.getName())
        );
    }

    /**
     * 프로젝트 Service 메소드
     *
     * @param apiDoc 프로젝트 API 문서
     */
    private String createServiceMethod(ApiDoc apiDoc) {
        String serviceMethod = """
                    public :responsebody-name :service-method-name(:parameters) {
                        return null;
                    }
                """;

        var mapUtil = new MapStringReplace(serviceMethod);
        mapUtil.addValue("responsebody-name", apiDoc.getResponseBodyName());
        mapUtil.addValue("service-method-name", convertToCamelCase(apiDoc.getMethodName()));

        List<String> parameters = new ArrayList<>();
        // PathVariables
        if (Objects.nonNull(apiDoc.getPathVariables()) && !apiDoc.getPathVariables().isEmpty()) {
            for (ApiVariable pathVariable : apiDoc.getPathVariables()) {
                parameters.add(makeMethodVariableAttr(pathVariable.getName(), pathVariable.getType().name()));
            }
        }
        // QueryParameters
        if (Objects.nonNull(apiDoc.getQueryParameters()) && !apiDoc.getQueryParameters().isEmpty()) {
            for (ApiVariable queryVariable : apiDoc.getQueryParameters()) {
                parameters.add(makeMethodVariableAttr(queryVariable.getName(), queryVariable.getType().name()));
            }
        }
        // RequestBody
        if (Objects.nonNull(apiDoc.getRequestBody()) && !apiDoc.getRequestBody().isBlank()) {
            parameters.add(makeMethodVariableAttr(apiDoc.getRequestBodyName(), apiDoc.getRequestBodyName()));
        }

        mapUtil.addValue("parameters", String.join(", ", parameters));

        return mapUtil.replace();
    }

    private String makeMethodVariableAttr(String name, String type) {
        // ObjectId projectId
        return String.format("%s %s",
                convertToPascalCase(type),
                convertToCamelCase(name)
        );
    }

    /**
     * 프로젝트 DAO 변수 치환
     *
     * @param domainSubPath   {projectpath}/domain/{domain}/dao
     * @param tableDefinition 테이블 정보
     * @throws IOException 파일 쓰기 실패
     */
    private void replaceRepositoryVariables(String domainSubPath, TableDefinition tableDefinition) throws IOException {
        List<File> files = getLeafFiles(new File(domainSubPath));

        Map<String, String> variables = new HashMap<>();
        variables.put("entity-id-type", tableDefinition.getTableIdType());

        for (File file : files) {
            replaceFileVariables(file, variables);
        }
    }

    /**
     * 프로젝트 Entity 변수 치환
     *
     * @param domainSubPath   {projectpath}/domain/{domain}/entity
     * @param tableDefinition 테이블 정보
     * @throws IOException 파일 쓰기 실패
     */
    private void replaceEntityVariables(String domainSubPath, TableDefinition tableDefinition) throws IOException {
        List<File> files = getLeafFiles(new File(domainSubPath));
        StringBuilder sb = new StringBuilder();
        Map<String, String> columns = new HashMap<>();

        for (String columnId : tableDefinition.getColumnIds()) {
            ColumnDefinition column = tableDefinition.getColumns().get(columnId);
            if (column.isForeignKey()) {
                continue;
            }
            try {
                columns.put(column.getValidParamName(), column.getColumnVariable());
            } catch (Exception e) {
                log.info("[빌드] 칼럼명: " + column.getName() + " 생성 실패");
            }
        }

        for (String value : columns.values()) {
            sb.append(value).append('\n');
        }

        Map<String, String> variables = new HashMap<>();
        variables.put("domain-columns", sb.toString());

        for (File file : files) {
            replaceFileVariables(file, variables);
        }
    }

    /**
     * Domain package의 서브 폴더 생성
     *
     * @param domain          도메인 이름
     * @param destination     도메인 경로
     * @param domainLeafFiles 도메인 최하위 파일 (File)
     * @throws IOException 파일 입출력 에러
     */
    private void createDomainSubPackages(String domain, String destination, Map<String, List<File>> domainLeafFiles) throws IOException {
        domain = StringParserUtil.convertToSnakeCase(domain);

        // 도메인 폴더 생성
        File domainFolder = new File(destination + File.separator + domain);
        // 도메인 폴더 생성
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

    /**
     * global package 변수 설정
     * 패키지 경로 등의 변수 설정
     *
     * @param project     프로젝트 정보
     * @param destination global 패키지 경로
     * @throws IOException 파일 입출력 에러
     */
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

    /**
     * 파일 변수 치환
     * 파일 정보 및 치환할 변수 맵을 이용하여 파일 문자열 치환
     *
     * @param file      파일 정보
     * @param variables 치환할 문자 맵
     * @throws IOException 파일 입출력 에러
     */
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
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile))) {
                bw.write(mapUtil.replace());
                bw.flush();
            }
        }
    }

    /**
     * 파일 메소드 삽입
     * 파일 정보 및 메소드 문자열을 이용해 메소드 삽입
     *
     * @param file      파일 정보
     * @param methodStr 삽입할 함수 문자열
     * @throws IOException 파일 입출력 에러
     */
    private void insertMethodIntoFile(File file, String methodStr) throws IOException {
        FileReader fileReader = new FileReader(file.getAbsolutePath());
        try (BufferedReader br = new BufferedReader(fileReader)) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }

            int closingBraceIndex = sb.toString().lastIndexOf('}');
            if (closingBraceIndex != -1) {
                sb.insert(closingBraceIndex, "\n" + methodStr);
            }

            File newFile = new File(file.getAbsolutePath());
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile))) {
                bw.write(sb.toString());
                bw.flush();
            }
        }
    }

    private void insertImportIntoFile(File file, String importStr) throws IOException {
        FileReader fileReader = new FileReader(file.getAbsolutePath());
        try (BufferedReader br = new BufferedReader(fileReader)) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }

            int packageIndex = sb.indexOf("package");
            if (packageIndex == -1) {
                packageIndex = 0;
            }

            int insertIndex = sb.indexOf(";", packageIndex) + 1;
            sb.insert(insertIndex, '\n' + importStr);

            File newFile = new File(file.getAbsolutePath());
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile))) {
                bw.write(sb.toString());
                bw.flush();
            }
        }
    }

    private void insertRelationIntoFile(File file, String relationStr) throws IOException {
        FileReader fileReader = new FileReader(file.getAbsolutePath());
        try (BufferedReader br = new BufferedReader(fileReader)) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }

            int closingBraceIndex = sb.toString().lastIndexOf('}');
            if (closingBraceIndex != -1) {
                sb.insert(closingBraceIndex, "\n" + relationStr);
            }

            File newFile = new File(file.getAbsolutePath());
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile))) {
                bw.write(sb.toString());
                bw.flush();
            }
        }
    }

    /**
     * global 기본 패키지 복사
     *
     * @param destination 복사할 경로
     * @throws IOException 파일 입출력 에러
     */
    private void copyGlobalGroup(String destination) throws IOException {
        // global 폴더 생성
        File destinationFolder = new File(destination);
        if (!destinationFolder.exists()) {
            destinationFolder.mkdir();
        }

        File sourceFolder = new File(globalPath);

        FileUtils.copyDirectory(sourceFolder, destinationFolder);
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
