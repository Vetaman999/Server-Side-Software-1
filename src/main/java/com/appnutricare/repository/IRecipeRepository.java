package com.appnutricare.repository;

import com.appnutricare.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IRecipeRepository extends JpaRepository<Recipe, Long> {
    //Aqui utilizo los Optional porque la busqueda ya esta predeterminada por el ID.

    //Optional<Recipe> findAllByNutri(Long nutri);
    //Optional<Recipe> findById(Long id);

    public List<Recipe> findAllByNutri(@Param("nutri") Long nutri);
    public Optional<Recipe> findById(Long id);
}