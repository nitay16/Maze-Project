package IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;

public class MyCompressorOutputStream extends OutputStream {
    protected OutputStream out;

    /**
     * constructor that gets object of "OutputStream" class
     */
    public MyCompressorOutputStream(OutputStream out) {
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
        //create new compressor by deflater class
        Deflater compressor = new Deflater();
        // set the level of compression
        compressor.setLevel(compressor.BEST_COMPRESSION);
        compressor.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        compressor.finish();
        byte[] buffer = new byte[1024];
        while (!compressor.finished()) {
            int len_of_bytes = compressor.deflate(buffer);
            outputStream.write(buffer, 0, len_of_bytes);
        }
        outputStream.close();
        byte[] output_bit_array = outputStream.toByteArray();
        return output_bit_array;
    }
}