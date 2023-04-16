package com.coco.builder.ui;


import com.coco.builder.model.EntityFieldModel;
import com.coco.builder.model.EntityModel;
import com.coco.builder.parser.sql.SqlCol;
import com.coco.builder.parser.sql.SqlData;
import com.coco.builder.parser.sql.SqlTab;
import com.coco.builder.utils.*;
import com.intellij.database.model.ObjectKind;
import com.intellij.database.psi.DbColumn;
import com.intellij.database.psi.DbElement;
import com.intellij.database.psi.DbTable;
import com.intellij.database.psi.DbTableImpl;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiElement;
import com.intellij.sql.SqlFileType;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.intellij.util.containers.JBIterable;
import com.intellij.util.ui.JBUI;
import freemarker.template.TemplateException;
import net.sf.jsqlparser.JSQLParserException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.java.JavaResourceRootType;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * SqlDialog
 *
 * @author lp
 * @version 1.0
 * @description sql dialog
 * @date 2023/3/18 16:26
 */
public class SqlDialog extends JDialog{

    private Editor editor;

    private final JButton ok = new JButton("ok");
    private final JButton cancel = new JButton("cancel");
    private final JButton next = new JButton("next");
    private final JButton back = new JButton("back");

    private final JLabel moduleNameLabel = new JLabel("module");
    private final ComboBox<String> moduleComboBox = new ComboBox<>();
    private final JLabel javaPathLabel = new JLabel("java path");
    private final TextFieldWithBrowseButton javaPathText = new TextFieldWithBrowseButton();

    private final JLabel xmlNameLabel = new JLabel("xml file name");
    private final TextFieldWithBrowseButton xmlPathText = new TextFieldWithBrowseButton();
    private final JLabel entityNameLabel = new JLabel("entity name");
    private final JTextField entityNameText = new JTextField();


    private JPanel sqlPanel;
    private JPanel tablePanel;

    private JTable table;

