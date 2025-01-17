package com.hiq.robot_simulator.service;

import com.hiq.robot_simulator.model.FaceDirection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hiq.robot_simulator.model.FaceDirection.*;

@Component
@Slf4j
public class RobotActionImpl implements RobotAction {

    private final RobotLocationOnTable robotLocationOnTable;

    public RobotActionImpl() {
        this.robotLocationOnTable = new RobotLocationOnTable(0, 0, NORTH);
    }

    @Override
    public void doPlace(String line) {
        List<String> startLine = List.of(line.split(",")); // TODO this is unchecked
        robotLocationOnTable.setXValue(Integer.valueOf(startLine.get(1)));
        robotLocationOnTable.setYValue(Integer.valueOf(startLine.get(2)));
        robotLocationOnTable.setDirection(FaceDirection.valueOf(startLine.get(3)));
    }

    @Override
    public void doMove() {
        if (robotLocationOnTable.getDirection() == NORTH
                && robotLocationOnTable.getYValue() > -1
                && robotLocationOnTable.getYValue() < 5) {
            robotLocationOnTable.setYValue(robotLocationOnTable.getYValue() + 1);
        }
        if (robotLocationOnTable.getDirection() == SOUTH
                && robotLocationOnTable.getYValue() > -1
                && robotLocationOnTable.getYValue() < 5) {
            robotLocationOnTable.setYValue(robotLocationOnTable.getYValue() - 1);
        }
        if (robotLocationOnTable.getDirection() == EAST
                && robotLocationOnTable.getXValue() > -1
                && robotLocationOnTable.getXValue() < 5) {
            robotLocationOnTable.setXValue(robotLocationOnTable.getXValue() + 1);
        }
        if (robotLocationOnTable.getDirection() == WEST
                && robotLocationOnTable.getXValue() > -1
                && robotLocationOnTable.getXValue() < 5) {
            robotLocationOnTable.setXValue(robotLocationOnTable.getXValue() - 1);
        }
    }

    @Override
    public void doTurnLeft() {
        switch (robotLocationOnTable.getDirection()) {
            case NORTH -> robotLocationOnTable.setDirection(FaceDirection.WEST);
            case SOUTH -> robotLocationOnTable.setDirection(EAST);
            case EAST -> robotLocationOnTable.setDirection(NORTH);
            case WEST -> robotLocationOnTable.setDirection(FaceDirection.SOUTH);
        }
    }

    @Override
    public void doTurnRight() {
        switch (robotLocationOnTable.getDirection()) {
            case NORTH -> robotLocationOnTable.setDirection(EAST);
            case SOUTH -> robotLocationOnTable.setDirection(FaceDirection.WEST);
            case EAST -> robotLocationOnTable.setDirection(FaceDirection.SOUTH);
            case WEST -> robotLocationOnTable.setDirection(NORTH);
        }
    }

    @Override
    public String doReport() {
        return "Output: " + robotLocationOnTable.getXValue() + ", " +
                robotLocationOnTable.getYValue() + ", " +
                robotLocationOnTable.getDirection();
    }
}
