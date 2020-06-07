package me.example.hellojsq.post;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostQuery implements GraphQLQueryResolver {
    private final PostRepository postRepository;

    public List<PostResponse> getPosts() {
        List<Post> posts = postRepository.findAll();
        return PostResponse.from(posts);
    }
}
