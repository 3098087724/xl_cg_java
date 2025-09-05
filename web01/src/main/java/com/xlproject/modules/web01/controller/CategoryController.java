package com.xlproject.modules.web01.controller;


import com.github.pagehelper.PageInfo;
import com.xlproject.modules.common.response.R;
import com.xlproject.modules.dto.categoryDto.CategoryWithProductsDTO;
import com.xlproject.modules.entity.bean.Category;
import com.xlproject.modules.service.category.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;

@RestController
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @GetMapping("/categorys")
    public  R<PageInfo<Category>> getCategorys(
            @RequestParam(defaultValue = "1")int pageNum,@RequestParam(defaultValue = "10")int pageSize
    ){
            return R.OK("获取所有商品分类成功",categoryService.getCategorys(pageNum,pageSize));
    }
    @GetMapping("/category")
    public  R<Category> getCategoryByID(@RequestParam BigInteger id ){
        return  R.OK("获取商品分类信息成功",categoryService.getCategoryById(id));
    }
    @PostMapping("/category")
    public R<Category> addCategory(@RequestBody @Valid Category newCategory){
        categoryService.addCategory(newCategory);
        if (newCategory.getId()!=null){
            return R.OK("新增商品分类成功",newCategory);
        }
        return R.ERROR(2000,"添加商品分类失败，请重试");
    }
    @DeleteMapping("/category/{id}")
    public R<?> delCategoryById(@PathVariable("id") @NotNull(message = "要删的商品分类id不能为空") BigInteger id){
        int i = categoryService.delCategoryById(id);
        if (i>0){
            return  R.OK("删除商品分类成功");
        }
            return  R.ERROR(2000,"删除商品分类失败");
    }
    @PutMapping("/category/edit")
    public  R<?> editCategory(@RequestBody Category category){
        int i = categoryService.editCategory(category);
        if (i>0){
            return  R.OK("编辑商品分类信息成功");
        }
        return  R.ERROR(2000,"商品分类更新失败，请重试");
    }

    @GetMapping("/category/products")
    public  R<CategoryWithProductsDTO> getCategoryWithProductsByCategoryId(@RequestParam(required = false) BigInteger id){
        if (id==null){
            return  R.ERROR(2000,"传入的id值有误");
        }
        return  R.OK("获取指定商品分类的商品成功",categoryService.getCategoryWithProductsByCategoryId(id));
    }

}
