package com.ypdchurch.roundleafcafe.menu.service;

import com.ypdchurch.roundleafcafe.menu.domain.MenuOption;
import com.ypdchurch.roundleafcafe.menu.exception.MenuOptionCustomException;
import com.ypdchurch.roundleafcafe.menu.exception.code.MenuOptionErrorCode;
import com.ypdchurch.roundleafcafe.menu.repository.MenuOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuOptionService {

    private final MenuOptionRepository menuOptionRepository;

    public Optional<MenuOption> findMenuOption(final Long id) {
        return menuOptionRepository.findById(id);
    }

    public List<MenuOption> findMenuOptions() {
        return menuOptionRepository.findAll();
    }

    public MenuOption save(final MenuOption menuOption) {
        if (menuOption == null) {
            throw new MenuOptionCustomException(MenuOptionErrorCode.MENU_OPTION_NOT_FOUND);
        }
        return menuOptionRepository.save(menuOption);
    }

}
