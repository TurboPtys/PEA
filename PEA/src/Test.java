import java.io.FileNotFoundException;

public class Test {

    public static void main (String[] arg)throws FileNotFoundException {


        long start,stop;
//        for(int i=0;i<51;i++){
//            Salesman s = new Salesman();
//            s.load();
//            start = System.nanoTime();
//            s.TSP();
//            stop = System.nanoTime();
//            System.out.println("\nczas wykonania:"+(stop - start)+"ns");
//        }


            Salesman s = new Salesman();
            s.load();
            //s.loadTSP();
            start = System.nanoTime();
            s.initDP();
            stop = System.nanoTime();
           s.printCities();
            s.printResult();
            //s.printEx();

            System.out.println("\nczas wykonania:"+(stop - start)+"ns");




    }


}
