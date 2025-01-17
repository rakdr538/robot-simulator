package com.hiq.robot_simulator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RobotSimulatorResult {
    private String place;
    private List<String> actions;
    private String report;
    private String output;
}
