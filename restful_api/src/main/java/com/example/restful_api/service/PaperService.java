package com.example.restful_api.service;

import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.api.dto.paper.PaperPostRequest;
import com.example.restful_api.api.dto.paper.PaperPutRequest;
import com.example.restful_api.api.dto.paper.PaperResponse;
import com.example.restful_api.domain.papers.Paper;
import com.example.restful_api.domain.papers.PaperRepository;
import com.example.restful_api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaperService {
    private final PaperRepository paperRepository;

    @Transactional
    public PaperResponse createPaper(PaperPostRequest request) {
        Paper paper = paperRepository.save(request.toEntity());
        return new PaperResponse(paper);
    }

    @Transactional
    public PaperResponse updatePaper(Long paperId , PaperPutRequest request) {
        Paper paper = verifyPost(paperId);

        paper.updateTitle(request.getTitle());
        if (!request.getTitle().isEmpty()) paper.updateContent(request.getContent());

        return new PaperResponse(paper);
    }

    @Transactional
    public PaperResponse deletePaper(Long paperId) {
        Paper paper = paperRepository.findById(paperId).orElseThrow(
                () -> new ResourceNotFoundException(Paper.class.getSimpleName(),"paperId", paperId)
        );
        paperRepository.delete(paper);

        return new PaperResponse(paper);
    }

    private Paper verifyPost(Long paperId) {
        Paper paper = paperRepository.findById(paperId).orElseThrow(
                () -> new ResourceNotFoundException(Paper.class.getSimpleName(), "paperId", paperId)
        );
        return paper;
    }


    @Transactional(readOnly = true)
    public BaseResponse<?> searchPaperList(String searchWord, Pageable pageable) {
        Page<Paper> paperPage = paperRepository.search(searchWord, pageable).orElseGet(Page::empty);

        if (paperPage.isEmpty()) {
            return BaseResponse.set(HttpStatus.NOT_FOUND, paperPage);
        } else {
            return BaseResponse.set(HttpStatus.OK, paperPage);
        }

    }

    @Transactional(readOnly = true)
    public PaperResponse getPaper(Long paperId) {
        Paper paper = paperRepository.findById(paperId).orElseThrow(
                () -> new ResourceNotFoundException(Paper.class.getSimpleName(), "paperId", paperId)
        );

        return new PaperResponse(paper);

    }
}
