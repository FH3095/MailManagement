package eu._4fh.mailmanagement.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "local_users", indexes = { @Index(name = "idx_active", columnList = "active", unique = false) })
public class LocalUser extends Address {
	private static final long serialVersionUID = -3926457520588983876L;

	@Column(name = "active", nullable = false)
	@ColumnDefault(value = "1")
	private boolean luActive;

	public LocalUser() {
	}

	public boolean isLocalUserActive() {
		return luActive;
	}

	public void setLocalUserActive(boolean active) {
		this.luActive = active;
	}

	@Override
	public boolean isAccessAllowed() {
		return false;
	}
}
