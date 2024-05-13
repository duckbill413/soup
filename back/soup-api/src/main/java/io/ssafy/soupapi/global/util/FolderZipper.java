package io.ssafy.soupapi.global.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FolderZipper {
    public static void zipFolder(String sourceFolderPath, String zipFilePath) throws IOException, IOException {
        File sourceFolder = new File(sourceFolderPath);
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath));

        zipFolder(sourceFolder, sourceFolder, zipOutputStream);

        zipOutputStream.close();
    }

    private static void zipFolder(File sourceFolder, File rootFolder, ZipOutputStream zipOutputStream) throws IOException {
        File[] files = sourceFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    zipFolder(file, rootFolder, zipOutputStream);
                } else {
                    zipFile(file, rootFolder, zipOutputStream);
                }
            }
        }
    }

    private static void zipFile(File file, File rootFolder, ZipOutputStream zipOutputStream) throws IOException {
        byte[] buffer = new byte[1024];
        FileInputStream fileInputStream = new FileInputStream(file);
        zipOutputStream.putNextEntry(new ZipEntry(getRelativePath(file, rootFolder)));
        int length;
        while ((length = fileInputStream.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, length);
        }
        zipOutputStream.closeEntry();
        fileInputStream.close();
    }

    private static String getRelativePath(File file, File rootFolder) {
        String relativePath = file.getAbsolutePath().substring(rootFolder.getAbsolutePath().length() + 1);
        return relativePath.replace('\\', '/');
    }
}
