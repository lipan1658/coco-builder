package com.coco.builder.model;

import lombok.Data;

/**
 * EntityFieldModel
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/3/7 21:03
 */
@Data
public class EntityFieldModel {
    /**
     * 实体属性
     */
    private String field;
    /**
     * 对应表字段
     */
    private String name;

    /**
     * java类型
     */
    private String javaType;
    /**
     * java类型全
     * 限定名
     */
    private String fullJavaType;
    /**
     * 注释
     */
    private String comment;

    /**
     * 是否主键
     */
    private boolean primary = false;
}
