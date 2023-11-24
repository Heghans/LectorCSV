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

public class VentasEntrega extends Ventas {

    private double TotalVentasEntrega;
    private List<Double> EnviosPorVenta;
    private List<Boolean> EntregadoPorVenta;
    private List<LocalDate> FechasEntrega;

    public VentasEntrega() {
        // Constructor vacío
        EnviosPorVenta = new ArrayList<>();
        EntregadoPorVenta = new ArrayList<>();
        FechasEntrega = new ArrayList<>();
    }

    public static void CalcularTotal(String csvFilePath, VentasEntrega ventasEntrega) {
        // Actualiza las ventas de entrega sin imprimir el resultado aquí
        leerCSV(csvFilePath, ventasEntrega);
    }

    private static void leerCSV(String archivo, VentasEntrega ventasEntrega) {
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

                            // Realiza los cálculos necesarios y actualiza TotalVentasEntrega, EnviosPorVenta y EntregadoPorVenta
                            ventasEntrega.actualizarVentas(totalVenta, costo, envio, entregado, fecha);
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

    public void actualizarVentas(double totalVenta, double costo, double envio, boolean entregado, LocalDate fecha) {
        // Realiza los cálculos necesarios y actualiza TotalVentasEntrega, EnviosPorVenta y EntregadoPorVenta
        this.TotalVentasEntrega += (totalVenta - costo);
        this.EnviosPorVenta.add(envio);
        this.EntregadoPorVenta.add(entregado);
        this.FechasEntrega.add(fecha);
    }

    public double getTotalVentasEntrega() {
        return this.TotalVentasEntrega;
    }

    public List<Double> getEnviosPorVenta() {
        return this.EnviosPorVenta;
    }

    public List<Boolean> getEntregadoPorVenta() {
        return this.EntregadoPorVenta;
    }

    public List<LocalDate> getFechasEntrega() {
        return this.FechasEntrega;
    }
}
