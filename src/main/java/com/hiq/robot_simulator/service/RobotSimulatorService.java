package com.hiq.robot_simulator.service;

import com.hiq.robot_simulator.model.RobotSimulatorResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RobotSimulatorService {

    List<RobotSimulatorResult> simulateRobotActions(MultipartFile file);
}
