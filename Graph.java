//頂点に関するクラス
public class Graph {
    //nodeには各頂点の座標が格納される
    private double[][] node;

    //matrixには重み付き隣接行列が格納される
    private double[][] matrix;


    Graph(int n, int dim) {
        node = new double[n][dim];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < dim; j++) {
                node[i][j] = 20*Math.random();
            }
        }
    }

    Graph(double[][] m) {
        matrix = m.clone();
    }


    void syo() {
        System.out.println("fffff");
        for(int i = 0; i < this.matrix.length; i++) {
            for(int j = 0; j < this.matrix[i].length; j++) {
                System.out.println(this.matrix[i][j]);
            }
        }
    }

    double cell(int row, int column) {
        return matrix[row][column];
    }

    double[] row(int i) {
        double[] res = new double[matrix.length];

        for(int j = 0; j < matrix.length; j++) {
            res[i] = matrix[j][i];
        }

        return res;
    } 


    double[] column(int i) {
        return matrix[i];
    }

    int getRowNum() {
        return matrix[0].length;
    }

    int getColumnNum() {
        return matrix.length;
    }

    static double norm(double[] m) {
        double res = 0;
        for(int i = 0; i < m.length; i++) {
            res += m[i]*m[i];
        }
        return Math.sqrt(res);
    }
}
