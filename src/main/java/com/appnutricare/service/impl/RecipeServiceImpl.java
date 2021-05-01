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
    private IRecipeRepository recipeService;

    @Override
    public Recipe save(Recipe recipe) throws Exception {
        return recipeService.save(recipe);
    }

    @Override
    @Transactional
    public void delete(long id) throws Exception {
        recipeService.findById(id);
    }

    @Override
    public List<Recipe> getAll() throws Exception {
        return recipeService.findAll();
    }

    @Override
    public Optional<Recipe> getById(Long id) throws Exception {
        return recipeService.findById(id);
    }

    @Override
    public List<Recipe> findAllByNutri(Long nutri) throws Exception {
        return null;
    }

    @Override
    public List<Recipe> findById(Long id) throws Exception {
        return null;
    }
}
