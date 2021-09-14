package com.m2i.demomedical.controller;

import com.m2i.demomedical.entities.PatientEntity;
import com.m2i.demomedical.helpers.LoggingHelper;
import com.m2i.demomedical.service.PatientService;
import com.m2i.demomedical.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    final private LoggingHelper lh;

    @Autowired
    final private PatientService ps;

    @Autowired
    final private VilleService vs;

    public PatientController(LoggingHelper lh, PatientService ps, VilleService vs) {
        this.lh = lh;
        this.ps = ps;
        this.vs = vs;
    }

    @GetMapping("")
    public String list( Model model ){
        model.addAttribute( "liste_patient" , ps.getList() );
        model.addAttribute( "liste_villes" , vs.getList() );
        return "patient/list";
    }

    @GetMapping("/add")
    public String add( Model model ){
        model.addAttribute( "entete_titre" , "Ajouter un patient" );
        model.addAttribute( "liste_villes" , vs.getList() );
        model.addAttribute( "p" , new PatientEntity());
        return "patient/add_edit";
    }

    @PostMapping("/add")
    public String addPost( HttpServletRequest request, Model model  ){
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
        String ville = request.getParameter("ville");


        //lh.log(" AddPost : Params : nom = "+nom+", prenom = "+nom+" ", INFO );

        try{
            ps.addPatient(nom, prenom, telephone, email, Integer.parseInt( ville ) );
            //lh.log(" Success AddPost " , INFO );
            return "redirect:/patient?success";
        }catch( Exception e ){
            //lh.log(" Erreur AddPost " + e.getMessage() , ERROR );

            model.addAttribute("error" , e.getMessage() );

            // Récupérer les anciens paramètres
            PatientEntity perreur = new PatientEntity();
            perreur.setNom(nom);

            model.addAttribute( "p" , perreur );
            return "patient/add_edit";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit( Model model , @PathVariable int id ){
        try{
            model.addAttribute( "entete_titre" , "Editer un patient" );
            model.addAttribute( "liste_villes" , vs.getList() );
            model.addAttribute( "p" , ps.find(id) );
            return "patient/add_edit";
        }catch ( Exception e ){
            return "redirect:/patient?error="+e.getMessage();
        }
    }

    @PostMapping("/edit/{id}")
    public String editPost( HttpServletRequest request , @PathVariable int id ){

        try{
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String telephone = request.getParameter("telephone");
            String email = request.getParameter("email");
            String ville = request.getParameter("ville");

            ps.editPatient(id, nom, prenom, email, telephone , Integer.parseInt( ville ) );
            return "redirect:/patient?success";
        }catch( Exception e ){
            return "patient/add_edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete( @PathVariable int id ){
        try{
            ps.delete(id);
            return "redirect:/patient?success";
        }catch( Exception e ){
        return "patient?error="+e.getMessage();
        }
    }

    /* @PostMapping("/addv2")
    public void addPostV2( HttpServletRequest request, Model model  ){
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");

        PatientEntity p = new PatientEntity();

        p.setNom(nom);
        p.setPrenom(prenom);
        p.setEmail(email);
        p.setTelephone(telephone);

        em.persist(p);

    } */
}
