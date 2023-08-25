package com.example.restful_api.api.dto;

import com.example.restful_api.api.dto.paper.PaperResponse;
import com.example.restful_api.domain.papers.Paper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PagingResponse {
    private int totalPageCount;
    private long totalElementCount;
    private int currentPageNum;
    private int currentPageElementCount;
    private List<PaperResponse> responseList = new ArrayList<>();

    public PagingResponse(Page<Paper> searchResults) {
        this.totalPageCount = searchResults.getTotalPages();
        this.currentPageNum = searchResults.getNumber();
        this.totalElementCount = searchResults.getTotalElements();
        this.currentPageElementCount = searchResults.getNumberOfElements();
        this.responseList = searchResults.getContent().stream().map(PaperResponse::new).collect(Collectors.toList());
    }

}











