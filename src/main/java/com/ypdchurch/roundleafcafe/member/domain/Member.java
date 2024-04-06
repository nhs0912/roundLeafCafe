package com.ypdchurch.roundleafcafe.member.domain;

import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import com.ypdchurch.roundleafcafe.member.enums.MemberGrade;
import com.ypdchurch.roundleafcafe.member.enums.MemberRole;
import com.ypdchurch.roundleafcafe.member.enums.MemberStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Builder
//@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @NotEmpty
    @Column
    private String name;

    @Column(name = "phone_name")
    private String phoneNumber;

    @NotEmpty
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberGrade grade;

    @Enumerated(EnumType.STRING)
    private MemberRole role;


}
