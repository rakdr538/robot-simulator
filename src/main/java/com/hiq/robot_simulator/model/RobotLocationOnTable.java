package com.hiq.robot_simulator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RobotLocationOnTable {
    private Integer xValue;
    private Integer yValue;
    private FaceDirection direction;
}
