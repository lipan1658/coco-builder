package com.coco.builder.settings;

import com.coco.builder.utils.FreeMarkerUtil;
import com.coco.builder.utils.TemplateEnum;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * AppSettingsState
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/5/6 6:25
 */
@State(name = "com.coco.builder.settings.AppSettingsState", storages = @Storage("CocoBuilderSettingsPlugin.xml"))
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    private  Map<String,String> map;

    @Override
    public @Nullable AppSettingsState getState() {
        return this;
    }

    public static AppSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AppSettingsState.class);
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public AppSettingsState() {
        init();
    }

    public void init(){
        map = new HashMap<>();
        TemplateEnum[] values = TemplateEnum.values();
        for (TemplateEnum value : values) {
            map.put(value.getName(), FreeMarkerUtil.getText(value));
        }
    }

    public Map<String, String> getMap() {
        return map;
    }
}
