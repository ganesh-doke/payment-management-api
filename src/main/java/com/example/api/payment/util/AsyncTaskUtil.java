package com.example.api.payment.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
@Slf4j
@RefreshScope
public class AsyncTaskUtil {

    @Value("${config.thread.executor.timeout:10000}")
    private int timeoutMills;

    public <T> List<T> run(List<Callable<T>> callables)
            throws ExecutionException, InterruptedException, TimeoutException {

        log.info("Start: run");
        ExecutorService executor = null;
        List<T> responseList = new ArrayList<>();
        try {
            executor = Executors.newFixedThreadPool(callables.size());

            List<Future<T>> futures = executor.invokeAll(callables);
            for (Future<T> future : futures) {
                T response = future.get(timeoutMills, TimeUnit.MILLISECONDS);
                responseList.add(response);
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("Error in parallel execution", e);
            throw e;
        } finally {
            if (executor != null) {
                executor.shutdown();
                if (!executor.isShutdown()) {
                    List<Runnable> killedTasks = executor.shutdownNow();
                    log.error("Total {} tasks being closed during parallel execution", killedTasks.size());
                }
            }
        }

        log.info("End: run");
        return responseList;
    }
}
