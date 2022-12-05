public class Main {
    public static void main(String[] args) {
        SHA sha = new SHA("привет мир");
        System.out.println(sha.gethex());
        System.out.println(sha.getInteger());
        /*byte value = 2;
        byte shortByteValue = 0b10; // 2
        System.out.println(shortByteValue);
        // Начиная с JDK7 мы можем разделять литералы подчёркиваниями
        byte minByteValue = (byte) 0B1000_0000; // -128
        byte maxByteValue = (byte) 0b0111_1111; // 127
        byte minusByteValue = (byte) 0b1111_1111; // -128 + 127
        System.out.println(minusByteValue);
        System.out.println(minByteValue + " to " + maxByteValue);
*/
    }
}