package com.mx.collection;

import java.util.Iterator;

public class GeneticIterator<T> implements Iterator<T> {
	private T[] items;
	private int index;
	private int size;
	
	protected GeneticIterator() {
	}

	public GeneticIterator(T[] items)
	{
		this.items=items;
		this.index=0;
		this.size=items.length;
	}
	
	public GeneticIterator(T[] items,int size)
	{
		this.items=items;
		this.index=0;
		this.size=size;
	}

	@Override
	public boolean hasNext() {
		if(this.index<this.size)
			return true;
		else return false;
	}

	@Override
	public T next() {
		if(this.index<this.size)
		{
			T item=items[index];
			this.index++;
			return item;
		}
		else return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void remove() {
		T[] newItems=(T[])new Object[this.size-1];
		for(int i=0;i<this.size-1;i++)
		{
			newItems[i]=this.items[i];
		}
		this.items=newItems;
	}
}
