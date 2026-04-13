package com.ec7205.event_hub.event_service_api.service;

import com.ec7205.event_hub.event_service_api.utils.CommonFileSavedBinaryDataDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    CommonFileSavedBinaryDataDto createResource(MultipartFile file, String directory, String bucket);

    void deleteResource(String bucket, String directory, String fileName);
}
