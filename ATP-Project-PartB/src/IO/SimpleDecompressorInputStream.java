package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream {
    protected InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }


    @Override
    public int read() throws IOException {
        return (int)in.read();
    }
    @Override
    public int read(byte[] b) throws IOException {
        /**The methods returns and estimated of the number
         * of remaining bytes that can be read from this input stream without blocking.*/
        byte[] copy_compress_arr= new byte[b.length+1];

        in.read(copy_compress_arr);

        //copy the first 10 bytes from b
        for(int i=0;i<10;i++){
            b[i]=copy_compress_arr[i];
        }

        int index_begin =10;
        byte zero_one = 0;
        for(int i=10;i<copy_compress_arr.length;i++){
            if(copy_compress_arr[i]!=128){
                for(int j=0;j<copy_compress_arr[i];j++){
                    b[index_begin]= zero_one;
                    index_begin++;
                }

            }
            if(zero_one==1){
                zero_one=0;
            }
            else {
                zero_one=1;
            }
        }
        return 0;
    }

}
