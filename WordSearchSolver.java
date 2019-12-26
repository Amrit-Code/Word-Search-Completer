import java.util.*;
import java.io.*;
class WordSearchSolver{
    public static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
        String file = Intro();
        String[][] wordSearch = SetupSearch(file);
        RunProgram(wordSearch);
    }

    public static String Intro(){
        System.out.println("Hello! Please enter the name of the file you would like to solve:");
        String name = scanner.nextLine();
        return name;
    }
    public static String[][] SetupSearch(String file){
        String[][] wordSearch;
        ArrayList<String[]> tempList = new ArrayList<String[]>();
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String wordSearchLine;
            while((wordSearchLine = bufferedReader.readLine()) != null){
                String[] line = wordSearchLine.toUpperCase().split("");
                tempList.add(line);
            }
            fileReader.close();
            bufferedReader.close();
        }catch(Exception e){
            System.out.println("Something went wrong.");
            ExitProgram();
        }
        wordSearch = new String[tempList.size()][tempList.get(0).length];
        int v = 0;
        for(String[] i : tempList){
            wordSearch[v] = i;
            v++;
        }
        return wordSearch;
    }

    public static void RunProgram(String[][] wordSearch){
        Boolean run = true;
        while(run){

            System.out.println("Enter next word to look for...");
            String word = scanner.nextLine().toUpperCase();
            if(!(word.equals(" "))){
                String[] wordsToFind = word.split(" ");
                String[][] letters = new String[wordsToFind.length][];

                for(int i = 0; i < wordsToFind.length; i ++){
                    String[] p = wordsToFind[i].split("-");
                    String in = "";
                    for(String s : p){
                        in += s;
                    }
                    letters[i] = in.split("");
                } 
                int[][] coordinates = Findcoordinates(wordSearch, letters);
                printSearch(wordSearch, coordinates);
            }else{
                run = false;
            }
        }
        ExitProgram();
    }

    public static int[][] Findcoordinates(String[][] wordSearch, String[][] letters){
        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        int colloums = wordSearch[0].length;
        int rows = wordSearch.length;
        for(int y = 0; y < rows; y++){
            for(int x = 0; x < colloums; x++){
                for(String[] i : letters){
                    if (i[0].equals(wordSearch[y][x])){
                        for(int dir = 1; dir <= 8; dir++){
                            int yy = y;
                            int xx = x;
                            int len = i.length;
                            ArrayList<int[]> temp = new ArrayList<int[]>();
                            temp.add(addE(y,x));
                            //up
                            for(int n = 1; n < len; n ++){
                                if(dir == 1){
                                    yy -= 1;
                                }else if(dir == 2){
                                    yy -= 1;
                                    xx -= 1;
                                }else if(dir == 3){
                                    xx -= 1;
                                }else if(dir == 4){
                                    yy += 1;
                                    xx -=1;
                                }else if(dir == 5){
                                    yy += 1;
                                }else if (dir == 6){
                                    yy+=1;
                                    xx+=1;
                                }else if (dir == 7){
                                    xx += 1;
                                }else {
                                    yy -= 1;
                                    xx += 1;
                                }
                                if(yy < 0 || xx < 0 || yy == rows || xx == colloums){
                                    break;
                                }else if(i[n].equals(wordSearch[yy][xx])){
                                    temp.add(addE(yy,xx));
                                    if(n == len-1){
                                        for(int[] v : temp){
                                            coordinates.add(v);
                                        }
                                    }
                                }else{
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        int[][] coorList = new int[coordinates.size()][2];
        int n = 0;
        for(int[] i : coordinates){
            coorList[n] = i;
            n++;
        }
        return coorList;
    }

    public static int[] addE(int y, int x){
        int[] coor = {y,x};
        return coor;
    }

    public static void printSearch(String[][] wordSearch, int[][] coordinates){
        int colloums = wordSearch[0].length;
        int rows = wordSearch.length;
        for(int y = 0; y < rows; y++){
            //String line = "";
            boolean next = false;
            for(int x = 0; x < colloums; x++){
                boolean found = false;
                for(int[] i : coordinates){
                    if(i[0] == y && i[1] == x){
                        if(!next){
                            System.out.print(String.format("%4s", ("|"+wordSearch[y][x]+"|")));
                        }else if(next){
                            System.out.print(String.format("%3s", ("|"+wordSearch[y][x]+"|")));
                        }
                        //line += ("%1$1s|"+wordSearch[y][x]+ "|%1$1s");
                        found = true;
                        next = true;
                        break;
                    }
                }
                if(! found && !next){
                    System.out.print(String.format("%3s", (wordSearch[y][x])));
                    //line += ("%1$1s"+wordSearch[y][x]+"%1$1s");
                }else if (! found && next){
                    System.out.print(String.format("%2s", (wordSearch[y][x])));
                    next = false;
                }
            }
            System.out.print("\n");
            //System.out.println(String.format(line, " "));
        }
    }

    public static void ExitProgram(){
        scanner.close();
        System.exit(0);
    }
}