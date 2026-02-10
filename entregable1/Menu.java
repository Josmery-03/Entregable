import java.util.Scanner;

public class Menu {

    static Producto[] productos = new Producto[20];
    static Cliente[] clientes = new Cliente[20];
    static Pedido[] pedidos = new Pedido[20];

    static int cp = 0, cc = 0, cped = 0;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int op;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Registrar producto");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Crear pedido");
            System.out.println("4. Agregar producto a pedido");
            System.out.println("5. Ver detalle pedido");
            System.out.println("6. Listar productos");
            System.out.println("7. Listar pedidos");
            System.out.println("8. Cambiar estado pedido");
            System.out.println("0. Salir");

            op = sc.nextInt();

            switch (op) {

                case 1:
                    registrarProducto(sc);
                    break;

                case 2:
                    registrarCliente(sc);
                    break;

                case 3:
                    crearPedido(sc);
                    break;

                case 4:
                    agregarProductoPedido(sc);
                    break;

                case 5:
                    verDetalle(sc);
                    break;

                case 6:
                    listarProductos();
                    break;

                case 7:
                    listarPedidos();
                    break;

                case 8:
                    cambiarEstado(sc);
                    break;
            }

        } while (op != 0);
    }

    // Registrar producto

    static void registrarProducto(Scanner sc) {

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Precio: ");
        double precio = sc.nextDouble();

        System.out.print("Stock: ");
        int stock = sc.nextInt();

        productos[cp++] = new Producto(id, nombre, precio, stock);
    }

    // Registrar cliente
    
    static void registrarCliente(Scanner sc) {

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("1-Regular 2-VIP: ");
        int tipo = sc.nextInt();

        if (tipo == 1) {
            clientes[cc++] = new ClienteRegular(id, nombre);
        } else {
            clientes[cc++] = new ClienteVIP(id, nombre, 10);
        }
    }

    // Crear pedido

    static void crearPedido(Scanner sc) {

        System.out.print("ID Pedido: ");
        int id = sc.nextInt();

        System.out.print("ID Cliente: ");
        int idc = sc.nextInt();

        for (int i = 0; i < cc; i++) {
            if (clientes[i].getId() == idc) {
                pedidos[cped++] = new Pedido(id, clientes[i]);
            }
        }
    }

    // Agregar producto pedido

    static void agregarProductoPedido(Scanner sc) {

        System.out.print("ID Pedido: ");
        int idp = sc.nextInt();

        System.out.print("ID Producto: ");
        int idprod = sc.nextInt();

        System.out.print("Cantidad: ");
        int cant = sc.nextInt();

        Pedido pedido = null;
        Producto prod = null;

        for (int i = 0; i < cped; i++) {
            if (pedidos[i].getId() == idp) {
                pedido = pedidos[i];
            }
        }

        for (int i = 0; i < cp; i++) {
            if (productos[i].getId() == idprod) {
                prod = productos[i];
            }
        }

        if (pedido != null && prod != null &&
            prod.getStock() >= cant) {

            pedido.agregarProducto(prod, cant);
        }
    }

    // Mostrar detalles

    static void verDetalle(Scanner sc) {

        System.out.print("ID Pedido: ");
        int id = sc.nextInt();

        for (int i = 0; i < cped; i++) {
            if (pedidos[i].getId() == id) {
                pedidos[i].mostrarDetalle();
            }
        }
    }

    // Listar productos

    static void listarProductos() {
        for (int i = 0; i < cp; i++) {
            System.out.println(productos[i]);
        }
    }

    // Listar pedidos

    static void listarPedidos() {
        for (int i = 0; i < cped; i++) {
            System.out.println("Pedido: " +
                    pedidos[i].getId() +
                    " Estado: " +
                    pedidos[i].getEstado());
        }
    }

    // Cambiar estado

    static void cambiarEstado(Scanner sc) {

        System.out.print("ID Pedido: ");
        int id = sc.nextInt();

        System.out.print("1-Confirmar 2-Cancelar: ");
        int op = sc.nextInt();

        for (int i = 0; i < cped; i++) {

            if (pedidos[i].getId() == id) {

                if (op == 1) {
                    pedidos[i].confirmarPedido();
                } else {
                    pedidos[i].cancelarPedido();
                }
            }
        }
    }
}
