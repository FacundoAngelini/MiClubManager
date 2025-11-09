import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class Partido {
    private String fecha;
    private int entradasVendidas;
    private Estadio estadio;
    private FichaDelPartido fichaDelPartido;
    private ValorEntradas valorEntrada;
    private int EntradasDadasSocio;

    public Partido(String fecha, int entradasVendidas, Estadio estadio, FichaDelPartido fichaDelPartido, ValorEntradas valorEntrada, int entradasDadasSocio) {
        this.fecha = fecha;
        this.entradasVendidas = entradasVendidas;
        this.estadio = estadio;
        this.fichaDelPartido = fichaDelPartido;
        this.valorEntrada = valorEntrada;
        EntradasDadasSocio = entradasDadasSocio;
    }

    public double obtenerGanancia(Estadio estadioDelClub) {
        if (this.estadio.equals(estadioDelClub)) {
            return valorEntrada.getPrecio() * (entradasVendidas - EntradasDadasSocio);
        } else {
            return 0;
        }
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEntradasDadasSocio() {
        return EntradasDadasSocio;
    }

    public void setEntradasDadasSocio(int entradasDadasSocio) {
        EntradasDadasSocio = entradasDadasSocio;
    }

    public ValorEntradas getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(ValorEntradas valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public FichaDelPartido getFichaDelPartido() {
        return fichaDelPartido;
    }

    public void setFichaDelPartido(FichaDelPartido fichaDelPartido) {
        this.fichaDelPartido = fichaDelPartido;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    public void setEntradasVendidas(int entradasVendidas) {
        this.entradasVendidas = entradasVendidas;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("fecha", fecha);
        obj.put("entradasVendidas", entradasVendidas);
        obj.put("entradasDadasSocio", EntradasDadasSocio);
        obj.put("estadio", estadio.getNombre());
        obj.put("valorEntrada", valorEntrada.getPrecio());


        FichaDelPartido ficha = this.fichaDelPartido;
        JSONObject objFicha = new JSONObject();
        objFicha.put("golesLocal", ficha.getGolesLocal());
        objFicha.put("golesVisitante", ficha.getGolesVisitante());

        JSONArray arrayGoles = new JSONArray();
        for (Gol gol : ficha.getGoles()) {
            JSONObject objGol = new JSONObject();
            objGol.put("jugador", gol.getJugador().getNombre() + " " + gol.getJugador().getApellido());
            objGol.put("fueGolLocal", gol.isFueGolLocal());
            objGol.put("minuto", gol.getMinuto());
            arrayGoles.put(objGol);
        }
        objFicha.put("goles", arrayGoles);

        JSONArray arrayTarjetas = new JSONArray();
        for (TarjetaAplicada tarjeta : ficha.getTarjetas()) {
            JSONObject objTarjeta = new JSONObject();
            objTarjeta.put("jugador", tarjeta.getJugador().getNombre() + " " + tarjeta.getJugador().getApellido());
            objTarjeta.put("tipoTarjeta", tarjeta.getTipoTarjeta().toString());
            objTarjeta.put("minuto", tarjeta.getMinuto());
            arrayTarjetas.put(objTarjeta);
        }
        objFicha.put("tarjetas", arrayTarjetas);

        JSONArray arrayLesionados = new JSONArray();
        for (Jugador lesionado : ficha.getLesionados()) {
            JSONObject objLesionado = new JSONObject();
            objLesionado.put("nombre", lesionado.getNombre() + " " + lesionado.getApellido());
            objLesionado.put("numeroCamiseta", lesionado.getNumeroCamiseta());
            arrayLesionados.put(objLesionado);
        }
        objFicha.put("lesionados", arrayLesionados);

        obj.put("fichaDelPartido", objFicha);

        return obj;
    }


    @Override
    public String toString() {
        return "Partido{" +
                "fecha='" + fecha + '\'' +
                ", entradasVendidas=" + entradasVendidas +
                ", estadio=" + estadio +
                ", fichaDelPartido=" + fichaDelPartido +
                ", valorEntrada=" + valorEntrada +
                ", EntradasDadasSocio=" + EntradasDadasSocio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Partido partido = (Partido) o;
        return Objects.equals(fecha, partido.fecha) && Objects.equals(estadio, partido.estadio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, estadio);
    }
}
