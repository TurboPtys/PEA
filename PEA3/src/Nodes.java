import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;

public class Nodes implements Cloneable{

    public int max=9999999;
    public ArrayList<Integer> Path;
    public int cost=0;
    public int value=0;

    Nodes(ArrayList<Integer> Path,Salesman s){
        this.Path=Path;
        cost=s.pathCostList(Path);
        value =max -cost;
    }

    public static Nodes newInstance(Nodes n,Salesman s){
        Nodes nodes = new Nodes(n.Path,s);
        nodes.Path= n.Path;
        nodes.cost=s.pathCostList(n.Path);
        nodes.value =nodes.max -nodes.cost;

        return nodes;
    }

    public static ArrayList<Integer> copyPath(Nodes n){

        ArrayList<Integer> a = new ArrayList<Integer>();
        a= n.Path;

        return a;
    }

    public static int copyCity(ArrayList<Integer> Path, int i ){

        int a = Path.get(i);

        return a;
    }

    public static int copyValue (Nodes n){
        int a = n.max -n.cost;

        return a;
    }

    public String printPath(){
        String r ="";
        for(int i=0;i<Path.size();i++){
            r+=Path.get(i)+ " ";
        }
        r+="  = "+cost+" \n";
        return r;
    }

    public void countValue (Salesman s){

        cost=s.pathCostList(Path);
        value=max-cost;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();


    }

    public int getValue(){
        return value;
    }

    public static Comparator<Nodes> ValueComparator = new Comparator<Nodes>() {

        public int compare(Nodes s1, Nodes s2) {
            int v1 = s1.getValue();
            int v2 = s2.getValue();

            //ascending order
            return v1-v2;

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
}
