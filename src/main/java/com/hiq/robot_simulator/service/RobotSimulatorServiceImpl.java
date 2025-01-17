package com.hiq.robot_simulator.service;

import com.hiq.robot_simulator.model.RobotActions;
import com.hiq.robot_simulator.model.RobotSimulatorConstants;
import com.hiq.robot_simulator.model.RobotSimulatorResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RobotSimulatorServiceImpl implements RobotSimulatorService {

    private final RobotAction robotAction;

    public List<RobotSimulatorResult> simulateRobotActions(MultipartFile file) {
        // Check if the file's content type is acceptable
//        if (Objects.equals(file.getContentType(), "text/plain")) {
//            log.debug("Input file is: {}", file.getOriginalFilename());
//        }

        List<RobotSimulatorResult> robotSimulatorResults = new ArrayList<>();

        var fileLines = getFileLines(file);

        // if there is no 'REPORT' in the file then do nothing.
        // if there are less than 2 lines in the file then do nothing.
        if (fileLines.isEmpty() || fileLines.size() < 2) {
            return List.of(); // TODO bad request.
        }

        var result = new RobotSimulatorResult();
        result.setActions(new ArrayList<>());

        for (String line : fileLines) {
            log.debug(line);

            if (line.startsWith(RobotSimulatorConstants.PLACE)) {
                robotAction.doPlace(line);
                result.setPlace(line);
                continue;
            }
            if (isFirstWordAndPlace(line, RobotActions.MOVE.name(), result.getPlace())) {
                robotAction.doMove();
                result.getActions().add(RobotActions.MOVE.name());
                continue;
            }
            if (isFirstWordAndPlace(line, RobotActions.LEFT.name(), result.getPlace())) {
                robotAction.doTurnLeft();
                result.getActions().add(RobotActions.LEFT.name());
                continue;
            }
            if (isFirstWordAndPlace(line, RobotActions.RIGHT.name(), result.getPlace())) {
                robotAction.doTurnRight();
                result.getActions().add(RobotActions.RIGHT.name());
                continue;
            }
            if (isFirstWordAndPlace(line, RobotSimulatorConstants.REPORT, result.getPlace())) {
                result.setReport(RobotSimulatorConstants.REPORT);
                result.setOutput(robotAction.doReport());
                // only add result when 'REPORT' is found
                robotSimulatorResults.add(result);
            }
        }
        return robotSimulatorResults;
    }

    private static List<String> getFileLines(MultipartFile file) {
        try {
            return new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .filter(line -> !line.isBlank())
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO
        }
    }

    private boolean isFirstWordAndPlace(String inString, String startsWithWord, String place) {
        return inString.startsWith(startsWithWord) && !place.isEmpty();
    }
}