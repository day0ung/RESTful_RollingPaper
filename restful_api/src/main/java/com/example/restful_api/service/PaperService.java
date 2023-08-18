package com.example.restful_api.service;

import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.api.dto.paper.PaperPostRequest;
import com.example.restful_api.api.dto.paper.PaperPutRequest;
import com.example.restful_api.api.dto.paper.PaperResponse;
import com.example.restful_api.domain.papers.Paper;
import com.example.restful_api.domain.papers.PaperRepository;
import com.example.restful_api.domain.user.User;
import com.example.restful_api.domain.user.UserRepository;
import com.example.restful_api.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaperService extends BaseService{
    private final PaperRepository paperRepository;

    private final UserRepository userRepository;

    @Transactional
    public PaperResponse createPaper(PaperPostRequest request, CustomUserPrincipal customUserPrincipal) {
        User user = verifyUser(customUserPrincipal, userRepository);
        Paper paper = paperRepository.save(request.toEntity(user));
        return new PaperResponse(paper);
    }

    @Transactional
    public PaperResponse updatePaper(Long paperId , PaperPutRequest request, CustomUserPrincipal customUserPrincipal) {
        User user = verifyUser(customUserPrincipal, userRepository);
        Paper paper = verifyId(paperId, paperRepository, Paper.class);

        log.info("update User id {}",user.getId());
        log.info("update paper in userId {}",paper.getUser().getId());

        paper.updateTitle(request.getTitle());
        if (!request.getTitle().isEmpty()) paper.updateContent(request.getContent());

        return new PaperResponse(paper);
    }

    @Transactional
    public PaperResponse deletePaper(Long paperId, CustomUserPrincipal customUserPrincipal) {
        Paper paper = verifyId(paperId, paperRepository, Paper.class);
        paperRepository.deleteById(paper.getId());

        return new PaperResponse(paper);
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
        Paper paper = verifyId(paperId, paperRepository, Paper.class);

        return new PaperResponse(paper);

    }
}
