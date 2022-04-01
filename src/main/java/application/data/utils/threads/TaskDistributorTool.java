package application.data.utils.threads;

public class TaskDistributorTool {
    public static void execute(Runnable task) {
        Thread thread = new Thread(task);
        thread.start();
    }
}
