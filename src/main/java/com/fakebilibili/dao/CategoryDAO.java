package com.fakebilibili.dao;

import com.fakebilibili.entity.Category;

import java.util.List;

public interface CategoryDAO {

    public int insertCategory(Category category);
    public Category getCategoryById(Integer id);
    public Category getCategoryByName(String name);
    public List<Category> getCategories();
}
