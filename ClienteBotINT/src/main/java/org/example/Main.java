package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    //crea un hilo para leer los mensajes del socket
    public static void leerMensaje(BufferedReader entradaSocket){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        String entrada = null;
                        while (true){
                            try {
                                entrada = entradaSocket.readLine();
                                System.out.println(entrada);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                }).start();
    }

    //lee los mensajes que se escriben por consola y los envía al socket a través del objeto
    public static void escribirMensajes(Scanner consola, PrintWriter salidaSocket){
        ;//leer lo de la consola de lo que escribimos en ella
        String lectura;

        while (true) {
            lectura = consola.nextLine();
            salidaSocket.println(lectura);
        }
    }
    //crea una conexión de socket y llama a estas dos funciones
    //para que se ejecuten en hilos separados.
    public static void main(String[] args) {
        byte[] ipAddr = new byte[]{127, 0, 0, 1}; //dirección ip dentro de un arreglo
        InetAddress ip = null;

        try {
            ip = InetAddress.getByAddress(ipAddr); //convertir el arreglo a una IP, se pasa el arreglo dentro del parentesis
            Socket socket = new Socket(ip, 8887); //se crea un socket, con está dirección ip y puerto remoto el puerto 9001 es porque está en internet

            BufferedReader entradaSocket = new BufferedReader( //lee texto de un flujo de caracteres de entrada y estructuras
                    new InputStreamReader(socket.getInputStream()) //está pasando como argumento un objeto que está creando a la instancia de BufferReader
            );       //InputStreamReader = Representa un puente entre caracteres y byte, está lee los bytes y los decodifica.

            PrintWriter salidaSocket = new PrintWriter(socket.getOutputStream(), true);//

            Scanner consola;
            consola = new Scanner(System.in);

            leerMensaje(entradaSocket);//INVOCACIÓN
            escribirMensajes(consola, salidaSocket); //INVOCACIÓN

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

