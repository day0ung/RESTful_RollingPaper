package com.example.restful_api.service;

import com.example.restful_api.api.dto.paper.PostPaperRequestDto;
import com.example.restful_api.api.dto.paper.PostPaperResponseDto;
import com.example.restful_api.domain.papers.PaperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaperService {
    private final PaperRepository paperRepository;

    @Transactional
    public PostPaperResponseDto save(PostPaperRequestDto requestDto) {
        return new PostPaperResponseDto(paperRepository.save(requestDto.toEntity()).getId());
    }

}
