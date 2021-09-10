package com.m2i.demomedical.api;

import com.m2i.demomedical.entities.PatientEntity;
import com.m2i.demomedical.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping("/api/patient")
public class PatientAPIController {

    @Autowired
    PatientService ps;

    // crud : ajout , update , delete , getall , get

    @GetMapping(path="", produces = "application/json")
    public Iterable<PatientEntity> getAll(){
        return ps.getList();
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<PatientEntity> get(@PathVariable("id") int id ){
        try {
            PatientEntity patient = ps.find(id);
            return ResponseEntity.ok() // HTTP 200
                    .body(patient);
        }catch( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    /*
    @TODO : Check Exception problem
     */

    @PostMapping(path="", produces = "application/json")
    public ResponseEntity<PatientEntity> add( @RequestBody PatientEntity patient ) {
        try{
            PatientEntity createPatient = ps.addPatient( patient.getNom() , patient.getPrenom() , patient.getTelephone()
                    , patient.getEmail() , patient.getVille().getId() );

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createPatient.getId())
                    .toUri();

            return ResponseEntity.created(uri) // created => HTTP 201
                    .body(createPatient);

        }catch ( Exception e ){
            System.out.println("Je suis ici");
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST , e.getMessage() );
        }

    }

    @PutMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<PatientEntity> edit( @RequestBody PatientEntity patient , @PathVariable("id") int id ) {
        try{
            PatientEntity updatedPatient  = ps.editPatient( id ,  patient.getNom() , patient.getPrenom() , patient.getTelephone()
                    , patient.getEmail() , patient.getVille().getId() );


            return ResponseEntity.ok() // OK => HTTP 200
                    .body(updatedPatient);

        }catch ( Exception e ){
            System.out.println("Je suis ici");
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST , e.getMessage()  );
        }

    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id ){
        try {
            ps.delete(id);
            return ResponseEntity.ok() // HTTP 200
                    .body(null);
        }catch( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }
}
