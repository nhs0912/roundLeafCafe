package com.ypdchurch.roundleafcafe.menu.repository;

import com.ypdchurch.roundleafcafe.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

}
