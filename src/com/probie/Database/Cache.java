package com.probie.Database;

import com.probie.Factory.DatabaseFactory;

public class Cache extends DatabaseFactory implements Cloneable {

    private Cache cache = this;

    public Cache() {

    }

    @Deprecated
    public Cache setCache(Cache cache) {
        this.cache = cache;
        return this;
    }

    public Cache getCache() {
        return cache;
    }

    @Override
    public String getDefaultPath() {
        String defaultPath = super.getDefaultPath();
        if (defaultPath.contains("\\")) {
            if (!defaultPath.endsWith("\\")) {
                defaultPath += "\\";
            }
        }
        else if (defaultPath.contains("/")) {
            if (!defaultPath.endsWith("/")) {
                defaultPath += "/";
            }
        }
        defaultPath += APIData.CacheName;
        return defaultPath;
    }

}