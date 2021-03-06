package com.fakebilibili.controller;

import com.fakebilibili.entity.Category;
import com.fakebilibili.service.CategoryService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 插入分类并将所有分类结果查寻返回,暂时不需要了，分开处理
     * @param name
     * @param description
     * @return
     */
    //@RequestMapping(value = "/addCategory",method = RequestMethod.POST)
   // @ResponseBody
    public String addCategoryAndReturn(@RequestParam(required = true) String name, String description){
        List<Category> categories = categoryService.insertCategoryAndReturn(name, description);
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        map.put("categories",categories);
        String json = gson.toJson(map);
        return json;
    }

    @RequestMapping(value = "/addCategory",method = RequestMethod.POST)
    @ResponseBody
    public String addCategoryFunction(@RequestParam(required = true) String name, String description){
        int i = categoryService.insertIntoCategoryFunction(name, description);
        if(i>=1){
            return "true";
        }else{
            return "false";
        }
    }

    @RequestMapping(value = "/getCategory",method = RequestMethod.GET)
    @ResponseBody
    public String selectAllCategory(){
        List<Category> categories = categoryService.selectAllCategory();
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        map.put("categories",categories);
        String json = gson.toJson(map);
        return json;
    }
}
