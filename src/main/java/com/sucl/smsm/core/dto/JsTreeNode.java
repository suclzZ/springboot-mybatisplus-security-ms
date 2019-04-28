package com.sucl.smsm.core.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 页面树元素（jstree控件）
 */
@Data
public class JsTreeNode {

	private String id;
	private String text;
	@JsonIgnore
	private String icon;
	private String data;
	private Map<String,Boolean> state;
	private String type;
	private Map li_attr;
	private Map a_attr;
	private List<JsTreeNode> children;
	
	public JsTreeNode() {}

	public JsTreeNode(String id, String text) {
		this.id = id;
		this.text = text;
	}
	
}
