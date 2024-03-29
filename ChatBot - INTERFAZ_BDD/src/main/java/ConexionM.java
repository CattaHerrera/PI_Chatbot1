import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class ConexionM {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;


    // Método para crear la conexión con la base de datos
    public void crearConexion() {
        mongoClient = MongoClients.create(
                "mongodb+srv://oluna1103:4LhDHtpTduRAnG83@clusterchatbot.iq5067f.mongodb.net/?retryWrites=true&w=majority");
        database = mongoClient.getDatabase("chatbot");
        collection = database.getCollection("clientes");
    }

    // Método para insertar un nuevo cliente en la base de datos
    public void insertar(ClienteM cliente) {
        try {
            // Obtener la dirección IP del dispositivo
            InetAddress direccionIP = InetAddress.getLocalHost();
            String direccionIPStr = direccionIP.getHostAddress();

            // Obtener la información del dispositivo
            String nombreDispositivo = System.getProperty("os.name");
            String modeloDispositivo = System.getProperty("os.arch");
            String versionDispositivo = System.getProperty("os.version");

            // Crear el documento a insertar en la base de datos
            Date fecha = new Date();
            Document doc = new Document("nombre", cliente.getNombre())
                    .append("telefono", cliente.getTelefono())
                    .append("correo", cliente.getCorreo())
                    .append("motivo", cliente.getMotivo())
                    .append("mensaje", cliente.getMensaje())
                    .append("fecha", fecha)
                    .append("direccionIP", direccionIPStr)
                    .append("nombreSistemaOperativo", nombreDispositivo)
                    .append("modeloDispositivo", modeloDispositivo)
                    .append("versionDispositivo", versionDispositivo);

            // Insertar el documento en la base de datos
            collection.insertOne(doc);
        } catch (UnknownHostException ex) {
            System.err.println("No se pudo obtener la información del dispositivo: " + ex.getMessage());
        }
    }

    // Método para cerrar la conexión con la base de datos
    public void cerrarConexion() {
        mongoClient.close();
    }
}
