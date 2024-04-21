package com.ypdchurch.roundleafcafe.menu.domain;

import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import com.ypdchurch.roundleafcafe.menu.enums.MenuStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "menu")
    private final List<MenuOption> menuOptions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    private MenuStatus status;

    @Column(name = "menu_picture_url")
    private String menuPictureUrl;

    @Column(name = "popular_rank")
    private int popularRank;

    public Menu changeCategory(Category category) {
        if(this.category.isExistMenu(this)){
            category.removeMenu(this);
        }
        this.category = category;
        this.category.addMenu(this);
        return this;
    }

}
