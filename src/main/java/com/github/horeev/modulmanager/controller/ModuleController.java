package com.github.horeev.modulmanager.controller;

import com.github.horeev.modulmanager.bean.ModuleDto;
import com.github.horeev.modulmanager.bean.ModuleEntity;
import com.github.horeev.modulmanager.bean.ModuleViewDto;
import com.github.horeev.modulmanager.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ModuleController {

    @Autowired
    private ModuleRepository repository;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home (Model model){
        return assembleViewData(model);
    }


    @RequestMapping(value = "/module/insert", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String formProcessing (@ModelAttribute ModuleDto module, Model input){
        ModuleEntity entity = new ModuleEntity();
        entity.setName(module.getName());
        if(module.getParentId() == null) {
            entity.setModuleLevel(0);
        } else {
            ModuleEntity parent = repository.findById(Long.valueOf (module.getParentId())).get();
            int parentLevel = parent.getModuleLevel();
            if (parentLevel >= 3) {
                throw new RuntimeException();
            }
            entity.setParentId(module.getParentId());
            entity.setModuleLevel(parentLevel + 1);
        }

        repository.save(entity);

//        System.out.println(repository.findAll());


        return assembleViewData(input);
    }

    private String assembleViewData (Model input){
        List<ModuleEntity> modules = repository.findAll();
        List<ModuleViewDto> result = new ArrayList<>();

        Map<Long, ModuleViewDto> map=new HashMap<>();

        for (ModuleEntity entity : modules        ){
            ModuleViewDto dto = new ModuleViewDto ();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setChildren(new ArrayList<>());
            if (entity.getParentId() == null){
                result.add(dto);
            }

            map.put (entity.getId(), dto);
        }
        for (ModuleEntity entity : modules        ){
            if (entity.getParentId() != null){
                map.get(Long.valueOf( entity.getParentId())).getChildren().add(map.get(entity.getId()));
            }
        }
        input.addAttribute("modules", result );
        input.addAttribute("module", new ModuleDto());
        return "home";
    }
}
