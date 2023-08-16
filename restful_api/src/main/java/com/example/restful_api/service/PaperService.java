package com.example.restful_api.service;

import com.example.restful_api.api.dto.paper.PaperPostRequest;
import com.example.restful_api.api.dto.paper.PaperResponse;
import com.example.restful_api.domain.papers.PaperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaperService {
    private final PaperRepository paperRepository;

    @Transactional
    public PaperResponse save(PaperPostRequest requestDto) {
        return new PaperResponse(paperRepository.save(requestDto.toEntity()).getId());
    }

}
