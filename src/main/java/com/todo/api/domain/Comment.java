package com.todo.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "comments")
public class Comment extends CommonDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;        // 내용
    private String writer;      // 작성자ID
    @Column(columnDefinition = "integer default 0")
    private String likePoint;   // 좋아요
    
    //대댓글을 위한 컬럼
    private String upper;
    private String level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;
}
