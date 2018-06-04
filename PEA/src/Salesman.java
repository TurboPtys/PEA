import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Salesman {

    public ArrayList<Integer> Path = new ArrayList<Integer>();
    public int size = -1;
    private int[][] cities;
    public int prob[][], backT[][], pow;
    public int cost=-1;

    public File file;


    public void load ()throws FileNotFoundException {
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

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                cities[i][j]=in.nextInt();
                if(i==j){
                    cities[i][j]=-1;
                }
            }
        }

        pow = (int) Math.pow(2, size);
        prob = new int[size][pow];         //tablica podproblemów
        backT = new int[size][pow];     //tablica do backtarckingu




        for (int i = 0; i < size; i++) {
            for (int j = 0; j < pow; j++) {
                prob[i][j] = -1;
                backT[i][j] = -1;
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




        pow = (int) Math.pow(2, size);
        prob = new int[size][pow];         //tablica podproblemów
        backT = new int[size][pow];     //tablica do backtarckingu




        for (int i = 0; i < size; i++) {
            for (int j = 0; j < pow; j++) {
                prob[i][j] = -1;
                backT[i][j] = -1;
            }
        }

    }

    public void initDP() {

        for (int i = 0; i < size; i++) {
            prob[i][0] = cities[i][0];
        }

        Path.add(1);
        cost =DP(0,pow-2);
        getPath(0,pow-2);


       Path.add(1);

    }

    public int DP(int start,int set){
        int subSet=0,mask=0,result=-1,tmp;


        //ominiecie jeśli wyliczono juz problemu
        if(prob[start][set]!=-1){

            return prob[start][set];
        }
        else{
            for(int i=0;i<size;i++){

                mask=pow-1-(int)Math.pow(2,i);      //mask do okreslenia podzbioru
                subSet=set & mask;

                if(subSet!=set){

                    tmp=cities[start][i]+DP(i,subSet);

                    if(result==-1 || tmp<result){
                        result = tmp;
                        backT[start][set]=i;

                    }
                }

            }
            prob[start][set]=result;
            return result;
        }
    }

    public void getPath(int start,int set){

        if(backT[start][set]==-1){
            return;
        }
        int x=backT[start][set];
        int mask=pow -1-(int)Math.pow(2,x);
        int masked=set &mask;

        Path.add(x+1);
        getPath(x,masked);
    }


    public void printEx(){
        System.out.println("****************");
        for(int i=0 ;i<size;i++){
            for(int j=0;j<pow;j++){
                System.out.print(prob[i][j]+" ");
            }
            System.out.println();
        }

        System.out.println("******* BT *********");
        for(int i=0 ;i<size;i++){
            for(int j=0;j<pow;j++){
                System.out.print(backT[i][j]+" ");
            }
            System.out.println();
        }
    }

    public void printResult(){
        System.out.println("\ndroga:");
        for(int i=0;i<Path.size();i++){
            System.out.print(Path.get(i)+" ");
        }
        System.out.println("\nkoszt:"+cost);
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


}
