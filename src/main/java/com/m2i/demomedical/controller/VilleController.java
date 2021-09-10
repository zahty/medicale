package com.m2i.demomedical.controller;

import com.m2i.demomedical.entities.PatientEntity;
import com.m2i.demomedical.entities.VilleEntity;
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
@RequestMapping("/ville")
public class VilleController {

    @Autowired
    final private VilleService vs;

    public VilleController(VilleService vs) {
        this.vs = vs;
    }

    @GetMapping("")
    public String list( Model model ){
        model.addAttribute( "liste_ville" , vs.getList() );
        return "ville/list";
    }

    @GetMapping("/add")
    public String add( Model model ){
        model.addAttribute( "liste_villes" , vs.getList() );
        model.addAttribute( "v" , new VilleEntity());
        return "ville/add_edit";
    }

    @PostMapping("/add")
    public String addPost( HttpServletRequest request ){
        try{
            String nom = request.getParameter("nom");
            String cp = request.getParameter("cp");
            vs.add(nom, cp );
            return "redirect:/ville?success";
        }catch( Exception e ){
            return "ville/add_edit";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit( Model model , @PathVariable int id ){
        model.addAttribute( "entete_titre" , "Editer une ville" );
        model.addAttribute( "v" , vs.find(id) );
        return "ville/add_edit";
    }

    @PostMapping("/edit/{id}")
    public String editPost( HttpServletRequest request , @PathVariable int id , Model model  ){
        try{
            String nom = request.getParameter("nom");
            String cp = request.getParameter("cp");

            vs.edit(id, nom, cp );
            return "redirect:/ville?success";
        }catch( Exception e ){
            System.out.println(e.getMessage() );
            model.addAttribute("error" , e.getMessage() );
            return "ville/add_edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete( @PathVariable int id ){
        try{
            vs.delete(id);
            return "redirect:/ville?success";
        }catch( Exception e ){
            return "ville?error="+e.getMessage();
        }
    }

}
