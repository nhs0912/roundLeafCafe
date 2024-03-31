package com.ypdchurch.roundleafcafe.talent.domain;

import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "talent")
public class Talent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "talent", updatable = false)
    private Long id;

    @Column(name = "balance")
    private BigDecimal balance;


}
