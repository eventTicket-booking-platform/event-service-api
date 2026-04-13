package com.ec7205.event_hub.event_service_api.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonFileSavedSimpleDataDto {
    private String hash;
    private String directory;
    private String fileName;
    private String resourceUrl;
}
