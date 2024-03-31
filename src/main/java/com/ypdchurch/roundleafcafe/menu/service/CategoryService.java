package com.ypdchurch.roundleafcafe.menu.service;

import com.ypdchurch.roundleafcafe.menu.domain.Category;
import com.ypdchurch.roundleafcafe.menu.exception.CategoryCustomException;
import com.ypdchurch.roundleafcafe.menu.exception.code.CategoryErrorCode;
import com.ypdchurch.roundleafcafe.menu.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Optional<Category> findCategoryByCategoryId(final Long id) {
        if (id == null) {
            throw new CategoryCustomException(CategoryErrorCode.THERE_IS_NOT_EXIST_ID);
        }

        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category changeShowStatus(Category category) {
        Category showedCategory = category.show();
        return this.save(showedCategory);
    }

}
