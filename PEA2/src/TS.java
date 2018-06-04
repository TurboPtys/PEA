import java.util.ArrayList;

public class TS {

    public int [][] tabuList;        // tabu list
    public Salesman s;              //klasa przechowywujaca graf
    public ArrayList<Integer> Path = new ArrayList<Integer>();//sciezka
    public ArrayList<Integer> bestBestSol = new ArrayList<Integer>();
    public int itr=2000;                                //liczba iteracji petli
    public int time=50;                                 // kadencja
    public int critical=0;int maxCritical=0;
    int bestBestCost=999999;
    public int t;

    TS(Salesman s){

        this.s=s;

        tabuList=new int [s.size][s.size];

        for(int i=0;i<s.size;i++){
            //path[i]=i;
            Path.add(i);
            for(int j=0;j<s.size;j++){
                tabuList[i][j]=0;
            }
        }
        time=s.size;

        maxCritical=s.size*2;//dywersyfikacji

    }

    public void Solution(){

    //permutacja

        java.util.Collections.shuffle(Path);


    ArrayList<Integer>newSol= new ArrayList<Integer>();
    //przypisanie obecnego wyniku jako najlepszy
    bestBestCost =s.pathCostList(Path);
    ArrayList<Integer> BestSol= new ArrayList<Integer>();


    //glowa petla
    for(int i=0;i<itr;i++){
        int city1=0;
        int city2=0;
        int bestCost=999999;
        for(int j=1;j<s.size;j++){
            for(int k=2;k<s.size;k++){
                if(j!=k){

                  newSol= new ArrayList<Integer>();

                    for(int m=0;m<Path.size();m++){
                        newSol.add(Path.get(m));
                    }

                    swap(newSol,j,k);

                    int newCost=s.pathCostList(newSol);

                    if(newCost<bestCost && tabuList[j][k]==0){
                        BestSol= new ArrayList<Integer>();
                        for(int m=0;m<Path.size();m++){
                           BestSol.add(newSol.get(m));
                        }
                        city1=j;
                        city2=k;
                        bestCost=newCost;
                    }

                }
            }
        }


        if(city1 !=0){
            //dekrementacja tabu oraz dodanie do tabu
            decrementation();
            addTabu(city1,city2);

        }
        //zapisywanie lepszego wyniku
        if(bestBestCost>bestCost){
            bestBestCost=bestCost;
            t=i;
            bestBestSol= new ArrayList<Integer>();
            for(int m=0;m<BestSol.size();m++){
                bestBestSol.add(BestSol.get(m));
            }
        }
        swap(Path,city1,city2);


        //dywersyfikacji
       criticalEvent(bestCost,bestBestSol);

       System.out.print(i+"\t");
       printBestCost();

    }
    }

    public void swap (ArrayList<Integer>Path, int i ,int j){

        int tmp = Path.get(i);
        Path.set(i,Path.get(j));
        Path.set(j,tmp);

    }

    public void criticalEvent(int bestCost,ArrayList<Integer> bestBestSol){
        if(bestBestCost<bestCost){
            critical++;
        }
        else{
            critical=0;
        }

        if(critical>maxCritical){


                        ArrayList<Integer> tmp = new ArrayList<Integer>();
            for(int n=0;n<Path.size();n++){
                tmp.add(Path.get(n));
            }


            for(int m=0;m<s.size;m++){

                java.util.Collections.shuffle(tmp);

                if(s.pathCostList(Path)>s.pathCostList(tmp)){
                    Path= new ArrayList<Integer>();
                    for(int n=0;n<Path.size();n++){
                        Path.add(tmp.get(n));
                    }

                    if(bestBestCost>s.pathCostList(Path)){

                        bestBestSol= new ArrayList<Integer>();
                        for(int n=0;n<Path.size();n++){
                            bestBestSol.add(Path.get(n));
                        }

                    }

                    //break;
                }
            }





            for(int m=0;m<s.size;m++){
                for(int n=0;n<s.size;n++){
                    tabuList[m][n]=0;
                }
            }
            critical=0;
        }
    }


    public void addTabu(int city1,int city2){

        tabuList[city1][city2]+= time;
        tabuList[city2][city1]+= time;

    }

    public void decrementation(){

        for(int i=0;i<s.size;i++){
            for(int j=0;j<s.size;j++){
                if(tabuList[i][j]>0){
                    tabuList[i][j]--;

                }
            }
        }

    }

    public void printBestCost(){
        System.out.println(bestBestCost);
    }

    public void printPath(){
        System.out.print(+ s.pathCostList(bestBestSol)+"\t\t");
        for(int i=0;i<bestBestSol.size();i++){
            System.out.print(bestBestSol.get(i)+" ");
        }
        System.out.println(bestBestSol.get(0));
        System.out.println();

    }

}


