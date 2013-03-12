/**
 * 
 */
package uk.ac.ed.inf.ir.lexicalization;

import java.io.Serializable;

/**
 * Override identifier
 * 
 * @author Miltos Allamanis <m.allamanis@ed.ac.uk>
 * 
 */
public class CodeIdentifier implements Comparable<CodeIdentifier>, Serializable {

	private static final long serialVersionUID = 3091530224477886227L;
	public String name;

	public CodeIdentifier(String ident) {
		name = ident;
	}

	@Override
	public int compareTo(final CodeIdentifier o) {
		return name.compareTo(o.name);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CodeIdentifier))
			return false;
		final CodeIdentifier ci = (CodeIdentifier) obj;
		return name.equals(ci.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}

}
