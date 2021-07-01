package com.subin.springmyworkspace.contact;

import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;

@Configuration
public class ContactCommentRepositoryEventListener extends AbstractRepositoryEventListener<ContactComment> {

	@Override
	public void onBeforeCreate(ContactComment comment) {
		comment.setCreatedTime(new Date().getTime());
	}
}
