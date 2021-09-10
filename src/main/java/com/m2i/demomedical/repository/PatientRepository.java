package com.m2i.demomedical.repository;


import com.m2i.demomedical.entities.PatientEntity;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<PatientEntity, Integer> {

}
