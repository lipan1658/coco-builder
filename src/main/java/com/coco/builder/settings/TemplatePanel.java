package com.coco.builder.settings;

import com.coco.builder.utils.TemplateEnum;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.testFramework.LightVirtualFile;
import com.intellij.ui.border.CustomLineBorder;
import com.intellij.ui.components.JBList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TemplatePanel
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/5/6 6:40
 */
public class TemplatePanel extends JPanel{

    private JPanel leftPanel;

    private JPanel rightPanel;

    private JBList<String> jbList;

    private Editor editor;

    public TemplatePanel() {
        // 右边面板
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(350,550));
        this.setLayout(new BorderLayout(5,5));

        this.add(rightPanel,BorderLayout.CENTER);
        createEditor();
        rightPanel.add(editor.getComponent());

        leftPanel = new JPanel(new BorderLayout());
        this.add(leftPanel,BorderLayout.WEST);
        TemplateEnum[] values = TemplateEnum.values();
        List<String> itemList = new ArrayList<>();
        for (TemplateEnum value : values) {
            itemList.add(value.getName());
        }
        jbList = new JBList<>(itemList);
        leftPanel.setPreferredSize(new Dimension(150,550));
        jbList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jbList.setPreferredSize(new Dimension(-1,-1));
        jbList.addListSelectionListener(listener -> {
            String selectedValue = jbList.getSelectedValue();
            TemplateEnum templateEnum = TemplateEnum.getByName(selectedValue);
            this.setContent(templateEnum);
        });
        leftPanel.add(jbList, BorderLayout.CENTER);
        leftPanel.setBorder(new CustomLineBorder(1, 1, 1, 1));
        ApplicationManager.getApplication().invokeLater(()->{
            jbList.setSelectedIndex(0);
        });

    }

    private void createEditor(){
        EditorFactory editorFactory = EditorFactory.getInstance();
        Document document = editorFactory.createDocument("");
        this.editor = editorFactory.createEditor(document);

        initEditorSettings();
    }

    private void initEditorSettings(){
        EditorSettings editorSettings = editor.getSettings();
        editorSettings.setCaretRowShown(true);
        // 关闭虚拟空间
        editorSettings.setVirtualSpace(false);
        // 关闭标记位置（断点位置）
        editorSettings.setLineMarkerAreaShown(false);
        // 关闭缩减指南
        editorSettings.setIndentGuidesShown(false);
        // 显示行号
        editorSettings.setLineNumbersShown(true);
        // 支持代码折叠
        editorSettings.setFoldingOutlineShown(true);
        // 附加行，附加列（提高视野）
        editorSettings.setAdditionalColumnsCount(3);
        editorSettings.setAdditionalLinesCount(3);
        // 不显示换行符号
        editorSettings.setCaretRowShown(false);
    }

    public void setContent(TemplateEnum templateEnum){
        Project project = ProjectManager.getInstance().getDefaultProject();
        AppSettingsState instance = AppSettingsState.getInstance();
        Map<String, String> map = instance.getMap();
        String contentText = map.get(templateEnum.getName());
        ((EditorImpl)this.editor).setViewer(false);
        // 重置文本内容
        WriteCommandAction.runWriteCommandAction(project, () -> this.editor.getDocument().setText(contentText));
        ((EditorEx)editor).setHighlighter(EditorHighlighterFactory.getInstance().createEditorHighlighter(project, templateEnum.getName()));
    }


    public void addTemplateContent(TemplateEnum templateEnum) {

        AppSettingsState instance = AppSettingsState.getInstance();
        Map<String, String> map = instance.getMap();
        Project project = ProjectManager.getInstance().getDefaultProject();
        EditorFactory editorFactory = EditorFactory.getInstance();
        if(editor != null){
            editorFactory.releaseEditor(editor);
        }
        String contentText = map.get(templateEnum.getName());
        LightVirtualFile virtualFile = new LightVirtualFile(templateEnum.getName(), contentText);
        virtualFile.putUserData(FileTemplateManager.DEFAULT_TEMPLATE_PROPERTIES, FileTemplateManager.getInstance(project).getDefaultProperties());
        Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
        editor = editorFactory.createEditor(document);
        EditorSettings editorSettings = editor.getSettings();
        editorSettings.setCaretRowShown(true);
        // 关闭虚拟空间
        editorSettings.setVirtualSpace(false);
        // 关闭标记位置（断点位置）
        editorSettings.setLineMarkerAreaShown(false);
        // 关闭缩减指南
        editorSettings.setIndentGuidesShown(false);
        // 显示行号
        editorSettings.setLineNumbersShown(true);
        // 支持代码折叠
        editorSettings.setFoldingOutlineShown(true);
        // 附加行，附加列（提高视野）
        editorSettings.setAdditionalColumnsCount(3);
        editorSettings.setAdditionalLinesCount(3);
        // 不显示换行符号
        editorSettings.setCaretRowShown(false);
        rightPanel.removeAll();
        this.rightPanel.add(editor.getComponent());

    }


    public String getSelectedItem(){
        return jbList.getSelectedValue();
    }

    public JBList<String> getJbList() {
        return jbList;
    }

    public Editor getEditor() {
        return editor;
    }
}
