package com.example.quizapp.controller;

import com.example.quizapp.dao.ModuleDAO;
import com.example.quizapp.model.Module;

import java.util.List;

public class ModuleController {
    private ModuleDAO moduleDAO;

    public ModuleController(ModuleDAO moduleDAO) {
        this.moduleDAO = moduleDAO;
    }

    public Module getModuleById(int moduleId) {
        return moduleDAO.getModuleById(moduleId);
    }

    public void createModule(Module module) {
        moduleDAO.createModule(module);
    }

    public void updateModule(Module updatedModule) {
        moduleDAO.updateModule(updatedModule);
    }

    public void deleteModule(int moduleId) {
        moduleDAO.deleteModule(moduleId);
    }

    public List<Module> getAllModules() {
        return moduleDAO.getAllModules();
    }
}
