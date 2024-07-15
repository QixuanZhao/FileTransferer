import cn.edu.xjtu.stu.zqx.FileServer;

public class FileServerStart {
    public static void main(String[] args) {
        int port = 23333;
        if (args.length > 1) {
            port = Integer.parseInt(args[0]);
        }

        FileServer server = new FileServer(port);
        server.start();
    }
}
