package com.coco.builder.action;

import com.coco.builder.ui.MainUI;
import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class MainAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiElement[] psiElements = e.getData(LangDataKeys.PSI_ELEMENT_ARRAY);
        if (psiElements == null || psiElements.length != 1) {
            Messages.showMessageDialog("Please select only one or more tables", "Notice", Messages.getInformationIcon());
            return;
        }
        if(psiElements[0] instanceof DbTable){
            try {
                new MainUI(e);
            } catch (SQLException | RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
