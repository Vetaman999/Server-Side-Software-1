package com.appnutricare.service;

import com.appnutricare.entities.Recipe;

import java.util.List;

public interface IRecipeService extends CrudService<Recipe>{
    public List<Recipe> findAllByNutritionist(Long nutritionist) throws Exception;
}
