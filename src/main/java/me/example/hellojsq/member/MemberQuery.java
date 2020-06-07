package me.example.hellojsq.member;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberQuery implements GraphQLQueryResolver {
    private final MemberRepository memberRepository;

    public MemberResponse getMemberInfo(String loginId) {
        Member member = memberRepository.findByLoginId(loginId);
        return MemberResponse.from(member);
    }


}
