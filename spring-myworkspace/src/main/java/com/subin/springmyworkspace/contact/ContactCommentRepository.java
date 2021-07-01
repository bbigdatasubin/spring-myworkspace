package com.subin.springmyworkspace.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

// ������ ���� �������̽� ����
// JpaRepository<��ƼƼŸ��, idŸ��>

// �������̽��� �ν��Ͻ� ������ �� �ȴ�.

@Repository
@RestResource(path = "contact-comments")
public interface ContactCommentRepository extends JpaRepository<ContactComment, Integer> {

}