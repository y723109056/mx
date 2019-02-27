package com.mx.sql.cache;

import java.util.ArrayList;
import java.util.List;

public class CacheKey {
    private int multiplier;
    private int hashcode;
    private long checksum;
    private int count;
    private List<Object> paramList;
    
	public CacheKey(){
        paramList = new ArrayList<Object>();
        hashcode = 17;
        multiplier = 37;
        count = 0;
    }

    public CacheKey(int initialNonZeroOddNumber){
        paramList = new ArrayList<Object>();
        hashcode = initialNonZeroOddNumber;
        multiplier = 37;
        count = 0;
    }

    public CacheKey(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber){
        paramList = new ArrayList<Object>();
        hashcode = initialNonZeroOddNumber;
        multiplier = multiplierNonZeroOddNumber;
        count = 0;
    }

    public CacheKey update(int x){
        update(new Integer(x));
        return this;
    }

    public CacheKey update(Object object){
        int baseHashCode = object.hashCode();
        count++;
        checksum += baseHashCode;
        baseHashCode *= count;
        hashcode = multiplier * hashcode + baseHashCode;
        paramList.add(object);
        return this;
    }

    @Override
    public boolean equals(Object object){
        if(this == object)
            return true;
        if(!(object instanceof CacheKey))
            return false;
        CacheKey cacheKey = (CacheKey)object;
        if(hashcode != cacheKey.hashcode)
            return false;
        if(checksum != cacheKey.checksum)
            return false;
        if(count != cacheKey.count)
            return false;
        for(int i = 0; i < paramList.size(); i++)
        {
            Object thisParam = paramList.get(i);
            Object thatParam = cacheKey.paramList.get(i);
            if(thisParam == null)
            {
                if(thatParam != null)
                    return false;
                continue;
            }
            if(!thisParam.equals(thatParam))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode(){
        return hashcode;
    }

    @Override
    public String toString(){
        StringBuffer returnValue = (new StringBuffer()).append(hashcode).append('|').append(checksum);
        for(int i = 0; i < paramList.size(); i++)
            returnValue.append('|').append(paramList.get(i));

        return returnValue.toString();
    }
}
