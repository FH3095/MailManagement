package eu._4fh.mailmanagement.data;

import java.io.Serializable;

public interface DataObject extends Serializable {
	public boolean isAccessAllowed();
}
