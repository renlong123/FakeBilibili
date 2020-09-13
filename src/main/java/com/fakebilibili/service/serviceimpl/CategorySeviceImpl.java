package com.fakebilibili.service.serviceimpl;

import com.fakebilibili.dao.CategoryDAO;
import com.fakebilibili.entity.Category;
import com.fakebilibili.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorySeviceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public List<Category> insertCategoryAndReturn(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        int insertCategory = categoryDAO.insertCategory(category);
        List<Category> categories = categoryDAO.getCategories();
        return categories;
    }
}
