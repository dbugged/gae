package com.kailar.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/mail")
public class MailController {


	@RequestMapping(value= "/autoReply", method = RequestMethod.POST)
	public String automaticMailReply(HttpServletRequest request, 
			@RequestParam(value="name", required=false) String name, 
			@RequestParam(value="description", required=false) String description, 
			@RequestParam(value="email", required=false) String email, HttpServletResponse response) throws IOException{

		/******** E-Mail ********/
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		/*Sending automated response to the user!*/
		try {
			sendUserResponseMail(name,email,session);
		} catch (Exception e) {
			return "redirect:../";
		}

		/*Sending automated mail to the admin!*/
		try {
			sendMailToAdmin(name,email,description,session);
		} catch (Exception e) {
			return "redirect:../";
		}

		/******** E-Mail ********/

		/*response.setContentType("text/plain");*/
		return "redirect:../";

	}



	private void sendMailToAdmin(String name, String email, String description,
			Session session) throws Exception{
		//TODO: User velocity templates for this mail
		StringBuilder strBuilder = new StringBuilder("Hello,");
		strBuilder.append("\n\t Congratulations you have got mail from "+name+".");
		strBuilder.append("\n\t E-mail ID : "+email);
		strBuilder.append("\n\t Comment : "+description);
		strBuilder.append("\n\nThanks,\nHarikesh Halemane\n+91 9880034479");
		String msgBody = strBuilder.toString();

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("admin@haalkerehomes.com",
				"Kailar Homes"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
				"admin@haalkerehomes.com",
				"Kailar Homes Admin"));
		/*msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
				"harikesh85@gmail.com", "Harikesh Halemane"));*/
		msg.addRecipients(Message.RecipientType.CC, getAdminList());
		msg.setSubject("Message from "+name+"!");
		msg.setText(msgBody);
		Transport.send(msg);
	}



	private void sendUserResponseMail(String name, String email, Session session) throws Exception{
		//TODO: User velocity templates for this mail
		StringBuilder strBuilder = new StringBuilder("Hi "+name+",\n");
		strBuilder.append("\t Thank you for contacting us! We will get back to you soon.\n");
		strBuilder.append("\t Have a nice day!!!\n");
		strBuilder.append("\nThanks,\nHarikesh Halemane\n+91 9880034479");
		strBuilder.append("\n\n Note: Automate mail, please don't reply!!");
		String msgBody = strBuilder.toString();

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("admin@haalkerehomes.com",
				":: Kailar Homes ::"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
				email, name));
		msg.setSubject("Greetings from Kailar Homes!");
		msg.setText(msgBody);
		Transport.send(msg);
	}
	
	
	private Address[] getAdminList() throws UnsupportedEncodingException{
		InternetAddress[] adminArray = new InternetAddress[4];
		adminArray[0] = new InternetAddress("harikesh85@gmail.com", "Harikesh Halemane");
		adminArray[1] = new InternetAddress("baggubhat@gmail.com", "Bhagavan Kailar Bhat");
		adminArray[2] = new InternetAddress("balish.c@rediffmail.com", "Balish");
		adminArray[3] = new InternetAddress("hb.shashidhar@gmail.com", "Shashidhar HB");
		return adminArray;
	}

}
