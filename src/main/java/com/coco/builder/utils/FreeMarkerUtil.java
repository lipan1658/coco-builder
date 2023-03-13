package com.coco.builder.utils;

import com.intellij.ide.fileTemplates.impl.UrlUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * FreeMarkerUtil
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/3/10 21:56
 */
public class FreeMarkerUtil {

    public static void createFile(String templateName, TemplateEnum templateEnum, String filePath, String fileName, Map<String, Object> dataMap) throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        URL resource = FreeMarkerUtil.class.getClassLoader().getResource(templateEnum.getName());
        assert resource != null;
        Template template = new Template(templateName, new StringReader(UrlUtil.loadText(resource)), configuration);
        createFile(template, filePath, fileName, dataMap);
    }

    private static void createFile(Template template, String filePath, String fileName, Map<String, Object> dataMap) throws IOException {
        File floder = new File(filePath);
        if (!floder.exists()) {
            floder.mkdirs();
        }
        File file = new File(filePath+"\\"+fileName);
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            template.process(dataMap, out);
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }

    }
}
