package com.example.restful_api.domain.commnets;


import com.example.restful_api.domain.BaseTimeEntity;
import com.example.restful_api.domain.papers.Paper;
import com.example.restful_api.domain.user.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paper_id")
    private Paper paper;

    public void updateName(String name) {
        this.name = name;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
