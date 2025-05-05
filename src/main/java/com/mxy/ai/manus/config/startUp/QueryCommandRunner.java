package com.mxy.ai.manus.config.startUp;

import com.mxy.ai.manus.config.ManusProperties;
import com.mxy.ai.manus.planning.PlanningFactory;
import com.mxy.ai.manus.planning.coordinator.PlanIdDispatcher;
import com.mxy.ai.manus.planning.coordinator.PlanningCoordinator;
import com.mxy.ai.manus.planning.model.vo.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * @Author：mxy
 * @Date：2025-05-04-10:37
 * @Version：1.0
 * @Description：
 */
@Component
public class QueryCommandRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(QueryCommandRunner.class);

    @Autowired
    @Lazy
    private PlanningFactory planningFactory;

    @Autowired
    private PlanIdDispatcher planIdDispatcher;

    @Autowired
    private ManusProperties manusProperties;

    @Override
    public void run(String... args) throws Exception {
        // 只有当启用了控制台查询模式时才执行
        if (!manusProperties.getConsoleQuery()) {
            logger.info("控制台交互模式未启用，跳过命令行查询");
            return;
        }

        logger.info("启动控制台交互模式，请输入查询...");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your query (or type 'exit' to quit): ");
            String query = scanner.nextLine();

            if ("exit".equalsIgnoreCase(query)) {
                System.out.println("Exiting...");
                break;
            }

            // 使用 PlanIdDispatcher 生成唯一的计划ID
            String planId = planIdDispatcher.generatePlanId();
            PlanningCoordinator planningCoordinator = planningFactory.createPlanningCoordinator(planId);
            ExecutionContext context = new ExecutionContext();
            context.setUserRequest(query);
            context.setPlanId(planId);
            try {
                var executionContext = planningCoordinator.executePlan(context);
                System.out.println("Plan " + planId + " executed successfully");
                System.out.println("Execution Context: " + executionContext.getResultSummary());
            }
            catch (Exception e) {
                logger.error("执行查询时发生错误", e);
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
