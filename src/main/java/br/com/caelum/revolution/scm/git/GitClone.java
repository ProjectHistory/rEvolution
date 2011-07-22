package br.com.caelum.revolution.scm.git;

import java.util.List;

import br.com.caelum.revolution.executor.CommandExecutor;
import br.com.caelum.revolution.scm.CommitData;
import br.com.caelum.revolution.scm.SCM;
import br.com.caelum.revolution.scm.SCMException;
import br.com.caelum.revolution.scm.changesets.ChangeSet;


public class GitClone implements SCM{

	private final Git git;

	public GitClone(String gitRepository, String destiny, CommandExecutor exec, Git git) {
		this.git = git;
		
		try {
			exec.execute("git clone " + gitRepository + " " + destiny, destiny);
		} catch (Exception e) {
			throw new SCMException(e);
		}
	}

	public List<ChangeSet> getChangeSets() {
		return git.getChangeSets();
	}

	public String goTo(ChangeSet cs) {
		return git.goTo(cs);
	}

	public CommitData detail(String id) {
		return git.detail(id);
	}

	public String sourceOf(String hash, String fileName) {
		return git.sourceOf(hash, fileName);
	}

	public String getPath() {
		return git.getPath();
	}

	public String blameCurrent(String file, int line) {
		return git.blameCurrent(file, line);
	}
	

}