package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.*;
import jdk.jfr.Unsigned;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerStrategySolveSearchProblem implements IServerStrategy{

    private String tempDirectoryPath = System.getProperty("java.io.tmpdir");
    private volatile AtomicInteger AtomicCounter = new AtomicInteger(0);
    private File arrays = new File(tempDirectoryPath + "/Arrays");

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        try{
            // Get and Send Object Stream
            ObjectInputStream FromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream ToClient = new ObjectOutputStream(outToClient);

            ToClient.flush();
            //Create the solution
            Solution solu;
            //Create the Searching algo
            ASearchingAlgorithm MySolu;
            //Read The Maze form client
            Maze maze_get = (Maze)FromClient.readObject();

            //Check if we already Solve This maze by check in the file
            if(Check_If_There_Solution(maze_get.toByteArray())!= -1)
            {
                solu = ReadSolutionFromFile(maze_get.toByteArray());
            }
            else {
                SearchableMaze searchableMa = new SearchableMaze(maze_get);
                // Check with type In the config
                String Sol = Configurations.mazeSearchingAlgorithm();
                int Chose=0;
                if(Sol.equals("BestFirstSearch")){}
                if(Sol.equals("BreadthFirstSearch")){Chose=1;}
                if(Sol.equals("DepthFirstSearch")){Chose=2;}
                switch(Chose) {
                    case 1:
                        MySolu = new BreadthFirstSearch();
                        break;
                    case 2:
                        MySolu = new DepthFirstSearch();
                        break;
                    default:
                        MySolu = new BestFirstSearch();
                }
                solu = MySolu.solve(searchableMa);
                // save the solution
                SolutionToSave(maze_get.toByteArray(),solu);

            }
            ToClient.writeObject(solu);
            ToClient.flush();
            ToClient.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    //Check if the arrays are the same
    private boolean CheckByName(byte[] WantToCheck , String FromSaved)
    {
        return Arrays.toString(WantToCheck).equals(FromSaved);

    }

    private void SolutionToSave(byte[] arrToSave , Solution SolToSave)
    {
        try {
                // writing the soultion
                File myObj = new File(tempDirectoryPath + "/Solutions" +AtomicCounter.get());
                FileOutputStream myWriter = new FileOutputStream(tempDirectoryPath + "/Solutions" +AtomicCounter.get());
                ObjectOutputStream Save = new ObjectOutputStream(myWriter);
                Save.writeObject(SolToSave);
                Save.close();
                myWriter.close();
                AtomicCounter.getAndIncrement();
                // writing the array
                FileWriter fw = new FileWriter(tempDirectoryPath + "/Arrays");
                fw.write(Arrays.toString(arrToSave));
                fw.close();
        } catch (Exception e) {
        }
    }

    private int Check_If_There_Solution(byte[] arrToCheck){
        try
        {
            AtomicInteger Con = new AtomicInteger(0);
            FileInputStream fis=new FileInputStream(tempDirectoryPath + "/Arrays");
            Scanner sc=new Scanner(fis);    //file to be scanned
            while(sc.hasNextLine())
            {
                if(CheckByName(arrToCheck,sc.nextLine()))
                {
                    return Con.get();
                }
                Con.getAndIncrement();
            }
            sc.close();     //closes the scanner
        }
        catch(IOException e) {}

        return -1;
    }

    private Solution ReadSolutionFromFile(byte[] arrToRead)
    {
        try {
                FileInputStream myReader = new FileInputStream(tempDirectoryPath + "/Solutions" + Check_If_There_Solution(arrToRead));
                ObjectInputStream Read = new ObjectInputStream(myReader);
                Solution BackToClient = (Solution) Read.readObject();
                Read.close();
                myReader.close();
                return BackToClient;
        } catch (Exception e) {
        }
        return null;
    }

}

