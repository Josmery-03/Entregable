package entregable3;

public class GeneradorReportes extends Thread {

    private SistemaPedidos sistema;

    public GeneradorReportes(SistemaPedidos sistema){
        this.sistema = sistema;
        setDaemon(true); 
    }

    @Override
    public void run(){

        while(true){

            try{

                Thread.sleep(10000); 

                synchronized(sistema){
                    sistema.generarReporte();
                }

                System.out.println("Reporte generado automaticamente.");

            }catch(Exception e){
                System.out.println("Error en hilo reporte: " + e.getMessage());
            }
        }
    }
}
