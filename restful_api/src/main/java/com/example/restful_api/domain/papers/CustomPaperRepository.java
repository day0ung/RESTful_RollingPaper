package com.example.restful_api.domain.papers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomPaperRepository {

    Optional<Page<Paper>> search(String searchWord, Pageable pageable);
}
