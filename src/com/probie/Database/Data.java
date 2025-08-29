package com.probie.Database;

import com.probie.Factory.DatabaseFactory;

public class Data extends DatabaseFactory {

    private Data data = this;

    public Data() {

    }

    @Deprecated
    public Data setData(Data data) {
        this.data = data;
        return this;
    }

    public Data getData() {
        return data;
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
        defaultPath += APIData.DataName;
        return defaultPath;
    }

}