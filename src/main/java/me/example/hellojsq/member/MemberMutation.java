package me.example.hellojsq.member;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberMutation implements GraphQLMutationResolver {

    private final SecurityUserDetailService userDetailsService;

    public MemberResponse createMember(MemberRequest memberRequest) {
        Member member = userDetailsService.createMember(memberRequest);
        return MemberResponse.from(member);
    }
}
