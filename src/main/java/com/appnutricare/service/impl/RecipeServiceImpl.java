package com.appnutricare.service.impl;

import com.appnutricare.entities.Recipe;
import com.appnutricare.repository.IRecipeRepository;
import com.appnutricare.service.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class RecipeServiceImpl implements IRecipeService {

    @Autowired
    private IRecipeRepository recipeRepository;

    @Override
    @Transactional
    public Recipe save(Recipe recipe) throws Exception {
        return recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws Exception {
        recipeRepository.findRecipeById(id);
    }

    @Override
    public List<Recipe> getAll() throws Exception {
        return recipeRepository.findAll();
    }

    @Override
    public Optional<Recipe> getById(Integer id) throws Exception {
        return recipeRepository.findRecipeById(id);
    }

    @Override
    public List<Recipe> findAllByNutritionist(Long nutritionist) throws Exception {
        return null;
    }
}
