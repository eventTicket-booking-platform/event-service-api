package com.ec7205.event_hub.event_service_api.dto.response.paginate;

import com.ec7205.event_hub.event_service_api.dto.response.EventSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventPaginateResponseDto {

    private List<EventSummaryResponse> dataList;
    private long dataCount;
}
