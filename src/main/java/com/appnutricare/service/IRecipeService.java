package com.appnutricare.service;

import com.appnutricare.entities.Recipe;

import java.util.List;

public interface IRecipeService extends CrudService<Recipe>{
    public List<Recipe> findAllByNutri(Long nutri) throws Exception;
    public List<Recipe> findById(Long id) throws Exception;
}
