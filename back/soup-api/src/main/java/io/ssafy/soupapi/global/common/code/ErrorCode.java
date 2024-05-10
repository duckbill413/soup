package io.ssafy.soupapi.global.common.code;

import lombok.Getter;

@Getter
public enum ErrorCode {
    /**
     * ******************************* Global Error CodeList ***************************************
     * HTTP Status Code
     * 400 : Bad Request
     * 401 : Unauthorized
     * 403 : Forbidden
     * 404 : Not Found
     * 409 : Conflict
     * 500 : Internal Server Error
     * *********************************************************************************************
     */
    // 잘못된 서버 요청
    BAD_REQUEST_ERROR(400, "G001", "Bad Request Exception"),

    // @RequestBody 데이터 미 존재
    REQUEST_BODY_MISSING_ERROR(400, "G002", "Required request body is missing"),

    // 유효하지 않은 타입
    INVALID_TYPE_VALUE(400, "G003", "Invalid Type Value"),

    // Request Parameter 로 데이터가 전달되지 않을 경우
    MISSING_REQUEST_PARAMETER_ERROR(400, "G004", "Missing Servlet RequestParameter Exception"),

    // 입력/출력 값이 유효하지 않음
    IO_ERROR(400, "G005", "I/O Exception"),

    // com.google.gson JSON 파싱 실패
    JSON_PARSE_ERROR(400, "G006", "JsonParseException"),

    // com.fasterxml.jackson.core Processing Error
    JACKSON_PROCESS_ERROR(400, "G007", "com.fasterxml.jackson.core Exception"),

    // 권한이 없음
    FORBIDDEN_ERROR(403, "G008", "Forbidden Exception"),

    // 서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(404, "G009", "Not Found Exception"),

    // NULL Point Exception 발생
    NULL_POINT_ERROR(404, "G010", "Null Point Exception"),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_ERROR(400, "G011", "Handle Validation Exception"),

    // Header 가 유효하지 않은 경우
    NOT_VALID_HEADER_ERROR(400, "G012", "Header에 데이터가 존재하지 않는 경우 "),
    // No resource founded 없는 경로로의 요청
    NO_RESOUCE_FOUNDED(404, "G013", "잘못된 API 요청 입니다."),

    // GMAIL 전송 실패,
    FAILED_TO_SEND_GMAIL(404, "F003", "GMAIL 전송 실패"),
    // 서버가 처리 할 방법을 모르는 경우 발생
    INTERNAL_SERVER_ERROR(500, "G999", "Internal Server Error Exception"),

    /**
     * ******************************* Business Error CodeList ***************************************
     */

    // 사용자 권한 인증 실패 (CODE: 100)
    UNAUTHORIZED_USER_EXCEPTION(403, "B100", "권한이 없는 사용자입니다."),
    INVALID_ACCESS_TOKEN_EXCEPTION(403, "B102", "유효하지 않은 ACCESS TOKEN 입니다."),
    EXPIRED_ACCESS_TOKEN_EXCEPTION(403, "B103", "만료된 ACCESS TOKEN 입니다."),
    INCONSISTENT_ACCESS_TOKEN_EXCEPTION(403, "B104", "일치하지 않는 ACCESS TOKEN 입니다."),
    INVALID_REFRESH_TOKEN_EXCEPTION(403, "B102", "유효하지 않은 REFRESH TOKEN 입니다."),
    EXPIRED_REFRESH_TOKEN_EXCEPTION(403, "B103", "만료된 REFRESH TOKEN 입니다."),
    INCONSISTENT_REFRESH_TOKEN_EXCEPTION(403, "B104", "일치하지 않는 REFRESH TOKEN 입니다."),
    UNSUPPORTED_SOCIAL_PLATFORM(403, "B105", "지원하지 않는 소셜 로그인 플랫폼 입니다."),
    NO_SOCIAL_USER_ATTRIBUTES(403, "B106", "소셜 회원 정보를 불러오지 못하였습니다"),

    // 유저 에러 (CODE: 200)
    NOT_FOUND_USER(404, "B200", "존재하지 않는 유저입니다."),

    // 프로젝트 에러 (CODE: 300)
    NOT_FOUND_PROJECT(404, "B300", "존재하지 않는 프로젝트 입니다."),
    FAILED_TO_CHANGE_PROJECT_IMAGE(404, "B301", "프로젝트 이미지 변경 실패"),

    // 프로젝트 권한 (CODE: 400)
    NOT_FOUND_PROJECT_AUTH(404, "B400", "프로젝트 권한이 존재하지 않습니다."),
    FAILED_TO_UPDATE_PROJECT(401, "B401", "프로젝트 업데이트 권한이 없습니다."),
    INVALID_INVITE_CODE(404, "B402", "확인되지 않는 초대 코드입니다."),
    ALREADY_EXISTS_PROJECT_MEMBER(404, "B403", "이미 프로젝트 회원입니다."),
    INVALID_INVITE_PROJECT_ROLE(404, "B404", "부여할 수 있는 프로젝트 권한이 아닙니다."),

    // 지라 관련 에러 (CODE: 500)
    FAILED_TO_REQUEST_JIRA_API(404, "B500", "지라 API 요청에 실패 하였습니다."),
    NOT_FOUND_JIRA_INFO(404, "B501", "프로젝트 지라 정보를 찾을 수 없습니다"),
    FAILED_TO_SYNC_JIRA(404, "B502", "프로젝트 이슈와 지라 이슈 동기화에 실패하였습니다."),
    FAILED_TO_UPDATE_JIRA_ISSUE(404, "B503", "지라 이슈 업데이트 실패"),

    // Vuerd 관련 에러 (CODE: 600)
    NOT_FOUND_PROJECT_VUERD(404, "B600", "프로젝트 VUERD를 찾을 수 없습니다."),

    // S3 파일 업로드 (CODE: 700)
    NOT_FOUND_S3FILE(404, "B701", "S3 파일이 존재하지 않습니다"),
    FILE_NOT_EXISTS(400, "B702", "업로드할 파일이 비어있습니다."),

    FAILED_TO_UPLOAD_LOCAL_FILE(404, "B703", "로컬 파일 업로드 실패"),
    FAILED_TO_UPLOAD_S3_FILE(404, "B704", "S3 파일 업로드 실패"),

    // OpenVidu 관련 에러 (CODE: 800)
    NOT_FOUND_SESSION(404, "B800", "존재하지 않는 세션입니다."),
    NOT_FOUND_EXPIRE_TIME(404,"B801","만료시간이 존재하지 않습니다."),

    // API DOCS 관련 에러 (CODE: 900)
    NOT_FOUND_API_DOC(404, "B900", "찾을 수 없는 API 문서 입니다."),
    ; // End

    /**
     * ******************************* Error Code Constructor ***************************************
     */
    // 에러 코드의 '코드 상태'을 반환한다.
    private final int status;

    // 에러 코드의 '코드간 구분 값'을 반환한다.
    private final String divisionCode;

    // 에러 코드의 '코드 메시지'을 반환한다.
    private final String message;

    // 생성자 구성
    ErrorCode(final int status, final String divisionCode, final String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
