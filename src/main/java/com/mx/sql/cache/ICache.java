package com.mx.sql.cache;

import java.util.Properties;

public interface ICache {
	
	void flush(CacheModel cachemodel);

    Object getObject(CacheModel cachemodel, Object obj);

    Object removeObject(CacheModel cachemodel, Object obj);

    void putObject(CacheModel cachemodel, Object obj, Object obj1);

    void onit(Properties properties);
}