package com.coco.builder.parser.visitor;

import com.coco.builder.parser.sql.SqlData;
import com.coco.builder.parser.sql.SqlThreadLocal;
import net.sf.jsqlparser.statement.select.*;

import java.util.List;

/**
 * CustSelectVisitorAdapter
 *
 * @author lp
 * @description TODO
 * @date 2023/2/27 16:13
 */
public class CustSelectVisitor extends SelectVisitorAdapter {

    public CustSelectVisitor(){
    }

    @Override
    public void visit(PlainSelect plainSelect) {
        FromItem fromItem = plainSelect.getFromItem();
        fromItem.accept(new CustFromItemVisitor());
        List<Join> joins = plainSelect.getJoins();
        if(joins != null){
            joins.forEach(o->{
                FromItem rightItem = o.getRightItem();
                rightItem.accept(new CustFromItemVisitor());
            });
        }
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        selectItems.forEach(o-> o.accept(new CustSelectItemVisitor()));
    }
}
