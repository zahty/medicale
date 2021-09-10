package com.m2i.demomedical.helpers;

import com.m2i.demomedical.entities.PatientEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Aspect
@Component
@Order(0)
public class LoggingAspect {

    final private LoggingHelper lh;

    public LoggingAspect(LoggingHelper lh) {
        this.lh = lh;
    }

    /* @Around("execution(* com.m2i.demomedical.service.PatientService.addPatient(..) )")
    public Object callOnPatientPOST( ProceedingJoinPoint proceedingJoinPoint ) throws IOException {
        String chaine = "";


        Object value = null;

        try {
            value = proceedingJoinPoint.proceed(); // Appel addPost  , add , ...
        } catch (Throwable e) {
            e.printStackTrace();

        }

        return value;
    }

    @After("execution(* com.m2i.demomedical.controller.PatientController.add(..) )")
    public void callAfterPatientGet() {
        System.out.println("Je suis un aspect, je m'execute après Get Patient");
    } */

    @Before("execution(* com.m2i.demomedical.service.PatientService.*(..) )")
    public void callOnPatientServiceCall() {
        System.out.println("Je suis un aspect, je m'execute avant chaque méthode de patient Service");
    }

    //@AfterThrowing()
    //public void envoiMailException(){
    // Envoi un mail à l'administrateur
    //}

    @Around("execution(* com.m2i.demomedical.controller.*.*(..))")
    public Object logActionOnFile(ProceedingJoinPoint proceedingJoinPoint) throws IOException {
        String chaine = "";

        if( proceedingJoinPoint.getArgs().length > 0 )
            chaine += "Valeur du paramètre 0 du contolleur " + proceedingJoinPoint.getArgs()[0];

        chaine += "Signature : " + proceedingJoinPoint.getSignature();

        lh.log( chaine  );
        Object value = null;

        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        chaine = "Valeur de retour de la méthode du controlleur : " + value;
        lh.log( chaine  );

        return value;
    }


}
