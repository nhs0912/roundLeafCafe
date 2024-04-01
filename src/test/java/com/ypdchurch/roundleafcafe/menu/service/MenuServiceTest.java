package com.ypdchurch.roundleafcafe.menu.service;

import com.ypdchurch.roundleafcafe.menu.domain.Category;
import com.ypdchurch.roundleafcafe.menu.domain.Menu;
import com.ypdchurch.roundleafcafe.menu.enums.CategoryStatus;
import com.ypdchurch.roundleafcafe.menu.enums.MenuStatus;
import com.ypdchurch.roundleafcafe.menu.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MenuServiceTest {
    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    @DisplayName("Menu 저장 서비스 성공 테스트")
    void saveMenuSuccessTest() {
        Menu cafeLatte = Menu.builder()
                .id(1L)
                .price(new BigDecimal("25000"))
                .name("카페라떼")
                .category(Category.builder()
                        .status(CategoryStatus.SHOW)
                        .id(1L)
                        .name("커피")
                        .build())
                .status(MenuStatus.SHOW)
                .build();

        when(menuRepository.save(any())).thenReturn(cafeLatte);
        Menu savedCafeLatte = menuService.save(cafeLatte);
        assertThat(savedCafeLatte.getName()).isEqualTo(cafeLatte.getName());
    }

    @Test
    @DisplayName("Menu 찾기 서비스 성공 테스트")
    void findMenuSuccessTest() {
        Menu cafeLatte = Menu.builder()
                .id(1L)
                .price(new BigDecimal("25000"))
                .name("카페라떼")
                .category(Category.builder()
                        .status(CategoryStatus.SHOW)
                        .id(1L)
                        .name("커피")
                        .build())
                .status(MenuStatus.SHOW)
                .build();

        when(menuRepository.findById(any())).thenReturn(Optional.ofNullable(cafeLatte));

        Optional<Menu> savedCafeLatteOptional = menuService.findById(1L);

        assert cafeLatte != null;
        assert savedCafeLatteOptional.isPresent();
        assertThat(savedCafeLatteOptional.get().getName()).isEqualTo(cafeLatte.getName());
    }

}