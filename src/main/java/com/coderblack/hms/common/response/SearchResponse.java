package com.coderblack.hms.common.response;

import java.util.List;

public record SearchResponse<T>(
        List<T> results,
        Long totalCount
) {
}
