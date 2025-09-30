package com.medismartsoft.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class TxtToPdfConverterTest {

    @Test
    void testConvertSimpleBinaryToPdf(@TempDir Path tempDir) throws IOException {
        // Arrange
        TxtToPdfConverter converter = new TxtToPdfConverter();
        
        // Binary for "Hello" (01001000 01100101 01101100 01101100 01101111)
        String binaryContent = "0100100001100101011011000110110001101111";
        
        Path inputFile = tempDir.resolve("test-input.txt");
        Path outputFile = tempDir.resolve("test-output.pdf");
        
        Files.writeString(inputFile, binaryContent);

        // Act
        converter.convertTxtToPdf(inputFile.toString(), outputFile.toString());

        // Assert
        assertTrue(Files.exists(outputFile));
        assertTrue(Files.size(outputFile) > 0);
    }

    @Test
    void testConvertBinaryWithSpacesToPdf(@TempDir Path tempDir) throws IOException {
        // Arrange
        TxtToPdfConverter converter = new TxtToPdfConverter();
        
        // Binary for "Hi" with spaces (01001000 01101001)
        String binaryContent = "01001000 01101001";
        
        Path inputFile = tempDir.resolve("test-input-spaces.txt");
        Path outputFile = tempDir.resolve("test-output-spaces.pdf");
        
        Files.writeString(inputFile, binaryContent);

        // Act
        converter.convertTxtToPdf(inputFile.toString(), outputFile.toString());

        // Assert
        assertTrue(Files.exists(outputFile));
        assertTrue(Files.size(outputFile) > 0);
    }

    @Test
    void testFileNotFoundThrowsException() {
        // Arrange
        TxtToPdfConverter converter = new TxtToPdfConverter();

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> {
            converter.convertTxtToPdf("nonexistent.txt", "output.pdf");
        });
        
        assertTrue(exception.getMessage().contains("no existe"));
    }

    @Test
    void testEmptyFileThrowsException(@TempDir Path tempDir) throws IOException {
        // Arrange
        TxtToPdfConverter converter = new TxtToPdfConverter();
        
        Path inputFile = tempDir.resolve("empty.txt");
        Path outputFile = tempDir.resolve("output.pdf");
        
        Files.writeString(inputFile, "");

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> {
            converter.convertTxtToPdf(inputFile.toString(), outputFile.toString());
        });
        
        assertTrue(exception.getMessage().contains("vacío"));
    }

    @Test
    void testInvalidBinaryStringThrowsException(@TempDir Path tempDir) throws IOException {
        // Arrange
        TxtToPdfConverter converter = new TxtToPdfConverter();
        
        Path inputFile = tempDir.resolve("invalid.txt");
        Path outputFile = tempDir.resolve("output.pdf");
        
        Files.writeString(inputFile, "invalid binary content with letters abc");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            converter.convertTxtToPdf(inputFile.toString(), outputFile.toString());
        });
        
        assertTrue(exception.getMessage().contains("cadena binaria válida"));
    }
}