import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class SHA {
    private int number_blocks = 0;
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
        //шаг 1
        String text = "hello world";
        byte[] byte_text = String_in_Bytes(text);
        //Добавляем едиинцу
        byte_text = add_one(byte_text);

        // Тут мы дополняем массив, нужно будет позже сделать универсальным
        //Добавляем код нулями, но пока что только для одного блока
        byte_text = add_zero(byte_text);

        for (int i = 0; i < number_blocks; i++) {
            int[] array_int  = words(Arrays.copyOfRange(byte_text, i * 64, 64 + i * 64));
            //System.out.println(Arrays.toString(array_int));
            array_int = add_words_zero(array_int);
            array_int = changing_zero_indexes(array_int);
            compression(array_int);

        }


        /*
        System.out.println(Integer.toBinaryString(array_int[1]));
        int left = array_int[1] << 25;
        int right = array_int[1] >> 7;
        int w_1 = left + right;
        int w_2 = array_int[1] >> 18;
        int w_3 = array_int[1] >> 3;
        System.out.println(Integer.toBinaryString(w_1));
        int s_0 = w_1;
        s_0 = Integer.rotateRight(array_int[1], 7);
        System.out.println(Integer.toBinaryString(s_0));// поворот в право
        //System.out.println(w_1);
        System.out.println("11011110110111100100000011101110"); */



        //byte[] byte_1 = Arrays.copyOf(byte_text, 64);
        //System.out.println(byte_1);
        //int test = ByteBuffer.wrap(byte_1).getInt();
        //System.out.println(test); // преобразование работает, останется зациклить


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
    private byte[] String_in_Bytes(String text){
        byte[] byte_text = text.getBytes();
        return byte_text;
    }
    private byte[] add_one(byte[] byte_text){
        byte_text = Arrays.copyOf(byte_text, byte_text.length + 1);
        byte_text[byte_text.length - 1] = (byte) 0b10000000;
        return byte_text;
    }
    private byte[] add_zero(byte[] byte_text){
        long size_array = byte_text.length; // для изменения размера массива
        long length_source_array = byte_text.length; // длина входного массива
        while (size_array % 64 != 0){
            byte_text = Arrays.copyOf(byte_text, byte_text.length + 1);
            byte_text[byte_text.length - 1] = (byte) 0b00000000;
            size_array = byte_text.length;

        }
        byte[] line = new byte[8];
        ByteBuffer line_array = ByteBuffer.allocate(line.length);
        line_array.putLong((length_source_array - 1) * 8);
        line = line_array.array();
        System.out.println(Arrays.toString(line));
        for (int i = 0; i < line.length; i++){
            byte_text[Math.toIntExact(size_array - 1 - i)] = line[line.length - 1 - i];
        }
        //System.out.println(Arrays.toString(byte_text));
        //byte_text[Math.toIntExact(size_array - 1)] = (byte) ((length_source_array - 1) * 8);
        //System.out.println(Arrays.toString(byte_text));
        number_blocks = (int) (size_array / 64);
        return byte_text;

    }
    private int[] words(byte[] byte_text){
        int[] array_int = new int[16];
        for (int i = 0; i < 16; i++) {
            byte[] byte_word = Arrays.copyOfRange(byte_text, i * 4, 4 + i * 4);
            array_int[i] = ByteBuffer.wrap(byte_word).getInt();
        }
        return array_int;
    }
    private int[] add_words_zero(int[] array_int){
        int size = array_int.length + 1;
        System.out.println(size);
        array_int = Arrays.copyOf(array_int, 64);
        Arrays.fill(array_int, size, 64, 0);

        return  array_int;
    }
    private int[] changing_zero_indexes(int[] array_int){
        long twe_32 = 4294967296l;
        System.out.println(twe_32);
        for(int j = 16; j < 64; j++) {
            long s_0 = Integer.rotateRight(array_int[j-15], 7) ^ Integer.rotateRight(array_int[j-15], 18) ^ (array_int[j-15] >>> 3);
            long s_1 = Integer.rotateRight(array_int[j - 2], 17) ^ Integer.rotateRight(array_int[j - 2], 19) ^ (array_int[j - 2] >>> 10);
            long result = (array_int[j-16] + s_0 + array_int[j - 7] + s_1) % twe_32;
            array_int[j] = (int) result;
            //System.out.println("arr[" + j + "]: " +Integer.toBinaryString(array_int[j]));
        }
        return array_int;
    }
    private void compression(int[] array_int){
        long twe_32 = 4294967296l;
        int a = h0;
        int b = h1;
        int c = h2;
        int d = h3;
        int e = h4;
        int f = h5;
        int g = h6;
        int h = h7;
        for(int j = 0; j < 64; j++){
            int Sum_1 = Integer.rotateRight(e, 6) ^ Integer.rotateRight(e, 11) ^ Integer.rotateRight(e,25);
            int Ch = (e & f) ^ ((~e) & g );
            int T_1 = (int) (h + Sum_1 + Ch + arr_const[j] + array_int[j] % twe_32);
            int Sum_0 = Integer.rotateRight(a, 2) ^ Integer.rotateRight(a, 13) ^ Integer.rotateRight(a, 22);
            int Ma = (a & b) ^ (a & c) ^ (b & c);
            int T_2 = (int) (Sum_0 + Ma % twe_32);
            h = g;
            g = f;
            f = e;
            e = (int) (d + T_1 % twe_32);
            d = c;
            c = b;
            b = a;
            a = (int) (T_1 +T_2 % twe_32);

        }

        h0 += a;
        h1 += b;
        h2 += c;
        h3 += d;
        h4 += e;
        h5 += f;
        h6 += g;
        h7 += h;
        System.out.println("[a] " + Integer.toBinaryString(h0));
        System.out.println("[b] " + Integer.toBinaryString(h1));
        System.out.println("[c] " + Integer.toBinaryString(h2));
        System.out.println("[d] " + Integer.toBinaryString(h3));
        System.out.println("[e] " + Integer.toBinaryString(h4));
        System.out.println("[f] " + Integer.toBinaryString(h5));
        System.out.println("[g] " + Integer.toBinaryString(h6));
        System.out.println("[h] " + Integer.toBinaryString(h7));
    }
}
