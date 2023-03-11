package com.coco.builder.model;

import lombok.Data;

/**
 * DAOModel
 *
 * @author lp
 * @version 1.0
 * @description dao模型
 * @date 2023/3/7 20:59
 */
@Data
public class DAOModel {

    /**
     * 名称
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
     * 指定名,DAO\Mapper\Repository
     */
    private String typeName;

}
