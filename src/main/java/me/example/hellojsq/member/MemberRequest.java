package me.example.hellojsq.member;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberRequest {
    String loginId;
    String password;
    String role;
    String name;
}
