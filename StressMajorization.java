public class StressMajorization implements Runnable {
    //nは頂点数、dimは次元数、dは全頂点最短経路の行列、wは重み行列
    
    private int n;
    private int dim;
    private double [][] d;
    private double [][] w;
    private Vertex Xt;
    private static final double EPS = 1e-4;
    private static final double INF = 1e9;

    StressMajorization(Vertex matrix) {
        n = matrix.getColumnNum();
        dim = matrix.getRowNum();
        d = new double[n][n];
        w = new double[n][n];
        matrix = matrix;
    }

    StressMajorization(StressMajorization sm) {
        this.n = sm.n;
        this.dim = sm.dim;
        this.d = sm.d.clone();
        this.w = sm.w.clone();
    }

    //dとwの準備
    public void ready() {
        for (int k = 0; k < n; k++){       
            for (int i = 0; i < n; i++) {    
                for (int j = 0; j < n; j++) {  
                    d[i][j] =   Math.min(d[i][j], d[i][k] + d[k][j]);
                }
            }
        }
    

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i != j) {
                    w[i][j] = 1 / (d[i][j]*d[i][j]);
                }
            }
        }
    }

    //stress関数
    private double stress(Vertex X) {
        double stressVal = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i != j) {
                    double [] Xij = new double[dim];
                    for(int k = 0; k < dim; k++) {
                        Xij[k] = X.cell(i, k)-X.cell(j, k);
                    }
                    stressVal += w[i][j]*(Vertex.norm(Xij)-d[i][j])*(Vertex.norm(Xij)-d[i][j]);
                }
            }
        }
        return stressVal;
    }

    public void run() {
        Xt = new Vertex(n, dim);
        double prev = INF;

        do {

            for(int i = 0; i < n; i++) {
                for(int a = 0; a < n; a++) {
                    double up = 0;
                    double down = 0;
                    for(int j = 0; j < n; j++) {
                        if(i == j) continue;
                        double [] Xij = new double[dim];
                        for(int k = 0; k < dim; k++) {
                            Xij[k] = Xt.cell(i, k)-Xt.cell(j, k);
                        }

                        if(Vertex.norm(Xij) < EPS) {
                            up += w[i][j]*(Xt[j][a]+d[i][j]*(Xt[i][a]-Xt[j][a])) / Vertex.norm(Xij)
                        } else {
                            up += w[i][j]*(X[j][a]);
                        }

                        Xt[i][a] =   up / down;                      
                    }
                }
            }

            double cur = stress(Xt);
            if(prev - cur < EPS) {
                break;
            }

            prev = cur;
        } while(true);
    }

    Vertex result() {
        return Xt;
    }


}
