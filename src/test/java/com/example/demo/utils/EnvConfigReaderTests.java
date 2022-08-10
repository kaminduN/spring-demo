package com.example.demo.utils;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class EnvConfigReaderTests {

    @Test
    @DisplayName("Single test successful")
    @SetEnvironmentVariable(key = "somevalue", value = "100")
	void variableLoadSuccess() {
        
        assertEquals("100",EnvConfigReader.getConfig("somevalue"));

	}

    @Test
    @DisplayName("Default value test")
	void variableLoadDefaut() {
        
        assertEquals("200",EnvConfigReader.getConfig("somevalue", "200"));
	}

    @Test
    @DisplayName("no default value test")
	void variableLoadNoDefault() {
        
        assertNull(EnvConfigReader.getConfig("nonexistentkey"));

	}
}