    public SqlDialog(AnActionEvent event) throws JSQLParserException {
        Project project = event.getData(CommonDataKeys.PROJECT);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setSize(700, 500);

        createSqlPanel(project);
        mainPanel.add(sqlPanel, BorderLayout.CENTER);

        JPanel toolPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cancel.addActionListener(o->{
            dispose();
        });
        next.addActionListener(o->{
            try {
                createTablePanel(event,project);
            } catch (JSQLParserException ex) {
                throw new RuntimeException(ex);
            }
            mainPanel.remove(sqlPanel);
            mainPanel.add(tablePanel, BorderLayout.CENTER);

            toolPanel.remove(next);
            toolPanel.add(ok);
            toolPanel.add(back);

            revalidate();
            repaint();
        });
        back.addActionListener(o->{
            mainPanel.remove(tablePanel);
            mainPanel.add(sqlPanel, BorderLayout.CENTER);

            toolPanel.remove(back);
            toolPanel.remove(ok);
            toolPanel.add(next);

            revalidate();
            repaint();
        });
        ok.addActionListener(o->{
            onOk();
            dispose();
            VirtualFileManager.getInstance().refreshWithoutFileWatcher(true);

        });
        toolPanel.add(cancel);
        toolPanel.add(next);
        mainPanel.add(toolPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setModal(true);
        setTitle("sql generate");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void onOk() {
        if(StringUtils.isBlank(entityNameText.getText())){
            Messages.showMessageDialog("Please enter entity name", "Notice", Messages.getInformationIcon());
            return;
        }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        Vector<Vector> dataVector = model.getDataVector();
        List<EntityFieldModel> entityFieldModelList = new ArrayList<>();
        EntityFieldModel entityFieldModel;
        for(int i=0;i<rowCount;i++){
            entityFieldModel = new EntityFieldModel();
            Vector vector = dataVector.get(i);
            entityFieldModel.setField((String) vector.get(0));
            entityFieldModel.setName((String) vector.get(1));
            entityFieldModel.setJdbcType((String) vector.get(2));
            entityFieldModel.setFullJavaType((String) vector.get(3));
            entityFieldModel.setJavaType(entityFieldModel.getFullJavaType().substring(entityFieldModel.getFullJavaType().lastIndexOf(".")+1));
            entityFieldModel.setComment((String) vector.get(4));
            entityFieldModelList.add(entityFieldModel);
        }

        String javaPath = javaPathText.getText();
        String basePackage = javaPath.substring(javaPath.indexOf("java") + 5).replace("\\", ".");
        EntityModel entityModel = new EntityModel();
        entityModel.setName(entityNameText.getText());
        entityModel.setPackageName(basePackage);
        setFields(entityFieldModelList, entityModel);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("entity",entityModel);
        dataMap.put("swagger",true);
        dataMap.put("author",System.getenv("USERNAME"));
        String fileName = entityModel.getName()+".java";
        FileInputStream fileInputStream = null;
        RandomAccessFile randomAccessFile = null;
        try {
            FreeMarkerUtil.createFile(TemplateEnum.DTO, javaPath, fileName, dataMap);
            fileInputStream = new FileInputStream(xmlPathText.getText());
            int available = fileInputStream.available();
            randomAccessFile = new RandomAccessFile(xmlPathText.getText(),"rw");
            randomAccessFile.seek((available-10));
            String resultMapStr = FreeMarkerUtil.parse(TemplateEnum.RESULTMAP, dataMap);
            randomAccessFile.writeBytes(resultMapStr+"\r\n\r\n</mapper>");
        } catch (IOException | TemplateException e  ) {
            Messages.showErrorDialog(e.getLocalizedMessage(),"Error");
        } finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(randomAccessFile != null){
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private void setFields(List<EntityFieldModel> entityFieldModelList, EntityModel entityModel) {
        entityModel.setFullName(entityModel.getPackageName() + "." + entityModel.getName());
        entityModel.setFields(entityFieldModelList);
        Set<String> jdbcTypeSet = new HashSet<>();
        entityFieldModelList.forEach(obj->{
            if(!obj.getFullJavaType().contains("java.lang")){
                jdbcTypeSet.add(obj.getFullJavaType());
            }
        });
        entityModel.setJavaTypeSet(jdbcTypeSet);
    }

    private void createSqlPanel(Project project){
        sqlPanel = new JPanel();
        sqlPanel.setLayout(null);
        sqlPanel.setSize(700,450);
        EditorFactory editorFactory = EditorFactory.getInstance();
        Document document = editorFactory.createDocument("");
        editor = editorFactory.createEditor(document,project);
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
        ((EditorEx) editor).setHighlighter(EditorHighlighterFactory.getInstance().createEditorHighlighter(project, SqlFileType.INSTANCE));
        JPanel component = (JPanel) editor.getComponent();
        component.setSize(700,450);
        sqlPanel.add(component);
    }

    private void createTablePanel(AnActionEvent event,Project project) throws JSQLParserException {
        ModuleManager moduleManager = ModuleManager.getInstance(project);
        Module[] modules = moduleManager.getModules();
        moduleComboBox.removeAllItems();
        for (Module module : modules) {
            moduleComboBox.addItem(module.getName());
        }
        moduleComboBox.addItemListener(e -> {
            String item = (String) e.getItem();
            Module selectModule = null;
            for (Module module : modules) {
                if (item.equals(module.getName())) {
                    selectModule = module;
                    break;
                }
            }
            if (selectModule != null) {
                ModuleRootManager instance = ModuleRootManager.getInstance(selectModule);
                List<VirtualFile> sourceRoots = instance.getSourceRoots(JavaSourceRootType.SOURCE);
                if (sourceRoots.size() > 0) {
                    javaPathText.setText(sourceRoots.get(0).getPath().replace("/", "\\"));
                }else{
                    javaPathText.setText("");
                }
                List<VirtualFile> resourceRoots = instance.getSourceRoots(JavaResourceRootType.RESOURCE);
                if (resourceRoots.size() > 0) {
                    xmlPathText.setText(resourceRoots.get(0).getPath().replace("/", "\\"));
                }
            }
        });
        moduleComboBox.setSelectedIndex(0);

        tablePanel = new JPanel(new BorderLayout());
        moduleNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);


        ModuleRootManager instance = ModuleRootManager.getInstance(modules[0]);
        List<VirtualFile> sourceRoots = instance.getSourceRoots(JavaSourceRootType.SOURCE);
        if (sourceRoots.size() > 0) {
            javaPathText.setText(sourceRoots.get(0).getPath().replace("/", "\\"));
        }else{
            javaPathText.setText("");
        }
        javaPathText.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor(), project) {
            @Override
            protected void onFileChosen(@NotNull VirtualFile chosenFile) {
                if (chosenFile.isDirectory()) {
                    javaPathText.setText(chosenFile.getPath().replace("/", "\\"));
                } else {
                    Messages.showMessageDialog("Please select a folder", "Notice", Messages.getInformationIcon());
                }
            }
        });

        List<VirtualFile> resourceRoots = instance.getSourceRoots(JavaResourceRootType.RESOURCE);
        if (resourceRoots.size() > 0) {
            xmlPathText.setText(resourceRoots.get(0).getPath().replace("/", "\\"));
        }
        xmlPathText.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor(), project) {
            @Override
            protected void onFileChosen(@NotNull VirtualFile chosenFile) {
                if (!chosenFile.isDirectory()) {
                    xmlPathText.setText(chosenFile.getPath().replace("/", "\\"));
                } else {
                    Messages.showMessageDialog("Please select a file", "Notice", Messages.getInformationIcon());
                }
            }
        });

