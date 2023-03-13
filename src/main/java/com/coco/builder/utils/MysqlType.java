package com.coco.builder.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

/**
 * MysqlType
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/3/9 20:31
 */
public enum MysqlType {

    DECIMAL("DECIMAL",Types.DECIMAL, BigDecimal.class),
    DECIMAL_UNSIGNED("DECIMAL UNSIGNED",Types.DECIMAL, BigDecimal.class),
    TINYINT("TINYINT",Types.TINYINT, Integer.class),
    TINYINT_UNSIGNED("TINYINT UNSIGNED",Types.TINYINT, Integer.class),
    BOOLEAN("BOOLEAN",Types.BOOLEAN, Boolean.class),
    SMALLINT("SMALLINT",Types.SMALLINT, Integer.class),
    SMALLINT_UNSIGNED("SMALLINT UNSIGNED",Types.SMALLINT, Integer.class),
    INT("INT",Types.INTEGER, Integer.class),
    INT_UNSIGNED("INT UNSIGNED",Types.INTEGER, Long.class),
    FLOAT("FLOAT",Types.REAL, Float.class),
    FLOAT_UNSIGNED("FLOAT UNSIGNED",Types.REAL, Float.class),
    DOUBLE("DOUBLE",Types.DOUBLE, Double.class),
    DOUBLE_UNSIGNED("DOUBLE UNSIGNED",Types.DOUBLE, Double.class),
    NULL("NULL",Types.NULL, Object.class),
    TIMESTAMP("TIMESTAMP",Types.TIMESTAMP, Timestamp.class),
    BIGINT("BIGINT",Types.BIGINT, Long.class),
    BIGINT_UNSIGNED("BIGINT UNSIGNED",Types.BIGINT, BigInteger.class),
    MEDIUMINT("MEDIUMINT",Types.INTEGER, Integer.class),
    MEDIUMINT_UNSIGNED("MEDIUMINT UNSIGNED",Types.INTEGER, Integer.class),
    DATE("DATE",Types.DATE, Date.class),
    TIME("TIME",Types.TIME, Time.class),
//    DATETIME("DATETIME",Types.TIMESTAMP, LocalDateTime.class),
    YEAR("YEAR",Types.DATE, Date.class),
    VARCHAR("VARCHAR",Types.VARCHAR, String.class),
    VARBINARY("VARBINARY",Types.VARBINARY, null),
    BIT("BIT",Types.BIT, Boolean.class),
    JSON("JSON",Types.LONGVARCHAR, String.class),
    ENUM("ENUM",Types.CHAR, String.class),
    SET("SET",Types.CHAR, String.class),
    TINYBLOB("TINYBLOB",Types.VARBINARY, null),
    TINYTEXT("TINYTEXT",Types.VARCHAR, String.class),
    MEDIUMBLOB("MEDIUMBLOB",Types.LONGVARBINARY, null),
    MEDIUMTEXT("MEDIUMTEXT",Types.LONGVARCHAR, String.class),
    LONGBLOB("LONGBLOB",Types.LONGVARBINARY, null),
    LONGTEXT("LONGTEXT",Types.LONGVARCHAR, String.class),
    BLOB("BLOB",Types.LONGVARBINARY, null),
    TEXT("TEXT",Types.LONGVARCHAR, String.class),
    CHAR("CHAR",Types.CHAR, String.class),
    BINARY("BINARY",Types.BINARY, null),
    GEOMETRY("GEOMETRY",Types.BINARY, null),
    UNKNOWN("UNKNOWN",Types.OTHER, null),
    //以上取自mysql8 jar包，以下为新增
    DATETIME("DATETIME",Types.TIMESTAMP, Timestamp.class),
    NUMBERIC("NUMBERIC",Types.NUMERIC, BigDecimal.class);


    private final String name;
    private int jdbcType;
    private final Class<?> javaClass;

    MysqlType(String mysqlTypeName, int jdbcType, Class<?> javaClass) {
        this.name = mysqlTypeName;
        this.jdbcType = jdbcType;
        this.javaClass = javaClass;
    }

    public String getName() {
        return name;
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(int jdbcType) {
        this.jdbcType = jdbcType;
    }

    public Class<?> getJavaClass() {
        return javaClass;
    }
}
