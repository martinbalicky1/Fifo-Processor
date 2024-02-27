package org.mb.service.impl;

import org.mb.service.Command;

public class PoisonPillCommand implements Command {
    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "PoisonPillCommand: Terminating command.";
    }
}