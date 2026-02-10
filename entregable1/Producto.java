public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;

        // Validar que "Precio" sea mayor que 0

        if (precio > 0) {
            this.precio = precio;
        } else {
            this.precio = 1;
        }

        //  Validar que "Stock" no sea negativo

        if (stock >= 0) {
            this.stock = stock;
        } else {
            this.stock = 0;
        }
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    // Metodos de apoyo
    public void disminuirStock(int cantidad) {
        stock -= cantidad;
    }

    public void aumentarStock(int cantidad) {
        stock += cantidad;
    }

    @Override
    public String toString() {
        return "ID: " + id +
               " Nombre: " + nombre +
               " Precio: " + precio +
               " Stock: " + stock;
    }
}
