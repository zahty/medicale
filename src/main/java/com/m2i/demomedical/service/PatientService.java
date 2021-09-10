package com.m2i.demomedical.service;

import com.m2i.demomedical.entities.PatientEntity;
import com.m2i.demomedical.entities.VilleEntity;
import com.m2i.demomedical.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository pr;

    public PatientService(PatientRepository pr) {
        this.pr = pr;
    }

    public Iterable<PatientEntity> getList(){
        return pr.findAll();
    }

    private void  checkPatient( String nom, String prenom, String telephone , String email ) throws Exception {
        if( prenom.length() < 2 ){
            throw new Exception("Invalid value pour prénom");
        }

        if( nom.length() < 2 ){
            throw new Exception("Invalid value pour nom");
        }

        if( telephone.length() < 2 ){
            throw new Exception("Invalid value pour telephone");
        }

        if( email.length() < 2 ){
            throw new Exception("Invalid value pour email");
        }
    }

    public PatientEntity addPatient(String nom, String prenom, String telephone , String email, int ville ) throws Exception {
        checkPatient( nom, prenom, telephone , email );
        PatientEntity p = new PatientEntity();
        p.setNom(nom);
        p.setPrenom(prenom);
        p.setEmail(email);
        p.setTelephone(telephone);
        VilleEntity villeP = new VilleEntity();
        villeP.setId( ville );
        p.setVille( villeP );
        pr.save( p );

        return p;
    }

    public PatientEntity editPatient(int idp, String nom, String prenom, String email, String telephone , int ville ) throws Exception {
        checkPatient( nom, prenom, telephone , email );

        PatientEntity p = pr.findById(idp).get();
        p.setNom(nom);
        p.setPrenom(prenom);
        p.setEmail(email);
        p.setTelephone(telephone);
        VilleEntity villeP = new VilleEntity();
        villeP.setId( ville );
        p.setVille( villeP );
        pr.save( p );
        return p;
    }

    public PatientEntity find(int id) {
        return pr.findById( id ).get();
    }

    public void delete(int id) {
        pr.deleteById(id);
    }
}
