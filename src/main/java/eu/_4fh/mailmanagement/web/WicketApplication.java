package eu._4fh.mailmanagement.web;

import javax.annotation.CheckForNull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.core.util.file.WebApplicationPath;
import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.CSPDirectiveSrcValue;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

import edu.umd.cs.findbugs.annotations.ReturnValuesAreNonnullByDefault;
import eu._4fh.mailmanagement.db.Db;
import eu._4fh.mailmanagement.web.pages.AliasDelete;
import eu._4fh.mailmanagement.web.pages.AliasEdit;
import eu._4fh.mailmanagement.web.pages.AliasesList;
import eu._4fh.mailmanagement.web.pages.LogIn;

@ParametersAreNonnullByDefault
@ReturnValuesAreNonnullByDefault
public class WicketApplication extends AuthenticatedWebApplication {
	public static final String persistenceUnitName = "eu._4fh.MailManagement";
	public static final MetaDataKey<Db> dbKey = new MetaDataKey<>() {
		private static final long serialVersionUID = 7505864667509603840L;
	};

	private static final EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory(persistenceUnitName);

	@Override
	public Class<? extends Page> getHomePage() {
		return AliasesList.class;
	}

	@Override
	protected void init() {
		super.init();
		getCspSettings().blocking().clear().add(CSPDirective.DEFAULT_SRC, CSPDirectiveSrcValue.SELF);
		getResourceSettings().getResourceFinders().add(new WebApplicationPath(getServletContext(), "htmlMarkup"));
		mountPage("/login", LogIn.class);
		mountPage("/address/edit", AliasEdit.class);
		mountPage("/address/delete", AliasDelete.class);
		getRequestCycleListeners().add(new IRequestCycleListener() {
			@Override
			public void onBeginRequest(RequestCycle cycle) {
				IRequestCycleListener.super.onBeginRequest(cycle);
				final WicketSession session = WicketSession.get();
				if (session.isSignedIn()) {
					final Db db = new Db(entityManagerFactory, session.getRoles());
					cycle.setMetaData(dbKey, db);
				}
			}

			@Override
			public void onEndRequest(RequestCycle cycle) {
				IRequestCycleListener.super.onEndRequest(cycle);
				final @CheckForNull Db db = cycle.getMetaData(dbKey);
				if (db != null) {
					db.close();
				}
			}
		});
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return WicketSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LogIn.class;
	}
}
