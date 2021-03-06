/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.apache.shiro.cache.spring;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * 包装Spring cache抽象
 * <p>User: hyssop
 * <p>Date: 13-3-23 上午8:26
 * <p>Version: 1.0
 */
@Repository
public class SpringCacheManagerWrapper implements CacheManager {

    private org.springframework.cache.CacheManager cacheManager;

    /**
     * 设置spring cache manager
     *
     * @param cacheManager
     */
    public void setCacheManager(org.springframework.cache.CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }


    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        org.springframework.cache.Cache springCache = cacheManager.getCache(name);
        return new SpringCacheWrapper(springCache);
    }

    static class SpringCacheWrapper implements Cache {
        private org.springframework.cache.Cache springCache;

        SpringCacheWrapper(org.springframework.cache.Cache springCache) {
            this.springCache = springCache;
        }


        public Object get(Object key) throws CacheException {
            Object value = springCache.get(key);
            if (value instanceof SimpleValueWrapper) {
                return ((SimpleValueWrapper) value).get();
            }
            return value;
        }


        public Object put(Object key, Object value) throws CacheException {
            springCache.put(key, value);
            return value;
        }


        public Object remove(Object key) throws CacheException {
            springCache.evict(key);
            return null;
        }


        public void clear() throws CacheException {
            springCache.clear();
        }


        public int size() {
            throw new UnsupportedOperationException("invoke spring cache abstract size method not supported");
        }


        public Set keys() {
            throw new UnsupportedOperationException("invoke spring cache abstract keys method not supported");
        }


        public Collection values() {
            throw new UnsupportedOperationException("invoke spring cache abstract values method not supported");
        }
    }
}
