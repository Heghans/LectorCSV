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
 * La clase VentasRetirar representa las ventas para retirar.
 */
public class VentasRetirar {

    private double TotalVentasRetirar;
    private List<String> EmpaquesPorVenta;

    /**
     * Constructor de la clase VentasRetirar. Inicializa la lista de empaques por venta.
     */
    public VentasRetirar() {
        EmpaquesPorVenta = new ArrayList<>();
    }

    /**
     * Método estático que calcula el total de ventas a retirar y muestra el resultado.
     *
     * @param csvFilePath Ruta del archivo CSV que contiene los datos de ventas a retirar.
     * @param ventasRetirar Instancia de la clase VentasRetirar.
     */
    public static void CalcularTotal(String csvFilePath, VentasRetirar ventasRetirar) {
        // Actualiza las ventas a retirar sin imprimir el resultado aquí
        leerCSV(csvFilePath, ventasRetirar);

        // Imprime el total de ventas a retirar después de procesar el archivo CSV
        System.out.println("\nEl total de ventas a retirar es: " + ventasRetirar.getTotalVentasRetirarFormateado());
    }

    /**
     * Obtiene el total de ventas a retirar formateado como cadena.
     *
     * @return Total de ventas a retirar formateado.
     */
    private double getTotalVentasRetirarFormateado() {
        return TotalVentasRetirar;
    }

    /**
     * Lee el archivo CSV y actualiza las ventas a retirar.
     *
     * @param archivo      Ruta del archivo CSV.
     * @param ventasRetirar Instancia de la clase VentasRetirar.
     */
    private static void leerCSV(String archivo, VentasRetirar ventasRetirar) {
        double totalVentas = 0; // Variable para mantener el total de ventas a retirar

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
                    if (nextLine.length >= 4) {
                        // Procesa los datos de cada fila
                        String fechaStr = nextLine[0];
                        String totalVentaStr = nextLine[1];
                        String costoStr = nextLine[2];
                        String empaque = nextLine[3];

                        // Resto del código...
                        LocalDate fecha = formatearFecha(fechaStr, filaActual);

                        // Realiza la conversión solo si las cadenas son numéricas
                        if (esNumerico(totalVentaStr) && esNumerico(costoStr)) {
                            double totalVenta = Double.parseDouble(totalVentaStr);
                            double costo = Double.parseDouble(costoStr);

                            // Suma el total de ventas a retirar
                            totalVentas += totalVenta;

                            // Realiza los cálculos necesarios y actualiza TotalVentasRetirar e EmpaquesPorVenta
                            ventasRetirar.actualizarVentas(totalVenta, costo, empaque);
                        }
                    } else {
                        System.err.println("Error: la fila " + filaActual + " no tiene suficientes columnas. Fila: " + String.join(", ", nextLine));
                    }
                } catch (CsvValidationException e) {
                    // Manejar la excepción de validación del CSV
                    System.err.println("Error de validación CSV en la fila " + filaActual + ": " + e.getMessage());
                }
            }

            // Asigna el total de ventas a retirar a la instancia de VentasRetirar
            ventasRetirar.setTotalVentasRetirar(totalVentas);
        } catch (IOException e) {
            // Maneja la excepción de entrada/salida
            e.printStackTrace();
        }
    }

    /**
     * Establece el total de ventas a retirar.
     *
     * @param totalVentas Total de ventas a retirar.
     */
    private void setTotalVentasRetirar(double totalVentas) {
        this.TotalVentasRetirar = totalVentas;
    }

    /**
     * Formatea la fecha a partir de la cadena de fecha proporcionada.
     *
     * @param fechaStr Cadena de fecha.
     * @param fila     Número de fila actual.
     * @return Objeto LocalDate o null si hay un error al parsear la fecha.
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
     * @return true si la cadena es numérica, false en caso contrario.
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
     * Actualiza las ventas a retirar con los datos de una venta.
     *
     * @param totalVenta Total de la venta.
     * @param costo      Costo de la venta.
     * @param empaque    Tipo de empaque.
     */
    public void actualizarVentas(double totalVenta, double costo, String empaque) {
        // Realiza los cálculos necesarios y actualiza TotalVentasRetirar y EmpaquesPorVenta
        this.TotalVentasRetirar += (totalVenta - costo);
        this.EmpaquesPorVenta.add(empaque);
    }

    /**
     * Obtiene el total de ventas a retirar.
     *
     * @return Total de ventas a retirar.
     */
    public double getTotalVentasRetirar() {
        return this.TotalVentasRetirar;
    }

    /**
     * Obtiene la lista de empaques por venta.
     *
     * @return Lista de empaques por venta.
     */
    public List<String> getEmpaquesPorVenta() {
        return this.EmpaquesPorVenta;
    }
}
