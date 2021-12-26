public class Output {
    double stress;
    double[][] Xt;

    Output(double stress, double[][] Xt) {
        this.stress = stress;
        this.Xt = Xt.clone();
    }
}
