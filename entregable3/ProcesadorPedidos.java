package entregable3;

public class ProcesadorPedidos extends Thread {

    private SistemaPedidos sistema;
    private boolean activo = true;

    public ProcesadorPedidos(SistemaPedidos sistema){
        this.sistema = sistema;
        setPriority(Thread.MAX_PRIORITY); 
    }

    public void detener(){
        activo = false;
    }

    @Override
    public void run(){

        while(activo){

            try{

                synchronized(sistema){

                    for(Pedido p : sistema.getPedidos()){

                        if(p.getEstado().equals("CONFIRMADO")){

                            System.out.println("Procesando pedido " + p.getId());

                            Thread.sleep(3000); 
                            p.procesarPedido();

                            sistema.guardarPedidos();
                            sistema.guardarProductos();

                            System.out.println("Pedido " + p.getId() + " PROCESADO");
                        }
                    }
                }

                Thread.sleep(2000);

            }catch(Exception e){
                System.out.println("Error en hilo procesador: " + e.getMessage());
            }
        }
    }
}
