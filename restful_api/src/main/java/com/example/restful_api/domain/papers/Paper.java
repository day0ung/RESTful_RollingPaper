package com.example.restful_api.domain.papers;


import com.example.restful_api.domain.BaseTimeEntity;
import com.example.restful_api.domain.commnets.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor

@NoArgsConstructor
@Entity
@Table(name = "PAPER")
public class Paper extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    @Column(length = 500, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private String author;
    @OneToMany(mappedBy = "paper")
    private List<Comment> commentList = new ArrayList<>();

}
