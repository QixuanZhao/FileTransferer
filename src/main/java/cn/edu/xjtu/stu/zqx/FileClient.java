package cn.edu.xjtu.stu.zqx;

import java.io.*;
import java.net.Socket;

public class FileClient {

    public static void transfer(String filename, String serverAddress, int serverPort) {
        File file = new File(filename);
        if (!file.isFile()) {
            System.out.println(filename + " is not a file.");
            return;
        }

        try (Socket socket = new Socket(serverAddress, serverPort)) {
            OutputStream out = socket.getOutputStream();
            long fileSize = file.length();
            byte[] fileSizeBytes = IntegerUtil.toBytes(fileSize);
            out.write(fileSizeBytes);
            byte[] fileNameBytes = file.getName().getBytes();
            out.write(IntegerUtil.toBytes(fileNameBytes.length));
            out.write(fileNameBytes);
            out.flush();

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] content = bis.readAllBytes();
            System.out.println("Content Size: " + content.length);
            bis.close();
            out.write(content);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 23333;

        if (args.length < 2) {
            System.out.println("USAGE: java FileClient host[:port] filename");
            return;
        }

        File file = new File(args[1]);
        if (!file.isFile()) {
            System.out.println(args[1] + " is not a file.");
            return;
        }

        try (Socket socket = new Socket(host, port)) {
            OutputStream out = socket.getOutputStream();
            out.write((int) file.length());
//            PrintWriter out = new PrintWriter(socket.getOutputStream());
//            out.println("The Sample Text");
//            out.println("IS OVER");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
