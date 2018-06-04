import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Salesman {

   // public ArrayList<Integer> Path = new ArrayList<Integer>();
    public int size = -1;
    public int[][] cities;
    public int cost=0;
    public File file;
    public boolean ATSP=false;
    public String a="";


    public void load ()throws FileNotFoundException {
        System.out.println("podaj nazwe pliku do wczytania");
        Scanner input = new Scanner(System.in);
        //file = new File("kro124p.atsp");
        //file = new File("ftv70.atsp");
        //file = new File("ftv64.atsp");
        //file = new File("ft53.atsp");
        //file = new File("fri26.tsp");
        // file = new File("br17.atsp");


         //file = new File("bays29.txt");
        file = new File(input.next());


        Scanner in = new Scanner(file);

        for(int i=0;i<8;i++) {

            if(i==1){
                in.next();

               if(in.next().equals("ATSP")){
                   ATSP=true;
               }
               else {
                   ATSP=false;
               }
               in.nextLine();
            }
            else if(i==3){
                in.next();
                size=in.nextInt();
            }
            else {
                in.nextLine();
            }

        }
        // macierz sasiedztwa
        cities= new int [size][size];

        if(ATSP) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    cities[i][j] = in.nextInt();
                    if (i == j) {
                        cities[i][j] = -1;
                    }
                }
            }
        }
        else{
            for (int i =0; i<size; i++) {
                for (int j = 0; j<i+1; j++) {

                    cities[i][j] = in.nextInt();
                    if(i==j){
                        cities[i][j]=-1;
                    }
                    cities[j][i] = cities[i][j];

                }
            }
        }

    }

    public void loadTSP()throws FileNotFoundException{

        System.out.println("podaj nazwe pliku do wczytania");
        Scanner input = new Scanner(System.in);
        //file = new File("br17.atsp");
        // file = new File("test.txt");
        file = new File(input.next());


        Scanner in = new Scanner(file);

        for(int i=0;i<7;i++) {

            if(i==3){
                in.next();
                size=in.nextInt();
            }
            in.nextLine();

        }
        // macierz sasiedztwa
        cities= new int [size][size];
        int y;
        for (int i =0; i<size; i++) {
            for (int j = 0; j<i+1; j++) {



                cities[i][j] = in.nextInt();
                if(i==j){
                    cities[i][j]=-1;
                }
                cities[j][i] = cities[i][j];

            }
        }


    }


    public void  printCities(){
        System.out.println();
        System.out.println();
        for(int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                System.out.print(cities[i][j]+"\t");
            }
            System.out.println();
        }
    }


    public int pathCost(int [] path){
        cost=0;
        for(int i=0;i<path.length-1;i++){
            cost=cost+cities[path[i]][path[i+1]];
        }
        cost=cost+cities[path[path.length-1]][path[0]];

        return cost;
    }

    public int pathCostList(ArrayList<Integer> Path){
        cost =0;
        for(int i=0;i<Path.size()-1;i++){
            cost=cost+cities[Path.get(i)][Path.get(i+1)];
        }

        cost=cost+cities[Path.get(Path.size()-1)][Path.get(0)];

        return cost;
    }

}
