public class StressMajorization implements Runnable {
    //nは頂点数、dimは次元数、dは全頂点最短経路の行列、wは重み行列
    
    private int n;
    private int dim;
    private double [][] d;
    private double [][] w;
    private double [][] Xt;
    private static final double EPS = 1e-4;
    private static final double INF = 1e9;
    private Result result;


    StressMajorization(int [][] input, int dim) {
        this.dim = dim;
        this.n = -1;
        for(int i = 0; i < input.length; i++) {
            n = Math.max(n, Math.max(input[i][0], input[i][1]));
        }

        d = new double[n][n];
        w = new double[n][n];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i == j) {
                    d[i][j] = 0;
                } else {
                    d[i][j] = INF;
                }
            }
        }

        for(int i = 0; i < input.length; i++) {
            int from = input[i][0]-1;
            int to = input[i][1]-1;
            int weight = input[i][2];

            d[from][to] = weight;
            d[to][from] = weight;
        }
    }

    StressMajorization(int id ,StressMajorization sm, Result result) {
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
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
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

    private double norm(double[] m) {
        double res = 0;
        for(int i = 0; i < m.length; i++) {
            res += m[i]*m[i];
        }
        return Math.sqrt(res);
    }
    //stress関数
    private double stress(double[][] X) {
        double stressVal = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i != j) {
                    double [] Xij = new double[dim];
                    for(int k = 0; k < dim; k++) {
                        Xij[k] = X[i][k]-X[j][k];
                    }
                    stressVal += w[i][j]*(norm(Xij)-d[i][j])*(norm(Xij)-d[i][j]);
                }
            }
        }
        return stressVal;
    }

    public void run() {
        Xt = new double[n][dim];
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
                            Xij[k] = Xt[i][k]-Xt[j][k];
                        }

                        if(Graph.norm(Xij) < EPS) {
                            up += w[i][j]*(Xt[j][a]+d[i][j]*(Xt[i][a]-Xt[j][a])) / norm(Xij);
                        } else {
                            up += w[i][j]*(Xt[j][a]);
                        }

                        Xt[i][a] =   up / down;                      
                    }
                }
            }

            double cur = stress(Xt);
            if(prev - cur < EPS) {
                prev = cur;
                break;
            }

            prev = cur;
        } while(true);

        result.addResult(new Output(prev, Xt));//curとXtを保存し、Resultに格納する
    }


}
