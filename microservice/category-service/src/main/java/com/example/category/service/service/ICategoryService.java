package com.example.category.service.service;

import com.example.category.service.dto.SalonDTO;
import com.example.category.service.modal.Category;

import java.util.List;
import java.util.Set;

public interface ICategoryService {

    Category saveCategory(Category category, SalonDTO salonDTO);

    Set<Category> getAllCategriesBySalon(Long id);

    Category getCategoryById(Long id) throws Exception;

    void deleteCategoryById(Long id, Long salonId ) throws Exception;
}
