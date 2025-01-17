package com.hiq.robot_simulator.service;

public interface RobotAction {

    void doPlace(String line);
    void doMove();
    void doTurnLeft();
    void doTurnRight();
    String doReport();

}
