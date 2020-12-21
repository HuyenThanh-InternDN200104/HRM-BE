package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.Skill;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.SkillEntity;
import com.example.HRM.BE.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    SkillService skillService;

    @Autowired
    private Converter<SkillEntity, Skill> skillEntityToSkill;

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    @GetMapping("/{id}")
    public Skill getSkillFollowID(@PathVariable("id") int id) {
        return skillService.getSkillFollowID(id);
    }

    @GetMapping("/user")
    public List<Skill> getSkillsFollowUser(@RequestParam int id) {
        return skillService.getSkillsFollowUser(id);
    }

    @GetMapping("/category")
    public List<Skill> getSkillsFollowCategory(@RequestParam int id) {
        return skillService.getSkillsFollowCategory(id);
    }

    @PostMapping
    public void addNewSkill(@RequestBody @Validated Skill skill) {
        skillService.addNewSkill(skill);
    }

    @PostMapping("/list")
    public List<Skill> addNewSkills(@RequestBody @Validated List<Skill> skills) {
        List<Skill> skillListResult = new ArrayList<>();
        for(Skill skill : skills){
            skillListResult.add(
                    skillEntityToSkill.convert(skillService.addNewSkill(skill))
            );
        }
        return skillListResult;
    }

    @PutMapping
    public void editSkill(@RequestBody @Validated Skill skill,
                          @PathParam("id") int id) {
        skill.setId(id);
        skillService.editSkill(id, skill);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable int id) {
        skillService.deleteSkill(id);
    }
}
