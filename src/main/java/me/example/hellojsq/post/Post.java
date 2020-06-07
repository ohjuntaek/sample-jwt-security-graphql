package me.example.hellojsq.post;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    private String title;
    private String content;
    @NonNull
    private String writerId;

}
