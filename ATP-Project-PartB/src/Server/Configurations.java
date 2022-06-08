package Server;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {
    static Properties Proper = new Properties();

    // Create func for each Config
    public static int threadPoolSize(){
        //Create defualt size
        int PoolSize =4;
        try {
            InputStream inputConf = new FileInputStream("resources/config.properties");
            //Load
            Proper.load(inputConf);
            // return the value from the config
            return Integer.parseInt(Proper.getProperty("threadPoolSize"));
        }
        catch (Exception ex)
        {
            return PoolSize;
        }
    }

    public static String mazeGeneratingAlgorithm()
    {
        //defualt Generatiing
        String Backup = "MyMazeGenerator";
        try {
            InputStream inputConf = new FileInputStream("resources/config.properties");
            //Load
            Proper.load(inputConf);
            // return the value from the config
            return Proper.getProperty("mazeGeneratingAlgorithm");
        }
        catch (Exception ex)
        {
            return Backup;
        }
    }

    public static String mazeSearchingAlgorithm()
    {
        //defualt Generatiing
        String Backup = "BestFirstSearch";
        try {
            InputStream inputConf = new FileInputStream("resources/config.properties");
            //Load
            Proper.load(inputConf);
            // return the value from the config
            return Proper.getProperty("mazeSearchingAlgorithm");
        }
        catch (Exception ex)
        {
            return Backup;
        }
    }


}
