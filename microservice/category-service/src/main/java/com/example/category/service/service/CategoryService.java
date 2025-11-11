package com.example.category.service.service;

import com.example.category.service.dto.SalonDTO;
import com.example.category.service.modal.Category;
import com.example.category.service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;
    @Override
    public Category saveCategory(Category category, SalonDTO salonDTO) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setSalonId(category.getSalonId());
        newCategory.setImage(category.getImage());

        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategriesBySalon(Long id) {
        return categoryRepository.findBySalonId(id);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        Category category = new Category();
        if (category == null){
            throw new Exception("category not existed");

        }

        return category;
    }

    @Override
    public void deleteCategoryById(Long id , Long salonId) throws Exception {
        Category category = getCategoryById(id);
        if (!category.getSalonId().equals(salonId) ){
            throw new Exception("You dont have a permission to delete this category");
        }
        categoryRepository.deleteById(id);

    }
}
