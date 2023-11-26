package Objetos;
import java.text.DecimalFormat;
/**
 * La clase Main es la clase principal que contiene el método main para probar
 * las clases VentasLocal, VentasEntrega y VentasRetirar.
 */
public class AnalisisVentas {

    /**
     * Método principal que prueba las clases VentasLocal, VentasEntrega y VentasRetirar.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en este ejemplo).
     */
    public static void main(String[] args) {
        // Rutas de los archivos CSV
        String rutaCSVLocal = "C:\\Users\\josue\\Downloads\\ventas_en_local.csv";
        String rutaCSVEntrega = "C:\\Users\\josue\\Downloads\\ventas_con_entrega.csv";
        String rutaCSVRetirar = "C:\\Users\\josue\\Downloads\\ventas_para_retirar.csv";



        // Crear instancias de las clases VentasLocal, VentasEntrega y VentasRetirar
        VentasLocal ventasLocal = new VentasLocal();
        VentasEntrega ventasEntrega = new VentasEntrega();
        VentasRetirar ventasRetirar = new VentasRetirar();

        // Calcular totales para cada tipo de venta
        VentasLocal.CalcularTotal(rutaCSVLocal, ventasLocal);
        VentasEntrega.CalcularTotal(rutaCSVEntrega, ventasEntrega);
        VentasRetirar.CalcularTotal(rutaCSVRetirar, ventasRetirar);



        // Obtener totales de ventas
        double totalVentasRetirar = ventasRetirar.getTotalVentasRetirar();
        double totalVentasLocal = ventasLocal.getTotalVentasLocal();
        double totalVentasEntrega = ventasEntrega.getTotalVentasEntrega();

        // Funcion encargada de evitar tantos decimales
        DecimalFormat df = new DecimalFormat("#.##");
        String totalVentasLocalFormateado = df.format(totalVentasLocal);
        String totalVentasEntregaFormateado = df.format(totalVentasEntrega);
        String totalVentasRetirarFormateado = df.format(totalVentasRetirar);

        
        double totalfinal = totalVentasLocal + totalVentasRetirar + totalVentasEntrega;
        System.out.println("\nTotal de ventas: " + totalfinal);
    }
}
