package com.mx.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlNodeList implements Iterable<XmlNode> {

	private List<XmlNode> nodes;
	
	protected XmlNodeList() {
	}

	public XmlNode get(int index)
	{
		return this.nodes.get(index);
	}
	
	public XmlNodeList(NodeList nodeList)
	{
		this.nodes=new ArrayList<XmlNode>(); 
		if(nodeList!=null)
		{
			for(int i=0;i<nodeList.getLength();i++)
			{
				Node n=nodeList.item(i);
				XmlNode cn=new XmlNode(n);
				nodes.add(cn);					
			}
		}
	}

	@Override
	public Iterator<XmlNode> iterator() {
		return nodes.iterator();
	}
}
