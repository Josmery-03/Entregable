package entregable2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {

    private int id;
    private Cliente cliente;
    private String estado;
    private List<DetallePedido> detalles;
    private Date fechaCreacion;

    public Pedido(int id, Cliente cliente){
        this.id = id;
        this.cliente = cliente;
        this.estado = "BORRADOR";
        this.detalles = new ArrayList<>();
        this.fechaCreacion = new Date();
    }

    public int getId(){ return id; }
    public String getEstado(){ return estado; }
    public Cliente getCliente(){ return cliente; }
    public Date getFechaCreacion(){ return fechaCreacion; }

    public void agregarProducto(Producto producto, int cantidad)
            throws StockInsuficienteException {

        if(!estado.equals("BORRADOR"))
            throw new IllegalStateException("El pedido no esta en borrador");

        if(producto.getStock() < cantidad)
            throw new StockInsuficienteException("Stock insuficiente");

        detalles.add(new DetallePedido(producto, cantidad));
    }

    public double calcularSubtotal(){
        double subtotal = 0;
        for(DetallePedido d : detalles)
            subtotal += d.calcularSubtotal();
        return subtotal;
    }

    public double calcularDescuento(){
        return cliente.calcularDescuento(calcularSubtotal());
    }

    public double calcularTotal(){
        return calcularSubtotal() - calcularDescuento();
    }

    public void confirmarPedido() throws PedidoInvalidoException, StockInsuficienteException {

        if(detalles.isEmpty())
            throw new PedidoInvalidoException("No se puede confirmar un pedido vacio");

        estado = "CONFIRMADO";

        for(DetallePedido d : detalles){
            d.getProducto().disminuirStock(d.getCantidad());
        }
    }

    public void cancelarPedido(){
        if(estado.equals("CONFIRMADO")){
            for(DetallePedido d : detalles){
                d.getProducto().aumentarStock(d.getCantidad());
            }
        }
        estado = "CANCELADO";
    }

    public void mostrarDetalle(){

        System.out.println("Pedido: "+id);
        System.out.println("Cliente: "+cliente.getNombre());
        System.out.println("Fecha: "+fechaCreacion);
        System.out.println("Estado: "+estado);

        for(DetallePedido d : detalles)
            System.out.println(d);

        System.out.println("Subtotal: "+calcularSubtotal());
        System.out.println("Descuento: "+calcularDescuento());
        System.out.println("Total: "+calcularTotal());
    }
}

