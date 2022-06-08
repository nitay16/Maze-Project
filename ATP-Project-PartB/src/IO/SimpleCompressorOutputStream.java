package IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class SimpleCompressorOutputStream extends OutputStream {
    protected OutputStream out;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }
    @Override
    public void write(byte[] b) throws IOException {
        byte[] tocompress_array = compress(b);
        for (int i = 0; i < tocompress_array.length; i++) {
            write(tocompress_array[i]);
        }
    }
    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        ArrayList<Byte> int_array= new ArrayList<>();
        ArrayList<Byte> byte_list = new ArrayList<>();
        byte counter_zero;
        byte prev_number;
        boolean flag_zero= false;
        if(data[10]==1){
             counter_zero =0;
             prev_number= 1;
            int_array.add((byte)0);
        } else{
            counter_zero =1;
            prev_number= 0;
        }
            int current =11;

            while(current<data.length){
                if(data[current]==prev_number){
                    if(counter_zero==126){
                        int_array.add(counter_zero);
                        int_array.add((byte)0);
                        counter_zero=0;
                    }
                    counter_zero++;
                    prev_number=data[current];
                    current++;
                }

                else{
                    int_array.add(counter_zero);
                    counter_zero=1;
                    prev_number=data[current];
                    current++;
                }
            }
            int_array.add(counter_zero);
            byte[] byte_array_return =new byte[data.length+int_array.size()];
            for(int i=0;i<10;i++){
                byte_array_return[i]=data[i];
            }
            int index =10;
            for(int j=0;j<int_array.size();j++){
                byte_array_return[index]= int_array.get(j);
                index++;
            }
            return byte_array_return;


    }
}
