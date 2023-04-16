package com.coco.builder.utils;

public enum TemplateEnum {
    ENTITY("entity","entity.ftl"),
    DAO("dao","dao.ftl"),
    SERVICE("service","service.ftl"),
    SERVICEIMPL("serviceImpl","serviceImpl.ftl"),
    CONTROLLER("controller","controller.ftl"),
    XML("xml","xml.ftl"),
    DTO("dto","dto.ftl"),
    RESULTMAP("resultMap","resultMap.ftl")
    ;

    private String code;

    private String name;


    TemplateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
