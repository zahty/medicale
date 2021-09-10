package com.m2i.demomedical.service;

import com.m2i.demomedical.entities.VilleEntity;
import com.m2i.demomedical.entities.VilleEntity;
import com.m2i.demomedical.repository.VilleRepository;
import org.springframework.stereotype.Service;

@Service
public class VilleService {

    private VilleRepository pr;

    public VilleService(VilleRepository pr) {
        this.pr = pr;
    }

    public Iterable<VilleEntity> getList(){
        return pr.findAll();
    }

    public VilleEntity find(int id) {
        return pr.findById( id ).get();
    }

    public void add(String nom, String cp) {
        VilleEntity p = new VilleEntity();
        p.setNom(nom);
        p.setCodePostal( Integer.parseInt( cp) ) ;
        pr.save( p );
    }

    public void edit(int id, String nom, String cp) {
        VilleEntity p = pr.findById(id).get();
        p.setNom(nom);
        p.setCodePostal( Integer.parseInt( cp) ) ;
        pr.save( p );
    }

    public void delete(int id) {
        pr.deleteById(id);
    }
}

