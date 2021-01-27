package com.github.pjfanning.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.LookupCache;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CaffeineLookupCacheTest {
    @Test
    public void testCache() {
        LookupCache<Long, String> cache = new CaffeineLookupCache(10);
        assertNull(cache.get(1000L));
        assertNull(cache.put(1000L, "Thousand"));
        assertEquals("Thousand", cache.get(1000L));
        assertEquals("Thousand", cache.putIfAbsent(1000L, "Míle"));
        assertEquals("Thousand", cache.get(1000L));
        assertEquals("Thousand", cache.put(1000L, "Míle"));
        assertEquals("Míle", cache.get(1000L));
        cache.clear();
        assertNull(cache.put(1000L, "Thousand"));
    }

    @Test
    public void testCompatibility() {
        LookupCache<Object, JavaType> cache = new CaffeineLookupCache(1000);
        TypeFactory tf = TypeFactory.defaultInstance().withCache(cache);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setTypeFactory(tf);
        assertEquals("1000", mapper.writeValueAsString(1000));
    }

    @Test
    public void testBuilderCompatibility() {
        LookupCache<Object, JavaType> cache = new CaffeineLookupCache(1000);
        TypeFactory tf = TypeFactory.defaultInstance().withCache(cache);
        ObjectMapper mapper = JsonMapper.builder().typeFactory(tf).build();
        assertEquals("1000", mapper.writeValueAsString(1000));
    }
}
