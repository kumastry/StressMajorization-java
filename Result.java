import java.util.ArrayList;

public class Result {
    private ArrayList<Output> results = new ArrayList<>();
    Output finalResult;
    Result(){}

    void addResult(Output ot) {
        synchronized(this) {
            results.add(ot);
        }
    }
    
    void draw() {
        double minStress = 1e18;
        //System.out.println("111111");
        int index = 1;
        for(Output ot : results) {
            System.out.println(index + " : " + ot.stress);
            if(ot.stress < minStress) {
                minStress = ot.stress;
                finalResult = ot;
            }
            index ++;
        }

        System.out.println("");
        System.out.println("Ultimate result");
        index = 1;
        for(double[] coor : finalResult.Xt) {
            System.out.print(index++ + ". " + "(");
            for(int i = 0; i < coor.length; i++) {
                double coordinate = ((double)Math.round(coor[i] * 100))/100;
                if(i != coor.length-1) {
                    System.out.print(coordinate + ", ");
                } else {
                    System.out.print(coordinate);
                }
            }
            System.out.println(")");
        }

        System.out.println("Stress: " + finalResult.stress);
        System.out.println("");
        
    }
}
