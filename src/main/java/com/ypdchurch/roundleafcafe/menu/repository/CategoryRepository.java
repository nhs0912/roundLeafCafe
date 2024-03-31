package com.ypdchurch.roundleafcafe.menu.repository;

import com.ypdchurch.roundleafcafe.menu.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
