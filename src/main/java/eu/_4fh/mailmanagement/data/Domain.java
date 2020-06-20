package eu._4fh.mailmanagement.data;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;

import edu.umd.cs.findbugs.annotations.ReturnValuesAreNonnullByDefault;

@ParametersAreNonnullByDefault
@ReturnValuesAreNonnullByDefault
@Entity
@NamedQuery(name = "Domain.findNonLocalDomains", query = "SELECT d FROM Domain d WHERE d.localUserDomain = false ORDER BY d.name")
@NamedQuery(name = "Domain.findByName", query = "SELECT d FROM Domain d WHERE d.name = ?1")
@Table(name = "domains", uniqueConstraints = {
		@UniqueConstraint(name = "unique_domain", columnNames = "domain") }, indexes = {
				@Index(name = "idx_domain", columnList = "domain", unique = true),
				@Index(name = "idx_local_user_domain", columnList = "local_user_domain", unique = false) })
public class Domain implements DataObject {
	private static final long serialVersionUID = 6400924104055964325L;

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	private long id;

	@Column(name = "domain", nullable = false, length = 255, updatable = false)
	private String name;

	@Column(name = "local_user_domain", nullable = false, updatable = false)
	@ColumnDefault(value = "0")
	private boolean localUserDomain;

	public Domain() {
	}

	public Domain(final long id, final String domain, final boolean localUserDomain) {
		this.id = id;
		this.name = domain;
		this.localUserDomain = localUserDomain;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isLocalUserDomain() {
		return localUserDomain;
	}

	@Override
	public boolean isAccessAllowed() {
		return true;
	}
}
