# jackson-caffeine-cache

[![Build Status](https://travis-ci.org/pjfanning/jackson-caffeine-cache.svg?branch=master)](https://travis-ci.org/pjfanning/jackson-caffeine-cache)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.pjfanning/jackson-caffeine-cache/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.pjfanning/jackson-caffeine-cache)

A Caffeine based type cache for Jackson.

The built-in type cache ([LRUMap](https://github.com/FasterXML/jackson-databind/blob/2.10/src/main/java/com/fasterxml/jackson/databind/util/LRUMap.java))
in Jackson has synchronization that might cause performance issues if you have a lot of types in the cache (see [issue](https://github.com/FasterXML/jackson-module-scala/issues/428)).

To use an LRUMap with larger max size:

        LRUMap<Object, JavaType> cache = new LRUMap(4, 1000);
        TypeFactory tf = TypeFactory.defaultInstance().withCache(cache);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setTypeFactory(tf);

To use this [Caffeine](https://github.com/ben-manes/caffeine) based cache:

        CaffeineLookupCache<Object, JavaType> cache = new CaffeineLookupCache(1000);
        TypeFactory tf = TypeFactory.defaultInstance().withCache(cache);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setTypeFactory(tf);
