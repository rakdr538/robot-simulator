package com.hiq.robot_simulator.controller;

import com.hiq.robot_simulator.model.RobotSimulatorResult;
import com.hiq.robot_simulator.service.RobotSimulatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FileUploadController {

    private final RobotSimulatorService simulatorService;

    @PostMapping("/upload")
    public ResponseEntity<List<RobotSimulatorResult>> uploadFile(@RequestParam("file") MultipartFile file) {
        log.debug("Input file is: {}", file.getOriginalFilename());
        return ResponseEntity.ok().body(simulatorService.simulateRobotActions(file));
    }
}
