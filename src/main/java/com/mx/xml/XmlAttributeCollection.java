package com.mx.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;

public class XmlAttributeCollection implements Iterable<XmlAttribute> {
	private Map<String,XmlAttribute> attrs;
	
	protected XmlAttributeCollection() {
	}

	public int size()
	{
		return this.attrs.size();
	}
	
	public XmlAttribute get(String name)
	{
		return this.attrs.get(name);
	}
	
	public boolean containsKey(String name)
	{
		return this.attrs.containsKey(name);
	}
	
	public XmlAttributeCollection(NamedNodeMap map)
	{
		this.attrs=new HashMap<String,XmlAttribute>();
		for(int i=0;i<map.getLength();i++)
		{
			XmlAttribute attr=new XmlAttribute(map.item(i).getNodeName(),map.item(i).getNodeValue());
			attrs.put(attr.getName(),attr);
		}
	}
	
	@Override
	public Iterator<XmlAttribute> iterator() {
		return attrs.values().iterator();
	}

}
