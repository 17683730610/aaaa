package com.example.dao;

import com.example.pojo.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author lijinlong
 * @version 1.0
 * @date 2019/6/12 15:47
 */
public interface JobDao extends JpaRepository<Job,Long>,JpaSpecificationExecutor<Job> {
    /**
     * test
     * @param name
     * @return
     */
    List<Job> findJobsByName(String name);
}
