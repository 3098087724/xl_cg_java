package com.xlproject.modules.dao.mapper;

import com.xlproject.modules.dto.categoryDto.CategoryWithProductsDTO;
import com.xlproject.modules.entity.bean.Category;
import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> getCategorys();
    Category getCategoryById(BigInteger id);
    Category getCategoryByName(String name);
    int addCategory(Category newCategory);
    int delCategoryById(BigInteger id);
    int editCategory(Category category);
    CategoryWithProductsDTO getCategoryWithProductsByCategoryID(BigInteger id);
}
