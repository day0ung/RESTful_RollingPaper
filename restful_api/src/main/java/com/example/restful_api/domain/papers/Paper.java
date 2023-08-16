package com.example.restful_api.domain.papers;


import com.example.restful_api.domain.BaseTimeEntity;
import com.example.restful_api.domain.commnets.Comment;
import com.example.restful_api.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Getter
@Builder
@AllArgsConstructor

@NoArgsConstructor
@Entity
@Table(name = "PAPER")
public class Paper extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder.Default
    @OneToMany(mappedBy = "paper", cascade = ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

}
