package com.linkmoretech.notice.resposity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkmoretech.notice.entity.Notice;

public interface NoticeResposity extends JpaRepository<Notice, Long> {

	Notice findByUuid(String uuid);

}
