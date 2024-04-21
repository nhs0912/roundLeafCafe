package com.ypdchurch.roundleafcafe.menu.domain;

import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import com.ypdchurch.roundleafcafe.menu.enums.CategoryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Entity(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private CategoryStatus status;

    @OneToMany(mappedBy = "category")
    private final List<Menu> menus = new ArrayList<>();

    public List<Menu> removeMenu(Menu menu) {
        menus.remove(menu);
        return this.menus;
    }

    public List<Menu> addMenu(Menu menu) {
        menus.add(menu);
        return this.menus;
    }

    public boolean isExistMenu(Menu menu) {
        return this.menus.contains(menu);
    }

    public boolean isShow() {
        return this.status == CategoryStatus.SHOW;
    }

    public boolean isHide() {
        return this.status == CategoryStatus.HIDE;
    }

    public Category show() {
        this.status = CategoryStatus.SHOW;
        return this;
    }

    public Category hide() {
        this.status = CategoryStatus.HIDE;
        return this;
    }

}
