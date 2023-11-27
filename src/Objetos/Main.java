package Objetos;

import java.text.DecimalFormat;

/**
 * La clase AnalisisVentas es la clase principal que contiene el método main para probar
 * las clases VentasLocal, VentasEntrega y VentasRetirar.
 */
public class AnalisisVentas {

    /**
     * Método principal que prueba las clases VentasLocal, VentasEntrega y VentasRetirar.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        // Variables para almacenar las rutas de los archivos CSV y el número de meses
        String rutaCSVLocal = null;
        String rutaCSVEntrega = null;
        String rutaCSVRetirar = null;
        int mesesParaAnalizar = 0;

        // Procesar los argumentos de la línea de comandos
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-l":
                    // Argumento siguiente es la ruta del archivo CSV para ventas locales
                    rutaCSVLocal = args[++i];
                    break;
                case "-r":
                    // Argumento siguiente es la ruta del archivo CSV para ventas a retirar
                    rutaCSVRetirar = args[++i];
                    break;
                case "-e":
                    // Argumento siguiente es la ruta del archivo CSV para ventas con entrega
                    rutaCSVEntrega = args[++i];
                    break;
                case "-m":
                    // Argumento siguiente es el número de meses para analizar
                    mesesParaAnalizar = Integer.parseInt(args[++i]);
                    break;
                default:
                    // Manejar argumentos no reconocidos o incorrectos
                    System.err.println("Argumento no reconocido: " + args[i]);
                    System.exit(1); // Salir con código de error
            }
        }

        // Verificar si se proporcionaron todas las rutas de archivos y el número de meses
        if (rutaCSVLocal == null || rutaCSVEntrega == null || rutaCSVRetirar == null || mesesParaAnalizar == 0) {
            System.err.println("Faltan argumentos. Uso: java AnalisisVentas -l <locales.csv> -r <retirar.csv> -e <entrega.csv> -m <meses para analizar>");
            System.exit(1); // Salir con código de error
        }

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

        // Comparar totales para determinar cuál es el más rentable
        if (totalVentasLocal > totalVentasRetirar && totalVentasLocal > totalVentasEntrega) {
            System.out.println("\nLas ventas locales son las más rentables.");
        } else if (totalVentasRetirar > totalVentasLocal && totalVentasRetirar > totalVentasEntrega) {
            System.out.println("\nLas ventas a retirar son las más rentables.");
        } else {
            System.out.println("\nLas ventas con entrega son las más rentables.");
        }

        // Mostrar la opción menos rentable
        if (totalVentasLocal < totalVentasRetirar && totalVentasLocal < totalVentasEntrega) {
            System.out.println("\nLas ventas locales son las menos rentables.");
        } else if (totalVentasRetirar < totalVentasLocal && totalVentasRetirar < totalVentasEntrega) {
            System.out.println("\nLas ventas a retirar son las menos rentables.");
        } else {
            System.out.println("\nLas ventas con entrega son las menos rentables.");
        }

        System.out.println("Gracias por escogernos!");
    }
}
