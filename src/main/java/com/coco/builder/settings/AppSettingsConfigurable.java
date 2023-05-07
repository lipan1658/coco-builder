package com.coco.builder.settings;

import com.coco.builder.utils.FreeMarkerUtil;
import com.coco.builder.utils.TemplateEnum;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

/**
 * AppSettingsConfigurable
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/5/6 6:29
 */
public class AppSettingsConfigurable implements Configurable {

    private final AppSettingsComponent appSettingsComponent = new AppSettingsComponent();;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "coco-builder";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return appSettingsComponent.getMyMainPanel();
    }

    @Override
    public boolean isModified() {
        AppSettingsState instance = AppSettingsState.getInstance();
        Map<String, String> map = instance.getMap();
        TemplatePanel templatePanel = appSettingsComponent.getTemplatePanel();
        Editor editor = templatePanel.getEditor();
        JBList<String> jbList = templatePanel.getJbList();
        Document document = editor.getDocument();
        return map.get(jbList.getSelectedValue())== null || !map.get(jbList.getSelectedValue()).equals(document.getText());
    }

    @Override
    public void apply() throws ConfigurationException {
        AppSettingsState instance = AppSettingsState.getInstance();
        Map<String, String> map = instance.getMap();
        TemplatePanel templatePanel = appSettingsComponent.getTemplatePanel();
        Editor editor = templatePanel.getEditor();
        Document document = editor.getDocument();
        String selectedItem = templatePanel.getSelectedItem();
        map.put(selectedItem,document.getText());
    }

    @Override
    public void reset() {
        AppSettingsState instance = AppSettingsState.getInstance();
        Map<String, String> map = instance.getMap();
        TemplatePanel templatePanel = appSettingsComponent.getTemplatePanel();
        String selectedItem = templatePanel.getSelectedItem();
        TemplateEnum templateEnum = TemplateEnum.getByName(selectedItem);
        assert templateEnum != null;
        map.put(selectedItem,FreeMarkerUtil.getText(templateEnum));
        templatePanel.addTemplateContent(templateEnum);
    }
}
