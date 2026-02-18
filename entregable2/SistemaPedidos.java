package entregable2;

import java.util.ArrayList;
import java.util.List;

public class SistemaPedidos {

    private List<Producto> productos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();

    // BUSQUEDAS

    private Producto buscarProducto(int id){
        for(Producto p : productos)
            if(p.getId()==id)
                return p;
        return null;
    }

    private Cliente buscarCliente(int id){
        for(Cliente c : clientes)
            if(c.getId()==id)
                return c;
        return null;
    }

    private Pedido buscarPedido(int id){
        for(Pedido p : pedidos)
            if(p.getId()==id)
                return p;
        return null;
    }

    // REGISTROS

    public void registrarProducto(int id, String nombre, double precio, int stock){

        if(buscarProducto(id)!=null){
            System.out.println("Producto duplicado");
            return;
        }

        productos.add(new Producto(id,nombre,precio,stock));
    }

    public void registrarCliente(int id,String nombre,boolean vip){

        if(buscarCliente(id)!=null){
            System.out.println("Cliente duplicado");
            return;
        }

        if(vip)
            clientes.add(new ClienteVIP(id,nombre,10));
        else
            clientes.add(new ClienteRegular(id,nombre));
    }

    public void crearPedido(int idPedido,int idCliente) throws PedidoInvalidoException{

        Cliente c = buscarCliente(idCliente);
        if(c==null)
            throw new PedidoInvalidoException("Cliente no existe");

        pedidos.add(new Pedido(idPedido,c));
    }

    public void agregarProductoPedido(int idPedido,int idProducto,int cantidad)
            throws ProductoNoEncontradoException,StockInsuficienteException,PedidoInvalidoException{

        Pedido pedido = buscarPedido(idPedido);
        if(pedido==null)
            throw new PedidoInvalidoException("Pedido no encontrado");

        Producto producto = buscarProducto(idProducto);
        if(producto==null)
            throw new ProductoNoEncontradoException("Producto no existe");

        pedido.agregarProducto(producto,cantidad);
    }

    public void confirmarPedido(int idPedido)
            throws PedidoInvalidoException,StockInsuficienteException{

        Pedido p = buscarPedido(idPedido);
        if(p==null)
            throw new PedidoInvalidoException("Pedido no encontrado");

        p.confirmarPedido();
    }

    public void cancelarPedido(int idPedido) throws PedidoInvalidoException{

        Pedido p = buscarPedido(idPedido);
        if(p==null)
            throw new PedidoInvalidoException("Pedido no encontrado");

        p.cancelarPedido();
    }

    public void verDetalle(int idPedido) throws PedidoInvalidoException{

        Pedido p = buscarPedido(idPedido);
        if(p==null)
            throw new PedidoInvalidoException("Pedido no encontrado");

        p.mostrarDetalle();
    }

    public void listarProductos(){
        for(Producto p : productos)
            System.out.println(p);
    }

    public void listarPedidos(){
        for(Pedido p : pedidos)
            System.out.println("Pedido "+p.getId()+"  "+p.getEstado());
    }
}

