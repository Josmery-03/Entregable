public class Cliente {
    private int id;
    private String nombre;


    public Cliente( int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

   // Getters

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
   
   // Metodo "calcularDescuento"

   public double calcularDescuento(double subtotal){
    return 0;
   }

   @Override
    public String toString() {
        return "ID: " + id + " Nombre: " + nombre;
    }
    
}
