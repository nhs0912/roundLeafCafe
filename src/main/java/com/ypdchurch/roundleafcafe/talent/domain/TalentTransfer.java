package com.ypdchurch.roundleafcafe.talent.domain;

import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "talent_transfer")
public class TalentTransfer extends BaseEntity {
    @Id
    @GeneratedValue()
}
