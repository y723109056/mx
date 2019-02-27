package com.mx.xml;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.mx.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class XmlDocument {
	private Document doc;
	private XmlNodeList childNodes;
	private XmlNodeType type;
	private static DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
	private static XPathFactory pathFactory = XPathFactory.newInstance();  
	
	protected XmlDocument() {
	}

	public XmlDocument(String content){
		this.doc=loadXmlString(content);
		this.type = XmlNodeType.parser(this.doc.getNodeType());
	}
	
	public XmlDocument(File xmlFile)
	{
		this.doc=loadFile(xmlFile);
		this.type = XmlNodeType.parser(this.doc.getNodeType());
	}
	
	public XmlDocument(InputStream stream)
	{
		this.doc=loadStream(stream);
		this.type = XmlNodeType.parser(this.doc.getNodeType());
	}
	
	public void buildNodeIndex(){
		this.setNodeIndexPath("0",this.doc);
	}
	
	public XmlNode findFirstNodeByPath(String xpath)
	{
		try {
			XPath xp = pathFactory.newXPath();
			XPathExpression expression;
			expression = xp.compile(xpath);
			Object obj = expression.evaluate(this.doc, XPathConstants.NODE);
			if(obj!=null)
			{
				Node n = (Node)obj;
				return new XmlNode(n);
			}
			else return null;
		} catch (XPathExpressionException e) {
			throw new RuntimeException(StringUtil.format("XPath {0} 查询节点出错", xpath),e);
		}
	}
	
	public XmlNodeList findNodesByPath(String xpath)
	{
		try {
			XPath xp = pathFactory.newXPath();
			XPathExpression expression;
			expression = xp.compile(xpath);
			Object obj = expression.evaluate(this.doc, XPathConstants.NODESET);
			if(obj!=null)
			{
				NodeList list = (NodeList)obj;
				return new XmlNodeList(list);
			}
			else return null;
		} catch (XPathExpressionException e) {
			throw new RuntimeException(StringUtil.format("XPath {0} 查询节点出错",xpath),e);
		}
	}
	
	private void setNodeIndexPath(String indexPath,Node node)
	{
		node.setUserData("IndexPath", indexPath, null);
		for(int i=0;i<node.getChildNodes().getLength();i++)
		{
			Node n = node.getChildNodes().item(i);
			String path = indexPath+"/"+i;
			if(n.getChildNodes().getLength()>0)
				this.setNodeIndexPath(path, n);
		}
	}
	
	public String getName()
	{
		return doc.getNodeName();
	}
	
	public String getValue()
	{
		return doc.getNodeValue();
	}
	
	public XmlNodeType getNodeType()
	{
		return this.type;
	}
	
	public XmlNodeList getChildNodes()
	{
		if(childNodes==null)childNodes=new XmlNodeList(this.doc.getChildNodes());
		return childNodes;
	}
	
	private Document loadXmlString(String content) 
	{
		try {
			DocumentBuilder builder=factory.newDocumentBuilder();
			return builder.parse(new InputSource(new StringReader(content.trim())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(StringUtil.format("Xml解析失败  [{0}]",content));
		}
	}
	
	private Document loadFile(File xmlFile) 
	{
		try {
			DocumentBuilder builder=factory.newDocumentBuilder();
			return builder.parse(xmlFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(StringUtil.format("Xml文件读取失败,文件格式是否为utf-8 文件名 [{0}]",xmlFile));
		}
	}
	
	private Document loadStream(InputStream stream) 
	{
		try {
			DocumentBuilder builder=factory.newDocumentBuilder();
			return builder.parse(stream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(StringUtil.format("Xml文件读取失败"));
		}
	}
}
