package io.ssafy.soupapi.domain.projectbuilder.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariable;
import io.ssafy.soupapi.global.util.MapStringReplace;
import io.ssafy.soupapi.global.util.StringParserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Repository
@RequiredArgsConstructor
public class ProjectBuilderRepositoryImpl implements ProjectBuilderRepository {
    final String sourcePath = "src/main/resources/templates/springboot-default-project";
    final String destinationPath = "C:\\util\\%s"; // TODO: 환경 변수를 이용하여 경로 변경

    @Override
    public void packageBuilder(Project project) {
        String destination = String.format(destinationPath, project.getProjectBuilderInfo().getName());
        // package name을 기반으로 폴더 생성
//        createDefaultFolders(destination, project.getProjectBuilderInfo().getPackageName());
        // start class 생성
        String application = """
                package com.example.demo;
                                
                import org.springframework.boot.SpringApplication;
                import org.springframework.boot.autoconfigure.SpringBootApplication;
                                
                @SpringBootApplication
                public class :springboot-project-name {
                                
                	public static void main(String[] args) {
                		SpringApplication.run(:springboot-project-name.class, args);
                	}
                                
                }
                """;
        var mapUtil = new MapStringReplace(application);

        String replaced = mapUtil.replace();
    }

    private void createDefaultFolders(String destination, String packageName) {
        String[] packageParts = packageName.split("\\.");
        StringBuilder folderPath = new StringBuilder(destination);
    }

    @Override
    public String ControllerBuilder(ApiDoc apiDoc) {
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

    @Override
    public void copyDefaultProject(Project project) {
        String destination = String.format(destinationPath, project.getProjectBuilderInfo().getName());
        File sourceFolder = new File(sourcePath);
        File destinationFolder = new File(destination);

        try {
            if (destinationFolder.exists()) {
                FileUtils.deleteDirectory(destinationFolder);
            }

            FileUtils.copyDirectory(sourceFolder, destinationFolder);
            log.info("Folder copied successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
