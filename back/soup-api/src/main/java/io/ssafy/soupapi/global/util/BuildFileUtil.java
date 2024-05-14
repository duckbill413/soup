package io.ssafy.soupapi.global.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BuildFileUtil {
    /**
     * 폴더내 모든 파일의 정보 리스트
     *
     * @param folder 폴더
     * @return 파일 정보 리스트
     */
    public static List<File> getLeafFiles(File folder) {
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
     * 파일 변수 치환
     * 파일 정보 및 치환할 변수 맵을 이용하여 파일 문자열 치환
     *
     * @param file      파일 정보
     * @param variables 치환할 문자 맵
     * @throws IOException 파일 입출력 에러
     */
    public static void replaceFileVariables(File file, Map<String, String> variables) throws IOException {
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
    public static void insertMethodIntoFile(File file, String methodStr) throws IOException {
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

    public static void insertImportIntoFile(File file, String importStr) throws IOException {
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

    public static void insertRelationIntoFile(File file, String relationStr) throws IOException {
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
}
