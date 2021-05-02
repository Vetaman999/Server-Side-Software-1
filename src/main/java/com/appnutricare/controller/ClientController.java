package com.appnutricare.controller;

import com.appnutricare.entities.Client;
import com.appnutricare.service.IClientService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
@Api(tags = "Client", value = "Servicio Web RESTful de Clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Clients", notes = "Método para listar todos los clients")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Clients encontrados"),
            @ApiResponse(code = 404, message = "Clients no encontrados")
    })
    public ResponseEntity<List<Client>> findAll() {
        try {
            List<Client> clients = new ArrayList<>();
            clients = clientService.getAll();
            if (clients.isEmpty())
                return new ResponseEntity<List<Client>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Client por Id", notes = "Método para encontrar un Client por Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client encontrado"),
            @ApiResponse(code = 404, message = "Client no encontrado")
    })
    public ResponseEntity<Client> findById(@PathVariable("id") Integer id)
    {
        try{
            Optional<Client> client = clientService.getById(id);
            if(!client.isPresent())
                return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Client>(client.get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByUsername/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Client por username", notes = "Método para encontrar un Client por username")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client encontrado"),
            @ApiResponse(code = 404, message = "Client no encontrado")
    })
    public ResponseEntity<Client> findByUsername(@PathVariable("username") String username)
    {
        try{
            Client client = clientService.findByUsername(username);
            if(client == null)
                return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Client>(client, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByFirstName/{firstname}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Clients por firstname", notes = "Método para encontrar Clients por firstname")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Clients encontrados"),
            @ApiResponse(code = 404, message = "Clients no encontrados")
    })
    public ResponseEntity<List<Client>> findByFirstName(@PathVariable("firstname") String firstname)
    {
        try {
            List<Client> clients = new ArrayList<>();
            clients = clientService.findByFirstName(firstname);
            if(clients.isEmpty())
                return new ResponseEntity<List<Client>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByLastName/{lastname}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Clients por lastname", notes = "Método para encontrar Clients por lastname")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Clients encontrados"),
            @ApiResponse(code = 404, message = "Clients no encontrados")
    })
    public ResponseEntity<List<Client>> findByLastName(@PathVariable("lastname") String lastname)
    {
        try {
            List<Client> clients = new ArrayList<>();
            clients = clientService.findByLastName(lastname);
            if(clients.isEmpty())
                return new ResponseEntity<List<Client>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByFirstNameAndLastName/{firstname}/{lastname}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Clients por firstname y lastname", notes = "Método para encontrar Clients por firstname y lastname")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Clients encontrados"),
            @ApiResponse(code = 404, message = "Clients no encontrados")
    })
    public ResponseEntity<List<Client>> findByFirstNameAndLastName(@PathVariable("firstname") String firstname, @PathVariable("lastname") String lastname)
    {
        try {
            List<Client> clients = new ArrayList<>();
            clients = clientService.findByFirstNameAndLastName(firstname, lastname);
            if(clients.isEmpty())
                return new ResponseEntity<List<Client>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Clients", notes = "Método para agregar un Client")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client agregado"),
            @ApiResponse(code = 404, message = "Client no fue agregado")
    })
    public ResponseEntity<Client> insertCustomer(@Valid @RequestBody Client client)
    {
        try{
            Client newClient = clientService.save(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
        }catch (Exception e){
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Client", notes = "Método que actualiza los datos de un Client")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Client actualizado"),
            @ApiResponse(code = 404, message = "Client no fue encontrado")
    })
    public ResponseEntity<Client> updateClient(
            @PathVariable("id") Integer id, @Valid @RequestBody Client client){
        try {
            Optional<Client> foundClient = clientService.getById(id);
            if(!foundClient.isPresent())
                return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
            client.setId(id);
            clientService.save(client);
            return new ResponseEntity<Client>(client, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de un Client", notes = "Método para eliminar un Client")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Client eliminado"),
            @ApiResponse(code = 404, message = "Client no encontrado")
    })
    public ResponseEntity<Client> deleteCustomer(@PathVariable("id") Integer id)
    {
        try{
            Optional<Client> deletedCustomer = clientService.getById(id);
            if(!deletedCustomer.isPresent())
                return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
            clientService.delete(id);
            return new ResponseEntity<Client>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}