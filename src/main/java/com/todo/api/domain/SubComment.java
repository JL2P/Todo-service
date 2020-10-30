package com.todo.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "sub_comments")
public class SubComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;        // 내용
    private String writer;      // 작성자ID

    @Column(columnDefinition = "integer default 0")
    private String likePoint;   // 좋아요
    private String targetId;    // 어떤 사람의 댓글인지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;
}