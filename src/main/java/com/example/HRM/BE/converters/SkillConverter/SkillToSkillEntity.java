package com.example.HRM.BE.converters.SkillConverter;

import com.example.HRM.BE.DTO.Category;
import com.example.HRM.BE.DTO.Skill;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.CategoryEntity;
import com.example.HRM.BE.entities.SkillEntity;
import com.example.HRM.BE.exceptions.CategoryException.CategoryNotFound;
import com.example.HRM.BE.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SkillToSkillEntity extends Converter<Skill, SkillEntity> {

    @Autowired
    private Converter<Category, CategoryEntity> categoryToCategoryEntity;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public SkillEntity convert(Skill source) {
        SkillEntity skillEntity = new SkillEntity();

        skillEntity.setId(source.getId());
        skillEntity.setName(source.getName());
//        skillEntity.setCategoryEntity(categoryToCategoryEntity.convert(source.getCategory()));
        if(source.getCategory().getId() != 0) {
            CategoryEntity categoryEntity = this.categoryRepository.findById(source.getCategory().getId()).orElseThrow(
                    () -> new CategoryNotFound()
            );
            skillEntity.setCategoryEntity(categoryEntity);
        }
        return skillEntity;
    }
}
