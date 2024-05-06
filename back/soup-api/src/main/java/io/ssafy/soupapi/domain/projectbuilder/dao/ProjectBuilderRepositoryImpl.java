package io.ssafy.soupapi.domain.projectbuilder.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDocs;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariable;
import io.ssafy.soupapi.global.util.MapStringReplace;
import io.ssafy.soupapi.global.util.StringParserUtil;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProjectBuilderRepositoryImpl {
    public void ProjectBuild(ApiDocs apiDocs) {
        Map<String, String> names = new HashMap<>();
//        names.put("springboot-project-name", );

        // Package Builder
        packageBuilder(names);
        // Controller Builder


        // Service Builder
        // Repository Builder
        // Entity Builder
        // DTO Builder
    }

    private void packageBuilder(Map<String, String> names) {
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
        mapUtil.addAllValues(names);

        String replaced = mapUtil.replace();
    }

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
