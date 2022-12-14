package com.tabram.sudokugenerator.dto;

import com.tabram.sudokugenerator.validation.FileValid;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NotNull
@FileValid
public class FileBucket {

    MultipartFile file;

    public FileBucket(MultipartFile file) {
        this.file = file;
    }

    public FileBucket() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "FileBucket{" +
                "file=" + file +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileBucket that = (FileBucket) o;
        return Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
