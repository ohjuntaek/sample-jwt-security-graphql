package me.example.hellojsq.member;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {
    private String memberName;
    private String memberLoginId;
    private String role;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .memberLoginId(member.getLoginId())
                .memberName(member.getName())
                .role(member.getRole())
                .build();
    }
}
