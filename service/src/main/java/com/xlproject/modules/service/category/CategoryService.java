package com.xlproject.modules.service.category;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.dto.categoryDto.CategoryWithProductsDTO;
import com.xlproject.modules.entity.bean.Category;
import java.math.BigInteger;

public interface CategoryService {
     PageInfo<Category> getCategorys(int pageNum, int pageSize);
     Category getCategoryById(BigInteger id);
     void addCategory(Category newCategory);
     int delCategoryById(BigInteger id);
     int editCategory(Category category);
     CategoryWithProductsDTO getCategoryWithProductsByCategoryId(BigInteger id);
}
