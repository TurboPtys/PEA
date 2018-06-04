import java.io.FileNotFoundException;

public class Test {
    public static void main (String[] arg)throws FileNotFoundException {


        long start,stop;
        int populationSize =200;
        int itr =200;
        int childrenPairsSize =50;//populationSize/2;
        float mutationProbability =0.08f;
        float crossProbability = 0.8f;


        for(int i =0;i<50;i++) {
            Salesman s = new Salesman();
            s.load();
            //TabuSearch t= new TabuSearch(s);
            //t.k();
            Genetic g = new Genetic(s);
            //g.createStartingPopulation(5);
            //g.printPopulation();

            start = System.nanoTime();
            int r = g.solution(populationSize, itr, childrenPairsSize, mutationProbability, crossProbability);
            stop = System.nanoTime();
            System.out.print((stop - start) + "\t");
            System.out.println(9999999-r);
        }
        //g.solution(50,8,12,0.2f);
        //g.solution(50,2,10,0.2f,0.5f);
       // g.printPopulation();
       /* for(int i=0;i<itr;i++) {

           // TS t= new TS(s);
            start=System.nanoTime();
            //t.Solution();
            stop= System.nanoTime();
            System.out.print((stop - start)+"\t");
           // System.out.println("\nOstatnia zmiana: "+t.t);
           // t.printBestCost();
           // t.printPath();
//            TabuSearch t = new TabuSearch(s);
//            start = System.nanoTime();
//            //t.Tabu();
//            t.k();
//            stop = System.nanoTime();
//            System.out.print((stop - start)+"\t");
//            t.printBestCost();
        }*/

        //s.printCities();





    }
}
