import cn.edu.xjtu.stu.zqx.FileClient;
import cn.edu.xjtu.stu.zqx.FileServer;

public class FileTransferer {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("USAGE: java FileTransferer filename destination-host[:port]");
            return;
        }

        String filename = args[0];
        String destination = args[1];
        String[] destinationSubstrings = destination.split(":");

        int serverPort = 23333;
        if (destinationSubstrings.length == 2) {
            serverPort = Integer.parseInt(destinationSubstrings[1]);
            destination = destinationSubstrings[0];
        }

        FileClient.transfer(filename, destination, serverPort);
    }
}
