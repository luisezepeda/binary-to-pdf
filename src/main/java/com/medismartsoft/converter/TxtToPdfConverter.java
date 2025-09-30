package com.medismartsoft.converter;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Simple Java 20 application to convert binary string from TXT files to PDF.
 * Usage: java -jar txt-to-pdf-converter.jar <input.txt> <output.pdf>
 */
public class TxtToPdfConverter {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: java -jar txt-to-pdf-converter.jar <archivo_entrada.txt> <archivo_salida.pdf>");
            System.err.println("Ejemplo: java -jar txt-to-pdf-converter.jar input.txt output.pdf");
            System.exit(1);
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try {
            var converter = new TxtToPdfConverter();
            converter.convertTxtToPdf(inputFilePath, outputFilePath);
            System.out.println("✅ Conversión exitosa: " + outputFilePath);
        } catch (Exception e) {
            System.err.println("❌ Error durante la conversión: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Converts a TXT file containing binary string to PDF.
     *
     * @param inputFilePath  Path to the input TXT file
     * @param outputFilePath Path to the output PDF file
     * @throws IOException if file operations fail
     */
    public void convertTxtToPdf(String inputFilePath, String outputFilePath) throws IOException {
        // Read the binary string from TXT file
        Path inputPath = Paths.get(inputFilePath);
        if (!Files.exists(inputPath)) {
            throw new IOException("El archivo de entrada no existe: " + inputFilePath);
        }

        String binaryContent = Files.readString(inputPath);
        if (binaryContent == null || binaryContent.trim().isEmpty()) {
            throw new IOException("El archivo de entrada está vacío");
        }

        // Validate binary string
        String cleanBinaryString = binaryContent.trim();
        if (!isValidBinaryString(cleanBinaryString)) {
            throw new IllegalArgumentException("El contenido no es una cadena binaria válida (solo debe contener 0s y 1s)");
        }

        // Convert binary to text
        String decodedText = binaryToText(cleanBinaryString);

        // Create PDF
        createPdf(decodedText, outputFilePath, inputFilePath);
    }

    /**
     * Validates if a string contains only binary characters (0 and 1).
     *
     * @param binaryString the string to validate
     * @return true if valid binary string, false otherwise
     */
    private boolean isValidBinaryString(String binaryString) {
        return binaryString.matches("[01\\s]+");
    }

    /**
     * Converts binary string to readable text.
     *
     * @param binaryString the binary string to convert
     * @return decoded text
     */
    private String binaryToText(String binaryString) {
        // Remove any whitespace
        String cleanBinary = binaryString.replaceAll("\\s", "");
        
        // Ensure the binary string length is multiple of 8
        if (cleanBinary.length() % 8 != 0) {
            // Pad with zeros if necessary
            int padding = 8 - (cleanBinary.length() % 8);
            cleanBinary = "0".repeat(padding) + cleanBinary;
        }

        StringBuilder result = new StringBuilder();
        
        // Process 8-bit chunks
        for (int i = 0; i < cleanBinary.length(); i += 8) {
            String binaryChunk = cleanBinary.substring(i, i + 8);
            int charCode = Integer.parseInt(binaryChunk, 2);
            
            // Only add printable ASCII characters or common whitespace
            if ((charCode >= 32 && charCode <= 126) || charCode == 10 || charCode == 13 || charCode == 9) {
                result.append((char) charCode);
            } else {
                // For non-printable characters, show their binary representation
                result.append("[").append(binaryChunk).append("]");
            }
        }
        
        return result.toString();
    }

    /**
     * Creates a PDF file with the decoded content.
     *
     * @param content        the text content to write to PDF
     * @param outputFilePath the output PDF file path
     * @param sourceFile     the source file name for reference
     * @throws IOException if PDF creation fails
     */
    private void createPdf(String content, String outputFilePath, String sourceFile) throws IOException {
        try {
            PdfWriter writer = new PdfWriter(outputFilePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add title
            Paragraph title = new Paragraph("Contenido Decodificado de Archivo Binario")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(16)
                    .setBold();
            document.add(title);

            // Add source file information
            Paragraph source = new Paragraph("Archivo fuente: " + sourceFile)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(10)
                    .setItalic();
            document.add(source);

            // Add separator
            document.add(new Paragraph("\n"));

            // Add content
            Paragraph contentParagraph = new Paragraph(content)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(contentParagraph);

            document.close();
        } catch (Exception e) {
            throw new IOException("Error al crear el archivo PDF: " + e.getMessage(), e);
        }
    }
}