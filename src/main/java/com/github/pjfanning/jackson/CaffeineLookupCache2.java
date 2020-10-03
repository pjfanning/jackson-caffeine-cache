package com.github.pjfanning.jackson;

import com.fasterxml.jackson.databind.util.LookupCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.ConcurrentMap;

/**
 * Temporary version of CaffeineLookupCache that does not extend <code>LRUMap</code>
 */
public class CaffeineLookupCache2<K, V> implements LookupCache<K, V> {

    private final ConcurrentMap<K, V> cache;
    private final int maxEntries;

    public CaffeineLookupCache2(int maxEntries) {
        this.maxEntries = maxEntries;
        Cache<K, V> fullCache = Caffeine.newBuilder().maximumSize(maxEntries).build();
        this.cache = fullCache.asMap();
    }

    @Override
    public V put(K key, V value) {
        return cache.put(key, value);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return cache.putIfAbsent(key, value);
    }

    @Override
    public V get(Object key) {
        return cache.get(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int size() {
        return cache.size();
    }
}
