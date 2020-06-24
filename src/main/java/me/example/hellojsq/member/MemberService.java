package me.example.hellojsq.member;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@GraphQLApi
public class MemberService {
    private final MemberRepository memberRepository;
    private final SecurityUserDetailService userDetailsService;

    @GraphQLQuery
    public MemberResponse getMemberInfo(String loginId) {
        Member member = memberRepository.findByLoginId(loginId);
        return MemberResponse.from(member);
    }

    @GraphQLMutation
    public MemberResponse createMember(MemberRequest memberRequest) {
        Member member = userDetailsService.createMember(memberRequest);
        return MemberResponse.from(member);
    }
}
