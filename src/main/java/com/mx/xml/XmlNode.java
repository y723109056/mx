package com.mx.xml;

import org.w3c.dom.Node;

public class XmlNode {
	private Node node;
	private XmlNodeList childNodes;
	private XmlNodeType type;
	private XmlAttributeCollection attrs;
	
	protected XmlNode() {
	}

	public String getName()
	{
		return node.getNodeName();
	}
	
	public String getValue()
	{
		return node.getNodeValue();
	}
	
	public Object getKey()
	{
		return node.getUserData("IndexPath");
	}
	
	public XmlAttributeCollection getAttributes()
	{
		if(attrs==null)attrs=new XmlAttributeCollection(this.node.getAttributes());
		return attrs;
	}
	
	public XmlNodeType getNodeType()
	{
		return this.type;
	}
	
	public XmlNodeList getChildNodes()
	{
		if(childNodes==null)childNodes=new XmlNodeList(node.getChildNodes());
		return childNodes;
	}
	
	public XmlNode(Node node)
	{
		this.node=node;
		this.type=XmlNodeType.parser(node.getNodeType());
	}
}
