package com.sucl.smsm.core.dto;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 */
public class TreeUtils {

    public static <T> Map<Node,List<Node>> buildRoot(List<T> TreeBeans) throws IllegalAccessException {
        Map<Node,List<Node>> root = new HashMap<Node,List<Node>>();
        if(TreeBeans !=null){
            for(T t : TreeBeans){
                Node node = new Node();
                Class<?> clazz = t.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for(Field field :fields){

                }
            }
        }
        return null;
    }

    /**
     *
     * @param nodes
     * @return
     */
    public static String buildMenuHtml(List<Node> nodes){
        StringBuilder nodeHtml = new StringBuilder();
        if(nodes!=null){
            for(Node node : nodes){
                nodeHtml.append(buildMenuHtml(node));
            }
        }
        return nodeHtml.toString();
    }

    public static String buildMenuHtml(List<Node> nodes,String expand){
        for(Node node : nodes){
            if(StringUtils.isNotEmpty(expand) && expand.equals(node.getLink())){
                node.setExpanded(true);
            }else{
                List<Node> children = node.getChildren();
                if(children!=null){
                    for(Node cnode : children){
                        if(StringUtils.isNotEmpty(expand) && expand.equals(cnode.getLink())){
                            cnode.setExpanded(true);
                            node.setExpanded(true);
                        }
                    }
                }
            }
        }
        return  buildMenuHtml(nodes);
    }

    public static String buildMenuHtml(Node node){
        StringBuilder menuHtml = new StringBuilder();
        boolean parent = node.getChildren()!=null && node.getChildren().size()>0;
        menuHtml.append("<li "+(parent?"class=\"treeview "+(node.isExpanded()?"is-expanded":"")+"\"":"")+">" )
            .append( "<a class=\"app-menu__item page-nav "+(node.isExpanded()&&!parent?"active":"")+"\"  "+(parent?"data-toggle=\"treeview\"":pageLink(node.getLink()))+">")
            .append("<i class=\"app-menu__icon fa "+node.getStyle()+"\"></i>")
            .append("<span  class=\"app-menu__label\">"+node.getText()+"</span>");
        if(parent){
            menuHtml.append("<i class=\"treeview-indicator fa fa-angle-right\"></i>");
        }
        menuHtml.append("</a>");
        if(parent){
            menuHtml.append("<ul class=\"treeview-menu\">");
            for(Node child : node.getChildren()){
                menuHtml.append(buildMenuHtml(child));
            }
            menuHtml.append("</ul>");
        }
        menuHtml.append("</li>");
        return menuHtml.toString();
    }

    private static String pageLink(String link){
        if(StringUtils.isEmpty(link)){
            return "";
        }
        return "href='"+pageEncode(link)+"'";
    }

    private static String pageEncode(String page){
        return "p-"+page;
    }

    public static String pageDecode(String page){
        return page.substring(2);
    }

    @Deprecated
    public static String buildNodeHtml(Node node){
        StringBuilder nodeHtml = new StringBuilder();
        boolean parent = node.getChildren()!=null && node.getChildren().size()>0;
        boolean expanded = node.isExpanded();
        String liClass = parent? " class=\"nav-parent "+(expanded?"nav-expanded":"")+" \" ":"";
        nodeHtml.append("<li "+liClass+" >");
        if(parent)
            nodeHtml.append("<a>")
                    .append("<i class=\"fa "+(node.getStyle()==null?"":node.getStyle())+"\"></i>")
                    .append("<span>").append(node.getText()).append("</span>")
                    .append("</a>");
        else
            nodeHtml.append("<a href=\""+pageLink(node)+"\" class=\"page-link\">").append(node.getText()).append("</a>");
        if(parent){
            List<Node> nodeChildren = node.getChildren();
            nodeHtml.append("<ul class=\"nav nav-children\">");
            for(Node child :nodeChildren){
                nodeHtml.append(buildNodeHtml(child));
            }
            nodeHtml.append("</ul>");
        }
        return nodeHtml.toString();
    }

    private static String pageLink(Node node) {
        return node.getLink()+".html";
    }
}
