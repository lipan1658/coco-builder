package com.coco.builder.parser.visitor;

import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * CustExpressionVisitorAdapter
 *
 * @author lp
 * @description TODO
 * @date 2023/2/27 16:04
 */
public class CustExpressionVisitor extends ExpressionVisitorAdapter {


    public CustExpressionVisitor(){

    }

    @Override
    public void visit(SubSelect subSelect) {
        SelectBody selectBody = subSelect.getSelectBody();
        selectBody.accept(new CustSelectVisitor());
    }



}
