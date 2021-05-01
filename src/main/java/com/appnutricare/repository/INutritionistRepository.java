package com.appnutricare.repository;

import com.appnutricare.entities.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INutritionistRepository extends JpaRepository<Nutritionist, Long> {
    public Nutritionist findByEmail(String email);
    public List<Nutritionist> findByLastName(String lastname);
    public List<Nutritionist> findByFirstName(String lastname);
    public List<Nutritionist> findByFirstNameAndLastName(String firstname, String lastname);
}
