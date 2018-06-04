import java.io.FileNotFoundException;

public class Test {
    public static void main (String[] arg)throws FileNotFoundException {


        long start,stop;
        int itr =1;


        Salesman s = new Salesman();
        s.load();
        //TabuSearch t= new TabuSearch(s);
        //t.k();
        for(int i=0;i<itr;i++) {

            TS t= new TS(s);
            start=System.nanoTime();
            t.Solution();
            stop= System.nanoTime();
            System.out.print((stop - start)+"\t");
            System.out.println("\nOstatnia zmiana: "+t.t);
           // t.printBestCost();
            t.printPath();
//            TabuSearch t = new TabuSearch(s);
//            start = System.nanoTime();
//            //t.Tabu();
//            t.k();
//            stop = System.nanoTime();
//            System.out.print((stop - start)+"\t");
//            t.printBestCost();
        }

        s.printCities();





    }
}
