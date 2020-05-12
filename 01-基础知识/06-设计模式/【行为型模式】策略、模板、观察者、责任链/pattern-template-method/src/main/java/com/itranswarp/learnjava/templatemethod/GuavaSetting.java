package com.itranswarp.learnjava.templatemethod;
import com.google.common.cache.*;

public class GuavaSetting extends AbstractSetting {
    private Cache<String, String > cache = CacheBuilder.newBuilder().build();

    @Override
    protected String lookupCache(String key){
        return cache.getIfPresent(key);
    }

    @Override
    protected void putIntoCache(String key, String value){
        cache.put(key, value);
    }
}
