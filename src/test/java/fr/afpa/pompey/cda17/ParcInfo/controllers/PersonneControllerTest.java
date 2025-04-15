package fr.afpa.pompey.cda17.ParcInfo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for PersonneController.
 * This class contains integration tests to verify the behavior of the PersonneController.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PersonneControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc is used to perform HTTP requests and verify responses.

    /**
     * Test the index method of PersonneController.
     * This test verifies that the "/personnes" endpoint returns the correct view and contains expected content.
     */
    @Test
    public void index() throws Exception {
        // Perform a GET request to the "/personnes" endpoint.
        mockMvc.perform(get("/personnes"))
                // Verify that the HTTP status is 200 (OK).
                .andExpect(status().isOk())
                // Verify that the returned view name is "personnes/index".
                .andExpect(view().name("personnes/index"))
                // Verify that the response content contains the string "Condé".
                .andExpect(content().string(containsString("Condé")))
                // Verify that the response content contains the string "Kuntz".
                .andExpect(content().string(containsString("Kuntz")))
                // Verify that the response content contains the string "Pierson".
                .andExpect(content().string(containsString("Pierson")));
    }
}
