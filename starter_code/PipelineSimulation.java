package com.revature.lab;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class PipelineSimulation {

    public static void main(String[] args) {
        System.out.println("--- Starting Pipeline ---");

        // Stage 1: Build
        if (!runStage("Build", PipelineSimulation::checkSourceCode))
            return;

        // Stage 2: Test
        if (!runStage("Test", PipelineSimulation::runRandomTests))
            return;

        // Stage 3: Deploy
        if (!runStage("Deploy", PipelineSimulation::deployArtifact))
            return;

        System.out.println("--- Pipeline SUCCESS ---");
    }

    private static boolean runStage(String name, Supplier<Boolean> task) {
        System.out.println("Running Stage: " + name + "...");
        // TODO: Execute task and handle return value
        boolean result = task.get();
        if (!result) {
            System.out.println(name + " FAILED. Pipeline stopped.");
            return false;
        }

        System.out.println(name + " SUCCESS.");
        return true;
    }

    // TODO: Implement helper methods
    private static boolean checkSourceCode() {
        File source = new File("source_code.txt");
        if (source.exists()) {
            System.out.println("Compilation Success");
            return true;
        } else {
            System.out.println("Error: source_code.txt not found");
            return false;
        }
    }

    private static boolean runRandomTests() {
        Random random = new Random();
        boolean passed = random.nextBoolean();

        if (passed) {
            System.out.println("Tests Passed");
            return true;
        } else {
            System.out.println("Tests Failed");
            return false;
        }
    }

    private static boolean deployArtifact() {
        File deployDir = new File("deploy");
        if (!deployDir.exists()) {
            deployDir.mkdir();
        }

        File artifact = new File("artifact.jar");
        File deployedArtifact = new File(deployDir, "artifact.jar");

        if (artifact.exists() && artifact.renameTo(deployedArtifact)) {
            System.out.println("Artifact deployed");
            return true;
        }
        System.out.println("Deployment Failed");
        return false;
    }

    interface Supplier<T> {
        T get();
    }
}
