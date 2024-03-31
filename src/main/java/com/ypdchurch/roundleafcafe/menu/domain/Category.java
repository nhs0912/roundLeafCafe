package com.ypdchurch.roundleafcafe.menu.domain;

import com.ypdchurch.roundleafcafe.common.domain.BaseEntity;
import com.ypdchurch.roundleafcafe.menu.enums.CategoryStatus;
import jakarta.persistence.*;
import lombok.*;

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
