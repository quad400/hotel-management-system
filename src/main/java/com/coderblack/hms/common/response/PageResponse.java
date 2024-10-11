package com.coderblack.hms.common.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private boolean success;
    private String message;
    private int currentPage;
    private long totalCount;
    private int totalPages;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private List<T> data;

    public PageResponse(String message, int number, long totalElements, int totalPages, boolean first, boolean last, List<T> data) {
        this.success = true;
        this.message = message;
        this.currentPage = number;
        this.totalCount = totalElements;
        this.totalPages = totalPages;
        this.hasPreviousPage = first;
        this.hasNextPage = last;
        this.data = data;
    }
}

