package com.subin.springmyworkspace.todo;

import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;

@Configuration
public class TodoCommentRepositoryEventListener extends AbstractRepositoryEventListener<TodoComment> {

	@Override
	public void onBeforeCreate(TodoComment comment) {
		comment.setCreatedTime(new Date().getTime());
	}
}
