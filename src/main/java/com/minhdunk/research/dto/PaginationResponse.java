package com.minhdunk.research.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponse <T>  {
    private long total;
    private int page;
    private int pageSize;
    private int start;
    private T data;

    public PaginationResponse(int page, int pageSize, T data) {
        this.page = page;
        this.pageSize = pageSize;
        this.start = page*pageSize + 1;
        this.data = data;
    }
}

