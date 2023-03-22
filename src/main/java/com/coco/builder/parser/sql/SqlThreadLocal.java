package com.coco.builder.parser.sql;

/**
 * SqlThreadLocal
 *
 * @author lp
 * @version 1.0
 * @description SqlThreadLocal
 * @date 2023/3/19 9:34
 */
public class SqlThreadLocal {

    private static ThreadLocal<SqlData> SQLDATA_LOCAL = new ThreadLocal<>();

    public static SqlData get(){
        return SQLDATA_LOCAL.get();
    }

    public static void set(SqlData sqlData){
        SQLDATA_LOCAL.set(sqlData);
    }

    public static void remove(){
        SQLDATA_LOCAL.remove();
    }




}
