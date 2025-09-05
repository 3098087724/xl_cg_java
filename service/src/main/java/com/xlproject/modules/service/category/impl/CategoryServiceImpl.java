package com.xlproject.modules.service.category.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xlproject.modules.common.exception.BizException;
import com.xlproject.modules.common.exception.BizExceptionEnum;
import com.xlproject.modules.dao.mapper.CategoryMapper;
import com.xlproject.modules.dto.categoryDto.CategoryWithProductsDTO;
import com.xlproject.modules.entity.bean.Category;
import com.xlproject.modules.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryMapper categoryMapper;
    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {this.categoryMapper = categoryMapper;}

    /**
     * @return 返回所有商品分类信息
     */
    @Override
    public PageInfo<Category> getCategorys(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        try {
            List<Category> categorys = categoryMapper.getCategorys();
            return new PageInfo<>(categorys);
        }finally {
            PageHelper.clearPage();
        }
    }

    /**
     * 根据id获取商品分类信息
     *
     * @param id id值
     * @return 返回获取的商品分类信息
     */

    @Override
    public Category getCategoryById(BigInteger id) {
        Category categoryById = categoryMapper.getCategoryById(id);
        if (categoryById == null) {
            throw new BizException(BizExceptionEnum.CATEGORY_NOT_EXIT);
        }
        return categoryById;
    }

    /**
     * 新增商品分类
     *
     * @param newCategory 新商品分类对象
     */
    @Override
    public void addCategory(Category newCategory) {
        Category categoryByName = categoryMapper.getCategoryByName(newCategory.getName());
        if (categoryByName != null) {
            throw new BizException(BizExceptionEnum.CATEGORY_ALREADY_EXIT);
        }
        newCategory.setCreateTime(LocalDateTime.now());
        int i = categoryMapper.addCategory(newCategory);
        System.out.println("servicerImpl---" + newCategory);
        if (i <= 0) {
            throw new BizException(BizExceptionEnum.CATEGORY_ADD_ERROR);
        }
    }

    /**
     * @param id 要删除商品分类的id
     * @return 返会影响行数
     */
    @Override
    public int delCategoryById(BigInteger id) {
        Category category = categoryMapper.getCategoryById(id);
        if (category == null) {
            throw new BizException(2000, "您要删除的商品不存在，请重新确认");
        }
        return categoryMapper.delCategoryById(id);
    }

    /**
     * @param category 要编辑的商品分类信息
     * @return int 返回影响行数
     */
    @Override
    public int editCategory(Category category) {
        // 检查 ID 是否为空
        if (category.getId() == null) {
            throw new BizException(2000, "要编辑的商品分类ID不能为空");
        }

        // 检查要更新的分类是否存在
        Category categoryById = categoryMapper.getCategoryById(category.getId());
        if (categoryById == null) {
            throw new BizException(2000, "您要修改的商品分类不存在，请重新确认");
        }

        // 检查名称是否重复（如果提供了新名称）
        if (category.getName() != null && !category.getName().trim().isEmpty()) {
            Category categoryByName = categoryMapper.getCategoryByName(category.getName());
            // 如果存在同名分类，且不是当前分类本身，则抛出异常
            if (categoryByName != null && !category.getId().equals(categoryByName.getId())) {
                throw new BizException(BizExceptionEnum.CATEGORY_ALREADY_EXIT);
            }
        }
        if (category.getStatus() == null && category.getName() == null) {
            throw new BizException(2000, "您并没有对商品信息进行更改，请重新确认");
        }

        // 执行更新操作
        int result = categoryMapper.editCategory(category);
        if (result <= 0) {
            throw new BizException(BizExceptionEnum.CATEGORY_UPDATE_ERROR); // 需要定义此枚举
        }
        return result;
    }

    /**
     * 根据商品分类id获取该分类下的所有商品
     *
     * @param id 索引值
     * @return 返回指定商品分类下的所有商品
     */
    @Override
    public CategoryWithProductsDTO getCategoryWithProductsByCategoryId(BigInteger id) {
        CategoryWithProductsDTO categoryWithProductsByCategoryID =
                categoryMapper.getCategoryWithProductsByCategoryID(id);
        if (categoryWithProductsByCategoryID == null) {
            throw new BizException(2000, "请选择正确的商品分类进行查询");
        }
        return categoryWithProductsByCategoryID;
    }
}
