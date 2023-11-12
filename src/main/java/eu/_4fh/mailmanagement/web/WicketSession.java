package eu._4fh.mailmanagement.web;

import java.util.Properties;

import javax.annotation.Nonnull;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import eu._4fh.mailmanagement.MailRoles;
import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;

public class WicketSession extends AuthenticatedWebSession {
	private static final long serialVersionUID = 1566657779328768438L;
	private final Roles roles;
	private @Nonnull String username;

	public WicketSession(Request request) {
		super(request);
		this.roles = new Roles();
		this.username = "";
	}

	@Override
	protected boolean authenticate(String username, String password) {
		final Properties imapProperties = Context.getInstance().getImapProperties();
		if (username.isEmpty() || password.isEmpty()) {
			throw new IllegalArgumentException("Missing username or password");
		}

		final Session mailSession = Session.getDefaultInstance(imapProperties);
		try (final Store mailStore = mailSession.getStore()) {
			mailStore.connect(username, password);
		} catch (AuthenticationFailedException e) {
			return false;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		this.username = username;
		roles.clear();
		roles.add(MailRoles.user);
		if (Context.getInstance().getAdminUsername().equals(username)) {
			roles.add(MailRoles.admin);
		}
		return true;
	}

	@Override
	public Roles getRoles() {
		return roles;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public void signOut() {
		super.signOut();
		this.username = "";
		roles.clear();
	}

	@Override
	public void invalidate() {
		super.invalidate();
		this.username = "";
		roles.clear();
	}

	public static WicketSession get() {
		return (WicketSession) org.apache.wicket.Session.get();
	}
}
