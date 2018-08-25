package org.softuni.webapp.util;

import org.softuni.webapp.constants.AppConstants;
import org.softuni.webapp.constants.ErrorMessagesBg;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public final class UploadImageValidator {

    private static boolean validateIfFileIsJpgImage(MultipartFile file) {
        String mime = file.getContentType().toUpperCase();

        return mime.endsWith("JPG") || mime.endsWith("JPEG");
    }

    private static boolean validateFileSize(MultipartFile file) {
        return file.getSize() < AppConstants.IMAGE_MAX_BYTES_SIZE;
    }

    public static void validateUploadedImages(List<MultipartFile> multipartFileList, BindingResult bindingResult) {
        for (MultipartFile picture : multipartFileList) {
            if (!validateIfFileIsJpgImage(picture)) {
                bindingResult.rejectValue(AppConstants.PICTURES_NAME,
                        AppConstants.PRODUCT_ADD_MODEL_NAME,
                        ErrorMessagesBg.INVALID_IMG_FILE_TYPE);

                break;
            } else if (!validateFileSize(picture)) {
                bindingResult.rejectValue(AppConstants.PICTURES_NAME,
                        AppConstants.PRODUCT_ADD_MODEL_NAME,
                        ErrorMessagesBg.INVALID_IMG_SIZE);

                break;
            }
        }
    }
}
