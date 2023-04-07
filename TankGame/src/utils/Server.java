package utils;

import java.net.*;
import java.io.*;
 
public class Server
{
    //initialize socket and input stream
    Socket          socket   = null;
    ServerSocket    server   = null;
    DataInputStream in       =  null;
    DataOutputStream out = null;
    String line = "START";
    
    
    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");
 
            System.out.println("Waiting for a client ...");
 
            socket = server.accept();
            System.out.println("Client accepted");
 
            // takes input from the client socket
            in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
 
            out = new DataOutputStream(System.out);
 
            // reads message from client until "Over" is sent
//            while (!line.equals("Over"))
//            {
//                try
//                {
//                    line = in.readUTF();
// 
//                }
//                catch(IOException i)
//                {
//                    System.out.println(i);
//                }
//            }
//            System.out.println("Closing connection");
 
            // close connection
//            socket.close();
//            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
 
    public static void main(String args[])
    {
        Server server = new Server(5002);
    }
}
