package com.mx.xml;

public enum XmlNodeType {
	None(0),
    Element(1),
    Attribute(2),
    Text(3),
    CDATA(4),
    EntityReference(5),
    Entity(6),
    ProcessingInstruction(7),
    Comment(8),
    Document(9),
    DocumentType(10),
    DocumentFragment(11),
    Notation(12),
    Whitespace(13),
    SignificantWhitespace(14),
    EndElement(15),
    EndEntity(16),
    XmlDeclaration(17);
	
	public int Value;
	
	XmlNodeType(int value)
	{
		this.Value=value;
	}
	
	public static XmlNodeType parser(int value)
	{
		switch(value)
		{
			case 0:
				return None;
			case 1:
				return Element;
			case 2:
				return Attribute;
			case 3:
				return Text;
			case 4:
				return CDATA;
			case 5:
				return EntityReference;
			case 6:
				return Entity;
			case 7:
				return ProcessingInstruction;
			case 8:
				return Comment;
			case 9:
				return Document;
			case 10:
				return DocumentType;
			case 11:
				return DocumentFragment;
			case 12:
				return Notation;
			case 13:
				return Whitespace;
			case 14:
				return SignificantWhitespace;
			case 15:
				return EndElement;
			case 16:
				return EndEntity;
			case 17:
				return XmlDeclaration;
			default:
				return None;
		}
	}
}
