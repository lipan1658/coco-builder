package com.coco.builder.settings;

import com.coco.builder.utils.FreeMarkerUtil;
import com.coco.builder.utils.TemplateEnum;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.ex.DocumentEx;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.impl.DocumentImpl;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.fileTypes.UnknownFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.intellij.openapi.vfs.impl.local.LocalFileSystemBase;
import com.intellij.openapi.vfs.impl.local.LocalFileSystemImpl;
import com.intellij.testFramework.LightVirtualFile;
import com.intellij.ui.border.CustomLineBorder;
import com.intellij.ui.components.JBList;
import com.intellij.util.LocalTimeCounter;
import com.vladsch.flexmark.util.misc.TemplateUtil;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
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
        leftPanel = new JPanel(new BorderLayout());
        TemplateEnum[] values = TemplateEnum.values();
        List<String> itemList = new ArrayList<>();
        for (TemplateEnum value : values) {
            itemList.add(value.getName());
        }
        jbList = new JBList<>(itemList);
        leftPanel.setPreferredSize(new Dimension(150,550));
        jbList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jbList.setPreferredSize(new Dimension(-1,-1));
        jbList.addListSelectionListener( listener -> {
            String selectedValue = jbList.getSelectedValue();
            TemplateEnum templateEnum = TemplateEnum.getByName(selectedValue);
            this.addTemplateContent(templateEnum);
        });
        leftPanel.add(jbList, BorderLayout.CENTER);
        leftPanel.setBorder(new CustomLineBorder(1, 1, 1, 1));

        // 右边面板
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(350,550));
//        rightPanel.setBorder(new LineBorder(Color.lightGray,1));
        this.setLayout(new BorderLayout(5,5));
        this.add(leftPanel,BorderLayout.WEST);
        this.add(rightPanel,BorderLayout.CENTER);
        if(itemList.size()>0){
            jbList.setSelectedIndex(0);
            String selectedValue = jbList.getSelectedValue();
            TemplateEnum templateEnum = TemplateEnum.getByName(selectedValue);
            this.addTemplateContent(templateEnum);
        }
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
