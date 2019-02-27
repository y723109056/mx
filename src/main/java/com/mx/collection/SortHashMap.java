package com.mx.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SortHashMap<K,V> extends HashMap<K,V> {
	private static final long serialVersionUID = 1L;
	List<V> list;
	
	public SortHashMap()
	{
		this.list = new ArrayList<V>();
	}
	
	@Override
	public V put(K key,V value)
	{
		if(!this.containsKey(key))
		{
			list.add(value);
			return super.put(key, value);
		}
		else return value;
	}
	
	@Override
	public V remove(Object key)
    {
        if (this.containsKey(key))
        {
            V obj = this.get(key);
            list.remove(obj);
            return super.remove(key);
        }
        else return null;
    }
	
	@Override
	public Collection<V> values()
	{
		return list;
	}
}
