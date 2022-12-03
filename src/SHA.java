import java.util.Arrays;

public class SHA {
    private int h0 = 0x6a09e667;
    private int h1 = 0xbb67ae85;
    private int h2 = 0x3c6ef372;
    private int h3 = 0xa54ff53a;
    private int h4 = 0x510e527f;
    private int h5 = 0x9b05688c;
    private int h6 = 0x1f83d9ab;
    private int h7 = 0x5be0cd19;
    int[] arr_const = {0x428a2f98,0x71374491,0xb5c0fbcf,0xe9b5dba5,0x3956c25b,0x59f111f1,0x923f82a4,0xab1c5ed5,
            0xd807aa98,0x12835b01,0x243185be,0x550c7dc3,0x72be5d74,0x80deb1fe,0x9bdc06a7,0xc19bf174,
            0xe49b69c1,0xefbe4786,0x0fc19dc6,0x240ca1cc,0x2de92c6f,0x4a7484aa,0x5cb0a9dc,0x76f988da,
            0x983e5152,0xa831c66d,0xb00327c8,0xbf597fc7,0xc6e00bf3,0xd5a79147,0x06ca6351,0x14292967,
            0x27b70a85,0x2e1b2138,0x4d2c6dfc,0x53380d13,0x650a7354,0x766a0abb,0x81c2c92e,0x92722c85,
            0xa2bfe8a1,0xa81a664b,0xc24b8b70,0xc76c51a3,0xd192e819,0xd6990624,0xf40e3585,0x106aa070,
            0x19a4c116,0x1e376c08,0x2748774c,0x34b0bcb5,0x391c0cb3,0x4ed8aa4a,0x5b9cca4f,0x682e6ff3,
            0x748f82ee,0x78a5636f,0x84c87814,0x8cc70208,0x90befffa,0xa4506ceb,0xbef9a3f7,0xc67178f2};
    public SHA(){
        String text = "hello world";
        byte[] byte_text = text.getBytes();
        byte_text = Arrays.copyOf(byte_text, byte_text.length + 1);
        byte_text[byte_text.length - 1] = (byte) 0b10000000;
        System.out.println(Arrays.toString(byte_text));
        // Тут мы дополняем массив, нужно будет позже сделать универсальным
        int size_max = 64 - byte_text.length;
        int size = byte_text.length;
        System.out.println(size);
        byte_text = Arrays.copyOf(byte_text, 64);
        Arrays.fill(byte_text,size, 64, (byte) 0);
        System.out.println(Arrays.toString(byte_text));
        byte_text[63] = (byte)size;
        System.out.println(Arrays.toString(byte_text));
        // шаг 5
        /* то что понадобится
        int[] int_text = new int[byte_text.length];
        System.out.println(Arrays.toString(byte_text));
        for (int i = 0; i < byte_text.length; i++ ){
            int_text[i] = Integer.parseInt(Integer.toBinaryString(byte_text[i] & 0xFF));
            //System.out.println(i_1);
        }
        int_text = Arrays.copyOf(int_text, int_text.length + 1);
        System.out.println(Arrays.toString(int_text));
        System.out.println(int_text[0]);
        */
        //String a = Integer.toBinaryString('h');
        //System.out.println(a);
        //int i_1 = Integer.parseInt(Integer.toBinaryString(b1 & 0xFF));
        //String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
        //System.out.println(s1);
    }
}
