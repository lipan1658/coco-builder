package com.coco.builder.model;

import lombok.Data;

/**
 * ServiceModel
 *
 * @author lp
 * @version 1.0
 * @description service模型
 * @date 2023/3/7 20:59
 */
@Data
public class ServiceModel {

    /**
     * service名
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
     * 接口名
     */
    private String interfaceName;

    /**
     * 接口包名
     */
    private String interfacePackageName;

    /**
     * 接口全限定名
     */
    private String interfaceFullName;
}
