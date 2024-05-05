package io.ssafy.soupapi.domain.projectbuilder.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariable;
import io.ssafy.soupapi.global.util.MapStringReplace;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProjectBuilderRepositoryImpl {
    public String ControllerBuilder(ApiDoc apiDoc) {
        String controller = """
                @Operation(summary = ":description")
                :methodType(":apiUriPath")
                public ResponseEntity<BaseResponse<:responseBodyName>> :methodName(
                    :pathVariables
                    :queryParameters
                    :requestBody
                ) {
                    return :serverMethodName
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
        mapUtil.addValue(":responseBodyName", Character.toUpperCase(apiDoc.getResponseBodyName().charAt(0)) + apiDoc.getResponseBodyName().substring(1));
        mapUtil.addValue(":methodName", Character.toUpperCase(apiDoc.getMethodName().charAt(0)) + apiDoc.getMethodName().substring(1));
        ;
        mapUtil.addValue(":pathVariables", addPathVariableBuilder(apiDoc.getPathVariables()));
        mapUtil.addValue(":queryParameters", addQueryParameters(apiDoc.getQueryParameters()));
        mapUtil.addValue(":requestBody", addRequestBody(apiDoc.getRequestBodyName()));
        mapUtil.addValue(":serverMethodName", Character.toUpperCase(apiDoc.getMethodName().charAt(0)) + apiDoc.getMethodName().substring(1));
        return controller;
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
