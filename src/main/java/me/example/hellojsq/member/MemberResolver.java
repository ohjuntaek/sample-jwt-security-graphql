package me.example.hellojsq.member;

import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberResolver implements GraphQLResolver<MemberResponse> {
    private final MemberRepository memberRepository;

}
