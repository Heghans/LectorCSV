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

public class VentasLocal extends Ventas {

    private double TotalVentasLocal;
    private List<Double> ImpuestosPorVenta;

    public VentasLocal() {
        // Constructor vacío
        ImpuestosPorVenta = new ArrayList<>();
    }

    public static void CalcularTotal(String csvFilePath, VentasLocal ventasLocal) {
        // Actualiza las ventas locales sin imprimir el resultado aquí
        leerCSV(csvFilePath, ventasLocal);

        // Imprime el total de ventas al final del proceso
        System.out.println("\nEl total de ventas locales es: " + ventasLocal.getTotalVentasLocalFormateado());
    }

    private double getTotalVentasLocalFormateado() {
        return TotalVentasLocal;
    }

    private static void leerCSV(String archivo, VentasLocal ventasLocal) {
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
                    if (nextLine.length >= 4) {
                        // Procesa los datos de cada fila
                        String fechaStr = nextLine[0];
                        String totalVentaStr = nextLine[1];
                        String costoStr = nextLine[2];
                        String impuestosStr = nextLine[3];

                        // Resto del código...
                        LocalDate fecha = formatearFecha(fechaStr, filaActual);

                        // Realiza la conversión solo si las cadenas son numéricas
                        if (esNumerico(totalVentaStr) && esNumerico(costoStr) && esNumerico(impuestosStr)) {
                            double totalVenta = Double.parseDouble(totalVentaStr);
                            double costo = Double.parseDouble(costoStr);
                            double impuestos = Double.parseDouble(impuestosStr);

                            // Suma el total de ventas
                            totalVentas += totalVenta;

                            // Realiza los cálculos necesarios y actualiza TotalVentasLocal e ImpuestosPorVenta
                            ventasLocal.actualizarVentas(totalVenta, costo, impuestos);
                        }
                    } else {
                        System.err.println("Error: la fila " + filaActual + " no tiene suficientes columnas. Fila: " + String.join(", ", nextLine));
                    }
                } catch (CsvValidationException e) {
                    // Manejar la excepción de validación del CSV
                    System.err.println("Error de validación CSV en la fila " + filaActual + ": " + e.getMessage());
                }
            }

            // Asigna el total de ventas a la instancia de VentasLocal
            ventasLocal.setTotalVentasLocal(totalVentas);
        } catch (IOException e) {
            // Maneja la excepción de entrada/salida
            e.printStackTrace();
        }
    }

    public void setTotalVentasLocal(double totalVentas) {
        this.TotalVentasLocal = totalVentas;
    }
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



    private static boolean esNumerico(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void actualizarVentas(double totalVenta, double costo, double impuestos) {
        // Realiza los cálculos necesarios y actualiza TotalVentasLocal e ImpuestosPorVenta
        this.TotalVentasLocal += (totalVenta - costo);
        this.ImpuestosPorVenta.add(impuestos);
    }

    public double getTotalVentasLocal() {
        return this.TotalVentasLocal;
    }

    public List<Double> getImpuestosPorVenta() {
        return this.ImpuestosPorVenta;
    }
}

