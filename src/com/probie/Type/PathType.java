package com.probie.Type;

public enum PathType {

    /**
     * @deprecated
     * 【-1: Unknow 未知地址】
     * 【0: Local 本地路径】
     * 【1: Url 网址路径】
     * 【2-5: Url(Http/https/ftp/ssh) 网址协议】
     * 【6: BufferedImage 缓存地址】
     * */
    Unknow(-1,"unknow"),
    Local(0,"local"),
    Url(1,"url","unknow"),
    Url_Http(2,"url","http"),
    Url_Https(3,"url","https"),
    Url_Ftp(4,"url","ftp"),
    Url_Ssh(5,"url","ssh"),
    BufferedImage(6,"bufferedImage");

    private final int typeCode;
    private final String typeName;
    private final boolean isUrl;
    private final String urlType;

    PathType(int typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.isUrl = false;
        this.urlType = "unknow";
    }

    PathType(int typeCode, String typeName, String urlType) {
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.isUrl = true;
        this.urlType = urlType;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public boolean getIsUrl() {
        return isUrl;
    }

    public String getUrlType() {
        return urlType;
    }

    /**
     * @param index
     * 【-1: Unknow 未知地址】
     * 【0: Local 本地路径】
     * 【1: Url 网址路径】
     * 【2-5: Url(Http/https/ftp/ssh) 网址协议】
     * 【6: BufferedImage 缓存地址】
     * */
    public static PathType getPathType(int index) {
        switch (index) {
            case 0: {
                return Local;
            }
            case 1: {
                return Url;
            }
            case 2: {
                return Url_Http;
            }
            case 3: {
                return Url_Https;
            }
            case 4: {
                return Url_Ftp;
            }
            case 5: {
                return Url_Ssh;
            }
            case 6: {
                return BufferedImage;
            }
            case -1:
            default: {
                return Unknow;
            }
        }
    }

}