package Objetos;
/**
 * La clase Main es la clase principal que contiene el método main para probar
 * las clases VentasLocal, VentasEntrega y VentasRetirar.
 */
public class Main {

    /**
     * Método principal que prueba las clases VentasLocal, VentasEntrega y VentasRetirar.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en este ejemplo).
     */
    public static void main(String[] args) {
        // Rutas de los archivos CSV
        String rutaCSVLocal = "C:\\Users\\josue\\OneDrive\\locales.csv";
        String rutaCSVEntrega = "";  // Reemplaza con la ruta real
        String rutaCSVRetirar = "";  // Reemplaza con la ruta real

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

        // Imprimir resultados
        System.out.println("\nEl total de ventas locales es: " + totalVentasLocal);
        System.out.println("\nEl total de ventas de entrega es: " + totalVentasEntrega);
        System.out.println("\nEl total de ventas a retirar es: " + totalVentasRetirar);
    }
}
