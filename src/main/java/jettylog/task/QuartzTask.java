package jettylog.task;

import org.springframework.stereotype.Component;

/**
 * User:huangtao
 * Date:2015-09-17
 * description：
 */
@Component
public class QuartzTask implements Runnable {

    private static int counter = 0;
    public void execute(){
        long ms = System.currentTimeMillis();
        System.out.println(ms);
        System.out.print("count:" + counter++);
    }

    @Override
    public void run() {
        long ms = System.currentTimeMillis();
        System.out.println(ms);
        System.out.println("counter:" + counter++);
    }
}
