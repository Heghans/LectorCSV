package Objetos;

public class Ventas {

    private double Fecha;
    private double TotalVenta;
    private double Costo;
    private double Impuesto;
    private double Envio;
    private double Empaque;
    private double Entrega;

    public Ventas(){
        this.Fecha = 0.0;
        this.Empaque = 0.0;
        this.TotalVenta = 0.0;
        this.Entrega = 0.0;
        this.Envio = 0.0;
        this.Impuesto = 0.0;
        this.Costo = 0.0;
    }

    public double getFecha() {
        return Fecha;
    }

    public double getTotalVenta() {
        return TotalVenta;
    }

    public double getCosto() {
        return Costo;
    }

    public double getImpuesto() {
        return Impuesto;
    }

    public double getEnvio() {
        return Envio;
    }

    public double getEmpaque() {
        return Empaque;
    }

    public double getEntrega() {
        return Entrega;
    }
}
