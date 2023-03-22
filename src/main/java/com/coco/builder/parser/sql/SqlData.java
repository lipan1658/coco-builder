package com.coco.builder.parser.sql;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SqlData
 *
 * @author lp
 * @version 1.0
 * @description sql中数据信息
 * @date 2023/3/19 9:29
 */
@Data
public class SqlData {

    private Set<SqlTab> tabSet;

    private SqlCol currCol;

    private List<SqlCol> colList = new ArrayList<>();

    public void addCol(SqlCol col){
        colList.add(col);
    }
}
