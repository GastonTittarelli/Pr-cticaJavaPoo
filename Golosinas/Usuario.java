package Golosinas;
public class Usuario {
    private int id;
    private String nombre;

    public Usuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}


class EstadoPedido {
    private String nombre;

    public EstadoPedido(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}


class Pedido {
    private int id;
    private EstadoPedido estado;
    // private Usuario usuario;

    public Pedido(int id, Usuario usuario) {
        this.id = id;
        // this.usuario = usuario;
        this.estado = new EstadoPedido("Borrador"); // Estado inicial
    }

    public int getId() {
        return id;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public void enviarAprobacion() {
        actualizarEstado("Borrador", "Aprobación Pendiente");
    }

    public void aprobar() {
        actualizarEstado("Aprobación Pendiente", "Aprobado");
    }

    public void rechazar() {
        actualizarEstado("Aprobación Pendiente", "Rechazado");
    }

    private void actualizarEstado(String estadoActual, String nuevoEstado) {
        if (estado.getNombre().equals(estadoActual)) {
            estado = new EstadoPedido(nuevoEstado);
        } else {
            System.out.println("El pedido no se encuentra en el estado adecuado para realizar esta acción.");
        }
    }

    public boolean comprobarRestriccionFlujo(EstadoPedido nuevoEstado) {
        String estadoActual = estado.getNombre();

        // Verifica si el nuevo estado es anterior al estado actual
        if (nuevoEstado.getNombre().equals("Borrador") ||
            (estadoActual.equals("Aprobación Pendiente") && nuevoEstado.getNombre().equals("Borrador")) ||
            (estadoActual.equals("Aprobado") && !nuevoEstado.getNombre().equals("Rechazado"))) {
            System.out.println("Error: No se puede cambiar al estado " + nuevoEstado.getNombre());
            return false;
        }

        return true;
    }

}


class Main {
    public static void main(String[] args) {
        Usuario usuario = new Usuario(1, "NombreUsuario");
        Pedido pedido = new Pedido(1, usuario);
        
        EstadoPedido nuevoEstado = new EstadoPedido("Aprobado");

        // Verifica si es posible cambiar al estado nuevo 
        if (pedido.comprobarRestriccionFlujo(nuevoEstado)) {

            pedido.aprobar();
        }
    }
}

