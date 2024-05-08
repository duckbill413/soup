package io.ssafy.soupapi.domain.projectbuilder.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariable;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderInfo;
import io.ssafy.soupapi.global.util.MapStringReplace;
import io.ssafy.soupapi.global.util.StringParserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Log4j2
@Repository
@RequiredArgsConstructor
public class ProjectBuilderRepositoryImpl implements ProjectBuilderRepository {
    final String globalPath = "src/main/resources/templates/springboot-default-project-global";
    final String sourcePath = "src/main/resources/templates/springboot-default-project";
    final String destinationPath = "C:\\util\\%s\\%s"; // TODO: 환경 변수를 이용하여 경로 변경

    @Override
    public void packageBuilder(Project project) throws IOException {
        String destination = getDestination(project);
        // package name을 기반으로 폴더 생성
        createDefaultPackage(destination, project.getProjectBuilderInfo());
    }

    private String getDestination(Project project) {
        return String.format(destinationPath, project.getId(), project.getProjectBuilderInfo().getName());
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
        mapUtil.addValue("responseBodyName", StringParserUtil.upperFirst(apiDoc.getResponseBodyName()));
        mapUtil.addValue("methodName", StringParserUtil.lowerFirst(apiDoc.getMethodName()));
        mapUtil.addValue("pathVariables", addPathVariableBuilder(apiDoc.getPathVariables()));
        mapUtil.addValue("queryParameters", addQueryParameters(apiDoc.getQueryParameters()));
        mapUtil.addValue("requestBody", addRequestBody(apiDoc.getRequestBodyName()));
        mapUtil.addValue("serviceClassName", StringParserUtil.lowerFirst(apiDoc.getDomain()) + "Service");
        mapUtil.addValue(":serviceMethodName", addServiceMethod(apiDoc));

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
        String[] path = project.getProjectBuilderInfo().getPackageName().split("\\.");
        StringBuilder destination = new StringBuilder(getDestination(project));
        destination.append("\\src\\main\\java\\");
        destination.append(String.join("\\", path));
        destination.append(File.separator).append("global");
        // Step 1. copy global folder
        copyGlobalGroup(project, destination.toString());
        // Step 2. replace global folder variables
        replaceGlobalGroupVariables(project, destination.toString());
    }

    @Override
    public void createDomainPackages(Project project) {

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

    private void copyGlobalGroup(Project project, String destination) throws IOException {
        // global 폴더 생성
        File destinationFolder = new File(destination);
        if (!destinationFolder.exists()) {
            destinationFolder.mkdir();
        }

        File sourceFolder = new File(globalPath);

        FileUtils.copyDirectory(sourceFolder, destinationFolder);
        log.info("Global Folder copied successfully");
    }

    private String addServiceMethod(ApiDoc apiDoc) {
        String serviceMethodName = StringParserUtil.lowerFirst(apiDoc.getMethodName()) + "(:parameters)";
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
}
