package com.github.pjfanning.jackson;

import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.type.TypeFactory;
import tools.jackson.databind.util.SimpleLookupCache;
import org.junit.Test;

import static org.junit.Assert.*;

public class CaffeineLRUMapTest {
    @Test
    public void testCache() {
        SimpleLookupCache<Long, String> cache = new CaffeineLRUMap(10);
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
        SimpleLookupCache<Object, JavaType> cache = new CaffeineLRUMap(1000);
        TypeFactory tf = TypeFactory.createDefaultInstance().withCache(cache);
        ObjectMapper mapper = JsonMapper.builder().typeFactory(tf).build();
        assertEquals("1000", mapper.writeValueAsString(1000));
    }
}
