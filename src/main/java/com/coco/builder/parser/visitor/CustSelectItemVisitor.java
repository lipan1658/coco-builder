package com.coco.builder.parser.visitor;

import com.coco.builder.parser.sql.SqlCol;
import com.coco.builder.parser.sql.SqlData;
import com.coco.builder.parser.sql.SqlThreadLocal;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;
import net.sf.jsqlparser.statement.select.SubSelect;

import java.math.BigDecimal;
import java.util.List;

/**
 * CustSelectItemVisitorAdapter
 *
 * @author lp
 * @description TODO
 * @date 2023/2/27 16:01
 */
public class CustSelectItemVisitor extends SelectItemVisitorAdapter {

    public CustSelectItemVisitor() {

    }

    @Override
    public void visit(SelectExpressionItem item) {
        SqlData sqlData = SqlThreadLocal.get();
        Alias alias = item.getAlias();
        String aliasName;
        String columnName;
        Expression expression = item.getExpression();
        if (expression instanceof Column) {
            SqlCol col = new SqlCol();
            columnName = ((Column) expression).getColumnName();
            Table table = ((Column) expression).getTable();
            if(table != null){
                col.setTableName(table.getName());
            }
            if(sqlData.getCurrCol() !=null){
                aliasName = sqlData.getCurrCol().getAlias();
                col.setAlias(aliasName);
                col.setName(columnName);
                sqlData.addCol(col);
                sqlData.setCurrCol(null);
            }else{
                if (alias != null) {
                    aliasName = alias.getName();
                } else {
                    aliasName = columnName;
                }
                col.setAlias(aliasName);
                col.setName(columnName);
                sqlData.addCol(col);
            }
        }else if(expression instanceof SubSelect){
            SelectBody selectBody = ((SubSelect) expression).getSelectBody();
            SqlCol col = new SqlCol();
            col.setAlias(alias.getName().replace("AS ",""));
            sqlData.setCurrCol(col);
            selectBody.accept(new CustSelectVisitor());
        } else if(expression instanceof CaseExpression caseExpression){
            SqlCol col = new SqlCol();
            aliasName = alias.getName().replace("AS ","");
            col.setAlias(aliasName);
            col.setName(aliasName);
            Expression elseExpression = caseExpression.getElseExpression();
            if(elseExpression == null){
                List<WhenClause> whenClauses = caseExpression.getWhenClauses();
                Expression thenExpression = whenClauses.get(0).getThenExpression();
                buildType(col, thenExpression);
            }else{
                buildType(col, elseExpression);
            }
            sqlData.addCol(col);
        } else if(expression instanceof Function function){
            String name = function.getName();
            SqlCol col = new SqlCol();
            aliasName = alias.getName().replace("AS ","");
            col.setAlias(aliasName);
            col.setName(aliasName);
            buildType(name, col, function);
            //暂不处理其他
            sqlData.addCol(col);
        }
    }

    private static void buildType(String name, SqlCol col, Function function) {
        if("count".equals(name)){
            col.setJavaType(Integer.class);
            col.setJdbcType("INT");
        }else if("sum".equals(name)){
            col.setJavaType(BigDecimal.class);
            col.setJdbcType("DECIMAL");
        }else if("ifnull".equals(name)){
            ExpressionList parameters = function.getParameters();
            List<Expression> expressions = parameters.getExpressions();
            Expression expression = expressions.get(0);
            Column column = (Column) expression;
            col.setTableName(column.getTable().getName());
            col.setName(column.getColumnName());
        }
    }

    private static void buildType(SqlCol col, Expression elseExpression) {
        if(elseExpression instanceof LongValue){
            col.setJavaType(Integer.class);
            col.setJdbcType("INT");
        }else if(elseExpression instanceof StringValue){
            col.setJavaType(String.class);
            col.setJdbcType("VARCHAR");
        }
    }
}
