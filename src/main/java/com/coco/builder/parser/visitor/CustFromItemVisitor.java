package com.coco.builder.parser.visitor;

import com.coco.builder.parser.sql.SqlData;
import com.coco.builder.parser.sql.SqlTab;
import com.coco.builder.parser.sql.SqlThreadLocal;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitorAdapter;

import java.util.HashSet;
import java.util.Set;

/**
 * CustFromItemVisitor
 *
 * @author lp
 * @description CustFromItemVisitor
 * @date 2023/2/27 16:57
 */
public class CustFromItemVisitor extends FromItemVisitorAdapter {

    public CustFromItemVisitor(){

    }

    @Override
    public void visit(Table table) {
        SqlData sqlData = SqlThreadLocal.get();
        SqlTab tab = new SqlTab();
        tab.setAlias(table.getAlias()==null?table.getName(): table.getAlias().getName());
        tab.setName(table.getName());
        Set<SqlTab> tabSet;
        if(sqlData.getTabSet() == null){
            tabSet = new HashSet<>();
            sqlData.setTabSet(tabSet);
        }else{
            tabSet = sqlData.getTabSet();
        }
        tabSet.add(tab);
    }
}
