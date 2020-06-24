package me.example.hellojsq.post;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostResponse {
    private String title;
    private String content;
    private String writerId;

    public static List<PostResponse> from(List<Post> posts) {
        return posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writerId(post.getWriterId())
                .build();
    }
}
