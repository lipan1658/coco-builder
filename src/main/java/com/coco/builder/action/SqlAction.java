package com.coco.builder.action;

import com.coco.builder.ui.SqlDialog;
import com.intellij.openapi.actionSystem.*;
import net.sf.jsqlparser.JSQLParserException;
import org.jetbrains.annotations.NotNull;

/**
 * TableAction
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/3/18 16:52
 */
public class SqlAction extends AnAction {

    public SqlAction() {
    }

    public SqlAction(String text){
        super(text);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
//        PsiElement[] psiElements = e.getData(LangDataKeys.PSI_ELEMENT_ARRAY);
//        Object delegate = ((DbTableImpl) psiElements[0]).getDasParent().getDelegate();
//        DbNamespace dbNamespace = (DbNamespace) ((DbTableImpl) psiElements[0]).getDasParent();
//        JBIterable<? extends DbElement> dasChildren = ((DbTableImpl) psiElements[0]).getDasParent().getDasChildren(ObjectKind.TABLE);
//        PsiElement[] children = dbNamespace.getChildren();
        try {
            new SqlDialog(e);
        } catch (JSQLParserException ex) {
            throw new RuntimeException(ex);
        }
    }
}
