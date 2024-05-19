package io.ssafy.soupapi.domain.projectbuilder.dto.response;

public record ClassFileInfo(
        String name,
        String data
) implements Comparable<ClassFileInfo> {

    @Override
    public int compareTo(ClassFileInfo other) {
        return this.name.compareTo(other.name());
    }
}
