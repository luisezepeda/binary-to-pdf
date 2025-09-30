package com.medismartsoft.converter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Simple converter to decode Base64 strings from TXT files and save them as PDF files.
 * This is specifically designed to handle PDF content encoded in Base64 format.
 */
public class Base64ToPdfConverter {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: java Base64ToPdfConverter <archivo-txt-entrada> <archivo-pdf-salida>");
            System.err.println("Ejemplo: java Base64ToPdfConverter input.txt output.pdf");
            System.exit(1);
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try {
            convertBase64TxtToPdf(inputFilePath, outputFilePath);
            System.out.println("✅ Conversión exitosa: " + outputFilePath);
        } catch (Exception e) {
            System.err.println("❌ Error durante la conversión: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Convierte un archivo TXT con contenido Base64 a un archivo PDF
     */
    public static void convertBase64TxtToPdf(String inputPath, String outputPath) throws IOException {
        // Validar archivo de entrada
        if (!Files.exists(Paths.get(inputPath))) {
            throw new FileNotFoundException("El archivo de entrada no existe: " + inputPath);
        }

        // Leer el contenido del archivo TXT
        String base64Content = Files.readString(Paths.get(inputPath)).trim();
        
        // Validar que el contenido parece ser Base64 válido
        if (base64Content.isEmpty()) {
            throw new IllegalArgumentException("El archivo de entrada está vacío");
        }

        // Verificar si comienza con JVBER (que es "JVB" en Base64, correspondiente a "%PDF" en PDF)
        if (base64Content.startsWith("JVBER")) {
            System.out.println("✓ Detectado contenido PDF en Base64 (JVBER...)");
        } else {
            System.out.println("⚠ Advertencia: El contenido no comienza con JVBER, pero se intentará decodificar");
        }

        try {
            // Decodificar Base64
            byte[] pdfBytes = Base64.getDecoder().decode(base64Content);
            
            // Verificar que los bytes decodificados parecen ser un PDF válido
            if (pdfBytes.length < 4 || !isPdfSignature(pdfBytes)) {
                throw new IllegalArgumentException("El contenido decodificado no parece ser un PDF válido");
            }

            // Escribir los bytes al archivo PDF
            Files.write(Paths.get(outputPath), pdfBytes);
            
            System.out.println("✓ Archivo PDF creado exitosamente");
            System.out.println("✓ Tamaño: " + pdfBytes.length + " bytes");
            
        } catch (IllegalArgumentException e) {
            throw new IOException("Error al decodificar Base64: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica si los bytes tienen la signatura de un archivo PDF (%PDF)
     */
    private static boolean isPdfSignature(byte[] bytes) {
        if (bytes.length < 4) return false;
        
        // Un PDF válido debe comenzar con "%PDF"
        return bytes[0] == '%' && bytes[1] == 'P' && bytes[2] == 'D' && bytes[3] == 'F';
    }

    /**
     * Método auxiliar para validar si una cadena es Base64 válida
     */
    public static boolean isValidBase64(String base64String) {
        try {
            Base64.getDecoder().decode(base64String);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}