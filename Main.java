public class Main {
    private final static int TH_COUNT = 3;
    private final static String G_FILE = "sample.csv";
    public static void main(String... args) throws InterruptedException{
        long startTimeMsec = System.currentTimeMillis();

        StressMajorization preparation = new StressMajorization(double);
        preparation.ready();

        Thread[] smThreads = new Thread[TH_COUNT];
        for(int i = 0; i < TH_COUNT; i++) {
            StressMajorization sm = new StressMajorization(preparation);
        }

        

        long entTimeMsec = System.currentTimeMillis();
        System.out.println("Processing time:" + (entTimeMsec - startTimeMsec) + " msec");
    }
}