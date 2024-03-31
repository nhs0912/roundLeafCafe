package com.ypdchurch.roundleafcafe.menu.repository;

import com.ypdchurch.roundleafcafe.menu.domain.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {
}
