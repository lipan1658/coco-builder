package com.coco.builder.utils;

import com.coco.builder.model.EntityModel;
import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.intellij.openapi.util.text.StringUtilRt;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * FreeMarkerUtil
 *
 * @author lp
 * @version 1.0
 * @description FreeMarker工具类
 * @date 2023/3/10 21:56
 */
public class FreeMarkerUtil {

    private static final List<String> elementIdList = Arrays.asList("BaseResultMap","queryById","queryAllByLimit","queryAll","count","insert",
            "insertBatch","insertOrUpdateBatch","update","deleteById");

    public static void createFile(TemplateEnum templateEnum, String filePath, String fileName, Map<String, Object> dataMap) throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        URL resource = FreeMarkerUtil.class.getClassLoader().getResource(templateEnum.getName());
        assert resource != null;
        Template template = new Template(templateEnum.getName(), new StringReader(UrlUtil.loadText(resource)), configuration);
        createFile(template, filePath, fileName, dataMap);
    }

    private static void createFile(Template template, String filePath, String fileName, Map<String, Object> dataMap) {
        File floder = new File(filePath);
        if (!floder.exists()) {
            floder.mkdirs();
        }
        File file = new File(filePath+"\\"+fileName);
        if(file.exists() && "xml.ftl".equals(template.getName())){
            createNewXml(template, dataMap, file);
        }else{
            try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
                template.process(dataMap, out);
            } catch (TemplateException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 生成xml时不覆盖自定义内容
     */
    private static void createNewXml(Template template, Map<String, Object> dataMap, File file) {
        XMLWriter xmlWriter = null;
        try {
            StringWriter stringWriter = new StringWriter();
            template.process(dataMap, stringWriter);
            SAXReader saxReader = new SAXReader(false);
            Document targetDocument = saxReader.read(file);
            SAXReader saxReaderNew = new SAXReader(false);
            Document sourceDocument = saxReaderNew.read(new StringReader(stringWriter.toString()));
            Dom4jUtil.replaceElements(targetDocument, sourceDocument, elementIdList);
            xmlWriter = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            xmlWriter.write(targetDocument);
        } catch (TemplateException | IOException | DocumentException e) {
            throw new RuntimeException("生成xml文件失败",e);
        } finally {
            if(xmlWriter != null){
                try {
                    xmlWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String parse(TemplateEnum templateEnum, Map<String, Object> dataMap) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        URL resource = FreeMarkerUtil.class.getClassLoader().getResource(templateEnum.getName());
        assert resource != null;
        Template template = new Template(templateEnum.getName(), new StringReader(UrlUtil.loadText(resource)), configuration);
        StringWriter stringWriter = new StringWriter();
        template.process(dataMap, stringWriter);
        return stringWriter.toString();
    }

    public static void appendToXml(TemplateEnum templateEnum, Map<String, Object> dataMap , String file) {
        String resultMapId = ((EntityModel)dataMap.get("entity")).getName().concat("Map");
        XMLWriter xmlWriter = null;
        RandomAccessFile randomAccessFile = null;
        try {
            String str = parse(templateEnum, dataMap);
            SAXReader saxReader = new SAXReader(false);
            Document targetDocument = saxReader.read(new File(file));
            SAXReader saxReaderNew = new SAXReader(false);
            Document sourceDocument = saxReaderNew.read(new StringReader(str));
            Element sourceRootElement = sourceDocument.getRootElement();
            if(Dom4jUtil.containElement(targetDocument, resultMapId)){
                Dom4jUtil.replaceElement(targetDocument,sourceRootElement,resultMapId);
                xmlWriter = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                xmlWriter.write(targetDocument);
            }else{
                randomAccessFile = new RandomAccessFile(file,"rw");
                randomAccessFile.seek((randomAccessFile.length()-10));
                String resultMapStr = FreeMarkerUtil.parse(TemplateEnum.RESULTMAP, dataMap);
                randomAccessFile.writeBytes(resultMapStr+"\r\n\r\n</mapper>");
            }
        } catch (DocumentException | IOException | TemplateException e) {
            throw new RuntimeException(e);
        } finally {
            if(xmlWriter != null){
                try {
                    xmlWriter.close();
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

    public static String getText(TemplateEnum templateEnum){
        URL resource = FreeMarkerUtil.class.getClassLoader().getResource(templateEnum.getName());
        try {
            assert resource != null;
            return StringUtilRt.convertLineSeparators(UrlUtil.loadText(resource));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
