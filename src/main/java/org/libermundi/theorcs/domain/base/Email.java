/*
 * Copyright (c) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.libermundi.theorcs.domain.base;

import java.io.Serializable;

import javax.mail.internet.MimeMessage;

import org.libermundi.theorcs.exceptions.EmailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

public interface Email extends Serializable {
	/**
	 * Convenient method to get a {@link SimpleMailMessage}
	 * @return {@link SimpleMailMessage} with default settings
	 * @throws EmailException
	 */
	SimpleMailMessage getDefaultMailMessage() throws EmailException;
	
	/**
	 * Convenient method to get a {@link getMimeMessageHelper}
	 * @return {@link getMimeMessageHelper} with default settings
	 * @throws EmailException
	 */
	MimeMessageHelper getDefaultMimeMessageHelper(MimeMessage mimeMessage) throws EmailException;
}
