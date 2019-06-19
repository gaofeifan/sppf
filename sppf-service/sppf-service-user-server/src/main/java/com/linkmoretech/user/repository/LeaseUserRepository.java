package com.linkmoretech.user.repository;

import com.linkmoretech.user.entity.LeaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: alec
 * Description:
 * @date: 16:22 2019-05-22
 */
public interface LeaseUserRepository extends JpaRepository<LeaseUser, Long> {

}
