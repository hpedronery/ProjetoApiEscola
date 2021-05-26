package br.com.nova.api.escola.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;

/**
 * Classe com métodos utilitários para testes
 */
public final class TestUtils {

    private final static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private TestUtils() {
    }

    public static <T> T getMock(String mockFolder, String fileName, Class<T> targetClass) {
        String filePath = "/" + mockFolder + "/" + fileName;
        try (InputStream is = TestUtils.class.getResourceAsStream(filePath)) {
            return mapper.readValue(is, targetClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Um erro ocorreu ao carregar o JSON de teste: " + filePath);
        }
    }
}
