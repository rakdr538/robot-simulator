package com.hiq.robot_simulator.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiq.robot_simulator.model.RobotSimulatorResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class FileUploadControllerTestIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldSimulateUploadedFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());
        this.mvc.perform(multipart("/upload").file(multipartFile))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowBadRequestException() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.json",
                "application/json", "{Spring Framework}".getBytes());
        this.mvc.perform(multipart("/upload").file(multipartFile))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldSimulateBigUploadedFile() throws Exception {
        String multiLineStr = "PLACE,0,0,NORTH" + System.lineSeparator()
                + "MOVE" + System.lineSeparator()
                + "REPORT" + System.lineSeparator()
                + "PLACE,0,0,NORTH" + System.lineSeparator()
                + "LEFT" + System.lineSeparator()
                + "REPORT" + System.lineSeparator()
                + "PLACE,1,2,EAST" + System.lineSeparator()
                + "MOVE" + System.lineSeparator()
                + "MOVE" + System.lineSeparator()
                + "LEFT" + System.lineSeparator()
                + "MOVE" + System.lineSeparator()
                + "REPORT";
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", multiLineStr.getBytes());
        var result = this.mvc.perform(multipart("/upload").file(multipartFile))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        assertNotNull(json);
        List<RobotSimulatorResult> simulatorResults = new ObjectMapper().readValue(json, new TypeReference<>() {});
        assertEquals(3, simulatorResults.size());
        assertEquals("Output: 0, 1, NORTH", simulatorResults.getFirst().getOutput());
        assertEquals("Output: 3, 3, NORTH", simulatorResults.getLast().getOutput());
    }
}