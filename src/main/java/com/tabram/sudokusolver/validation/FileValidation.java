package com.tabram.sudokusolver.validation;

import com.tabram.sudokusolver.dto.FileBucket;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class FileValidation implements ConstraintValidator<FileValid, FileBucket> {
    @Override
    public void initialize(FileValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(FileBucket fileBucket, ConstraintValidatorContext constraintValidatorContext) {

        String contentType = fileBucket.getFile().getContentType();
        if (!isSupportedContentType(contentType)) {
            return false;
        }

        return true;
    }


    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("application/json");

    }
}



