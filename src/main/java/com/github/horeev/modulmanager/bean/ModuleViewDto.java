package com.github.horeev.modulmanager.bean;

import java.util.List;

public class ModuleViewDto {
    private String name;
    private long id;
    private List<ModuleViewDto> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ModuleViewDto> getChildren() {
        return children;
    }

    public void setChildren(List<ModuleViewDto> children) {
        this.children = children;
    }
}
