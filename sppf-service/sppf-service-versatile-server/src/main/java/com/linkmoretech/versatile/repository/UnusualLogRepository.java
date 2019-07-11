package com.linkmoretech.versatile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkmoretech.versatile.entity.UnusualLog;
/**
 * 异常日志
 * @author jhb
 * @Date 2019年6月27日 上午10:53:47
 * @Version 1.0
 */
public interface UnusualLogRepository extends JpaRepository<UnusualLog, Long> {

}
