package com.mx.sql.mapping;

import java.util.ArrayList;
import java.util.List;

public class SqlItemInclude extends SqlItemContent implements ISqlItemClone<SqlItemInclude> {
	private String refId;
	private List<SqlLink> links;

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public List<SqlLink> getLinks() {
		return links;
	}

	public void setLinks(List<SqlLink> links) {
		this.links = links;
	}

	public SqlItemInclude(){
		this.refId = "";
		this.links = new ArrayList<SqlLink>();
	}

	public SqlItemInclude clone(Object args) {
		SqlItemInclude include = new SqlItemInclude();
		include.parts=this.cloneParts(args);
		return include;
	}

}
