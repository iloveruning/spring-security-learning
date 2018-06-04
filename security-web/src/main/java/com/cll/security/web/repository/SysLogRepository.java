package com.cll.security.web.repository;

import com.cll.security.web.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chenliangliang
 * @date 2018/6/4
 */
@Repository
public interface SysLogRepository extends JpaRepository<SysLog,Integer> {
}
