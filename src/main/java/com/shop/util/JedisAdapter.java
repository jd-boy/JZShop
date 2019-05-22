package com.shop.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.shop.aspect.LogAspect;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class JedisAdapter implements InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	private JedisPool pool = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		pool = new JedisPool("localhost", 6379);
	}
	
	private Jedis getJedis() {
		return pool.getResource();
	}
	
	public void setex(String key, int time, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.setex(key, time, value);
		} catch(Exception e) {
			logger.error("JedisAdapter.setex()发生异常" + e.getMessage());
			return ;
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return  getJedis().get(key);
		} catch(Exception e) {
			logger.error("JedisAdapter.get()发生异常" + e.getMessage());
			return null;
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("JedisAdapter.set()发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
	
	public void setObject(String key, Object obj) {
		set(key, JSON.toJSONString(obj));
	}
	
	public <T> T getObjec(String key, Class<T> clazz) {
		String value = get(key);
		if(value != null) {
			return JSON.parseObject(value, clazz);
		}
		return null;
	}
	
	public long sadd(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sadd(key, value);
		} catch(Exception e) {
			logger.error("JedisAdapter.sadd()发生异常" + e.getMessage());
			return 0;
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public boolean sismember(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sismember(key, value);
		} catch(Exception e) {
			logger.error("JedisAdapter.sismember()发生异常" + e.getMessage());
			return false;
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public long scard(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.scard(key);
		} catch(Exception e) {
			logger.error("JedisAdapter.scard()发生异常" + e.getMessage());
			return 0;
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("JedisAdapter.lpush()发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("JedisAdapter.brpop()发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
