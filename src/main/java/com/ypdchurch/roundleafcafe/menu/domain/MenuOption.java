package com.ypdchurch.roundleafcafe.menu.domain;

import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import com.ypdchurch.roundleafcafe.menu.enums.MenuOptionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "menu_option")
public class MenuOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_option_id", updatable = false)
    private Long id;
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "option")
    private String option;

    @Column(name = "content")
    private String content;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private MenuOptionStatus status;
}
