package com.ypdchurch.roundleafcafe.menu.service;

import com.ypdchurch.roundleafcafe.menu.domain.Category;
import com.ypdchurch.roundleafcafe.menu.domain.Menu;
import com.ypdchurch.roundleafcafe.menu.domain.MenuOption;
import com.ypdchurch.roundleafcafe.menu.enums.CategoryStatus;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
                .category(Category.builder()
                        .id(1L)
                        .name("아이스크림")
                        .status(CategoryStatus.SHOW)
                        .build())
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
                .option("초콜릿")
                .build();

        //when
        when(menuOptionRepository.save(menuOption)).thenReturn(menuOption);

        //then
        MenuOption savedMenuOption = menuOptionService.save(menuOption);

        assertThat(savedMenuOption.getId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("menuOption 찾아오기 성공 테스트")
    void findMenuOptionSuccessTest() {

        //given
        MenuOption menuOption = MenuOption.builder()
                .id(1L)
                .price(BigDecimal.TEN)
                .content("크림")
                .status(MenuOptionStatus.SHOW)
                .menu(menu)
                .option("초콜릿")
                .build();

        //when
        when(menuOptionRepository.findById(any())).thenReturn(Optional.ofNullable(menuOption));

        //then
        Optional<MenuOption> menuOptionOptional = menuOptionService.findMenuOption(1L);


        assertThat(menuOptionOptional.get().getId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("menuOption 보이는 상태인 메뉴옵션 찾아오기 성공 테스트")
    void findShowStatusMenuOptionSuccessTest() {

        //given
        MenuOption menuOption = MenuOption.builder()
                .id(1L)
                .price(BigDecimal.TEN)
                .content("크림")
                .status(MenuOptionStatus.SHOW)
                .menu(menu)
                .option("초콜릿")
                .build();

        MenuOption shot = MenuOption.builder()
                .id(2L)
                .price(new BigDecimal("500"))
                .content("에스프레소")
                .status(MenuOptionStatus.SHOW)
                .menu(menu)
                .option("더블샷")
                .build();

        MenuOption milk = MenuOption.builder()
                .id(3L)
                .price(new BigDecimal("1000"))
                .content("오유")
                .status(MenuOptionStatus.HIDE)
                .menu(menu)
                .option("오트우유")
                .build();

        List<MenuOption> menuOptions = List.of(menuOption, shot, milk);
        //when
        when(menuOptionRepository.findAll()).thenReturn(menuOptions);

        //then
        List<MenuOption> showStatusMenuOptions = menuOptionService.findShowStatusMenuOptions();


        assertThat((long) showStatusMenuOptions.size()).isEqualTo(2);

    }

}