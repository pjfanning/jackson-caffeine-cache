package com.github.pjfanning.jackson;

import com.fasterxml.jackson.databind.util.LookupCache;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CaffeineLookupCache2Test {
    @Test
    public void testCache() {
        LookupCache<Long, String> cache = new CaffeineLookupCache2(10);
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
}
