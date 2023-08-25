package com.example.restful_api.domain.papers;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.example.restful_api.domain.papers.QPaper.paper;


@Slf4j
@Repository
public class CustomPaperRepositoryImpl implements CustomPaperRepository{
    private final JPAQueryFactory query;


    public CustomPaperRepositoryImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }



    @Override
    public Optional<Page<Paper>> search(String searchWord, Pageable pageable) {
        log.info("searchWord {}", searchWord);
        BooleanExpression titlePredicate = Optional.ofNullable(searchWord)
                .map(sw -> paper.title.containsIgnoreCase(sw))
                .orElse(null);


        List<Paper> result = query
                .selectFrom(paper)
                .where(titlePredicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = query
                .selectFrom(paper)
                .where(titlePredicate)
                .fetchCount();

        // 페이징 정보와 함께 결과를 반환
        Page<Paper> pageResult = new PageImpl<>(result, pageable, total);
        return Optional.of(pageResult);

    }

}
