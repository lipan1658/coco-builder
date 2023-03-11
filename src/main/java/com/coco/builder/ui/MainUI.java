package com.coco.builder.ui;

import com.coco.builder.model.*;
import com.coco.builder.utils.CommonUtil;
import com.coco.builder.utils.FreeMarkerUtil;
import com.coco.builder.utils.MysqlType;
import com.coco.builder.utils.TemplateEnum;
import com.intellij.database.model.DasColumn;
import com.intellij.database.model.DasNamespace;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.*;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
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
import com.intellij.psi.PsiElement;
import com.intellij.util.containers.JBIterable;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.java.JavaResourceRootType;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

/**
 * MianUI
 *
 * @author lp
 * @version 1.0
 * @description MainUI
 * @date 2023/2/26 21:30
 */
public class MainUI extends JDialog {

    private JLabel tableNameLabel = new JLabel("table");
    private JTextField tableNameText = new JTextField();
    private JLabel moduleNameLabel = new JLabel("module");
    private ComboBox<String> moduleComboBox = new ComboBox<>();

    private JLabel javaPathLabel = new JLabel("java path");

    private TextFieldWithBrowseButton javaPathText = new TextFieldWithBrowseButton();
    private JLabel xmlPathLabel = new JLabel("xml path");
    private TextFieldWithBrowseButton xmlPathText = new TextFieldWithBrowseButton();

    private JCheckBox entityCheckBox = new JCheckBox("entity");

    private JCheckBox daoCheckBox = new JCheckBox("dao");

    private JCheckBox serviceCheckBox = new JCheckBox("service");

    private JCheckBox controllerCheckBox = new JCheckBox("controller");

    private JCheckBox xmlCheckBox = new JCheckBox("xml");

    private JCheckBox swaggerCheckBox = new JCheckBox("swagger");

    private JLabel removePrefixLabel = new JLabel("remove prefix");

    private JTextField removePrefixText = new JTextField();

    private JLabel daoPackageLabel = new JLabel("dao package");

    private JTextField daoPackageText = new JTextField("dao");

    private JLabel daoClassLabel = new JLabel("dao suffix");

    private JTextField daoClassText = new JTextField("DAO");


    public JLabel getTableNameLabel() {
        return tableNameLabel;
    }

    public void setTableNameLabel(JLabel tableNameLabel) {
        this.tableNameLabel = tableNameLabel;
    }

    public JTextField getTableNameText() {
        return tableNameText;
    }

    public void setTableNameText(JTextField tableNameText) {
        this.tableNameText = tableNameText;
    }

    public JLabel getModuleNameLabel() {
        return moduleNameLabel;
    }

    public void setModuleNameLabel(JLabel moduleNameLabel) {
        this.moduleNameLabel = moduleNameLabel;
    }

    public ComboBox<String> getModuleComboBox() {
        return moduleComboBox;
    }

    public void setModuleComboBox(ComboBox<String> moduleComboBox) {
        this.moduleComboBox = moduleComboBox;
    }

    public JLabel getJavaPathLabel() {
        return javaPathLabel;
    }

    public void setJavaPathLabel(JLabel javaPathLabel) {
        this.javaPathLabel = javaPathLabel;
    }

    public TextFieldWithBrowseButton getJavaPathText() {
        return javaPathText;
    }

    public void setJavaPathText(TextFieldWithBrowseButton javaPathText) {
        this.javaPathText = javaPathText;
    }

    public JLabel getXmlPathLabel() {
        return xmlPathLabel;
    }

    public void setXmlPathLabel(JLabel xmlPathLabel) {
        this.xmlPathLabel = xmlPathLabel;
    }

    public TextFieldWithBrowseButton getXmlPathText() {
        return xmlPathText;
    }

    public void setXmlPathText(TextFieldWithBrowseButton xmlPathText) {
        this.xmlPathText = xmlPathText;
    }

    public JCheckBox getEntityCheckBox() {
        return entityCheckBox;
    }

    public void setEntityCheckBox(JCheckBox entityCheckBox) {
        this.entityCheckBox = entityCheckBox;
    }

    public JCheckBox getdaoCheckBox() {
        return daoCheckBox;
    }

    public void setdaoCheckBox(JCheckBox daoCheckBox) {
        this.daoCheckBox = daoCheckBox;
    }

    public JCheckBox getServiceCheckBox() {
        return serviceCheckBox;
    }

    public void setServiceCheckBox(JCheckBox serviceCheckBox) {
        this.serviceCheckBox = serviceCheckBox;
    }

