package com.example.managersystem.dao;

import com.example.managersystem.model.UserEndPointsRelationEntity;
import com.example.managersystem.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * UserAndEndPointRelationDao
 */
@Repository
@Slf4j
public class UserEndPointsRelationDao {

    @Value("${user.endpoints.data.file.path}")
    String dataFilePath;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // single-point server, use ReentrantLock
    private final Lock lock = new ReentrantLock();

    public Optional<UserEndPointsRelationEntity> findByUserId(long userId) {
        Map<Long, UserEndPointsRelationEntity> dataMap = loadData();
        return Optional.ofNullable(dataMap.get(userId));
    }

    public boolean update(UserEndPointsRelationEntity entity) {
        log.info("try updating entity {}", entity
        );
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(200, TimeUnit.MILLISECONDS);
            if (isLocked) {
                Map<Long, UserEndPointsRelationEntity> dataMap = loadData();
                dataMap.put(entity.getUserId(), entity);
                saveData(dataMap);
                return true;
            } else {
                // 如果在指定时间内未能获取锁，则打印日志或进行其他处理
                log.error("Lock acquire failure");
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // 处理中断异常
        } catch (Exception e) {
            log.error("Unexpected error", e);
        } finally {
            // 如果成功获取到锁，则释放锁
            if (isLocked) {
                lock.unlock();
            }
        }
        return false;
    }

    private Map<Long, UserEndPointsRelationEntity> loadData() {
        File file = new File(dataFilePath);
        if (file.exists()) {
            try {
                return JsonUtil.fromJson(file, new TypeReference<Map<Long, UserEndPointsRelationEntity>>() {});
            } catch (MismatchedInputException e) {
                return new HashMap<>();
            } catch (IOException e) {
                throw new RuntimeException("load data failure", e);
            }
        }
        return new HashMap<>();
    }

    private void saveData(Map<Long, UserEndPointsRelationEntity> dataMap) {
        try {
            File resultFile = new File(dataFilePath);
            if (!resultFile.exists()) {
                boolean createRes = resultFile.createNewFile();
                if (!createRes) {
                    log.error("create base file failure", new RuntimeException());
                    throw new RuntimeException("create base file failure");
                }
            }
            JsonUtil.toJsonFile(dataMap, resultFile);
        } catch (IOException e) {
            throw new RuntimeException("save data failure", e);
        }
    }
}