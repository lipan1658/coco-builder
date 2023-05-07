package com.coco.builder.utils;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Dom4jUtil
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/5/7 15:21
 */
public class Dom4jUtil {

    /**
     * 替换元素
     * @param target 目标
     * @param source 源
     * @param elementIdList 元素节点id
     */
    public static void replaceElements(Document target, Document source, List<String> elementIdList){
        Element sourceRootElement = source.getRootElement();
        List<Element> elements = sourceRootElement.elements();
        Map<String, Element> collect = elements.stream().filter(e -> elementIdList.contains(e.attributeValue("id"))).collect(Collectors.toMap(k -> k.attributeValue("id"), v -> v));
        Element targetRootElement = target.getRootElement();
        String id;
        List<Element> targetElements = targetRootElement.elements();
        for (Element targetElement : targetElements) {
            id = targetElement.attributeValue("id");
            if(elementIdList.contains(id)){
                targetElement.setContent(collect.get(id).content());
            }
        }
    }

    public static void replaceElement(Document target, Element element, String elementId){
        Element targetRootElement = target.getRootElement();
        String id;
        List<Element> targetElements = targetRootElement.elements();
        for (Element targetElement : targetElements) {
            id = targetElement.attributeValue("id");
            if(elementId.equals((id))){
                targetElement.setContent(element.content());
            }
        }
    }

    public static void addElement(Document target, Element element){
        Element targetRootElement = target.getRootElement();
//        element.setDocument(target);
//        element.setParent(targetRootElement);
        targetRootElement.add(element);
    }

    public static boolean containElement(Document document, String elementId){
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            if(elementId.equals(element.attributeValue("id"))){
                return true;
            }
        }
        return false;
    }
}
