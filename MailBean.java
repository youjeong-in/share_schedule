package one.services.beans;

import lombok.Data;

@Data
public class MailBean {

	private String from;
	private String to;
	private String subject;
	private String content;
}
