package cn.edu.xjtu.stu.zqx;

public class IntegerUtil {
    static byte[] toBytes(int a) {
        byte[] result = new byte[4];
        result[0] = (byte) (a & 0xff);
        result[1] = (byte) ((a >>> 8) & 0xff);
        result[2] = (byte) ((a >>> 16) & 0xff);
        result[3] = (byte) ((a >>> 24) & 0xff);
        return result;
    }

    static byte[] toBytes(long a) {
        byte[] result = new byte[8];
        for (int i = 0; i < 8; i++) {
            result[i] = (byte) ((a >>> (8*i)) & 0xffL);
        }
        return result;
    }

    static int composeInt(byte[] bytes) {
        int byte1 = 0xff & ((int) bytes[0]);
        int byte2 = (0xff & ((int) bytes[1])) << 8;
        int byte3 = (0xff & ((int) bytes[2])) << 16;
        int byte4 = (0xff & ((int) bytes[3])) << 24;
        return byte1 | byte2 | byte3 | byte4;
    }

    static long composeLong(byte[] bytes) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            long byteAtI = (0xffL & ((long) bytes[i])) << (8 * i);
            result |= byteAtI;
        }
        return result;
    }
}
