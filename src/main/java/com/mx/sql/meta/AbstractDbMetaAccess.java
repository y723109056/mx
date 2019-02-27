package com.mx.sql.meta;

import com.mx.sql.command.IDataReader;
import com.mx.sql.dialect.IMetaSql;
import com.mx.util.TypeUtil;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractDbMetaAccess implements IDbMetaAccess {
	private IMetaSql metaSql;
	
	public AbstractDbMetaAccess(IMetaSql metaSql)
	{
		this.metaSql = metaSql;
	}
	
	protected abstract void execute(String[] sqlStrings);
	
	protected abstract IDataReader getResult(String sqlString);
	
	protected abstract Object getValue(String sqlString);
	
	protected abstract List<Object> getValues(String sqlString);

	@Override
	public List<Table> getTables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table getTable(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createTable(Table tableName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropTable(String tableName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Sequence> getSequences() {
		String sqlString = this.metaSql.getSequencesSql();
		IDataReader reader = this.getResult(sqlString);
		List<Sequence> sequences = new ArrayList<Sequence>();
		while(reader.read())
		{
			String name = (String) TypeUtil.changeType(reader.get("sname"), String.class);
			Long value = (Long)TypeUtil.changeType(reader.get("svalue"),Long.class);
			sequences.add(new Sequence(name,value));
		}
		reader.close();
		return sequences;
	}

	@Override
	public Sequence getSequence(String sequenceName) {
		String sqlString = this.metaSql.getSequenceSql(sequenceName);
		IDataReader reader = this.getResult(sqlString);
		while(reader.read())
		{
			String name = (String)TypeUtil.changeType(reader.get("SNAME"),String.class);
			Long value = (Long)TypeUtil.changeType(reader.get("SVALUE"),Long.class);
			return new Sequence(name,value);
		}
		reader.close();
		return null;
	}

	@Override
	public Long getSequenceNextValue(String sequenceName) {
		String sqlString = this.metaSql.getSequenceNextSql(sequenceName);
		Object value = this.getValue(sqlString);
		return (Long)TypeUtil.changeType(value,Long.class);
	}
	
	@Override
	public Long[] getSequenceNextValue(String sequenceName,int num) {
		String sqlString = this.metaSql.getSequenceNextSql(sequenceName,num);
		List<Object> values = this.getValues(sqlString);
		Long[] ids = new Long[num];
		for(int i=0;i<values.size();i++){
			ids[i] = ((Long)TypeUtil.changeType(values.get(i),Long.class));
		}
		return ids;
	}

	@Override
	public void createSequence(Sequence sequence) {
		this.createSequence(sequence.getName(), sequence.getValue());
	}

	@Override
	public void createSequence(String sequenceName, Long sequeneValue) {
		String[] sqlStrings = this.metaSql.getCreateSequenceSql(sequenceName, sequeneValue);
		this.execute(sqlStrings);
	}

	@Override
	public void dropSequence(String sequenceName) {
		String sqlString = this.metaSql.getDropSequenceSql(sequenceName);
		this.execute(new String[]{sqlString});
	}
}
