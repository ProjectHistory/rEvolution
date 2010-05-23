package edu.usp.ime.revolution.changesets;

import java.util.Iterator;

import edu.usp.ime.revolution.changesets.ChangeSet;

public class ChangeSetBuilder {
	public static ChangeSet aChangeSet(final String name) {
		return new ChangeSet() {
			public String getName() {
				return name;
			}
		};
	}
	

	public static ChangeSetCollection aCollectionWith(final ChangeSet changeSet) {
		return new ChangeSetCollection() {
			private boolean next = true;
			
			public Iterator<ChangeSet> iterator() {
				return this;
			}
			
			public void remove() {
				
			}
			
			public ChangeSet next() {
				return changeSet;
			}
			
			public boolean hasNext() {
				next = !next;
				return !next;
			}
		};
	}
}
