package org.architecture_helper.backend;

import java.util.ArrayList;
import java.util.List;

public class Instruction {

    public final String name;

    public boolean branch  = false;

    public boolean regWrite   = false;
    public boolean regRead    = false;
    public boolean memWrite   = false;
    public boolean memRead    = false;

    public String rd = null;
    public List<String> rs = new ArrayList<>();


    public Instruction(String instructionText) {
        String[] instructionParts = parseInstruction(instructionText);
        name = instructionParts[0];

        //if this is a load instruction
        if (name.charAt(0) == 'L' && name != "LUI") {//LOAD
            regWrite = true;
            memRead = true;
            regRead = true;

            rd = instructionParts[1];
            rs.add(extractRegister(instructionParts[2]));

        } else if (name.charAt(0) == 'B') {//BRANCH
            branch = true;
            regRead = true;

            rs.add(instructionParts[1]);
            rs.add(instructionParts[2]);

        } else if (name.charAt(0) == 'S' && name.length() == 2) {//SAVE
            memWrite = true;
            regRead = true;

            rs.add(instructionParts[1]);
            rs.add(extractRegister(instructionParts[2]));

        } else if (name.charAt(name.length() - 1) == 'I' && name.substring(name.length() - 2) == "IW") {//IM
            regWrite = true;

            rd = instructionParts[1];
            if (name != "LUI") {
                rs.add(instructionParts[2]);
            }
        } else if (name == "JAL") {
            rd = instructionParts[1];
        } else {
            regRead = true;
            regWrite = true;

            rd = instructionParts[1];
            rs.add(instructionParts[2]);
            rs.add(instructionParts[3]);
        }
    }

    private String extractRegister(String instructionPart) {
        if (instructionPart.contains("(")) {
            int start = instructionPart.indexOf("(") + 1;
            int length = instructionPart.indexOf(")");
            return instructionPart.substring(start, length);
        }
        return instructionPart;
    }

    private String[] parseInstruction(String instructionText) {
        //spacing the ,
        instructionText = instructionText.replace(',', ' ');

        //Removing doubleSpaces
        instructionText = instructionText.trim().replaceAll("\\s+", " ");

        //removing trailing/leading spaces + toLower
        String[] output = instructionText.toLowerCase().split(" ");

        //uppercase on the instruction name
        output[0] = output[0].toUpperCase();

        return output;
    }


}