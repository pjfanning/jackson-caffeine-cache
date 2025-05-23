package com.github.pjfanning.jackson;

import tools.jackson.databind.util.LookupCache;
import tools.jackson.databind.util.SimpleLookupCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.ConcurrentMap;

public class CaffeineLRUMap<K, V> extends SimpleLookupCache<K, V> {

    private final ConcurrentMap<K, V> cache;
    private final int maxEntries;

    public CaffeineLRUMap(final int maxEntries) {
        super(4, 10); //super class has its own storage (that we will not use)
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
    public V get(K key) {
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

    @Override
    protected Object readResolve() {
        return new CaffeineLRUMap(maxEntries);
    }

    @Override
    public LookupCache<K, V> emptyCopy() {
        return new CaffeineLRUMap<>(maxEntries);
    }

    @Override
    public SimpleLookupCache<K, V> snapshot() {
        return new CaffeineLRUMap(maxEntries);
    }
}
