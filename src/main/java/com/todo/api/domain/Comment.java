package com.todo.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    @Builder.Default
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubComment> subComments = new ArrayList<SubComment>();
}
