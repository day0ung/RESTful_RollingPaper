package com.example.restful_api.domain.commnets;


import com.example.restful_api.domain.BaseTimeEntity;
import com.example.restful_api.domain.papers.Paper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor

@NoArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paper_id")
    private Paper paper;
}
