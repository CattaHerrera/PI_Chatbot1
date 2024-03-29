import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java KKMultiServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        //boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                new ChatbotSocket(serverSocket.accept()).start(); //se instacia la segunda clase y se pasan por argumento las solicitudes de los clientes, lo que activa los hilos ocn .start()
                /*la línea de arriba se puede hacer también de la siguiente manera:

                Socket clientSocket = serverSocket.accept();
                EchoMultiServerThread hilo = new EchoMultiServerThread(clienteSocket);
                hilo.start();

                 */
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}