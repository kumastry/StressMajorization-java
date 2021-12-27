import java.util.ArrayList;

public class Result {
    private ArrayList<Output> results = new ArrayList<>();
    Result() {}

    void addResult(Output ot) {
        synchronized(this) {
            results.add(ot);
        }
    }

    void draw() {
        System.out.println("111111");
        int index = 1;
        for(Output ot : results) {
            System.out.println(index  + " : " + ot.stress);
            index ++;
        }
        System.out.println(index);
    }
}
