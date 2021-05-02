package com.appnutricare.repository;

import com.appnutricare.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRecipeRepository extends JpaRepository<Recipe, Long> {
    //Aqui utilizo los Optional porque la busqueda ya esta predeterminada por el ID.

    //Optional<Recipe> findAllByNutri(Integer nutri);
    //Optional<Recipe> findById(Integer id);

    //public List<Recipe> findAllByNutritionist(Long nutritionist);
    public Optional<Recipe> findRecipeById(Integer id);
}