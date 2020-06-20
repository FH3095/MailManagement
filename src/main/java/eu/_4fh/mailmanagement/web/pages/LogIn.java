package eu._4fh.mailmanagement.web.pages;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.string.Strings;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@AuthorizeInstantiation(value = {})
public class LogIn extends GlobalTemplate {
	private static final long serialVersionUID = -1140962227832836078L;

	@SuppressWarnings("serial")
	@AuthorizeInstantiation
	private static class LoginForm extends StatelessForm<Void> {
		private String loginUsername;
		private String loginPassword;
		@SuppressWarnings("unused")
		private String loginStatus;

		public LoginForm(String id) {
			super(id);
			setDefaultModel(new CompoundPropertyModel<LoginForm>(this));
			add(new TextField<>("loginUsername"));
			add(new PasswordTextField("loginPassword"));
			add(new Label("loginStatus"));
		}

		@Override
		@SuppressFBWarnings(value = "UWF_UNWRITTEN_FIELD", justification = "Written by Wicket via CompoundPropertyModel")
		protected void onSubmit() {
			super.onSubmit();
			if (Strings.isEmpty(loginUsername) || Strings.isEmpty(loginPassword)) {
				return;
			}

			final boolean authResult = AuthenticatedWebSession.get().signIn(loginUsername, loginPassword);
			if (authResult) {
				setResponsePage(Application.get().getHomePage());
			} else {
				loginStatus = "Wrong username or password";
			}
		}
	}

	public LogIn() {
		super();
		add(new LoginForm("loginForm"));
		setStatelessHint(true);
	}
}
