package com.fakebilibili.dao.daoimpl;

import com.fakebilibili.dao.CategoryDAO;
import com.fakebilibili.entity.Category;
import com.fakebilibili.mapper.CategoryMapper;
import com.fakebilibili.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * DAO层插入分类
     * @param category
     * @return
     */
    @Override
    public int insertCategory(Category category) {
        int insert = categoryMapper.insert(category);
        return insert;
    }

    @Override
    public Category getCategoryById(Integer id) {
        return null;
    }

    @Override
    public Category getCategoryByName(String name) {
        return null;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categories = categoryMapper.selectByExample(null);
        return categories;
    }
}