    public JCheckBox getControllerCheckBox() {
        return controllerCheckBox;
    }

    public void setControllerCheckBox(JCheckBox controllerCheckBox) {
        this.controllerCheckBox = controllerCheckBox;
    }

    public JCheckBox getXmlCheckBox() {
        return xmlCheckBox;
    }

    public void setXmlCheckBox(JCheckBox xmlCheckBox) {
        this.xmlCheckBox = xmlCheckBox;
    }

    public JCheckBox getSwaggerCheckBox() {
        return swaggerCheckBox;
    }

    public void setSwaggerCheckBox(JCheckBox swaggerCheckBox) {
        this.swaggerCheckBox = swaggerCheckBox;
    }

    public JLabel getRemovePrefixLabel() {
        return removePrefixLabel;
    }

    public void setRemovePrefixLabel(JLabel removePrefixLabel) {
        this.removePrefixLabel = removePrefixLabel;
    }

    public JTextField getRemovePrefixText() {
        return removePrefixText;
    }

    public void setRemovePrefixText(JTextField removePrefixText) {
        this.removePrefixText = removePrefixText;
    }

    public JLabel getDaoPackageLabel() {
        return daoPackageLabel;
    }

    public void setDaoPackageLabel(JLabel daoPackageLabel) {
        this.daoPackageLabel = daoPackageLabel;
    }

    public JTextField getDaoPackageText() {
        return daoPackageText;
    }

    public void setDaoPackageText(JTextField daoPackageText) {
        this.daoPackageText = daoPackageText;
    }

    public JLabel getDaoClassLabel() {
        return daoClassLabel;
    }

    public void setDaoClassLabel(JLabel daoClassLabel) {
        this.daoClassLabel = daoClassLabel;
    }

    public JTextField getDaoClassText() {
        return daoClassText;
    }

    public void setDaoClassText(JTextField daoClassText) {
        this.daoClassText = daoClassText;
    }

