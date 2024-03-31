package com.ypdchurch.roundleafcafe.menu.service;

import com.ypdchurch.roundleafcafe.menu.domain.Category;
import com.ypdchurch.roundleafcafe.menu.enums.CategoryStatus;
import com.ypdchurch.roundleafcafe.menu.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("Category 정보 찾기 성공 테스트")
    void findByIdSuccessTest() {
        Category beverage = Category.builder()
                .id(1L)
                .name("음료")
                .status(CategoryStatus.SHOW)
                .build();

        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(beverage));
        Optional<Category> categoryByCategoryId = categoryService.findCategoryByCategoryId(beverage.getId());
        Category category = categoryByCategoryId.get();
        assertThat(category.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Category Show 상태인 정보 찾기 성공 테스트")
    void findShowStatusCategorySuccessTest() {
        Category beverage = Category.builder()
                .id(1L)
                .name("음료")
                .status(CategoryStatus.SHOW)
                .build();

        Category bread = Category.builder()
                .id(2L)
                .name("빵")
                .status(CategoryStatus.SHOW)
                .build();

        Category food = Category.builder()
                .id(3L)
                .name("음식")
                .status(CategoryStatus.HIDE)
                .build();

        Category desert = Category.builder()
                .id(4L)
                .name("디저트")
                .status(CategoryStatus.SHOW)
                .build();

        List<Category> categories = List.of(beverage, bread, food, desert);

        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> showStatusCategories = categoryService.findShowStatusCategories();

        assertThat(showStatusCategories.stream().filter(Category::isShow)
                .count()).isEqualTo(3);
    }

    @Test
    @DisplayName("Category 저장 성공 테스트")
    void saveCategorySuccessTest() {
        Category beverage = Category.builder()
                .id(1L)
                .name("음료")
                .status(CategoryStatus.SHOW)
                .build();

        when(categoryRepository.save(any())).thenReturn(beverage);

        Category savedCategory = categoryService.save(beverage);

        assertThat(savedCategory.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Category 상태 Show 변경 성공 테스트")
    void changeShowStatusCategorySuccessTest() {
        Category beverage = Category.builder()
                .id(1L)
                .name("음료")
                .status(CategoryStatus.HIDE)
                .build();

        beverage.show();

        when(categoryRepository.save(any())).thenReturn(beverage);

        Category savedCategory = categoryService.save(beverage);

        assertThat(savedCategory.isShow()).isTrue();
    }

    @Test
    @DisplayName("Category 상태 hide 변경 성공 테스트")
    void changeHideStatusCategorySuccessTest() {
        Category beverage = Category.builder()
                .id(1L)
                .name("음료")
                .status(CategoryStatus.SHOW)
                .build();

        beverage.hide();

        when(categoryRepository.save(any())).thenReturn(beverage);

        Category savedCategory = categoryService.save(beverage);

        assertThat(savedCategory.isHide()).isTrue();
    }


}