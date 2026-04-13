package com.ec7205.event_hub.event_service_api.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.ec7205.event_hub.event_service_api.service.FileService;
import com.ec7205.event_hub.event_service_api.utils.CommonFileSavedBinaryDataDto;
import com.ec7205.event_hub.event_service_api.utils.ImageUploadGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final AmazonS3Client s3Client;
    private final ImageUploadGenerator imageUploadGenerator;

    @Override
    public CommonFileSavedBinaryDataDto createResource(MultipartFile file, String directory, String bucket) {
        try {
            String originalFilename = file.getOriginalFilename();
            String safeOriginalFilename = originalFilename == null ? "banner" : originalFilename;
            String newFileName = imageUploadGenerator.generateCPDResourceName(
                    safeOriginalFilename,
                    UUID.randomUUID().toString()
            );

            PutObjectResult putObjectResult = s3Client.putObject(
                    new PutObjectRequest(bucket, directory + newFileName, file.getInputStream(), new ObjectMetadata())
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            return new CommonFileSavedBinaryDataDto(
                    new SerialBlob(putObjectResult.getContentMd5().getBytes()),
                    directory,
                    new SerialBlob(newFileName.getBytes()),
                    new SerialBlob(s3Client.getResourceUrl(bucket, directory + newFileName).toString().getBytes())
            );
        } catch (IOException | SQLException ex) {
            throw new IllegalStateException("Failed to upload file to S3", ex);
        }
    }

    @Override
    public void deleteResource(String bucket, String directory, String fileName) {
        s3Client.deleteObject(bucket, directory + fileName);
    }
}
