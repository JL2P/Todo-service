package com.todo.api.domain;

import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;       // 제목
    private String description; // 설명글
    private String category;    // 카테고리
    private String writer;      // 작성자ID
    private String endTime;     // 마감일자
    private String groupAt;     // 그룹계획여부
    @Column(columnDefinition = "integer default 0")
    private int likePoint;           // 좋아요
}
