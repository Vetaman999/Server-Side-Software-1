package com.appnutricare.controller;

import com.appnutricare.entities.Appointment;
import com.appnutricare.entities.Nutritionist;
import com.appnutricare.service.IAppointmentService;
import com.appnutricare.service.IClientService;
import com.appnutricare.service.INutritionistService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointment")
@Api(tags = "Appointment", value = "Servicio Web RESTFul de Appointment")
public class AppointmentController {

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private INutritionistService nutritionistService;

//    @Autowired
//    private IDietService dietService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Appointments", notes = "Método que registra Appointments en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Appointment creado"),
            @ApiResponse(code = 404, message = "Appointment no creado")
    })
    public ResponseEntity<Appointment> insertAppointment(@Valid @RequestBody Appointment appointment) {
        try {
            Appointment appointmentNew = appointmentService.save(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(appointmentNew);
        } catch (Exception e) {
            return new ResponseEntity<Appointment>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Appointment", notes = "Método que actualiza los datos de Appointment")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Datos de Appointment actualizado"),
            @ApiResponse(code = 404, message = "Appointment no encontrado")
    })
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable("id") Integer id, @Valid @RequestBody Appointment appointment) {
        try {
            Optional<Appointment> appointmentUp = appointmentService.getById(id);
            if (!appointmentUp.isPresent())
                return new ResponseEntity<Appointment>(HttpStatus.NOT_FOUND);
            appointment.setId(id);
            appointmentService.save(appointment);
            return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Appointment>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de datos de Appointment", notes = "Método que elimina los datos de Appointment en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Datos de Appointment eliminados"),
            @ApiResponse(code = 404, message = "Appointment no encontrado")
    })
    public ResponseEntity<Appointment> deleteAppointment(@PathVariable("id") Integer id) {
        try {
            Optional<Appointment> appointmentDelete = appointmentService.getById(id);
            if (!appointmentDelete.isPresent())
                return new ResponseEntity<Appointment>(HttpStatus.NOT_FOUND);
            appointmentService.delete(id);
            return new ResponseEntity<Appointment>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Appointment>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Appointments", notes = "Método para listar todos los Appointments")
    @ApiResponses({
            @ApiResponse(code=201, message = "Appointments encontrados"),
            @ApiResponse(code=404, message = "Appointments no encontrados")
    })
    public ResponseEntity<List<Appointment>> findAllAppointments(){
        try{
            List<Appointment> appointments = appointmentService.getAll();
            if(appointments.size()>0)
                return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
            else
                return new ResponseEntity<List<Appointment>>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<List<Appointment>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Appointment por Id", notes = "Método para listar un Appointment por Id")
    @ApiResponses({
            @ApiResponse(code=201, message = "Appointment encontrado"),
            @ApiResponse(code=404, message = "Appointment no encontrado")
    })
    public ResponseEntity<Appointment> findAppointmentById(@PathVariable("id") Integer id){
        try{
            Optional<Appointment> appointment = appointmentService.getById(id);
            if(!appointment.isPresent())
                return new ResponseEntity<Appointment>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<Appointment>(appointment.get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Appointment>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchBetweenDates", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Appointments entre fechas", notes = "Método para listar Appointments entre fechas")
    @ApiResponses({
            @ApiResponse(code=201, message = "Appointments encontrado"),
            @ApiResponse(code=404, message = "Appointments no encontrados")
    }) //Al requestparam le puedes decir que sea opcional y no necesita estar en el URL
    public ResponseEntity<List<Appointment>> findAppointmentByAppointmentDateBetweenDates(@RequestParam("date1") String date1_string,
                                                                                          @RequestParam("date2") String date2_string){
        try{
            Date checking_date = ParseDate(date1_string);
            Date checkout_date = ParseDate(date2_string);
            List<Appointment> appointments = appointmentService.findBetweenDates(checking_date, checkout_date);
            if(appointments!=null && appointments.size()>0)
                return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
            else
                return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<List<Appointment>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static Date ParseDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try{
            result = format.parse(date);
        }catch (Exception e){
        }
        return result;
    }

    // Modificar notas del appointment - - FALTA MODIFICAR BIEN
    @PutMapping(value = "/update_appointment_notes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de notas de Appointment", notes = "Método que actualiza las notas un Appointment")
    @ApiResponses({
            @ApiResponse(code=201, message = "Datos de Appointment actualizados"),
            @ApiResponse(code=404, message = "Appointment no encontrado")
    })
    public ResponseEntity<Appointment> updateAppointmentNotes(@PathVariable("id") Integer id,
                                                             @Valid @RequestBody Appointment appointment){
        try{
            Optional<Appointment> appointmentOld = appointmentService.getById(id); //Se encuentra un appointment
            if(!appointmentOld.isPresent()) {
                return new ResponseEntity<Appointment>(HttpStatus.NOT_FOUND);
            }
            else { //Si es así, se actualiza con nuevos datos del Appointment que está llegando
                appointment.setId(id);
                appointmentService.save(appointment);
                return new ResponseEntity<Appointment>(HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<Appointment>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Modificar la fecha del appointment - FALTA MODIFICAR BIEN

    @PutMapping(value = "/update_appointment_date/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de fecha de Appointment", notes = "Método que actualiza la fecha un Appointment")
    @ApiResponses({
            @ApiResponse(code=201, message = "Fecha de Appointment actualizada"),
            @ApiResponse(code=404, message = "Appointment no encontrado")
    })
    public ResponseEntity<Appointment> updateAppointmentDate(@PathVariable("id") Integer id,
                                                             @Valid @RequestBody Appointment appointment){
        try{
            Optional<Appointment> appointmentOld = appointmentService.getById(id); //Se encuentra un appointment
            if(!appointmentOld.isPresent()) {
                return new ResponseEntity<Appointment>(HttpStatus.NOT_FOUND);
            }
            else { //Si es así, se actualiza con nuevos datos del Appointment que está llegando
                appointment.setId(id);
                appointmentService.save(appointment);
                return new ResponseEntity<Appointment>(HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<Appointment>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Modificar la dieta
//    public ResponseEntity<Appointment> updateDiet(){
//
//    }

}
