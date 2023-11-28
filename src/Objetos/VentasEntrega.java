package Objetos;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase VentasEntrega representa las ventas con servicio de entrega.
 */
public class VentasEntrega extends Ventas {

    private double TotalVentasEntrega;
    private List<Double> EnviosPorVenta;
    private List<Boolean> EntregadoPorVenta;
    private List<LocalDate> FechasEntrega;

    /**
     * Constructor de la clase VentasEntrega. Inicializa las listas para envíos, entregas y fechas de entrega.
     */
    public VentasEntrega() {
        EnviosPorVenta = new ArrayList<>();
        EntregadoPorVenta = new ArrayList<>();
        FechasEntrega = new ArrayList<>();
    }

    /**
     * Método estático que calcula el total de ventas con servicio de entrega y muestra el resultado.
     *
     * @param csvFilePath   Ruta del archivo CSV que contiene los datos de ventas con entrega.
     * @param ventasEntrega  Instancia de la clase VentasEntrega.
     */
    public static void CalcularTotal(String csvFilePath, VentasEntrega ventasEntrega) {
        // Actualiza las ventas de entrega sin imprimir el resultado aquí
        leerCSV(csvFilePath, ventasEntrega);

        // Imprime el total de ventas de entrega al final del proceso
        System.out.println("\nEl total de ventas de entrega es: " + ventasEntrega.getTotalVentasEntregaFormateado());
    }

    /**
     * Obtiene el total de ventas con servicio de entrega formateado como cadena.
     *
     * @return Total de ventas con entrega formateado.
     */
    private double getTotalVentasEntregaFormateado() {
        return TotalVentasEntrega;
    }

    /**
     * Lee el archivo CSV y actualiza las ventas con servicio de entrega.
     *
     * @param archivo         Ruta del archivo CSV.
     * @param ventasEntrega   Instancia de la clase VentasEntrega.
     */
    private static void leerCSV(String archivo, VentasEntrega ventasEntrega) {
        double totalVentas = 0; // Variable para mantener el total de ventas

        try (CSVReader reader = new CSVReader(new FileReader(archivo))) {
            String[] nextLine;
            int filaActual = 0;
            while (true) {
                try {
                    // Intenta leer la siguiente línea
                    nextLine = reader.readNext();
                    filaActual++;

                    if (nextLine == null) {
                        break;  // Sale del bucle si no hay más líneas
                    }

                    // Verifica la longitud del array antes de acceder a los elementos
                    if (nextLine.length >= 5) {
                        // Procesa los datos de cada fila
                        String fechaStr = nextLine[0];
                        String totalVentaStr = nextLine[1];
                        String costoStr = nextLine[2];
                        String envioStr = nextLine[3];
                        String entregadoStr = nextLine[4];

                        // Resto del código...
                        LocalDate fecha = formatearFecha(fechaStr, filaActual);

                        // Realiza la conversión solo si las cadenas son numéricas
                        if (esNumerico(totalVentaStr) && esNumerico(costoStr) && esNumerico(envioStr)) {
                            double totalVenta = Double.parseDouble(totalVentaStr);
                            double costo = Double.parseDouble(costoStr);
                            double envio = Double.parseDouble(envioStr);
                            boolean entregado = Boolean.parseBoolean(entregadoStr);

                            // Suma el total de ventas
                            totalVentas += totalVenta;

                            // Realiza los cálculos necesarios y actualiza TotalVentasEntrega, EnviosPorVenta y EntregadoPorVenta
                            ventasEntrega.actualizarVentas(totalVenta, costo, envio, entregado, fecha);
                        }
                    } else {
                        System.err.println("Error: la fila " + filaActual + " no tiene suficientes columnas. Fila: " + String.join(", ", nextLine));
                    }
                } catch (CsvValidationException e) {
                    // Manejar la excepción de validación del CSV
                    System.err.println("Error de validación CSV en la fila " + filaActual + ": " + e.getMessage());
                }
            }

            // Asigna el total de ventas a la instancia de VentasEntrega
            ventasEntrega.setTotalVentasEntrega(totalVentas);
        } catch (IOException e) {
            // Maneja la excepción de entrada/salida
            e.printStackTrace();
        }
    }

    /**
     * Establece el total de ventas con servicio de entrega.
     *
     * @param totalVentas Total de ventas con entrega.
     */
    public void setTotalVentasEntrega(double totalVentas) {
        this.TotalVentasEntrega = totalVentas;
    }

    /**
     * Formatea la fecha a partir de la cadena de fecha proporcionada.
     *
     * @param fechaStr Cadena que representa la fecha.
     * @param fila     Número de fila para mensajes de error.
     * @return Objeto LocalDate que representa la fecha.
     */
    private static LocalDate formatearFecha(String fechaStr, int fila) {
        // Elimina el carácter invisible al comienzo de la cadena
        fechaStr = fechaStr.replace("\uFEFF", "").replace("\u200B", "");

        // Si la cadena es igual a "Fecha", retorna null
        if (fechaStr.trim().equalsIgnoreCase("Fecha")) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            return LocalDate.parse(fechaStr, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Error al parsear la fecha en la fila " + fila + ": " + fechaStr);
            System.err.println("Mensaje de error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verifica si una cadena es numérica.
     *
     * @param str Cadena a verificar.
     * @return true si la cadena es numérica, false de lo contrario.
     */
    private static boolean esNumerico(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Actualiza las ventas con servicio de entrega.
     *
     * @param totalVenta Total de la venta.
     * @param costo      Costo de la venta.
     * @param envio      Costo del servicio de entrega.
     * @param entregado  Indica si la venta fue entregada.
     * @param fecha      Fecha de la entrega.
     */
    public void actualizarVentas(double totalVenta, double costo, double envio, boolean entregado, LocalDate fecha) {
        // Realiza los cálculos necesarios y actualiza TotalVentasEntrega, EnviosPorVenta, EntregadoPorVenta y FechasEntrega
        this.TotalVentasEntrega += (totalVenta - costo + envio);
        this.EnviosPorVenta.add(envio);
        this.EntregadoPorVenta.add(entregado);
        this.FechasEntrega.add(fecha);
    }

    /**
     * Obtiene el total de ventas con servicio de entrega.
     *
     * @return Total de ventas con entrega.
     */
    public double getTotalVentasEntrega() {
        return this.TotalVentasEntrega;
    }

    /**
     * Obtiene la lista de costos de envío por venta.
     *
     * @return Lista de costos de envío por venta.
     */
    public List<Double> getEnviosPorVenta() {
        return this.EnviosPorVenta;
    }

    /**
     * Obtiene la lista de entregas por venta.
     *
     * @return Lista de entregas por venta.
     */
    public List<Boolean> getEntregadoPorVenta() {
        return this.EntregadoPorVenta;
    }

    /**
     * Obtiene la lista de fechas de entrega.
     *
     * @return Lista de fechas de entrega.
     */
    public List<LocalDate> getFechasEntrega() {
        return this.FechasEntrega;
    }
}
