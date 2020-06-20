package eu._4fh.mailmanagement.web.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import eu._4fh.mailmanagement.data.Alias;
import eu._4fh.mailmanagement.web.WicketApplication;

public class AliasDelete extends GlobalTemplate {
	private static final long serialVersionUID = 5612479889282527347L;

	private final Alias alias;

	public AliasDelete(final PageParameters params) {
		this.alias = getDb().getAlias(params.get("id").toLong());
	}

	private class DeleteForm extends Form<Alias> {
		private static final long serialVersionUID = 7969924896914540115L;

		public DeleteForm(String id) {
			super(id);
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			add(new Label("address", Model.of(alias.getFullAddress())));
		}

		@Override
		protected void onSubmit() {
			super.onSubmit();
			getDb().deleteAlias(alias);
			setResponsePage(WicketApplication.get().getHomePage());
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		bodyHeader.setDefaultModel(Model.of("Delete " + alias.getFullAddress()));

		add(new DeleteForm("deleteForm"));
	}
}
