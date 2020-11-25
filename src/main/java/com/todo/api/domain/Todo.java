package com.todo.api.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "todos")
public class Todo extends CommonDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;      // 이미지 URL
    private String title;       // 제목
    private String description; // 설명글
    private String category;    // 카테고리
    private String writer;      // 작성자ID
    private String endTime;     // 마감일자
    private String startTime;   // 시작일자
    private String groupAt;     // 그룹계획여부

    @Column(columnDefinition = "varchar(255) default 'N'")
    private String completed;    // 완료여부 (Y완료 N미완료)

    @Builder.Default
    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();

    @Builder.Default
    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<Like>(); //좋아요

    //groupGallery
    @Builder.Default
    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Gallery> galleries = new ArrayList<Gallery>();

    public void comleted(){
        this.completed="Y";
    }

    public void incompleted(){
        this.completed="N";
    }

    public void setStartTime(String startTime){
        this.startTime=startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

}
