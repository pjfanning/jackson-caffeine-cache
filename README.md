# jackson-caffeine-cache

[![Build Status](https://travis-ci.com/pjfanning/jackson-caffeine-cache.svg?branch=master)](https://travis-ci.com/pjfanning/jackson-caffeine-cache)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.pjfanning/jackson-caffeine-cache/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.pjfanning/jackson-caffeine-cache)

A Caffeine based type cache for Jackson. This is built with Caffeine 2.9 in order to support Java 8. If you are using Java 11 or above, it is recommended that you have your built pick up the latest Cafferine 3.0 jar. It is expected that this will work ok with jackson-caffeine-cache.

The built-in type cache ([LRUMap](https://github.com/FasterXML/jackson-databind/blob/2.10/src/main/java/com/fasterxml/jackson/databind/util/LRUMap.java))
in Jackson has synchronization that might cause performance issues if you have a lot of types in the cache (see [issue](https://github.com/FasterXML/jackson-module-scala/issues/428)). LRUMap empties the cache when it fills up. The Caffeine cache will remove the oldest entries.

To use an LRUMap with larger max size:

        LRUMap<Object, JavaType> cache = new LRUMap(4, 1000);
        TypeFactory tf = TypeFactory.defaultInstance().withCache(cache);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setTypeFactory(tf);
        //or ObjectMapper mapper = JsonMapper.builder().typeFactory(tf).build();

To use this [Caffeine](https://github.com/ben-manes/caffeine) based cache:

        CaffeineLookupCache<Object, JavaType> cache = new CaffeineLookupCache(1000);
        TypeFactory tf = TypeFactory.defaultInstance().withCache(cache);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setTypeFactory(tf);
        //or ObjectMapper mapper = JsonMapper.builder().typeFactory(tf).build();
        
# Jackson 2.11

jackson-caffeine-cache 1.0.1 is the right version to use with Jackson 2.11.
        
# Jackson 2.12 and above
 
jackson-caffeine-cache 1.1.0 was refactored to take advantage of a change in Jackson 2.12.0. LRUMap now implements a LookupCache interface. This makes it easier to create custom cache implementations.

CaffeineLookupCache will still work with TypeFactory, as above. Some Jackson APIs still require an LRUMap. If you want to use a Caffeine-based cache instead of a LRUMap, there is a new CaffeineLRUMap.

# Jackson-Module-Scala 2.12.2 and above

After the v2.12.2 release, it will be possible to replace the default LRUMap descriptorCache.

        import com.fasterxml.jackson.databind.type.ClassKey
        import com.fasterxml.jackson.module.scala.introspect.{BeanDescriptor, ScalaAnnotationIntrospector}
        
        val cache = new CaffeineLookupCache[ClassKey, BeanDescriptor](1000)
        ScalaAnnotationIntrospector.setDescriptorCache(cache)
