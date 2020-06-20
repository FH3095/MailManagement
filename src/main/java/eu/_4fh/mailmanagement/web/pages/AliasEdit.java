package eu._4fh.mailmanagement.web.pages;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import eu._4fh.mailmanagement.data.Alias;
import eu._4fh.mailmanagement.web.WicketApplication;
import eu._4fh.mailmanagement.web.models.JpaModel;

public class AliasEdit extends GlobalTemplate {
	private static final long serialVersionUID = 5612479889282527347L;

	private final IModel<Alias> alias;

	public AliasEdit(final PageParameters params) {
		final StringValue idStr = params.get("id");
		if (idStr.isNull()) {
			this.alias = new CompoundPropertyModel<>(getDb().createAlias());
		} else {
			this.alias = new CompoundPropertyModel<>(new JpaModel<>(getDb().getAlias(idStr.toLong())));
		}
	}

	private class DetailEditForm extends Form<Alias> {
		private static final long serialVersionUID = 7969924896914540115L;

		public DetailEditForm(String id, IModel<Alias> model) {
			super(id);
			setDefaultModel(model);
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			add(new TextField<>("localPart"));
			add(new DropDownChoice<>("domain", getDb().getNonLocalDomains(), new ChoiceRenderer<>("name", "id")));
			add(new CheckBox("adActive"));
			if (isAdmin()) {
				add(new TextField<>("target"));
				add(new CheckBox("alActive"));
			} else {
				add(new TextField<>("target", Model.of("")));
				add(new CheckBox("alActive", Model.of(Boolean.TRUE)));
			}
		}

		@Override
		protected void onSubmit() {
			super.onSubmit();
			getDb().saveAlias(getModelObject());
			setResponsePage(WicketApplication.get().getHomePage());
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		bodyHeader.setDefaultModel(Model.of("Edit " + alias.getObject().getFullAddress()));

		add(new DetailEditForm("editForm", alias));
	}
}
