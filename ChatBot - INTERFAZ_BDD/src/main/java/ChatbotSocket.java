import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatbotSocket extends Thread{

    private PrintWriter salida;
    private Socket socket;
    private ChatbotV2 chatbot;

    public ChatbotSocket(Socket socket){
        super("EchoMultiServerThread");
        this.socket = socket;
        chatbot = new ChatbotV2(this);

        try {
            salida = new PrintWriter(socket.getOutputStream(), true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public PrintWriter getSalida() {
        return salida;
    }

    public void setSalida(PrintWriter salida) {
        this.salida = salida;
    }

    public void run() {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Cliente: " + inputLine);
                String response = chatbot.getResponse(inputLine);
                System.out.println("Artemisa: " + response);
                out.println(response);
            }
        } catch (IOException e) {
            System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
        }
    }
}
