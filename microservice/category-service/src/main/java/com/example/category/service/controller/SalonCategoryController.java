package com.example.category.service.controller;

import com.example.category.service.dto.SalonDTO;
import com.example.category.service.modal.Category;
import com.example.category.service.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class SalonCategoryController {
    private final ICategoryService categoryService;

    @PostMapping("/salon-owner")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){

        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        Category saveCategory = categoryService.saveCategory(category , salonDTO);
        return ResponseEntity.ok(saveCategory);

    }

    @DeleteMapping ("/salon-owner/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws Exception {

        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        categoryService.deleteCategoryById(id , salonDTO.getId());
        return ResponseEntity.ok("category deleted succesfully");

    }
}
