package com.ypdchurch.roundleafcafe.menu.exception;

import com.ypdchurch.roundleafcafe.menu.exception.code.CategoryErrorCode;
import lombok.Getter;

@Getter
public class CategoryCustomException extends RuntimeException {
    private CategoryErrorCode categoryErrorCode;

    public CategoryCustomException(String message) {
        super(message);
    }

    public CategoryCustomException(CategoryErrorCode categoryErrorCode) {
        super(categoryErrorCode.getMessage());
        this.categoryErrorCode = categoryErrorCode;
    }
}
