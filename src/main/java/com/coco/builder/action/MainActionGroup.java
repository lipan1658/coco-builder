package com.coco.builder.action;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * MainActionGroup
 *
 * @author lp
 * @version 1.0
 * @description MainActionGroup
 * @date 2023/3/21 7:14
 */
public class MainActionGroup extends ActionGroup {
    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {

        String tableActionId = "com.coco.builder.action.TableAction";
        String sqlActionId = "com.coco.builder.action.SqlAction";
        ActionManager actionManager = ActionManager.getInstance();
        // 代码生成菜单
//        AnAction sqlAction = new SqlAction("sql generate");

        // 代码生成菜单
        AnAction tableAction = actionManager.getAction(tableActionId);
        if (tableAction == null) {
            tableAction = new TableAction("table generate");
            actionManager.registerAction(tableActionId, tableAction);
        }
        AnAction sqlAction = actionManager.getAction(sqlActionId);
        if (sqlAction == null) {
            sqlAction = new SqlAction("sql generate");
            actionManager.registerAction(sqlActionId, sqlAction);
        }
        return new AnAction[]{tableAction,sqlAction};
    }
}
