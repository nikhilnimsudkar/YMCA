/*
 * Copyright 2007-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ymca.web.email;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Sends an e-mail message.
 *
 * @author David Winterfeldt
 */
@Component
public class VelocityEmailSender implements Sender {

    private final VelocityEngine velocityEngine;
    private final JavaMailSender mailSender;

    /**
     * Constructor
     */
    @Autowired
    public VelocityEmailSender(VelocityEngine velocityEngine,
                               JavaMailSender mailSender) {
        this.velocityEngine = velocityEngine;
        this.mailSender = mailSender;
    }

    /**
     * Sends e-mail using Velocity template for the body and
     * the properties passed in as Velocity variables.
     *
     * @param   msg                 The e-mail message to be sent, except for the body.
     * @param   hTemplateVariables  Variables to use when processing the template.
     */
    @Override
    public void send(final SimpleMailMessage msg,
                     final Map<String, Object> hTemplateVariables) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
               MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
               message.setTo(msg.getTo());
               message.setFrom(msg.getFrom(),msg.getFrom());
               message.setSubject(msg.getSubject());

               String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/emailBody.vm", hTemplateVariables);

               message.setText(body, true);
               //File file = new File("src/main/webapp/resources/img/portal_Images/defaultpic.jpg");
               //message.addAttachment(file.getName(), new ClassPathResource(file.getCanonicalPath()));
            }
         };

         mailSender.send(preparator);

    }

	@Override
	public void sendwithattachment(final SimpleMailMessage msg,
			final Map<String, Object> hTemplateVariables, final File file) {
		// TODO Auto-generated method stub
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
               MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
               message.setTo(msg.getTo());
               message.setFrom(msg.getFrom(),msg.getFrom());
               message.setSubject(msg.getSubject());

               String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/emailBody.vm", hTemplateVariables);

               message.setText(body, true);
               if(file!=null)
            	   message.addAttachment(file.getName(), file);
            	   //message.addAttachment(file.getName(), new ClassPathResource(file.getCanonicalPath()));
            }
         };

         mailSender.send(preparator);
	}

}
