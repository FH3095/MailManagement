package eu._4fh.mailmanagement;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;

public class MailRoles {
	private MailRoles() {
	}

	public static final String user = Roles.USER;
	public static final String admin = Roles.ADMIN;
	public static final String nobody = "NOBODY";
}
