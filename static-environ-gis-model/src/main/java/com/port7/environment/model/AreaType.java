package com.port7.environment.model;

public final class AreaType {
	private final String typeName;
	
	public AreaType(final String typeName) {
		this.typeName = typeName;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof AreaType) {
			return ((AreaType) o).typeName.equals(typeName);
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 19 + typeName.hashCode();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return typeName;
	}
}
