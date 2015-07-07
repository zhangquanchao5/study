package com.study.service;

import java.util.List;

public interface IRedisService {

    public boolean deleteAllKeys(String pre);

    public boolean deleteOneKey(String key);

    public String get(String key);

    public void set(String key, String value, int seconds);

    public void setObject(String key, Object o, int seconds);

    public void set(String key, String value);

    public void setObject(String key, Object o);
    
    public Object getObject(String key);

	public void delete(String... keys);
	
	public void setExpire(String key, int seconds);

	public void setMap(String mapKey, String key, Object value);
	
	public Object getObjectFromMap(String mapKey, String key);
	
	public void deleteObjectFromMap(String mapKey, String key);
	
	public List getMapList(String mapKey);
	
	public long incr(String key);
	
	public boolean exists(String key);
	
	public long hlen(String key);
}
