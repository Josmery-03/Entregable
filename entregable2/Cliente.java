package entregable2;

public abstract class Cliente {

    private int id;
    private String nombre;

    public Cliente(int id, String nombre){
        nombre = nombre.trim();

        if(nombre.isEmpty())
            throw new IllegalArgumentException("Nombre de cliente invalido");

        this.id = id;
        this.nombre = nombre;
    }

    public int getId(){ return id; }
    public String getNombre(){ return nombre; }

    public abstract double calcularDescuento(double subtotal);

    @Override
    public String toString(){
        return "ID: "+id+" | "+nombre;
    }
}

