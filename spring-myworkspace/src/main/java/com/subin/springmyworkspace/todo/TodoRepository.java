package com.subin.springmyworkspace.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//������ ���� �������̽� ����
// JpaRepository<��ƼƼŸ��, idŸ��>

// �������̽��� �ν��Ͻ� ������ �ȵȴ�.
@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

}
