package com.program.probie;

import java.util.Scanner;
import java.util.Objects;
import com.program.probie.ProgrameTool.Website.Website;
import com.program.probie.ProgrameTool.Computer.Windows;

public class Tools {

    public static String toolsVersion = "9.0";
    public static String javaVersion = "21.0.8";
    public static String toolsName = "Tools";
    public static String author = "Probie";

    private static final String renewFile = "https://raw.githubusercontent.com/BProbie/api/Tools/download/Tools-"+toolsVersion+".jar";
    private static final String renewConfig = "https://raw.githubusercontent.com/BProbie/api/Tools/download/Tools.renew";

    public static void renew() {
        Object[] values = null;
        System.out.println("建立连接...("+renewConfig+")");
        try {
            values = Objects.requireNonNull(Website.getValueList(renewConfig)).toArray();
        } catch (Exception exception) {
            System.out.println("连接超时");
        }
        if (!Objects.requireNonNull(values)[0].equals(toolsVersion)) {
            System.out.println("检测到可更新内容:");
            Windows.sleep(1);
            System.out.println();
            Windows.sleep(1);
            for (int i = 1; i < values.length; i++) {
                System.out.println(values[i]);
                Windows.sleep(1);
            }
            System.out.println();
            Windows.sleep(1);
            System.out.print("请按回车键进行更新...");
            if (new Scanner(System.in).nextLine().isEmpty()) {
                System.out.println("更新文件...("+renewFile+")");
                if (Website.downFile(renewFile,Windows.getHere(),Windows.getFileName(renewFile))) {
                    System.out.println("更新完成,感谢支持!");
                } else {
                    System.out.println("连接超时");
                }
            } else {
                System.out.println("更新取消");
            }
        } else {
            System.out.println("当前已是最新版本");
        }
        System.gc();
    }

    public static void main(String[] args) {
        renew();
    }

}