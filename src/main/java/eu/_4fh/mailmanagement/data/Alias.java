package eu._4fh.mailmanagement.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import eu._4fh.mailmanagement.web.WicketSession;

@Entity
@Table(name = "aliases", indexes = { @Index(name = "idx_active", columnList = "active", unique = false) })
@NamedEntityGraph(name = "Alias.fetchAll", includeAllAttributes = true)
@NamedQuery(name = "Alias.findAll", query = "SELECT a FROM Alias a INNER JOIN a.domain d ORDER BY d.name,a.localPart")
public class Alias extends Address {
	private static final long serialVersionUID = -6936404050812831908L;

	@Column(name = "active", nullable = false)
	@ColumnDefault(value = "1")
	private boolean alActive;

	@Column(name = "target", nullable = false, length = 255)
	private String target;

	public Alias() {
	}

	public Alias(final boolean active, final String target, final Address address) {
		this.alActive = active;
		this.target = target;
	}

	public boolean isAliasActive() {
		return alActive;
	}

	public void setAliasActive(boolean active) {
		this.alActive = active;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public boolean isAccessAllowed() {
		return target != null && target.equals(WicketSession.get().getUsername());
	}
}
