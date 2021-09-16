package com.m2i.demomedical.controller;

import com.m2i.demomedical.entities.UserEntity;
import com.m2i.demomedical.repository.UserRepository;
import com.m2i.demomedical.security.ApplicationConfig;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
@Secured("ROLE_ADMIN")
public class UtilisateursController {

    final ApplicationConfig applicationConfig;
    final UserRepository ur;

    public UtilisateursController(ApplicationConfig applicationConfig, UserRepository ur) {
        this.applicationConfig = applicationConfig;

        this.ur = ur;
    }

    @RequestMapping(value = "")
    public String listAll(Model model) {
        List<UserEntity> lu = (List<UserEntity>) ur.findAll();
        model.addAttribute("liste_utilisateurs", lu);
        model.addAttribute("is_edit", false);
        model.addAttribute("as_admin", false);
        return "user/list";
    }

    @GetMapping(value = "/add")
    public String addUserGet(Model model) {
        model.addAttribute("entete_titre", "Ajouter utilisateur");
        model.addAttribute("placeholder_nom", "Nom*");
        model.addAttribute("placeholder_mail", "Email*");
        model.addAttribute("value_photouser", "user.png");
        model.addAttribute("as_admin", false);
        model.addAttribute("is_edit", false);
        model.addAttribute("button_submit_text", "Ajouter utilisateur");
        return "user/add_edit";
    }

    @PostMapping(value = "/add")
    public String addUserPost(HttpServletRequest request) {
        try {
            UserEntity u = new UserEntity();
            u.setName(request.getParameter("nom"));
            u.setUsername(request.getParameter("username"));
            u.setEmail(request.getParameter("email"));
            u.setPassword(applicationConfig.passwordEncoder().encode(request.getParameter("password")));
            u.setPhotouser(request.getParameter("photouser"));
            u.setRoles(request.getParameter("roles"));
            ur.save(u);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/user";
    }


    @GetMapping(value = "/edit/{id}")
    public String editUserget(Model model, @PathVariable int id) {
        try {
            UserEntity u = ur.findById(id).orElse(null);
            model.addAttribute("entete_titre", "Modifier Utilisateur ID " + String.valueOf(id));
            model.addAttribute("u", u);
            model.addAttribute("as_admin", u.getRoles().equals("ROLE_ADMIN"));
            model.addAttribute("is_edit", true);
            model.addAttribute("button_submit_text", "Mettre à jour");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient " + id + " n'est pas trouvé");
        }
        return "user/add_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String editUserPost(HttpServletRequest request, @PathVariable int id) {
        try {
            UserEntity u = ur.findById(id).orElse(null);
            u.setName(request.getParameter("nom"));
            u.setEmail(request.getParameter("email"));
            if (request.getParameter("password") != null) {
                u.setPassword(applicationConfig.passwordEncoder().encode(request.getParameter("password")));
            }
            u.setPhotouser(request.getParameter("photouser"));
            u.setRoles(request.getParameter("roles"));
            u.setUsername(request.getParameter("username"));
            ur.save(u);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur dans l'édition du patient " + id);
        }
        return "redirect:/user";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUserGet(Model model, @PathVariable int id) {
        try {
            model.addAttribute("entete_titre", "Supprimer utilisateur ID " + String.valueOf(id));
            UserEntity u = ur.findById(id).orElse(null);
            model.addAttribute("confirmation_text", "L'utilisateur " + u.getName() + " sera supprimé");
            model.addAttribute("button_submit_text", "Supprimer");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "L'utilisateur " + id + " n'est pas trouvé");
        }
        return "common/delete";
    }


    @PostMapping(value = "/delete/{id}")
    public String deleteUserDelete(@PathVariable int id) {
        try {
            UserEntity u = ur.findById(id).orElse(null);
            ur.delete(u);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur pendant la suppression de l'utilisateur "+id);
        }
        return "redirect:/user";
    }


    /*@GetMapping( value = "/profil/{id}" )
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public String myProfile( @PathVariable int id , Model model ){
    	try {
            UserEntity u = ur.findById(id).orElse(null);
            model.addAttribute("entete_titre", "Modifier profil" + String.valueOf(id));
            model.addAttribute("value_nom", u.getName());
            model.addAttribute("value_mail", u.getEmail());
            model.addAttribute("value_username", u.getUsername());
            model.addAttribute("value_photouser", u.getPhotouser());
            model.addAttribute("as_admin", u.getRoles().equals("ROLE_ADMIN"));
            model.addAttribute("is_edit", true);
            model.addAttribute("button_submit_text", "Mettre à jour");


		  } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "L'utilisateur " + id + " n'est pas trouvé");
          }
        return "user/add_edit";
    }*/
    
    @PostMapping( value = "/profil/{id}" )
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public String profilePost( HttpServletRequest request , @PathVariable int id , Model model ){
    	try {
            UserEntity u = ur.findById(id).orElse(null);
            u.setName(request.getParameter("nom"));
            u.setEmail(request.getParameter("email"));
            if (request.getParameter("password") != null && request.getParameter("password").length() > 2 ) {
            	System.out.println( "Changemet password" + request.getParameter("password") );	
            	u.setPassword(applicationConfig.passwordEncoder().encode(request.getParameter("password")));
            }
            
            u.setPhotouser(request.getParameter("photouser"));
            //u.setRoles(request.getParameter("roles")); // La gestion de profil ne donne pas la possibilité de changer de type d'utilisateur 
            u.setUsername(request.getParameter("username"));
            ur.save(u);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur dans l'édition du patient " + id);
        }
    	return "redirect:/patient";
    }
}
