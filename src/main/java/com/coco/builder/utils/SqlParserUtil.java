package com.coco.builder.utils;

import com.coco.builder.parser.sql.SqlCol;
import com.coco.builder.parser.sql.SqlData;
import com.coco.builder.parser.sql.SqlTab;
import com.coco.builder.parser.sql.SqlThreadLocal;
import com.coco.builder.parser.visitor.CustSelectVisitor;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * SqlParserUtil
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/3/19 14:31
 */
public class SqlParserUtil {

    public static SqlData parseSql(String sql) throws JSQLParserException {
        SqlData sqlData = new SqlData();
        SqlThreadLocal.set(sqlData);
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        SelectBody selectBody = select.getSelectBody();
        selectBody.accept(new CustSelectVisitor());
        SqlThreadLocal.remove();
        Set<SqlTab> tabSet = sqlData.getTabSet();
        List<SqlCol> colList = sqlData.getColList();
        for (SqlCol sqlCol : colList) {
            if(!StringUtils.isEmpty(sqlCol.getTableName())){
                String tableName = getTableName(sqlCol,tabSet);
                sqlCol.setTableName(tableName);
            }
        }
        return sqlData;
    }

    private static String getTableName(SqlCol next, Set<SqlTab> tabSet) {
        for (SqlTab sqlTab : tabSet) {
            if(next.getTableName().equals(sqlTab.getAlias())){
                return sqlTab.getName();
            }
        }
        return next.getTableName();
    }
}
