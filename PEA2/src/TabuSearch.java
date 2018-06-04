import java.util.ArrayList;
import java.util.Random;


public class TabuSearch {

    int [][] tabuList;
    int time=100;
    int cost=999999;
    int bestCost=999999;
    int iteration=2000;
    int critical=0,maxCritical=50;
    int size;
    int uba;
    int [][]cities;
    public Salesman s;
    public ArrayList<Integer> Path = new ArrayList<Integer>();
    public ArrayList<Integer> BestPath = new ArrayList<Integer>();
    public ArrayList<Integer> bestBestPath = new ArrayList<Integer>();
    TabuSearch(Salesman s){

        this.s=s;
        size=s.size;
        tabuList=new int [size][size];
      //  cities=s.cities.clone();
       // path = new int [size];
        for(int i=0;i<size;i++){
            //path[i]=i;
            Path.add(i);
            for(int j=0;j<size;j++){
                tabuList[i][j]=0;
            }
        }
       // System.out.println("Start");
       // printPath();

    }

    public void k(){

        java.util.Collections.shuffle(Path);


        for(int m=0;m<Path.size();m++){
            bestBestPath.add(Path.get(m));
        }

        uba=s.pathCostList(bestBestPath);

        for(int i=0;i<iteration;i++) {

            int start = 0;
            int dst = 0;


            for (int j = 1; j < size; j++) {
                for (int k = 2; k < size; k++) {

                    if (j != k) {
                        ArrayList<Integer> currPath = new ArrayList<Integer>();

                        for(int m=0;m<Path.size();m++){
                            currPath.add(Path.get(m));
                        }

                        swap(currPath,j, k);
                        // printPath(currPath);
                        int newCost = s.pathCostList(currPath);
                        if ((newCost < bestCost) && tabuList[j][k] == 0) {
                            start = j;
                            dst = k;
                            BestPath= new ArrayList<Integer>();

                            for(int m=0;m<Path.size();m++){
                                BestPath.add(currPath.get(m));
                            }

                            bestCost = newCost;
                        }

                    }
                }
            }

            if (start != 0) {
                decrementation();
                addTabu(start, dst);
            }
            if(uba>bestCost){
                uba=bestCost;
                bestBestPath= new ArrayList<Integer>();
                for(int m=0;m<BestPath.size();m++){
                          bestBestPath.add(BestPath.get(m));
                }
              //  printPath(bestBestPath);

            }

            if(uba<bestCost){
                critical++;
            }
            else{
                critical=0;
            }

            if(critical>maxCritical){

                java.util.Collections.shuffle(Path);
                for(int m=0;m<size;m++){
                    for(int n=0;n<size;n++){
                        tabuList[m][n]=0;
                    }
                }
                critical=0;
            }


            /*
            if(itPathCost>cost){
                critical++;
            }
            else{
                critical=0;
            }

            if(critical>maxCritical){
               // System.out.println();
               // System.out.println("*******Critical*******");
               // System.out.println();
                java.util.Collections.shuffle(Path);
                for(int m=0;m<size;m++){
                    for(int j=0;j<size;j++){
                        tabuList[m][j]=0;
                    }
                }
            }*/
        }

    }

//    public void Tabu(){
//
//       // printPath();
//        java.util.Collections.shuffle(Path);
//       // printPath();
//        for(int m=0;m<Path.size();m++){
//            BestPath.add(Path.get(m));
//        }
//
//        bestCost=s.pathCostList(BestPath);
//
//        for(int i=0;i<iteration;i++) {
//           // System.out.println(i+" it");
//            int itPathCost=s.pathCostList(Path);
//            int start = 0;
//            int dst = 0;
//
//            for (int j = 1; j < size; j++) {
//                for (int k = 2; k < size; k++) {
//
//                    if (j != k) {
//
//                        swap(j, k);
//                       // printPath();
//                         cost = s.pathCostList(Path);
//                        if ((cost < bestCost) && tabuList[j][k] == 0) {
//                            start = j;
//                            dst = k;
//                            BestPath= new ArrayList<Integer>();
//
//                            for(int m=0;m<Path.size();m++){
//                                BestPath.add(Path.get(m));
//                            }
//
//                            bestCost = cost;
//                        }
//                        if (start != 0) {
//                            decrementation();
//                            addTabu(start, dst);
//                        }
//                        if(bestCost<cost){
//                            critical++;
//                        }
//                        else{
//                            critical=0;
//                        }
//
//                        if(critical>maxCritical){
//
//                            java.util.Collections.shuffle(Path);
//                            for(int m=0;m<size;m++){
//                                for(int n=0;n<size;n++){
//                                    tabuList[m][n]=0;
//                                }
//                            }
//                            critical=0;
//                        }
//                    }
//                }
//            }
//
//
//
//            /*
//            if(itPathCost>cost){
//                critical++;
//            }
//            else{
//                critical=0;
//            }
//
//            if(critical>maxCritical){
//               // System.out.println();
//               // System.out.println("*******Critical*******");
//               // System.out.println();
//                java.util.Collections.shuffle(Path);
//                for(int m=0;m<size;m++){
//                    for(int j=0;j<size;j++){
//                        tabuList[m][j]=0;
//                    }
//                }
//            }*/
//        }
//
//    }


    public void swap (ArrayList<Integer>Path, int i ,int j){

        int tmp = Path.get(i);
        Path.set(i,Path.get(j));
        Path.set(j,tmp);

      //  System.out.println(i+" "+j);
      //  printPath();
      //  printList();

    }

    public void swap2(){

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){

                if(tabuList[i][j]==0){
                    int tmp = Path.get(i);
                    Path.set(i,Path.get(j));
                    Path.set(j,tmp);
                    cost=s.pathCostList(Path);

                    if(cost<bestCost){
                        for(int m=0;m<Path.size();m++){
                            BestPath.add(Path.get(m));
                        }
                        bestCost=s.pathCostList(BestPath);
                        addTabu(i,j);
                    }
                }

            }
        }


    }



    public void addTabu(int city,int city2){

        tabuList[city][city2]+= time;
        tabuList[city2][city]+= time;

    }

    // dekerementacja listy Tabu
    public void decrementation(){

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(tabuList[i][j]>0){
                    tabuList[i][j]-=1;

                }
            }
        }

    }


//    public void printTS(){
//        System.out.println();
//        for(int i=0;i<size;i++){
//            System.out.print(path[i]+" ");
//        }
//        s.pathCost(path);
//        System.out.println("cost: "+s.cost);
//
//    }

    public void printPath(ArrayList<Integer> Path){
        System.out.println();
        for(int i=0;i<Path.size();i++){
            System.out.print(Path.get(i)+" ");
        }
    }

    public void printList(){
        System.out.println();
        for(int i=0;i<size;i++){
            System.out.print(Path.get(i)+" ");
        }
        System.out.println(" Cost: "+s.pathCostList(Path));
        System.out.println("Best:");
        for(int i=0;i<BestPath.size();i++){
            System.out.print(BestPath.get(i)+" ");
        }
        System.out.println(" Cost: "+bestCost);
    }

    public void printBestCost(){
        System.out.println(uba);
    }


}
