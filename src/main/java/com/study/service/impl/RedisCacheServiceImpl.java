package com.study.service.impl;


import com.study.common.redis.HessianSerializer;
import com.study.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

@Repository("redisService")
public class RedisCacheServiceImpl implements IRedisService {

    @Autowired
    private  JedisPool jedisPool;

    /**
     *  清楚所有key
     */
    public boolean deleteAllKeys(String pre){
        Jedis jedis = jedisPool.getResource();
        boolean resutl=true;
        try{
           // System.out.println("ba   "+pre+"*");
            Set<String> set=jedis.keys(pre+"*");
            String[]key=new String[set.size()];
            Iterator it =set.iterator();
            int k=0;
            while(it.hasNext()){
               key[k]=(String)it.next();
              // System.out.println("batch delete:"+key[k]);
               k++;
            }
            if(key.length>0){
                long count= jedis.del(key);
                System.out.println("batch delete:"+count+"- k="+k);
            }

            System.out.println("batch delete k="+k);

        }catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            resutl=false;
        }finally{
            jedisPool.returnResource(jedis);
        }

        return resutl;
    }

    /**
     *  清楚one
     *  key
     */
    public boolean deleteOneKey(String key){
        Jedis jedis = jedisPool.getResource();
        boolean resutl=true;
        try{
            long count= jedis.del(key);
        }catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            resutl=false;
        }finally{
            jedisPool.returnResource(jedis);
        }

        return resutl;
    }


    /**
     * 设置超时
     * @param key
     * @param value
     * @param seconds
     */
    public void set(String key, String value, int seconds) {
    	Jedis jedis = jedisPool.getResource();
    	try{
    		jedis.setex(key, seconds, value);
        }catch (Exception e) {
        	jedisPool.returnBrokenResource(jedis);
		}finally{
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 把Object序列化
     * @param key
     * @param o
     */
    public void setObject(String key, Object o) {
    	if (null == o){
			return;
		}
    	Jedis jedis = jedisPool.getResource();
    	try{
            jedis.set(key.getBytes(),  HessianSerializer.getInstance().encode(o));
    		//jedis.setex(key.getBytes(),  HessianSerializer.getInstance().encode(o));
        }catch (Exception e) {
        	jedisPool.returnBrokenResource(jedis);
		}finally{
            jedisPool.returnResource(jedis);
        }
    }
    /**
     * 设置bu超时
     * @param key
     * @param value
     * @param
     */
    public void set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        try{
            jedis.set(key, value);
        }catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
        }finally{
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 把Object序列化
     * @param key
     * @param o
     */
    public void setObject(String key, Object o, int seconds) {
        if (null == o){
            return;
        }
        Jedis jedis = jedisPool.getResource();
        try{

            jedis.setex(key.getBytes(), seconds, HessianSerializer.getInstance().encode(o));
        }catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
        }finally{
            jedisPool.returnResource(jedis);
        }
    }
    public String get(String key){
        Jedis jedis = jedisPool.getResource();
        try{
            return jedis.get(key);
        }catch (Exception e) {
        	jedisPool.returnBrokenResource(jedis);
        	return null;
		}finally{
            jedisPool.returnResource(jedis);
        }
    }
    
    /**
     * 根据key获取Object
     * @param key
     * @param
     * @return
     */
    public Object getObject(String key){
        Jedis jedis = jedisPool.getResource();

        byte[] bs = null;
        try{
            bs = jedis.get(key.getBytes());
        }catch (Exception e) {
        	jedisPool.returnBrokenResource(jedis);
		}finally{
            jedisPool.returnResource(jedis);
        }
        if(null != bs) {
        	return HessianSerializer.getInstance().decode(bs);
        }
        return null;
    }
    
    /**设置过期**/
    public void setExpire(String key, int seconds) {
    	if (seconds <= 0) {
			return;
		}
    	Jedis jedis = jedisPool.getResource();
    	try{
    		jedis.expire(key, seconds);
        }catch (Exception e) {
        	jedisPool.returnBrokenResource(jedis);
		}finally{
            jedisPool.returnResource(jedis);
        }
    }
    
    public void setMap(String mapKey, String key, Object value) {
    	if (null == value || key == null){
			return;
		}
    	Jedis jedis = jedisPool.getResource();
    	try{

    		jedis.hset(mapKey.getBytes(), key.getBytes(), HessianSerializer.getInstance().encode(value));
        }catch (Exception e) {
        	jedisPool.returnBrokenResource(jedis);
		}finally{
            jedisPool.returnResource(jedis);
        }
    }
    
    public Object getObjectFromMap(String mapKey, String key) {
    	Jedis jedis = jedisPool.getResource();
    	byte[] bs = null;
        try{
			bs = jedis.hget(mapKey.getBytes(), key.getBytes());
        }catch (Exception e) {
        	jedisPool.returnBrokenResource(jedis);
		}finally{
            jedisPool.returnResource(jedis);
        }
        if(null != bs) {
        	return HessianSerializer.getInstance().decode(bs);
        }
        return null;
    }
    
    public List<Object> getMapList(String mapKey) {
    	Jedis jedis = jedisPool.getResource();
    	Map<byte[], byte[]> map = null;
        try{
        	map = jedis.hgetAll(mapKey.getBytes());
        }catch (Exception e) {
        	jedisPool.returnBrokenResource(jedis);
		}finally{
            jedisPool.returnResource(jedis);
        }
        List<Object> list = null;
        if (map != null) {
			list = new ArrayList<Object>();
			for(byte[] bs : map.values()) {
				list.add(HessianSerializer.getInstance().decode(bs));
			}
		}
        
        if (list != null && list.size() == 0) {
        	jedis = jedisPool.getResource();
        	try{
            	Boolean exists = jedis.exists(mapKey.getBytes());
            	if (!exists) {
					return null;
				}
            }catch (Exception e) {
            	jedisPool.returnBrokenResource(jedis);
    		}finally{
                jedisPool.returnResource(jedis);
            }
		}
        return list;
    }
    
    public void deleteObjectFromMap(String mapKey, String key) {
    	Jedis jedis = jedisPool.getResource();
        try{
			jedis.hdel(mapKey.getBytes(), key.getBytes());
        }catch (Exception e) {
        	jedisPool.returnBrokenResource(jedis);
		}finally{
            jedisPool.returnResource(jedis);
        }
    }

    public void destroy(){
        jedisPool.destroy();
    }

	public void delete(String... keys) {
		Jedis jedis = jedisPool.getResource();
        try{
        	jedis.del(keys);
        }catch (Exception e) {
        	jedisPool.returnBrokenResource(jedis);
		}finally{
            jedisPool.returnResource(jedis);
        }
	}

	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		long changeSize = 0l;
    	try{
    		changeSize = jedis.incr(key);
        }catch (Exception e) {
        	jedisPool.returnBrokenResource(jedis);
		}finally{
        	jedisPool.returnResource(jedis);
        }
    	return changeSize;
	}

	public boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
		Boolean exists = false;
		try{
			exists = jedis.exists(key.getBytes());
        }catch (Exception e) {
        	jedisPool.returnBrokenResource(jedis);
		}finally{
            jedisPool.returnResource(jedis);
        }
		return exists;
	}

	public long hlen(String key) {
		Jedis jedis = jedisPool.getResource();
		long size = 0;
		try{
			size = jedis.hlen(key.getBytes());
        }catch (Exception e) {
        	jedisPool.returnBrokenResource(jedis);
		}finally{
            jedisPool.returnResource(jedis);
        }
		return size;
	}
}
