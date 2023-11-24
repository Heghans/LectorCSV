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

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class VentasRetirar {

    private double TotalVentasRetirar;
    private List<String> EmpaquesPorVenta;

    public VentasRetirar() {
        // Constructor vacío
        EmpaquesPorVenta = new ArrayList<>();
    }

    public static void CalcularTotal(String csvFilePath, VentasRetirar ventasRetirar) {
        // Actualiza las ventas a retirar sin imprimir el resultado aquí
        leerCSV(csvFilePath, ventasRetirar);
    }

    private static void leerCSV(String archivo, VentasRetirar ventasRetirar) {
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

                            // Realiza los cálculos necesarios y actualiza TotalVentasRetirar e EmpaquesPorVenta
                            ventasRetirar.actualizarVentas(totalVenta, costo, empaque);
                        } else {
                            // Manejar el caso en el que las cadenas no son numéricas
                            System.err.println("Error: las cadenas no son numéricas en la fila " + filaActual + ": " + String.join(", ", nextLine));
                        }
                    } else {
                        System.err.println("Error: la fila " + filaActual + " no tiene suficientes columnas. Fila: " + String.join(", ", nextLine));
                    }
                } catch (CsvValidationException e) {
                    // Manejar la excepción de validación del CSV
                    System.err.println("Error de validación CSV en la fila " + filaActual + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            // Maneja la excepción de entrada/salida
            e.printStackTrace();
        }
    }

    private static LocalDate formatearFecha(String fechaStr, int fila) {
        // Elimina el carácter invisible al comienzo de la cadena
        fechaStr = fechaStr.replace("\uFEFF", "").replace("\u200B", "");

        // Si la cadena es igual a "Fecha", retorna null
        if (fechaStr.trim().equalsIgnoreCase("Fecha")) {
            System.err.println("Advertencia: Se encontró la cadena 'Fecha' en la fila " + fila + ". No es una fecha válida.");
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            return LocalDate.parse(fechaStr, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Error al parsear la fecha en la fila " + fila + ": " + fechaStr);
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

    public void actualizarVentas(double totalVenta, double costo, String empaque) {
        // Realiza los cálculos necesarios y actualiza TotalVentasRetirar y EmpaquesPorVenta
        this.TotalVentasRetirar += (totalVenta - costo);
        this.EmpaquesPorVenta.add(empaque);
    }

    public double getTotalVentasRetirar() {
        return this.TotalVentasRetirar;
    }

    public List<String> getEmpaquesPorVenta() {
        return this.EmpaquesPorVenta;
    }
}
