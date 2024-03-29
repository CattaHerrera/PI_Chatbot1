package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class ChatClientGUI extends JFrame implements ActionListener {

    private JTextField inputField;
    private JTextPane chatArea;
    private PrintWriter socketWriter;
    private SimpleAttributeSet userMessageStyle;
    private SimpleAttributeSet serverMessageStyle;

    public ChatClientGUI() {
        super("Chat Client");

        // Cambiar el color de fondo de la ventana
        getContentPane().setBackground(new Color(0x4B021C));

        // Cambiar el estilo de la interfaz gráfica
        try {
            URL iconURL = new URL("https://img.icons8.com/?size=1x&id=6nsw3h9gk8M8&format=png");
            Image iconImage = ImageIO.read(iconURL);
            setIconImage(iconImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crear la interfaz gráfica
        JPanel mainPanel = new JPanel(new BorderLayout());

        chatArea = new JTextPane();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 16));

        // Estilos para los mensajes
        userMessageStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(userMessageStyle, Color.WHITE);
        StyleConstants.setBackground(userMessageStyle, new Color(0x546E7A));

        serverMessageStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(serverMessageStyle, Color.BLACK);
        StyleConstants.setBackground(serverMessageStyle, new Color(0xE5861B));

        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        mainPanel.add(chatScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());

        inputField = new JTextField();
        inputField.addActionListener(this);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setBackground(new Color(0x8EA6A4)); // Cambiar el color de fondo del campo de entrada
        inputField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Agregar un margen interno
        inputPanel.add(inputField, BorderLayout.CENTER);

        JButton sendButton = new JButton("ENVIAR");
        sendButton.addActionListener(this);
        sendButton.setBackground(new Color(0x009FEE)); // Cambiar el color del botón
        sendButton.setForeground(new Color(0xEEEEEE)); // Cambiar el color del texto del botón
        sendButton.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar el tipo de letra y tamaño
        sendButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Agregar un margen interno
        inputPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // Configurar la ventana principal
        setContentPane(mainPanel);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Conectar al servidor
        try {
            byte[] ipAddr = new byte[]{127, 0, 0, 1};
            InetAddress ip = InetAddress.getByAddress(ipAddr);
            Socket socket = new Socket(ip, 8887);

            BufferedReader socketReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            socketWriter = new PrintWriter(socket.getOutputStream(), true);

            // Crear un hilo para leer mensajes del servidor
            new Thread(() -> {
                String message;
                try {
                    while ((message = socketReader.readLine()) != null) {
                        showMessage(message, false); // Mostrar el mensaje del servidor resaltado
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMessage(String message, boolean isUser) {
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        StyledDocument document = chatArea.getStyledDocument();

        try {
            SimpleAttributeSet messageStyle = isUser ? userMessageStyle : serverMessageStyle;
            document.insertString(document.getLength(), message + "\n\n", messageStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = inputField.getText();
        showMessage("Tú: " + input, true); // Mostrar el mensaje del usuario resaltado
        socketWriter.println(input);
        inputField.setText("");
    }

    public static void main(String[] args) {
        new ChatClientGUI();
    }
}
