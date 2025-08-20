package com.program.probie.ProgrameTool.Computer;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.util.Calendar;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import javax.swing.filechooser.FileSystemView;
import java.util.concurrent.atomic.AtomicBoolean;
import com.program.probie.ProgrameTool.Type.Show;
import com.program.probie.ProgrameTool.Type.Time;
import com.program.probie.ProgrameTool.Computer.misc.Mouse;
import com.program.probie.ProgrameTool.Computer.misc.Screen;
import com.program.probie.ProgrameTool.Computer.misc.Picture;

public class Windows {

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds*1000L);
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
    }

    public static void open(String path) {
        try {
            Desktop.getDesktop().open(new File(path));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static String readFile(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path),StandardCharsets.UTF_8));
            String values = ""; String value;
            while ((value=bufferedReader.readLine())!=null) values = values + value + "\n";
            return replaceLast(values,"\n","");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> readFileList(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path),StandardCharsets.UTF_8));
            ArrayList<String> arrayList = new ArrayList<>();
            String reader;
            while ((reader = bufferedReader.readLine())!=null) arrayList.add(reader);
            return arrayList;
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static void writeFile(String path,String value) {
        try {
            if (!new File(getPath(path)).exists()) {new File(getPath(path)).mkdirs();}
            if (!new File(path).exists()) new File(path).createNewFile();
            OutputStream outputStream = new FileOutputStream(path);
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static int runCommand(String worker,String state,String command) {
        try {
            return Runtime.getRuntime().exec(new String[] {worker,state,command}).waitFor();
        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
        }
        return -1;
    }

    public static void showInformation(String information) {
        writeFile(getTempPath()+"\\"+"information.vbs","Information=msgbox(\""+information+"\",vbOKOnly,\"Information\")");
        open(getTempPath()+"\\"+"information.vbs");
    }

    public static Object getInput(Show show) {
        if (show==Show.Frame) {
            JFrame frame = new JFrame();
            JTextField textField = new JTextField();
            JButton defineButton = new JButton("Define");
            JButton cancelButton = new JButton("Cancel");
            AtomicBoolean isReturn = new AtomicBoolean(false);

            frame.setSize(300,100);
            frame.setUndecorated(true);
            frame.setOpacity(0.9f);
            frame.setLayout(null);

            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation(dimension.width/2-frame.getWidth()/2,dimension.height/2-frame.getWidth()/2);

            textField.setFont(new Font("宋体",Font.BOLD,30));
            textField.setBounds(0,0,frame.getWidth(),frame.getHeight()/2);
            frame.add(textField);

            defineButton.setBounds(0,frame.getHeight()/2,frame.getWidth()/2,frame.getHeight()/2);
            defineButton.addActionListener(actionEvent -> {
                isReturn.set(true);
                frame.dispose();
            });
            frame.add(defineButton);

            cancelButton.setBounds(frame.getWidth()/2,frame.getHeight()/2,frame.getWidth()/2,frame.getHeight()/2);
            cancelButton.addActionListener(actionEvent -> frame.dispose());
            frame.add(cancelButton);
            frame.setVisible(true);

            while (frame.isVisible()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
            if (isReturn.get()) return textField.getText();
        } else if (show==Show.Cmd) {
            System.out.print("> ");
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        }
        return null;
    }

    public static File getChosenFile() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("D:\\"));
        boolean isOpen = jFileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION;
        if (isOpen) return jFileChooser.getSelectedFile();
        return null;
    }
    public static File getChosenFile(String defaultPath) {
        JFileChooser jFileChooser = new JFileChooser();
        File file = new File(defaultPath);
        if (file.exists()) {jFileChooser.setCurrentDirectory(file);}
        else {jFileChooser.setCurrentDirectory(new File("D:\\"));}
        boolean isOpen = jFileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION;
        if (isOpen) return jFileChooser.getSelectedFile();
        return null;
    }

    public static String replaceLast(String text,String regex, String replacement) {return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")",replacement);}

    public static char getPan(String path) {return path.toCharArray()[0];}
    public static String getUser() {return System.getenv().get("USERNAME");}

    public static String getPath(String path) {
        if (path.contains("https://")||path.contains("http://")||path.contains("ftps://")||path.contains("ftp://")) {return path.replaceAll(path.split("/")[path.split("/").length-1],"");}
        return path.replaceAll(path.split("\\\\")[path.split("\\\\").length-1],"");
    }
    public static String getFileName(String path) {
        if (path.contains("https://")||path.contains("http://")||path.contains("ftps://")||path.contains("ftp://")) {return path.split("/")[path.split("/").length-1];}
        return path.split("\\\\")[path.split("\\\\").length-1];
    }
    public static String getFilePath(String path,String fileName) {
        if (path.contains("https://")||path.contains("http://")||path.contains("ftps://")||path.contains("ftp://")) {
            if (path.endsWith("/")) {return path+fileName;}
            return path+"/"+fileName;
        }
        return path+"\\"+fileName;
    }

    public static String getHere() {return System.getProperty("user.dir");}
    public static String getTempPath() {return System.getenv().get("TEMP");}
    public static String getDesktopPath() {return FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();}


    public static int getTime(Time time) {
        switch (time) {
            case Year: return Calendar.getInstance().get(Calendar.YEAR);
            case Month: return Calendar.getInstance().get(Calendar.MONTH)+1;
            case Day: return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            case Hour: return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            case Minute: return Calendar.getInstance().get(Calendar.MINUTE);
            case Second: return Calendar.getInstance().get(Calendar.SECOND);
        }
        return -1;
    }

    public static Mouse getMouseTool() {return new Mouse();}
    public static Picture getPictureTool() {return new Picture();}
    public static Screen getScreenTool() {return new Screen();}

}