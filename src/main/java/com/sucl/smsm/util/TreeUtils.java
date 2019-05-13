package com.sucl.smsm.util;

import com.sucl.smsm.core.ui.TreeNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sucl
 * @date 2019/5/10
 */
public class TreeUtils {

    public static List<TreeNode> render(List<TreeNode> treeNodes){
        if(treeNodes!=null && treeNodes.size()>0){
            return treeNodes.stream().filter(node->{
                return node.getPid()==null || "".equals(node.getPid());
            }).map(pnode->{
                buildChildren(pnode,treeNodes);
                return pnode;
            }).collect(Collectors.toList());
        }
        return null;
    }

    private static void buildChildren(TreeNode pnode, List<TreeNode> treeNodes) {
        treeNodes.stream().forEach(node->{
            if(pnode.getId().equals(node.getPid())){
                buildChildren(node,treeNodes);
                pnode.add(node);
            }
        });
    }

}
