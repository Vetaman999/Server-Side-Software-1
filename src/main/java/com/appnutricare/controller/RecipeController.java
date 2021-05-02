package com.appnutricare.controller;

import com.appnutricare.entities.Recipe;
import com.appnutricare.service.IRecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Recipes")
@Api(tags="Recipe", value = "Servicio Web RESTFul de Recipe")
public class RecipeController {

    @Autowired
    private IRecipeService recipeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Bookings", notes="Método para listar todos los bookings")
    @ApiResponses({
            @ApiResponse(code=201, message = "recipe encontrados"),
            @ApiResponse(code=404, message = "recipe no encontrados")
    })
    public ResponseEntity<List<Recipe>> findAllRecipe(){
        try{
            List<Recipe> bookings = recipeService.getAll();
            if(bookings.size()>0)
                return new ResponseEntity<List<Recipe>>(bookings, HttpStatus.OK);
            else
                return new ResponseEntity<List<Recipe>>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<List<Recipe>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Recipe por Id", notes="Método para listar un recipe por Id")
    @ApiResponses({
            @ApiResponse(code=201, message = "recipe encontrado"),
            @ApiResponse(code=404, message = "recipe no encontrado")
    })
    public ResponseEntity<Recipe>findRecipeById(@PathVariable("id") Integer id){
        try{
            Optional<Recipe> booking= recipeService.getById(id);
            if(!booking.isPresent())
                return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<Recipe>(booking.get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    @PostMapping(value ="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de un Recipe de un Customer", notes ="Método que registra un booking" )
    @ApiResponses({
            @ApiResponse(code=201, message = "Booking creado"),
            @ApiResponse(code=404, message = "Booking no creado")
    })
    public ResponseEntity<Recipe> insertRecipe(@PathVariable("id") Integer id, @Valid @RequestBody Booking booking){
        try{
            Optional<Nutri> nutri = nutriservice.getById(id);
            if(nutri.isPresent()){
                recipe.setNutri(nutri.get());
                Recipe recipeNew = recipeService.save(recipe);
                return ResponseEntity.status(HttpStatus.CREATED).body(recipeNew);
            }
            else
                return new ResponseEntity<Recipe>(HttpStatus.FAILED_DEPENDENCY);
        }catch (Exception ex){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    */

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Actualización de datos de Recipe", notes = "Método que actualizar los datos de Recipe")
    @ApiResponses({
            @ApiResponse(code=200, message = "Datos de Recipe actualizados"),
            @ApiResponse(code=404, message = "REcipe no encontrado")
    })
    public ResponseEntity<Recipe> updateBooking(@PathVariable("id") Integer id, @Valid @RequestBody Recipe recipe){
        try{
            Optional<Recipe> bookingOld = recipeService.getById(id);
            if(!bookingOld.isPresent())
                return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
            else{
                recipe.setId(id);
                recipeService.save(recipe);
                return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Eliminación de Recipe por Id", notes = "Método que elimina los datos de un Recipe")
    @ApiResponses({
            @ApiResponse(code=200, message = "Datos de Recipe eliminados"),
            @ApiResponse(code=404, message = "Recipe no encontrado")
    })
    public ResponseEntity<Recipe> deleteBooking(@PathVariable("id") Integer id){
        try{
            Optional<Recipe> bookingDelete = recipeService.getById(id);
            if(bookingDelete.isPresent()){
                recipeService.delete(id);
                return new ResponseEntity<Recipe>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}