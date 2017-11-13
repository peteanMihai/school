package com.company;

import jdk.internal.util.xml.impl.Pair;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Petean Mihai on 11/8/2017.
 */
public class Scanner{
    private ArrayList<Tuple> pif = new ArrayList<>(); //program internal form
    private HashMap<String, Integer> variableSymbolTable = new HashMap<>();
    private HashMap<String, Integer> instructionCodes = new HashMap<>();
    private HashMap<String, Integer> constantSymbolTable = new HashMap<>();
    private BufferedReader fileReader;


    public boolean initInstructionCodesTable(String fileName) throws IOException {
        BufferedReader bufferedReader = fileReader = new BufferedReader(new FileReader(fileName)) ;
        String element = null;
        do{
            element = fileReader.readLine();
            instructionCodes.put(element, instructionCodes.size());
        }while(element != null);
        if(instructionCodes.size() > 0)
            return true;
        return false;
    }

    public Scanner(String sourceFile, String instructionCodesFile){
        try {
            initInstructionCodesTable(instructionCodesFile);
            BufferedReader bufferedReader = fileReader = new BufferedReader(new FileReader(sourceFile)) ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//this is not needed now
/*    static public boolean checkStatementValidity(String[] line){

        //this is horrible but it should work god damn it
        if(line[1] != ""
                && line[2] == "="
                && line[3] != ""
                && line[line.length] == ";"
                && line.length == 4)
            return true;

        return false;
    }*/
//this is not needed now
/*    public AssignStatement parseAssignStatement(String[] line){
        //means we parse a variable assign statement
        if(!checkStatementValidity(line))
            return null;
        if(line[0] == "var" )
            variableSymbolTable.put(line[1], Integer.parseInt(line[3]));
        else
            if(!constantSymbolTable.containsKey(line[1]))
                constantSymbolTable.put(line[1], Integer.parseInt(line[3]));
        return null;
    }*/

    //I AM PROUD OF THIS
    public String[] parseMissingSpaces(String[] line){
        String[] result = null;
        for(String section: line)
            if(section.contains(",") && section.length() > 1) {
                section.replace(",", " , ");
                for(String subSection: section.split(" "))
                    result[result.length] = subSection;
            }
            else
                result[result.length]  = section;
        return result;
    }

    public void parseLine(String line){
       //not for lab 2 boyooo
        /* String[] splitLine =  line.split(" ");
        if(splitLine.length == 0)
            return;
        switch (splitLine[0]){
            case "const":
            case "var":
                parseAssignStatement(splitLine); break;

        }*/
        String[] splitLine = line.split(" ");
        splitLine = parseMissingSpaces(splitLine);
        for(String element: splitLine){
            if(instructionCodes.containsKey(element))
                pif.add(new Tuple(instructionCodes.get(element), 0));
            else
                //this is quite stupid because it means all elements will be parsed as variables but it's easy to change
                if(!variableSymbolTable.containsKey(element))
                    variableSymbolTable.put(element, variableSymbolTable.size());
        }
    }

    public void readFile() throws IOException {
        String newStatement;
        do{
            newStatement = fileReader.readLine();
            parseLine(newStatement);
        }while(newStatement != null);
    }

    public int executeProgram(ArrayList<IStatement> pif, HashMap<String, Integer> variableSymbolTable,  HashMap<String, Integer> constantSymbolTable){
        return 0;
    }
}
