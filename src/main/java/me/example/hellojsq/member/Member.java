package me.example.hellojsq.member;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @NonNull
    private String loginId;

    private String password;

    @NonNull
    private String name;

    @NonNull
    private String role;

    public static Member from(MemberRequest memberRequest) {
        Member member = new Member();
        member.loginId = memberRequest.loginId;
        member.name = memberRequest.name;
        member.password = memberRequest.password;
        member.role = memberRequest.role;
        return member;
    }
}
