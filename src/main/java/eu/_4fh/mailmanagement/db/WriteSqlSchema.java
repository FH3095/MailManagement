package eu._4fh.mailmanagement.db;

import javax.naming.NamingException;
import javax.persistence.Persistence;

public class WriteSqlSchema {
	public static void main(String[] args) throws NamingException {
		Persistence.generateSchema("eu._4fh.MailManagement.schemagen", null);
	}
}
