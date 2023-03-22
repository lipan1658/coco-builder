package com.coco.builder.parser.sql;

import lombok.Data;

/**
 * DBCol
 *
 * @author lp
 * @version 1.0
 * @description sql中字段信息
 * @date 2023/3/19 9:25
 */
@Data
public class SqlCol {

    private String alias;

    private String name;

    private String tableName;

    private String jdbcType;

    private Class<?> javaType;
}
