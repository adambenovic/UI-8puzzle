import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class IO {
    public ArrayList<Puzzle> loadFromFile(String filename) {
        ArrayList<Puzzle> states = new ArrayList<>();
        String line = null;
        Puzzle newState = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            try {
                line = reader.readLine();

                while(line != null)
                {
                    newState = this.processLine(line);
                    if(newState != null)
                        states.add(newState);
                    line = reader.readLine();
                }
            }
            catch (IOException e)
            {
                System.out.println( "Unable to read " + filename + ".");
                System.exit(Enum.EXIT_READ_EXCEPTION);
            }
        }
        catch ( FileNotFoundException e)
        {
            System.out.println( "File " + filename + " not found." );
            System.exit(Enum.EXIT_FILE_NOT_FOUND);
        }

        if(states.isEmpty()) {
            System.out.println("Invalid states entered in the file " + filename + ".");
            System.exit(Enum.EXIT_FILE_INVALID_SCHEME);
        }

        return states;
    }

    private Puzzle processLine(String line) {
        Matcher matcher = Pattern.compile("\\(([^()]+)\\)").matcher(line);
        ArrayList<String> rows = new ArrayList<>();
        ArrayList<ArrayList<Integer>> map = new ArrayList<>();

        while (matcher.find())
            rows.add(matcher.group(0));

        for(int i = 0; i < rows.size(); i++) {
            matcher = Pattern.compile("([0-9]+|[m])").matcher(rows.get(i));
            map.add(new ArrayList<>());
            while (matcher.find()) {
                if(matcher.group(0).equals("m"))
                    map.get(i).add(0);
                else
                    map.get(i).add(Integer.parseInt(matcher.group(0)));
            }
        }

        Puzzle state  = makeMap(map);

        state.setPrevious(null);
        state.setPriceToFinish(0);
        state.setPriceSoFar(0);
        state.setLastOperator(null);
        state.setNext(null);
        state.setDepth(1);


        return state;
    }

    private Puzzle makeMap(ArrayList<ArrayList<Integer>> map) {
        int[][] intMap = new int[map.size()][map.get(0).size()];
        Puzzle state = new Puzzle();

        for(int i = 0; i < map.size(); i++) {
            for(int j = 0; j < map.get(0).size(); j++) {
                intMap[i][j] = map.get(i).get(j);
                if(intMap[i][j] == 0) {
                    state.setZeroRow(i);
                    state.setZeroColumn(j);
                }
            }
        }

        state.setHeight(map.size());
        state.setWidth(map.get(0).size());
        state.setMap(intMap);

        return state;
    }

    public void printIntMap(Puzzle state) {
        for(int i = 0; i < state.getHeight(); i++) {
            for(int j = 0; j < state.getWidth(); j++) {

                System.out.print(state.getMap()[i][j]);
            }
            System.out.println();
        }
    }

    public void writeToFile(ArrayList<Solution> solutions, String filename) {
        if(solutions.isEmpty()) {
            System.out.println("No solutions found.");
            System.exit(Enum.EXIT_SOLUTION_NONE);
        }

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(filename));
        }
        catch (IOException e)
        {
            System.out.println( "Unable to open " + filename + " for writing.");
            System.exit(Enum.EXIT_WRITE_EXCEPTION);
        }

        ArrayList<ArrayList<String>> lines = solutionsToStrings(solutions);

        try {
            for(int i = 0; i < lines.size(); i++) {
                writer.write("Solution no." + (i+1) +":\n");
                for (int j = 0; j < lines.get(i).size(); j++) {
                    writer.write("Step no." + (j + 1) + ": " + lines.get(i).get(j) + "\n");
                }
                writer.write("Run time = " + solutions.get(i).getRunTime() + "ms\n\n");
            }
        } catch (IOException e)
        {
            System.out.println( "Unable to write to " + filename + ".");
            System.exit(Enum.EXIT_WRITE_EXCEPTION);
        }

        try {
            writer.close();
        } catch (IOException e) {
            System.out.println( "Unable to close " + filename + ".");
            System.exit(Enum.EXIT_FILE_CLOSE);
        }

    }

    private ArrayList<ArrayList<String>> solutionsToStrings(ArrayList<Solution> solutions) {
       ArrayList<ArrayList<String>> result = new ArrayList<>();

        for (Solution solution : solutions) {
            ArrayList<String> stateString = new ArrayList<>();
            for (Puzzle state : solution.getStates()) {
                StringBuilder line = new StringBuilder();
                line.append("(");
                for(int i = 0; i < state.getHeight(); i++){
                    line.append("(");
                    for(int j = 0; j < state.getWidth(); j++) {
                        if(j == state.getWidth() - 1) {
                            if (state.getMap()[i][j] == 0)
                                line.append("m");
                            else
                                line.append(state.getMap()[i][j]);
                        }
                        else {
                            if (state.getMap()[i][j] == 0)
                                line.append("m").append(" ");
                            else
                                line.append(state.getMap()[i][j]).append(" ");
                        }
                    }
                    line.append(")");
                }
                line.append(")");
                stateString.add(line.toString());
            }
            result.add(stateString);
        }

        return result;
    }
}
