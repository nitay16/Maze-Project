package IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class MyDecompressorInputStream extends InputStream {
    protected InputStream in;

    public MyDecompressorInputStream(InputStream in) {
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
        byte[] temporal_array= new byte[in.available()];
        int index =0;
        while(index<temporal_array.length){
            temporal_array[index]=(byte)read();
            index++;
        }
        try {
            temporal_array = decompress(temporal_array);
        }catch (DataFormatException DFE){
            System.out.println("failed to decompress the file");
        }
        //todo while
        for (int i = 0; i < temporal_array.length; i++) {
            b[i]=temporal_array[i];
        }
        return 1;
    }
    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater decompressor = new Inflater();
        //todo notes***
        decompressor.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!decompressor.finished()) {
            int length = decompressor.inflate(buffer);
            outputStream.write(buffer, 0, length);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        return output;
    }

}