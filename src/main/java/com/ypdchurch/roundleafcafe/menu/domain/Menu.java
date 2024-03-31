package com.ypdchurch.roundleafcafe.menu.domain;

import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import com.ypdchurch.roundleafcafe.menu.enums.MenuStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "menu")
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "category_id")
    private Long categoryId;

    @Enumerated(EnumType.STRING)
    private MenuStatus status;

    @Column(name = "menu_picture_url")
    private String menuPictureUrl;

    @Column(name = "popular_rank")
    private int popularRank;

}
