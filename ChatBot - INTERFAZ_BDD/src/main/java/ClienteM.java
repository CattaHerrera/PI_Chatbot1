public class ClienteM {

    private String nombre;
    private String telefono;
    private String correo;
    private String motivo;
    private String mensaje;
    private String saludo;
    private String ayuda;
    private String infochatbot;

    public String getSaludo() {
        return saludo;
    }

    public void setSaludo(String saludo) {
        this.saludo = saludo;
    }

    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }

    public String getInfochatbot() {
        return infochatbot;
    }

    public void setInfochatbot(String infochatbot) {
        this.infochatbot = infochatbot;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ClienteM() {
    }

    public ClienteM(String nombre, String telefono, String correo, String motivo, String mensaje) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.motivo = motivo;
        this.mensaje = mensaje;
    }



}
