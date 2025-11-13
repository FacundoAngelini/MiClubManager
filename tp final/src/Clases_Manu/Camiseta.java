package Clases_Manu;

import exeptions.AccionImposible;
import org.json.JSONObject;

public class Camiseta extends Producto {
    private String sponsor;
    private int camisetasVendidas;

    public Camiseta(String nombre, String marca, int cantidad,  String sponsor) {
        super(nombre, marca, cantidad);
        this.camisetasVendidas = camisetasVendidas;
        this.sponsor = sponsor;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void cambiarSponsor(String sponsor) throws AccionImposible {
        if(this.sponsor.equals(sponsor)){
            throw new AccionImposible("El sponsor es el mismo");
        }
        this.sponsor = sponsor;
    }

    public void agregarSponsor(String sponsor) throws AccionImposible{
        if(this.sponsor!=null){
            throw new AccionImposible("Ya existe un sponsor");
        }
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    @Override
    protected String muestraDatos() {
        return "Clases_Manu.Camiseta{" +
                "sponsor='" + sponsor + '\'' +
                ", camisetasVendidas=" + camisetasVendidas +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("tipo", "Clases_Manu.Camiseta");
        obj.put("nombre", nombre);
        obj.put("marca", marca);
        obj.put("cantidad", cantidad);
        obj.put("sponsor", sponsor);
        obj.put("camisetasVendidas", camisetasVendidas);
        return obj;
    }

}