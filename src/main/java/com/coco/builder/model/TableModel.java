package com.coco.builder.model;

import lombok.Data;

/**
 * TableModel
 *
 * @author lp
 * @version 1.0
 * @description 表模型
 * @date 2023/3/7 20:58
 */
@Data
public class TableModel {

    /**
     * 表明
     */
    private String name;

    /**
     * 模式名
     */
    private String scheme;

    /**
     * 表说明
     */
    private String comment;
}
