import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Genetic {

    public int maxV =99999;
    public int bestSolution = 999999;    //najlepsze rozwiązanie
    public Nodes bestElement;
    public int numbersOfCities;        //liczba miast
    public ArrayList<Integer> nodes;               //wierzcholki
    public ArrayList<Nodes> population;
    public ArrayList<Nodes> parentsPopulation= new ArrayList<Nodes>();
    public ArrayList<Nodes> childrenPopulation= new ArrayList<Nodes>();
    public Salesman s;

    Genetic(Salesman s){
        this.s=s;
        numbersOfCities=s.size;
        population=new ArrayList<Nodes>();
        nodes = new ArrayList<Integer>();
        for(int i=0;i<numbersOfCities;i++){
            nodes.add(i);
        }

        bestElement=new Nodes(nodes,s);

    }

    int solution(int populationSize,int itr, int childrenPairsSize,float mutationProbability,float crossProbability ){

        int current_best_solution;

        createStartingPopulation(populationSize); // populacja startowa
        bestSolution=population.get(0).value;
        bestElement=population.get(0);
        int parentsPopulationSize =populationSize/2;
        int last=0;
       // printPopulation(population);


        for(int i=0;i<itr;i++){
            sortPopulation(population);
            // wybranie populacji rodziców
            parentsPopulation=findParentsInPopulation(populationSize,parentsPopulationSize);

            for(int j=0; j<childrenPairsSize;j++){

                // tworzenie par dzieci (selekcja, krzyżowanie,mutacja
               ArrayList<Nodes> childrenPair =generateChidrenPair(mutationProbability,crossProbability);

               //dodanie nowych osobników do populacji dzieci
               childrenPopulation.add(childrenPair.get(0));
               childrenPopulation.add(childrenPair.get(1));

            }

            sortPopulation(childrenPopulation);

            //wybranie nowej populacjii
            population=renegeratePopulation(populationSize);

            childrenPopulation = new ArrayList<Nodes>();

            current_best_solution=Nodes.copyValue(population.get(0));


            if(current_best_solution>bestSolution){
              //  System.out.println(i+")"+population.get(0).cost);
                last =i;
                bestSolution=Nodes.copyValue(population.get(0));
                bestElement=Nodes.newInstance(population.get(0),s);
            }

        }
        System.out.print(last+"\t");
        return bestSolution;

    }


    //tworzenie populacji startowej

    void createStartingPopulation(int populationSize){

        //population= new int [populationSize][numbersOfCities];

            population.add(new Nodes(nodes,s));

        for(int i=0;i<populationSize-1;i++){
            ArrayList<Integer> singlePopulationElement = new ArrayList<Integer>();
            singlePopulationElement=(ArrayList<Integer>) nodes.clone();
            java.util.Collections.shuffle(singlePopulationElement);
            Nodes n = new Nodes(singlePopulationElement,s);
            population.add(n);

        }
    }


    // tworzenie pary nowych dzieci

    ArrayList<Nodes> generateChidrenPair(float mutationProbability,float crossP){

        ArrayList<Nodes> childrenPair = new ArrayList<Nodes>();

        ArrayList<Nodes> parentsPair = generateParentsPair(crossP);


        childrenPair= crossParent(parentsPair);

       // printPopulation(childrenPair);

            childrenPair.get(0).Path = attemptChildMutation(mutationProbability, childrenPair.get(0));

            childrenPair.get(1).Path = attemptChildMutation(mutationProbability, childrenPair.get(1));
       // System.out.println("\n");
       // printPopulation(childrenPair);
       // System.out.println("end\n");

        childrenPair.get(0).countValue(s);
        childrenPair.get(1).countValue(s);
       // printPopulation(childrenPair);
       // System.out.println("+++++++++++++++++++++\n");

        return childrenPair;
    }


    //mutacja

    ArrayList<Integer> attemptChildMutation(float mutationProbability,Nodes n){

        Random rand = new Random();
        float r = rand.nextFloat();

        if(r<=mutationProbability){
            int firsElementIndex =0;
            int secondElementIndex=0;

            do{
                firsElementIndex=rand.nextInt(n.Path.size());
                secondElementIndex=rand.nextInt(n.Path.size());
            } while(firsElementIndex==secondElementIndex && (secondElementIndex-firsElementIndex<=(n.Path.size()*0.4f)));


           // for(int i=0;i<((secondElementIndex-firsElementIndex)/2);i++){
                return swap(n,firsElementIndex,secondElementIndex);
            //}

        }
        else{

            return n.Path;
        }

    }

    // wybranie pary rodzicielskiej z populacji rodzicielskiej

    ArrayList<Nodes> generateParentsPair (float crossP){

        ArrayList<Nodes> parentsPair = new ArrayList<Nodes>();

        int fatherIndex =0;
        int motherIndex=0;

        Random rand = new Random();

        float r ;

        do {

            fatherIndex=0;
            motherIndex=0;

            r=rand.nextFloat();
            while (fatherIndex == motherIndex) {


                fatherIndex = rand.nextInt(parentsPopulation.size());
                motherIndex = rand.nextInt(parentsPopulation.size());
            }

        }while (crossP<r);


        parentsPair.add(Nodes.newInstance(parentsPopulation.get(fatherIndex),s));
        parentsPair.add(Nodes.newInstance(parentsPopulation.get(motherIndex),s));

        return parentsPair;

    }


    //ruletka

    ArrayList<Nodes> findParentsInPopulation(int populationSize,int parentPopulationSize){

        ArrayList<Nodes> selectedParents = new ArrayList<Nodes>();
        long permutationsEvaluationSum =0;


        for(int i=0;i<populationSize;i++){
            permutationsEvaluationSum+=population.get(i).value;
        }

        long randomTargetValue;

        sortPopulation(population);

        for(int i=0;i<parentPopulationSize;i++){

            randomTargetValue=generateRandomizedTargetValue(permutationsEvaluationSum);

            long currentValue=0;
            long previousValue=0;

            for(int j=0;j<populationSize;j++){
                currentValue+=(long)population.get(j).value;

                if((previousValue<=randomTargetValue)&&(randomTargetValue<=currentValue)){
                    //permutationToAddAsParent
                    selectedParents.add(Nodes.newInstance(population.get(i),s));
                    break;
                }
                else{
                    previousValue=previousValue+(long)population.get(j).value;
                }
            }
        }

        return selectedParents;

    }

    //krzyżowanie

    ArrayList<Nodes> crossParent(ArrayList<Nodes> parentsPair){

        int childSize = parentsPair.get(0).Path.size();
        int firstCrossPoint=0;
        int secondCrossPoint=0;

        ArrayList<Integer>child = new ArrayList<Integer>();
        ArrayList<Integer>child2=new ArrayList<Integer>();
        for(int i=0;i<childSize;i++){
            child.add(-1);
            child2.add(-1);
        }

        Random rand = new Random();

        while(firstCrossPoint==secondCrossPoint){
            firstCrossPoint=rand.nextInt(childSize);
            secondCrossPoint=rand.nextInt(childSize);

        }

        if(firstCrossPoint>secondCrossPoint){
            int tmp = secondCrossPoint;
            secondCrossPoint=firstCrossPoint;
            firstCrossPoint=tmp;
        }

        for(int i =firstCrossPoint;i<secondCrossPoint;i++){

            child.set(i,Nodes.copyCity(parentsPair.get(1).Path,i));
            child2.set(i,Nodes.copyCity(parentsPair.get(0).Path,i));

        }


        for(int i=0;i<childSize;i++){

            boolean check = false;
           // boolean check2 = false;

            for(int j=firstCrossPoint;j<secondCrossPoint;j++){

               // if(i==parentsPair.get(0).Path.get(j) || i==parentsPair.get(1).Path.get(j) ){

               //     check= true;
                   // break;
               // }

            }

            if(!check){

                child.set(parentsPair.get(0).Path.indexOf(i),i);
                child2.set(parentsPair.get(1).Path.indexOf(i),i);

            }

        }

        for(int i=0; i<childSize;i++){

            if(child.get(i)==-1){
                int index=-1;
                boolean t = false;
                boolean in =false;

                index =parentsPair.get(1).Path.indexOf(parentsPair.get(0).Path.get(i));
                for(int j=firstCrossPoint;j<secondCrossPoint;j++) {

                    if (parentsPair.get(0).Path.get(index) == parentsPair.get(1).Path.get(j)) {
                        t = true;
                        if (index == j) {
                            in = true;
                        }
                    }
                }

                    if (!t) {

                        child.set(i, Nodes.copyCity(parentsPair.get(0).Path, index));

                    }
                    else if (t && !in){
                        //child.set(i,Nodes.copyCity(parentsPair.get(1).Path,index));

                        for(int k=0;k<child.size();k++){
                            if(!child.contains(k)){
                                child.set(i,k);
                            }
                        }
                    }
                    else{
                        for(int k=0;k<child.size();k++){
                            if(!child.contains(k)){
                                child.set(i,k);
                            }
                        }
                    }

            }



            if(child2.get(i)==-1){
               // System.out.println("@@@@@@@@ second child");
                int index=-1;
                boolean t = false;
                boolean in =false;

                index =parentsPair.get(0).Path.indexOf(parentsPair.get(1).Path.get(i));

//                System.out.println("??...... "+index+".......??");
//                System.out.println(parentsPair.get(1).Path.get(i));//value
//                System.out.println(parentsPair.get(1).Path.get(index));//index of value
//                System.out.println("''''''''''''''''''''''''''''''''");
//

                for(int j=firstCrossPoint;j<secondCrossPoint;j++) {

                    if (parentsPair.get(1).Path.get(index) == parentsPair.get(0).Path.get(j)) {
                        t = true;
                        if (index == j) {
                            in = true;
                        }
                    }
                }

                if (!t) {

                    child2.set(i, Nodes.copyCity(parentsPair.get(1).Path, index));

                }
                else if (t && !in){
                    //child2.set(i,Nodes.copyCity(parentsPair.get(0).Path,index));
                    for(int k=0;k<child2.size();k++){
                        if(!child2.contains(k)){
                            child2.set(i,k);
                        }
                    }
                }
                else{


                    for(int k=0;k<child2.size();k++){
                        if(!child2.contains(k)){
                            child2.set(i,k);
                        }
                    }
                }


            }

        }

        ArrayList<Nodes> y = new ArrayList<Nodes>();
        Nodes h = new Nodes(child,s);
        y.add(Nodes.newInstance(h,s));
        h = new Nodes(child2,s);
        y.add(Nodes.newInstance(h,s));

        return y;
    }

    long generateRandomizedTargetValue(long permutationsEvaluationSum){
        Random rand = new Random();
        float r = rand.nextFloat();
        float target=r*(permutationsEvaluationSum);

        return (long)target;
    }

    //wybór nowej populacji z populacji dzieci i poprzedniej populacji

    ArrayList<Nodes> renegeratePopulation(int populationSize){

        ArrayList<Nodes> newPopulation = new ArrayList<Nodes>();
        ArrayList<Nodes> currentPopulation= new ArrayList<Nodes>();
        ArrayList<Nodes> currentPopulationChildren= new ArrayList<Nodes>();
        currentPopulation=(ArrayList<Nodes>)population.clone();
        currentPopulationChildren =(ArrayList<Nodes>)childrenPopulation.clone();

        for(int i=0; i<populationSize;i++){

            if(currentPopulationChildren.isEmpty() && !currentPopulation.isEmpty()){
                newPopulation.add(Nodes.newInstance(currentPopulation.get(0),s));
                currentPopulation.remove(0);
                continue;
            }

            if(!currentPopulationChildren.isEmpty() && currentPopulation.isEmpty()){
                newPopulation.add(Nodes.newInstance(currentPopulationChildren.get(0),s));
                currentPopulation.remove(0);
                continue;
            }

            if(currentPopulationChildren.get(0).cost<currentPopulation.get(0).cost){

                newPopulation.add(Nodes.newInstance(currentPopulationChildren.get(0),s));
                currentPopulationChildren.remove(0);

            }
            else{
                newPopulation.add(Nodes.newInstance(currentPopulation.get(0),s));
                currentPopulation.remove(0);
            }

        }


        return newPopulation;
    }

    void sortPopulation(ArrayList<Nodes> population){

        for(int i=0;i<population.size();i++){
            population.get(i).countValue(s);
        }

        Collections.sort(population,Nodes.ValueComparator);
        Collections.reverse(population);


    }

    void printPopulation(ArrayList<Nodes> population){
        for(int i=0;i<population.size();i++){

                System.out.print(i +") "+population.get(i).value+"  "+population.get(i).printPath());

        }
    }

    public ArrayList<Integer> swap (Nodes n, int i ,int j){

       // System.out.println("in swap parents population");
       // printPopulation(population);

        for(int k=0;k<=((j-i)/2);k++) {

            int tmp = n.Path.get(i+k);
            n.Path.set(i+k, n.Path.get(j-k));
            n.Path.set(j-k, tmp);
        }

        n.countValue(s);

        return n.Path;
    }

}
