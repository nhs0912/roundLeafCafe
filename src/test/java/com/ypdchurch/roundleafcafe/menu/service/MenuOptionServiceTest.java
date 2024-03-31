package com.ypdchurch.roundleafcafe.menu.service;

import com.ypdchurch.roundleafcafe.menu.domain.Menu;
import com.ypdchurch.roundleafcafe.menu.domain.MenuOption;
import com.ypdchurch.roundleafcafe.menu.enums.MenuOptionStatus;
import com.ypdchurch.roundleafcafe.menu.repository.MenuOptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuOptionServiceTest {

    @Mock
    private MenuOptionRepository menuOptionRepository;

    @InjectMocks
    private MenuOptionService menuOptionService;

    private Menu menu;

    @BeforeEach
    void setUp() {
        menu = Menu.builder()
                .id(1L)
                .categoryId(1L)
                .price(new BigDecimal("250000"))
                .name("엄마는 외계인")
                .build();
    }

    @Test
    @DisplayName("menuOption 등록 성공 테스트")
    void saveMenuOptionSuccessTest() {

        //given
        MenuOption menuOption = MenuOption.builder()
                .id(1L)
                .price(BigDecimal.TEN)
                .content("크림")
                .status(MenuOptionStatus.SHOW)
                .menu(menu)
                .option("option값")
                .build();


        //when

        when(menuOptionRepository.save(menuOption)).thenReturn(menuOption);

        //then
        MenuOption savedMenuOption = menuOptionService.save(menuOption);

        assertThat(savedMenuOption.getId()).isEqualTo(1L);

    }
}