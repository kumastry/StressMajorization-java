public class Main {
    private final static int TH_COUNT = 3;
    private final static int DIMENSION = 2;

    public static void main(String... args) throws InterruptedException{
        long startTimeMsec = System.currentTimeMillis();
        //{from, to weight}(無向グラフ)
        int[][] input = {
            {1, 2, 4},
            {1, 3, 3},
            {1, 4, 6},
            {2, 4, 4},
            {3, 4, 4},
        };

        StressMajorization preparation = new StressMajorization(input, DIMENSION);
        preparation.ready();

        Result result = new Result();

        Thread[] smThreads = new Thread[TH_COUNT];
        for(int i = 0; i < TH_COUNT; i++) {
            StressMajorization sm = new StressMajorization(i+1,preparation, result);
            smThreads[i] = new Thread(sm);
            smThreads[i].start();
        }

        for(int i = 0; i < TH_COUNT; i++) {
            smThreads[i].join();
        }

        result.draw();
        long entTimeMsec = System.currentTimeMillis();
        System.out.println("Processing time:" + (entTimeMsec - startTimeMsec) + " msec");
    }
}