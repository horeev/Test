package com.github.horeev.modulmanager.controller;

import com.github.horeev.modulmanager.bean.ModuleDto;
import com.github.horeev.modulmanager.bean.ModuleEntity;
import com.github.horeev.modulmanager.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ModuleController {

    @Autowired
    private ModuleRepository repository;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home (Model model){
        model.addAttribute("module", new ModuleDto());
        return "home";
    }


    @RequestMapping(value = "/module/insert", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String formProcessing (@ModelAttribute ModuleDto module, Model input){
        ModuleEntity entity = new ModuleEntity();
        entity.setName(module.getName());
        if(module.getParentId() == null) {
            entity.setModuleLevel(0);
        }
        repository.save(entity);

        System.out.println(repository.findAll());

        input.addAttribute("module", new ModuleDto());
        return "home";
    }
}
