package com.sucl.smsm.core.dto;

import lombok.Data;

import java.util.List;

/**
 * 菜单树节点
 *
 */
@Data
public class Node {

    private String id;
    private String pid;
    private String text;
    private String style;
    private String link;
    private String cls;
    private String order;
    private List<Node> children;
    private boolean expanded;
}
