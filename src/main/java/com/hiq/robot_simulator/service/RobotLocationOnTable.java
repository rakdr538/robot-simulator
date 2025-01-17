package com.hiq.robot_simulator.service;

import com.hiq.robot_simulator.model.FaceDirection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RobotLocationOnTable {
    private Integer xValue;
    private Integer yValue;
    private FaceDirection direction;
}
