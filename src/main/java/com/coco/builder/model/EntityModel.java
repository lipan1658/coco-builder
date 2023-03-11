package com.coco.builder.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * EntityModel
 *
 * @author lp
 * @version 1.0
 * @description 实体模型
 * @date 2023/3/7 20:58
 */
@Data
public class EntityModel {

    /**
     * 实体类名
     */
    private String name;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 全限定名
     */
    private String fullName;

    /**
     * 属性
     */
    private List<EntityFieldModel> fields;

    /**
     * java类型集合（全限定名）
     */
    private Set<String> javaTypeSet;

    /**
     * 主键属性
     */
    private EntityFieldModel primary;


}