        JPanel entityParent = new JPanel();
        JPanel entityPanel = new JPanel(new GridLayout(2,4));
        entityPanel.setBorder(JBUI.Borders.empty(10));
        entityPanel.setPreferredSize(new Dimension(600,80));
        //module
        entityPanel.add(moduleNameLabel);
        entityPanel.add(moduleComboBox);
        //entity
        entityNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        entityPanel.add(entityNameLabel);
        entityNameText.setHorizontalAlignment(SwingConstants.LEFT);
        entityPanel.add(entityNameText);

        //java path
        javaPathLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        entityPanel.add(javaPathLabel);
        entityPanel.add(javaPathText);
        //xml path
        xmlNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        entityPanel.add(xmlNameLabel);
        entityPanel.add(xmlPathText);


        entityParent.add(entityPanel);
        tablePanel.add(entityParent, BorderLayout.NORTH);


        PsiElement[] psiElements = event.getData(LangDataKeys.PSI_ELEMENT_ARRAY);
        assert psiElements != null;
        JBIterable<? extends DbElement> tableChildren = Objects.requireNonNull(((DbTableImpl) psiElements[0]).getDasParent()).getDasChildren(ObjectKind.TABLE);

        JScrollPane jScrollPane = new JBScrollPane();

        String sql = this.editor.getDocument().getText();
        SqlData sqlData = SqlParserUtil.parseSql(sql);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("filed name");
        tableModel.addColumn("column name");
        tableModel.addColumn("jdbc type");
        tableModel.addColumn("java type");
        tableModel.addColumn("comment");
        List<SqlCol> colList = sqlData.getColList();
        for (SqlCol sqlCol : colList) {
            String[] columnRow = buildColumnRow(sqlCol, tableChildren);
            tableModel.addRow(columnRow);
        }
        table = new JBTable(tableModel);
        //不能直接add
        jScrollPane.setViewportView(table);
        tablePanel.add(jScrollPane, BorderLayout.CENTER);
    }

    private DbTable getDbTable(JBIterable<? extends DbElement> dasChildren, String tableName){
        for (DbElement dasChild : dasChildren) {
            DbTable dbTable = (DbTable) dasChild;
            if(tableName.equals(dbTable.getName())){
                return dbTable;
            }
        }
        return null;
    }

    private DbColumn getDbColumn(JBIterable<? extends DbElement> dasChildren, String columnName){
        for (DbElement dasChild : dasChildren) {
            DbColumn dbColumn = (DbColumn) dasChild;
            if(columnName.equals(dbColumn.getName())){
                return dbColumn;
            }
        }
        return null;
    }

    private String[] buildColumnRow(SqlCol sqlCol, JBIterable<? extends DbElement> tableChildren){
        String columnName = sqlCol.getName();
        String filedName = CommonUtil.lineToHump(columnName);
        String jdbcTypeName;
        String javaTypeName;
        String comment;
        if(!StringUtils.isEmpty(sqlCol.getTableName())){
            DbTable dbTable = getDbTable(tableChildren, sqlCol.getTableName());
            JBIterable<? extends DbElement> columnChildren = dbTable.getDasChildren(ObjectKind.COLUMN);
            columnName = sqlCol.getName().replace("`","");
            DbColumn dbColumn = getDbColumn(columnChildren, columnName);
            filedName = CommonUtil.lineToHump(sqlCol.getAlias());
            if(sqlCol.getJdbcType() != null){
                javaTypeName = sqlCol.getJavaType().getCanonicalName();
                jdbcTypeName = sqlCol.getJdbcType();
            }else{
                MysqlType mysqlType = CommonUtil.getMysqlType(dbColumn.getDataType().typeName);
                JdbcType jdbcType = JdbcType.forCode(mysqlType.getJdbcType());
                jdbcTypeName = jdbcType.name();
                javaTypeName = mysqlType.getJavaClass().getCanonicalName();
            }
            comment = dbColumn.getComment();
        }else{
            jdbcTypeName = sqlCol.getJdbcType();
            javaTypeName = sqlCol.getJavaType().getCanonicalName();
            comment = "";
        }

        return new String[]{filedName, columnName, jdbcTypeName,javaTypeName, comment};
    }

    public static void main(String[] args) {
    }

}
