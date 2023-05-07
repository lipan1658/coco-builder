package com.coco.builder.settings;

import javax.swing.*;
import java.awt.*;

/**
 * AppSettingsComponent
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/5/6 6:35
 */
public class AppSettingsComponent {

    private final JPanel myMainPanel;

    private final TemplatePanel templatePanel;

    public AppSettingsComponent() {
        this.myMainPanel = new JPanel(new BorderLayout());
        templatePanel = new TemplatePanel();
        myMainPanel.add(templatePanel,BorderLayout.CENTER);
    }

    public JPanel getMyMainPanel() {
        return myMainPanel;
    }

    public TemplatePanel getTemplatePanel() {
        return templatePanel;
    }
}
