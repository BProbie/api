package com.program.probie.ProgrameTool.Website;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.nio.file.StandardCopyOption;
import javax.net.ssl.SSLHandshakeException;

public class Website {

    public static boolean downFile(String fileUrl,String downPath,String downFileName) {
        try {
            try {
                InputStream inputStream = new URL(fileUrl).openConnection().getInputStream();
                File path = new File(downPath);
                if (!path.exists()) path.mkdirs();
                File file;
                if (downPath.endsWith("\\")) {file = new File(downPath+downFileName);}
                else {file = new File(downPath+"\\"+downFileName);}
                Files.copy(inputStream,file.toPath(),StandardCopyOption.REPLACE_EXISTING);
            } catch (SSLHandshakeException sslHandshakeException) {
                X509TrustManager.trustConnect();
                downFile(fileUrl,downPath,downFileName);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getValue(String valueUrl) {
        try {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(valueUrl).openConnection().getInputStream()));
                String urlValue = "";
                String temp;
                while ((temp = bufferedReader.readLine())!=null) urlValue = urlValue + temp + "\n";
                return replaceLast(urlValue,"\n","");
            } catch (SSLHandshakeException sslHandshakeException) {
                X509TrustManager.trustConnect();
                return getValue(valueUrl);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getValueList(String valueUrl) {
        try {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(valueUrl).openConnection().getInputStream()));
                ArrayList<String> urlValueList = new ArrayList<>();
                String temp;
                while ((temp = bufferedReader.readLine())!=null) urlValueList.add(temp);
                return urlValueList;
            } catch (SSLHandshakeException sslHandshakeException) {
                X509TrustManager.trustConnect();
                return getValueList(valueUrl);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    private static String replaceLast(String text,String regex, String replacement) {return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")",replacement);}

}