    public MainUI(AnActionEvent anActionEvent) throws SQLException, RemoteException {
        setSize(450, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        Project project = anActionEvent.getProject();
        assert project != null;
        ModuleManager moduleManager = ModuleManager.getInstance(project);
        Module[] modules = moduleManager.getModules();

        JPanel tablePanel = new JPanel(new GridLayout(4, 2, 10, 5));
        tablePanel.setSize(new Dimension(300, 135));
        tablePanel.setLocation(10, 10);

        tableNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        tableNameText.setHorizontalAlignment(SwingConstants.LEFT);
        PsiElement[] psiElements = anActionEvent.getData(LangDataKeys.PSI_ELEMENT_ARRAY);
        assert psiElements != null;
        DbTable dbTable = (DbTable) psiElements[0];
        tableNameText.setText(dbTable.getName());
        moduleNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        ComboBox<String> moduleComboBox = new ComboBox<>();
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
                }
                List<VirtualFile> resourceRoots = instance.getSourceRoots(JavaResourceRootType.RESOURCE);
                if (resourceRoots.size() > 0) {
                    xmlPathText.setText(resourceRoots.get(0).getPath().replace("/", "\\"));
                }
            }
        });
        javaPathLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        ModuleRootManager instance = ModuleRootManager.getInstance(modules[0]);
        List<VirtualFile> sourceRoots = instance.getSourceRoots(JavaSourceRootType.SOURCE);
        if (sourceRoots.size() > 0) {
            javaPathText.setText(sourceRoots.get(0).getPath().replace("/", "\\"));
        }
        javaPathText.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor(), project) {
            @Override
            protected void onFileChosen(@NotNull VirtualFile chosenFile) {
                if (chosenFile.isDirectory()) {
                    super.onFileChosen(chosenFile);
                } else {
                    Messages.showMessageDialog("Please select a folder", "Notice", Messages.getInformationIcon());
                }
            }
        });
        xmlPathLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        List<VirtualFile> resourceRoots = instance.getSourceRoots(JavaResourceRootType.RESOURCE);
        if (resourceRoots.size() > 0) {
            xmlPathText.setText(resourceRoots.get(0).getPath().replace("/", "\\"));
        }
        xmlPathText.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor(), project) {
            @Override
            protected void onFileChosen(@NotNull VirtualFile chosenFile) {
                if (chosenFile.isDirectory()) {
                    super.onFileChosen(chosenFile);
                } else {
                    Messages.showMessageDialog("Please select a folder", "Notice", Messages.getInformationIcon());
                }
            }
        });

        tablePanel.add(tableNameLabel);
        tablePanel.add(tableNameText);
        tablePanel.add(moduleNameLabel);
        tablePanel.add(moduleComboBox);
        tablePanel.add(javaPathLabel);
        tablePanel.add(javaPathText);
        tablePanel.add(xmlPathLabel);
        tablePanel.add(xmlPathText);

        JPanel checkBoxPanel = new JPanel(new GridLayout(2, 3, 2, 5));
        checkBoxPanel.setSize(new Dimension(255, 60));
        checkBoxPanel.setLocation(100, 150);
        entityCheckBox = new JCheckBox("entity");
        daoCheckBox = new JCheckBox("dao");
        serviceCheckBox = new JCheckBox("service");
        controllerCheckBox = new JCheckBox("controller");
        xmlCheckBox = new JCheckBox("xml");
        swaggerCheckBox = new JCheckBox("swagger");

        checkBoxPanel.add(entityCheckBox);
        checkBoxPanel.add(daoCheckBox);
        checkBoxPanel.add(serviceCheckBox);
        checkBoxPanel.add(controllerCheckBox);
        checkBoxPanel.add(xmlCheckBox);
        checkBoxPanel.add(swaggerCheckBox);

        JPanel configPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        configPanel.setSize(new Dimension(300, 90));
        configPanel.setLocation(10, 215);

        removePrefixLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        removePrefixText.setHorizontalAlignment(SwingConstants.LEFT);

        daoPackageLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        daoPackageText.setHorizontalAlignment(SwingConstants.LEFT);
        daoClassLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        daoClassText.setHorizontalAlignment(SwingConstants.LEFT);

        configPanel.add(removePrefixLabel);
        configPanel.add(removePrefixText);
        configPanel.add(daoPackageLabel);
        configPanel.add(daoPackageText);
        configPanel.add(daoClassLabel);
        configPanel.add(daoClassText);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 5));
        buttonPanel.setSize(120, 30);
        buttonPanel.setLocation(250, 335);
        JButton cancelBtn = new JButton("cancel");
        cancelBtn.setMargin(JBUI.emptyInsets());
        cancelBtn.addActionListener(listener -> {
            dispose();
        });
        JButton okBtn = new JButton("ok");
        okBtn.addActionListener(listener -> {
            this.onOk(dbTable);
        });
        buttonPanel.add(okBtn);
        buttonPanel.add(okBtn);
        buttonPanel.add(cancelBtn);


        Container container = getContentPane();
        container.setLayout(null);
        container.add(tablePanel);
        container.add(checkBoxPanel);
        container.add(configPanel);
        container.add(buttonPanel);


        setModal(true);
        setTitle("coco-builder");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void onOk(DbTable dbTable) {
        JBIterable<? extends DasColumn> columns = DasUtil.getColumns(dbTable);
        EntityFieldModel entityFieldModel;
        List<EntityFieldModel> entityFieldModelList = new ArrayList<>();
        String javaPath = javaPathText.getText();
        String basePackage = javaPath.substring(javaPath.indexOf("java") + 5).replace("\\", ".");
        String xmlPath = xmlPathText.getText();
        String baseXmlPath = javaPath.substring(xmlPath.indexOf("resources") + 10);
        for (DasColumn column : columns) {
            entityFieldModel = new EntityFieldModel();
            entityFieldModel.setComment(column.getComment());
            entityFieldModel.setField(CommonUtil.lineToHump(column.getName()));
            entityFieldModel.setName(column.getName());
            Set<DasColumn.Attribute> columnAttrs = dbTable.getColumnAttrs(column);
            if(columnAttrs.contains(DasColumn.Attribute.PRIMARY_KEY)){
                entityFieldModel.setPrimary(true);
            }
            MysqlType mysqlType = CommonUtil.getMysqlType(column.getDataType().typeName);
            assert mysqlType != null;
            entityFieldModel.setJavaType(mysqlType.getJavaClass().getSimpleName());
            entityFieldModel.setFullJavaType(mysqlType.getJavaClass().getCanonicalName());
            entityFieldModelList.add(entityFieldModel);
        }
        String humpClassName = CommonUtil.lineToHump(tableNameText.getText().replace(removePrefixText.getText() == null ? "" : removePrefixText.getText(), ""));
        String baseClassName = humpClassName.substring(0, 1).toUpperCase() + humpClassName.substring(1);

        EntityModel entityModel = new EntityModel();
        entityModel.setName(baseClassName + "Entity");
        entityModel.setPackageName(basePackage + ".entity");
        entityModel.setFullName(entityModel.getPackageName() + "." + entityModel.getName());
        entityModel.setFields(entityFieldModelList);
        Set<String> jdbcTypeSet = new HashSet<>();
        entityFieldModelList.forEach(o->{
            jdbcTypeSet.add(o.getFullJavaType());
        });
        entityModel.setJavaTypeSet(jdbcTypeSet);
        for (EntityFieldModel fieldModel : entityFieldModelList) {
            if(fieldModel.isPrimary()){
                entityModel.setPrimary(fieldModel);
            }
        }

        System.out.println("entityModel=" + entityModel);

        DAOModel daoModel = new DAOModel();
        daoModel.setName(baseClassName + daoClassText.getText());
        daoModel.setPackageName(basePackage + "." + daoPackageText.getText());
        daoModel.setFullName(daoModel.getPackageName() + "." + daoModel.getName());
        daoModel.setTypeName(daoPackageText.getText());

        System.out.println("daoModel=" + daoModel);

        ServiceModel serviceModel = new ServiceModel();
        serviceModel.setName(baseClassName + "ServiceImpl");
        serviceModel.setInterfaceName(baseClassName + "Service");
        serviceModel.setPackageName(basePackage + ".service.impl");
        serviceModel.setInterfacePackageName(basePackage + ".service");
        serviceModel.setFullName(serviceModel.getPackageName() + "." + serviceModel.getName());
        serviceModel.setInterfaceFullName(serviceModel.getInterfacePackageName() + "." + serviceModel.getInterfaceName());

        System.out.println("serviceModel=" + serviceModel);

        ControllerModel controllerModel = new ControllerModel();
        controllerModel.setName(baseClassName + "Controller");
        controllerModel.setPackageName(basePackage + ".controller");
        controllerModel.setFullName(controllerModel.getPackageName() + "." + controllerModel.getName());

        System.out.println("controllerModel=" + controllerModel);

        TableModel tableModel = new TableModel();
        tableModel.setName(dbTable.getName());
        tableModel.setComment(dbTable.getComment());
        DasNamespace currentRootNamespace = dbTable.getDataSource().getModel().getCurrentRootNamespace();
        tableModel.setScheme(currentRootNamespace == null ? null : currentRootNamespace.getName());


        Map<String, Boolean> generateMap = new HashMap<>();
        generateMap.put("dao",daoCheckBox.isSelected());
        generateMap.put("service",serviceCheckBox.isSelected());
        generateMap.put("controller",controllerCheckBox.isSelected());
        generateMap.put("xml",xmlCheckBox.isSelected());
        generateMap.put("swagger",swaggerCheckBox.isSelected());
        generateMap.put("entity",entityCheckBox.isSelected());

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("entity",entityModel);
        dataMap.put("dao",daoModel);
        dataMap.put("table",tableModel);
        dataMap.put("controller",controllerModel);
        dataMap.put("service",serviceModel);
        String filePath;
        String fileName;
        if(entityCheckBox.isSelected()){
            filePath = javaPath+"\\"+"entity";
            fileName = entityModel.getName()+".java";
            try {
                FreeMarkerUtil.createFile("entity", TemplateEnum.ENTITY,filePath, fileName, dataMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(xmlCheckBox.isSelected()){
            filePath = xmlPath;
            fileName = humpClassName+"Mapper.xml";
            try {
                FreeMarkerUtil.createFile("xml", TemplateEnum.XML,filePath, fileName, dataMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(daoCheckBox.isSelected()){
            filePath = javaPath+"\\"+daoPackageText.getText();
            fileName = daoModel.getName()+".java";
            try {
                FreeMarkerUtil.createFile("dao", TemplateEnum.DAO,filePath, fileName, dataMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(serviceCheckBox.isSelected()){
            filePath = javaPath+"\\"+"service";
            fileName = baseClassName+"Service.java";
            try {
                FreeMarkerUtil.createFile("service", TemplateEnum.SERVICE,filePath, fileName, dataMap);
                filePath = javaPath+"\\"+"service\\impl";
                fileName = baseClassName+"ServiceImpl.java";
                FreeMarkerUtil.createFile("serviceimpl", TemplateEnum.SERVICEIMPL,filePath, fileName, dataMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(controllerCheckBox.isSelected()){
            filePath = javaPath+"\\"+"controller";
            fileName = baseClassName+"Controller.java";
            try {
                FreeMarkerUtil.createFile("controller", TemplateEnum.CONTROLLER,filePath, fileName, dataMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

}
