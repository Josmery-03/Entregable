package entregable3;

import java.util.Scanner;

public class Menu {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        SistemaPedidos sistema = new SistemaPedidos();

        // CARGAR DATOS AL INICIAR
        sistema.cargarProductos();
        sistema.cargarClientes();
        sistema.cargarPedidos();

        // INICIAR HILOS
        ProcesadorPedidos procesador = new ProcesadorPedidos(sistema);
        GeneradorReportes reporte = new GeneradorReportes(sistema);

        procesador.start();
        reporte.start();

        int op;

        do{
            System.out.println("\n----SISTEMA DE PRODUCTOS----");
            System.out.println("1. Registrar producto");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Crear pedido");
            System.out.println("4. Confirmar pedido");
            System.out.println("5. Listar productos");
            System.out.println("6. Listar pedidos");
            System.out.println("8. Guardar sistema manualmente");
            System.out.println("9. Cargar sistema desde archivos");
            System.out.println("10. Generar reporte del sistema");
            System.out.println("11. Ver estado de procesamiento");
            System.out.println("0. Salir");

            op = sc.nextInt();

            try{

                switch(op){

                    case 1:
                        System.out.print("ID: ");
                        int idp=sc.nextInt(); sc.nextLine();
                        System.out.print("Nombre: ");
                        String np=sc.nextLine();
                        System.out.print("Precio: ");
                        double pr=sc.nextDouble();
                        System.out.print("Stock: ");
                        int st=sc.nextInt();
                        sistema.registrarProducto(idp,np,pr,st);
                        break;

                    case 2:
                        System.out.print("ID: ");
                        int idc=sc.nextInt(); sc.nextLine();
                        System.out.print("Nombre: ");
                        String nc=sc.nextLine();
                        System.out.print("VIP? (1=si 0=no): ");
                        boolean vip=sc.nextInt()==1;
                        sistema.registrarCliente(idc,nc,vip);
                        break;

                    case 3:
                        System.out.print("ID Pedido: ");
                        int idped=sc.nextInt();
                        System.out.print("ID Cliente: ");
                        int idcli=sc.nextInt();
                        sistema.crearPedido(idped,idcli);
                        break;

                    case 4:
                        System.out.print("ID Pedido: ");
                        sistema.confirmarPedido(sc.nextInt());
                        break;

                    case 5:
                        sistema.listarProductos();
                        break;

                    case 6:
                        sistema.listarPedidos();
                        break;

                    case 8:
                        sistema.guardarProductos();
                        sistema.guardarClientes();
                        sistema.guardarPedidos();
                        System.out.println("Sistema guardado manualmente.");
                        break;

                    case 9:
                        sistema.cargarProductos();
                        sistema.cargarClientes();
                        sistema.cargarPedidos();
                        System.out.println("Sistema cargado.");
                        break;

                    case 10:
                        sistema.generarReporte();
                        System.out.println("Reporte generado manualmente.");
                        break;

                    case 11:
                        int pendientes = 0;
                        for(Pedido p : sistema.getPedidos())
                            if(p.getEstado().equals("CONFIRMADO"))
                                pendientes++;
                        System.out.println("Pedidos pendientes de procesar: "+pendientes);
                        break;
                }

            }catch(Exception e){
                System.out.println("ERROR: "+e.getMessage());
            }

        }while(op!=0);

        procesador.detener();

        sistema.guardarProductos();
        sistema.guardarClientes();
        sistema.guardarPedidos();

        System.out.println("Sistema finalizado.");
    }
}