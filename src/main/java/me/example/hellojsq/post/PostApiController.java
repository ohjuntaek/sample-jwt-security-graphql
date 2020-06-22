package me.example.hellojsq.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostApiController {

    private final PostRepository postRepository;

    @GetMapping("/post")
    public ResponseEntity getPosts() {
        List<Post> all = postRepository.findAll();
        log.info(all.toString());
        return ResponseEntity.ok().body(all);
    }
}
