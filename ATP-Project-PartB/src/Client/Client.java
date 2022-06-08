package Client;

import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy clientStrategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.clientStrategy = clientStrategy;
    }

    public void communicateWithServer(){
        try {
            Socket Server = new Socket(serverIP,serverPort);
            System.out.println("Client Connected To Server: IP: "+serverIP + "Port: " + serverPort);
            clientStrategy.clientStrategy(Server.getInputStream(),Server.getOutputStream());
            Server.close();
        }
        catch (Exception ex)
        {
        }
    }






}
