
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import java.io.File;

public class ChatbotV2 {

    private static final boolean TRACE_MODE = false;
    static String botName = "super";

    Bot bot;
    Chat chatSession;
    ClienteM cliente;
    ConsultasBD consultas;

    public ChatbotV2(ChatbotSocket sock) {
        String resourcesPath = getResourcesPath();
        System.out.println(resourcesPath);
        MagicBooleans.trace_mode = TRACE_MODE;
        bot = new Bot("super", getResourcesPath());
        chatSession = new Chat(bot);
        cliente = new ClienteM();
        bot.brain.nodeStats();
        
        //
        consultas = new ConsultasBD(sock);
    }

    public String getResponse(String textLine) {
        String response = "";
        while (response.equals("")) {
            
            //Iniciar conexión a la base de datos y de la colección Consultas
            consultas.conectar();
            
            String request = textLine;
            if (MagicBooleans.trace_mode)
                System.out.println(
                        "STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
                                + ":TOPIC=" + chatSession.predicates.get("topic"));
            response = chatSession.multisentenceRespond(request);
            while (response.contains("&lt;"))
                response = response.replace("&lt;", "<");
            while (response.contains("&gt;"))
                response = response.replace("&gt;", ">");
            
            
            //Implementación de las consultas
            cliente.setSaludo(chatSession.predicates.get("saludo"));
            cliente.setAyuda(chatSession.predicates.get("ayuda"));
            cliente.setInfochatbot(chatSession.predicates.get("infochatbot"));

            if(!cliente.getSaludo().equals("unknown")){

                consultas.saludos(cliente);
                //chatbotSocket.setSalida(consultas.saludos(cliente));
                //ejecutarSaludo = true;
                cliente.setSaludo(chatSession.predicates.replace("saludo", "unknown"));
            }
            if(!cliente.getAyuda().equals("unknown")){
                consultas.ayuda(cliente);
                //ejecutarAyuda = true;
                cliente.setAyuda(chatSession.predicates.replace("ayuda", "unknown"));
            }
            if(!cliente.getInfochatbot().equals("unknown")){
                consultas.InfoChat(cliente);
                cliente.setInfochatbot(chatSession.predicates.replace("infochatbot", "unknown"));
            }
            consultas.desconectar();

            
            
            // lo nuevo que se implemento en la clase del profe colombo
            System.out.println(chatSession.predicates.get("nombre"));
            System.out.println(chatSession.predicates.get("telefono"));
            System.out.println(chatSession.predicates.get("correo"));
            System.out.println(chatSession.predicates.get("motivo"));
            System.out.println(chatSession.predicates.get("mensaje"));

            cliente.setNombre(chatSession.predicates.get("nombre"));
            cliente.setTelefono(chatSession.predicates.get("telefono"));
            cliente.setCorreo(chatSession.predicates.get("correo"));
            cliente.setMotivo(chatSession.predicates.get("motivo"));
            cliente.setMensaje(chatSession.predicates.get("mensaje"));

                /*if(cliente.getNombre() != null && cliente.getTelefono() != null && cliente.getMotivo() != null){
                    ConexionM conexion = new ConexionM();
                    conexion.crearConexion();
                    conexion.insertar(cliente);
                    conexion.cerrarConexion();
                }*/

            if(!cliente.getNombre().equals("unknown") && !cliente.getTelefono().equals("unknown") &&
                    !cliente.getCorreo().equals("unknown")&&!cliente.getMotivo().equals("unknown") &&
                    !cliente.getMensaje().equals("unknown") ){
                ConexionM conexion = new ConexionM();
                conexion.crearConexion();
                conexion.insertar(cliente);
                conexion.cerrarConexion();
            }

            /*ConexionM conexion = new ConexionM();
            conexion.crearConexion();
            conexion.insertar(cliente);
            conexion.cerrarConexion();*/
        }
        return response;
    }

    // devuelve la ruta donde se encuentran los recursos del bot
    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }
}
