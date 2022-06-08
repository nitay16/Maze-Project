package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService executorService;

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
    }


    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Runing();
            }
        }).start();
    }


    public void Runing() {
        //Check if the input in Config is ok
        if (Configurations.threadPoolSize() >= 0) {
            executorService = Executors.newFixedThreadPool(Configurations.threadPoolSize());
        } else {
            executorService = Executors.newFixedThreadPool(4);
        }

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            while (!stop)
            {
                try
                {
                    Socket clientSocket = serverSocket.accept();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            HandClient(clientSocket);
                        }
                    });
                }
                catch (Exception ex)
                {
                }
            }
            executorService.shutdown();
            serverSocket.close();

        }
        catch (Exception ex)
        {

         }

    }
    private void HandClient(Socket ClientSoc)
    {
        try
        {
            strategy.serverStrategy(ClientSoc.getInputStream(),ClientSoc.getOutputStream());
            ClientSoc.close();
        }
        catch (Exception ex)
        {}

    }
    //Stop the server
    public void stop(){stop=true;}

}

