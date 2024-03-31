package com.ypdchurch.roundleafcafe.talent.domain;

import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import com.ypdchurch.roundleafcafe.talent.enums.TransferResult;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "talent_transfer")
public class TalentTransfer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "talent_transfer_id")
    private Long id;

    @Column(name = "talent_Id")
    private Long talentId;

    @Column(name = "withdraw_member_Id")
    private Long withdrawMemberId;

    @Column(name = "deposit_member_Id")
    private Long depositMemberId;

    @Column(name = "expense")
    private BigDecimal expense;

    @Enumerated
    @Column(name = "transfer_result")
    private TransferResult result;
}
