package com.appnutricare.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="Recipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name="Customer.findByFirstName",query = "select  r from Recipe r where r.name= ?1")
public class Recipe implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @Column(name="name", nullable = false, length = 50)
        private String name;
        @Column(name="descripcion", nullable = false, length = 250)
        private String descripcion;
        @Column(name="preparation", nullable = false, length = 500)
        private String preparation;
        @Column(name="ingredients", nullable = false, length = 500)
        private String ingredients;
        @Column(name="favorite", nullable = true, length = 10)
        private Long favorite;
        @Column(name="created_at", nullable = false)
        private Date created_at;
        @Column(name="last_modification", nullable = false)
        private Date last_modification;



        //Implementacion de FK - el many to one con nutritionist
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Nutritionist_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Nutritionist nutritionist;
     */
}
