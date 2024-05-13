package io.ssafy.soupapi.global.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
}
