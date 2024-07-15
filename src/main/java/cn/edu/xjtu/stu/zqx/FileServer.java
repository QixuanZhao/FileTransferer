package cn.edu.xjtu.stu.zqx;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Date;

public class FileServer {

    /***
     * create a FileServer instance
     * @param port the port on which the server listens
     */
    public FileServer(int port) {
        this.port = port;
    }

    /***
     * create a FileServer instance which will listen on port 23333
     */
    public FileServer() {
    }

    private int port = 23333;
    private boolean started = false;

    /***
     * start the server
     */
    public void start() {
        try (ServerSocket serversocket = new ServerSocket(port)) {
            started = true;
            System.out.println("Server listening on port " + port);
            while (started) {
                Socket socket = serversocket.accept();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            PrintWriter out = new PrintWriter(socket.getOutputStream());
                            InputStream in = socket.getInputStream();

                            final int BUFFER_SIZE = 1024 * 1024;
                            byte[] fileSizeBytes = in.readNBytes(8);
                            long fileSize = IntegerUtil.composeLong(fileSizeBytes);

                            byte[] filenameLengthBytes = in.readNBytes(4);
                            int filenameLength = IntegerUtil.composeInt(filenameLengthBytes);
                            byte[] filenameBytes = in.readNBytes(filenameLength);
                            String filename = new String(filenameBytes, "UTF-8");
                            File file = new File(filename);
                            String[] filenameParts = filename.split("\\.", 2);
                            String rawName = filenameParts[0];
                            String extendedName = "";
                            if (filenameParts.length == 2) {
                                extendedName = "." + filenameParts[1];
                            }
                            int temp = 1;
                            while (file.exists()) {
//                                filenameParts.
                                String newFilename = rawName + " (" + temp + ")" + extendedName;
                                file = new File(newFilename);
                                temp++;
                            }

                            try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file))) {
                                BufferedInputStream bis = new BufferedInputStream(in);
                                long remainingSize = fileSize;
                                while (remainingSize > 0) {
                                    int tempSize = (remainingSize > 1024 * 1024) ? (1024 * 1024) : (int) remainingSize;
                                    byte[] content = bis.readNBytes(tempSize);
                                    writer.write(content);
                                    writer.flush();
                                    remainingSize -= content.length;
                                }
                            }
                            out.println("OK");
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            started = false;
        }
    }

    /***
     * stop the server
     */
    public void stop() {
        started = false;
    }
}
