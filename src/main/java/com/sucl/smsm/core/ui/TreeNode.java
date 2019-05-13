package com.sucl.smsm.core.ui;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sucl
 * @date 2019/5/10
 */
@Data
public class TreeNode {
    private String id;
    private String name;
    private String pid;//构建关系时建立
    private String style;
    private String path;
    private Object data;
    private List<TreeNode> children;// 返回时构建

    public void add(TreeNode node) {
        if(children == null){
            children = new ArrayList<>();
        }
        children.add(node);
    }
}
