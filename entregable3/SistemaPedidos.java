package entregable3;
import java.io.*;
import java.util.*;

public class SistemaPedidos {

    private List<Producto> productos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();

    // GETTERS

    public synchronized List<Pedido> getPedidos(){ return pedidos; }
    public synchronized List<Producto> getProductos(){ return productos; }
    public synchronized List<Cliente> getClientes(){ return clientes; }

    // BUSQUEDAS

    private Producto buscarProducto(int id){
        for(Producto p : productos)
            if(p.getId()==id) return p;
        return null;
    }

    private Cliente buscarCliente(int id){
        for(Cliente c : clientes)
            if(c.getId()==id) return c;
        return null;
    }

    private Pedido buscarPedido(int id){
        for(Pedido p : pedidos)
            if(p.getId()==id) return p;
        return null;
    }

    // REGISTROS

    public synchronized void registrarProducto(int id,String nombre,double precio,int stock){
        if(buscarProducto(id)!=null){
            System.out.println("Producto duplicado");
            return;
        }
        productos.add(new Producto(id,nombre,precio,stock));
        guardarProductos();
    }

    public synchronized void registrarCliente(int id,String nombre,boolean vip){
        if(buscarCliente(id)!=null){
            System.out.println("Cliente duplicado");
            return;
        }

        if(vip)
            clientes.add(new ClienteVIP(id,nombre,10));
        else
            clientes.add(new ClienteRegular(id,nombre));

        guardarClientes();
    }

    public synchronized void crearPedido(int idPedido,int idCliente) throws PedidoInvalidoException{
        Cliente c = buscarCliente(idCliente);
        if(c==null)
            throw new PedidoInvalidoException("Cliente no existe");

        pedidos.add(new Pedido(idPedido,c));
        guardarPedidos();
    }

    public synchronized void confirmarPedido(int id) throws Exception{
        Pedido p = buscarPedido(id);
        if(p==null) throw new PedidoInvalidoException("Pedido no encontrado");

        p.confirmarPedido();
        guardarPedidos();
        guardarProductos();
    }

    // PRODUCTOS

    public synchronized void guardarProductos(){
        try(DataOutputStream out = new DataOutputStream(new FileOutputStream("productos.dat"))){
            for(Producto p : productos){
                out.writeInt(p.getId());
                out.writeUTF(p.getNombre());
                out.writeDouble(p.getPrecio());
                out.writeInt(p.getStock());
            }
        }catch(IOException e){
            System.out.println("Error guardando productos");
        }
    }

    public synchronized void cargarProductos(){
        File f = new File("productos.dat");
        if(!f.exists()) return;

        try(DataInputStream in = new DataInputStream(new FileInputStream(f))){
            while(true){
                productos.add(new Producto(
                        in.readInt(),
                        in.readUTF(),
                        in.readDouble(),
                        in.readInt()));
            }
        }catch(EOFException e){
        }catch(IOException e){
            System.out.println("Error cargando productos");
        }
    }

    // CLIENTES

    public synchronized void guardarClientes(){
        try(DataOutputStream out = new DataOutputStream(new FileOutputStream("clientes.dat"))){
            for(Cliente c : clientes){
                out.writeInt(c.getId());
                out.writeUTF(c.getNombre());
            }
        }catch(IOException e){
            System.out.println("Error guardando clientes");
        }
    }

    public synchronized void cargarClientes(){
        File f = new File("clientes.dat");
        if(!f.exists()) return;

        try(DataInputStream in = new DataInputStream(new FileInputStream(f))){
            while(true){
                clientes.add(new ClienteRegular(
                        in.readInt(),
                        in.readUTF()));
            }
        }catch(EOFException e){
        }catch(IOException e){
            System.out.println("Error cargando clientes");
        }
    }

    // PEDIDOS
  
    public synchronized void guardarPedidos(){
        try(PrintWriter out = new PrintWriter(new FileWriter("pedidos.txt"))){
            for(Pedido p : pedidos){
                out.println(p.getId()+"|"+
                        p.getCliente().getId()+"|"+
                        p.getFechaCreacion()+"|"+
                        p.getEstado()+"|"+
                        p.calcularTotal());
            }
        }catch(IOException e){
            System.out.println("Error guardando pedidos");
        }
    }

    public synchronized void cargarPedidos(){
        File f = new File("pedidos.txt");
        if(!f.exists()) return;

        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            String linea;
            while((linea = br.readLine()) != null){
                String[] datos = linea.split("\\|");
                int id = Integer.parseInt(datos[0]);
                int idCliente = Integer.parseInt(datos[1]);
                String estado = datos[3];

                Cliente c = buscarCliente(idCliente);
                if(c != null){
                    Pedido p = new Pedido(id,c);
                    if(estado.equals("CONFIRMADO"))
                        p.procesarPedido();
                    pedidos.add(p);
                }
            }
        }catch(IOException e){
            System.out.println("Error cargando pedidos");
        }
    }

    // REPORTE COMPLETO

    public synchronized void generarReporte(){

        try(PrintWriter out = new PrintWriter(new FileWriter("reporte_sistema.txt"))){

            out.println("REPORTE DEL SISTEMA");
            out.println("Fecha: " + new Date());
            out.println("Total productos: " + productos.size());
            out.println("Total clientes: " + clientes.size());

            int borrador=0, confirmado=0, procesado=0;
            double ingresos=0;

            for(Pedido p : pedidos){
                if(p.getEstado().equals("BORRADOR")) borrador++;
                if(p.getEstado().equals("CONFIRMADO")){
                    confirmado++;
                    ingresos += p.calcularTotal();
                }
                if(p.getEstado().equals("PROCESADO")){
                    procesado++;
                    ingresos += p.calcularTotal();
                }
            }

            out.println("BORRADOR: " + borrador);
            out.println("CONFIRMADO: " + confirmado);
            out.println("PROCESADO: " + procesado);

            out.println("Productos con stock bajo (<5):");
            for(Producto p : productos){
                if(p.getStock() < 5)
                    out.println(p.getNombre()+" - Stock: "+p.getStock());
            }

            out.println("Total ingresos: " + ingresos);

        }catch(IOException e){
            System.out.println("Error generando reporte");
        }
    }

// LISTADOS

public synchronized void listarProductos(){
    for(Producto p : productos)
        System.out.println(p);
}

public synchronized void listarPedidos(){
    for(Pedido p : pedidos)
        System.out.println("Pedido " + p.getId() + " - Estado: " + p.getEstado());
}
}