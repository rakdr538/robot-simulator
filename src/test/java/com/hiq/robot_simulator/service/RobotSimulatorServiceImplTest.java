package com.hiq.robot_simulator.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RobotSimulatorServiceImplTest {

    @Autowired
    private RobotSimulatorServiceImpl robotSimulatorServiceImpl;

    @Test
    void simulateRobotActionsWithEmptyFile() {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "".getBytes());
        var results = robotSimulatorServiceImpl.simulateRobotActions(multipartFile);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void simulateRobotActionsWithSingleLineInFile() {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "place,0,0,north".getBytes());
        var results = robotSimulatorServiceImpl.simulateRobotActions(multipartFile);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void simulateRobotActionsWithTwoLineFile() {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", ("place,0,0,north" + System.lineSeparator() + "report").getBytes());
        var results = robotSimulatorServiceImpl.simulateRobotActions(multipartFile);
        assertNotNull(results);
        assertEquals(1, results.size());
        assertTrue(results.getFirst().getOutput().contains("0, 0, NORTH"));
    }

    @Test
    void simulateRobotActionsWithExtraNewLinesFile() {
        String multiLine = "place,0,0,north"
                + System.lineSeparator()
                + "report"
                + System.lineSeparator()
                + System.lineSeparator();
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", multiLine.getBytes());
        var results = robotSimulatorServiceImpl.simulateRobotActions(multipartFile);
        assertNotNull(results);
        assertEquals(1, results.size());
        assertTrue(results.getFirst().getOutput().contains("0, 0, NORTH"));
    }

    @Test
    void simulateRobotActionsWithMultiLineFileWithoutReport() {
        String multiLineFile = "place,0,0,north" + System.lineSeparator()
                + "left" + System.lineSeparator()
                + "move";
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", multiLineFile.getBytes());
        var results = robotSimulatorServiceImpl.simulateRobotActions(multipartFile);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void simulateRobotActionsWithMultiLineFileWithReport() {
        String multiLineFile = "place,0,0,north" + System.lineSeparator()
                + "left" + System.lineSeparator()
                + "report";
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", multiLineFile.getBytes());
        var results = robotSimulatorServiceImpl.simulateRobotActions(multipartFile);
        assertNotNull(results);
        assertEquals(1, results.size());
        assertTrue(results.getFirst().getOutput().contains("0, 0, WEST"));
    }

    @Test
    void simulateRobotActionsMoveNorth() {
        String multiLineFile = "place,0,0,north" + System.lineSeparator()
                + "move" + System.lineSeparator()
                + "report";
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", multiLineFile.getBytes());
        var results = robotSimulatorServiceImpl.simulateRobotActions(multipartFile);
        assertNotNull(results);
        assertEquals(1, results.size());
        assertTrue(results.getFirst().getOutput().contains("0, 1, NORTH"));
    }

    @Test
    void simulateRobotActionsMultiMove() {
        String multiLineFile = "PLACE,1,2,EAST" + System.lineSeparator()
                + "move" + System.lineSeparator()
                + "move" + System.lineSeparator()
                + "left" + System.lineSeparator()
                + "move" + System.lineSeparator()
                + "report";
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", multiLineFile.getBytes());
        var results = robotSimulatorServiceImpl.simulateRobotActions(multipartFile);
        assertNotNull(results);
        assertEquals(1, results.size());
        assertTrue(results.getFirst().getOutput().contains("3, 3, NORTH"));
    }
}