public class GraphDrawer {
    private double[][] X;
    GraphDrawer(double[][] matrix) {
        X = matrix.clone();        
    }

    void draw()  {
        for(int i = 0; i < X.length; i++) {
            System.out.println("");
        }
    }
}
