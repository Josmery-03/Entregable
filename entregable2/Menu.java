package entregable2;

import java.util.Scanner;

public class Menu {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        SistemaPedidos sistema = new SistemaPedidos();

        int op;

        do{ System.out.println("\n----SISTEMA DE PRODUCTOS----");
            System.out.println("1. Registrar producto");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Crear pedido");
            System.out.println("4. Agregar producto a pedido");
            System.out.println("5. Ver detalle pedido");
            System.out.println("6. Listar productos");
            System.out.println("7. Listar pedidos");
            System.out.println("8. Confirmar pedido");
            System.out.println("9. Cancelar pedido");
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
                        int ip=sc.nextInt();
                        System.out.print("ID Producto: ");
                        int ipr=sc.nextInt();
                        System.out.print("Cantidad: ");
                        int cant=sc.nextInt();
                        sistema.agregarProductoPedido(ip,ipr,cant);
                        break;

                    case 5:
                        System.out.print("ID Pedido: ");
                        sistema.verDetalle(sc.nextInt());
                        break;

                    case 6:
                        sistema.listarProductos();
                        break;

                    case 7:
                        sistema.listarPedidos();
                        break;

                    case 8:
                        sistema.confirmarPedido(sc.nextInt());
                        break;

                    case 9:
                        sistema.cancelarPedido(sc.nextInt());
                        break;
                }

            }catch(Exception e){
                System.out.println("ERROR: "+e.getMessage());
            }

        } while(op!=0);
    }
}
