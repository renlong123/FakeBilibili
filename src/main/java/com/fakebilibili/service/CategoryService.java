package com.fakebilibili.service;

import com.fakebilibili.entity.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> insertCategoryAndReturn(String name, String description);

    public int insertIntoCategoryFunction(String name, String description);

    public List<Category> selectAllCategory();

}
