package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{



    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        try{
            // Get and Send Object Stream
            ObjectInputStream FromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream ToClient = new ObjectOutputStream(outToClient);
            // Create the ByteArray to send
            ByteArrayOutputStream OutByte = new ByteArrayOutputStream();
            // Check with type In the config
            AMazeGenerator mazeGen;
            String Gen = Configurations.mazeGeneratingAlgorithm();
            int Chose=0;
            if(Gen.equals("MyMazeGenerator")){}
            if(Gen.equals("SimpleMazeGenerator")){Chose=1;}
            if(Gen.equals("EmptyMazeGenerator")){Chose=2;}
            switch(Chose) {
                case 1:
                    mazeGen = new SimpleMazeGenerator();
                    break;
                case 2:
                    mazeGen = new EmptyMazeGenerator();
                    break;
                default:
                    mazeGen = new MyMazeGenerator();
            }
            //Get the input from the user
            int[] Input = (int[])FromClient.readObject();
            //Create the maze to send back
            Maze Maze_SendBack = mazeGen.generate(Input[0],Input[1]);

            //Compressor
            MyCompressorOutputStream comp = new MyCompressorOutputStream(OutByte);
            comp.write(Maze_SendBack.toByteArray());
            //Wait for the reasults
            comp.flush();
            comp.close();
            // Send backToClient
            ToClient.writeObject(OutByte.toByteArray());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
