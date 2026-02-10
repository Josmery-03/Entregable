public class Pedido {

    private int id;
    private Cliente cliente;
    private String estado;
    private DetallePedido[] detalles;
    private int contadorDetalles;

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.estado = "BORRADOR";
        this.detalles = new DetallePedido[10];
        this.contadorDetalles = 0;
    }

    public int getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    // Agregar productos
    public void agregarProducto(Producto producto, int cantidad) {

        if (estado.equals("BORRADOR")) {
            detalles[contadorDetalles] =
                    new DetallePedido(producto, cantidad);
            contadorDetalles++;
        } else {
            System.out.println("No se pueden agregar productos.");
        }
    }

    // Subtotal
    public double calcularSubtotal() {
        double subtotal = 0;

        for (int i = 0; i < contadorDetalles; i++) {
            subtotal += detalles[i].calcularSubtotal();
        }

        return subtotal;
    }

    // Descuento polimorfico
    public double calcularDescuento() {
        return cliente.calcularDescuento(calcularSubtotal());
    }

    // Total final
    public double calcularTotal() {
        return calcularSubtotal() - calcularDescuento();
    }

    // CONFIRMADO
    public void confirmarPedido() {

        if (contadorDetalles == 0) {
            System.out.println("No se puede confirmar sin productos.");
            return;
        }

        estado = "CONFIRMADO";

        for (int i = 0; i < contadorDetalles; i++) {
            Producto p = detalles[i].getProducto();
            p.disminuirStock(detalles[i].getCantidad());
        }
    }

    // CANCELADO

    public void cancelarPedido() {

        if (estado.equals("CONFIRMADO")) {

            for (int i = 0; i < contadorDetalles; i++) {
                Producto p = detalles[i].getProducto();
                p.aumentarStock(detalles[i].getCantidad());
            }
        }

        estado = "CANCELADO";
    }

    // Mostrar detalle
    public void mostrarDetalle() {

        System.out.println("Pedido ID: " + id);
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Estado: " + estado);

        for (int i = 0; i < contadorDetalles; i++) {
            System.out.println(detalles[i]);
        }

        System.out.println("Subtotal: " + calcularSubtotal());
        System.out.println("Descuento: " + calcularDescuento());
        System.out.println("Total: " + calcularTotal());
    }
}

