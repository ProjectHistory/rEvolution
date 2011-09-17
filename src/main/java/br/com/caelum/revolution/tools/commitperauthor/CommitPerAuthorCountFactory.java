package br.com.caelum.revolution.tools.commitperauthor;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.config.IsTool;
import br.com.caelum.revolution.tools.SpecificToolFactory;
import br.com.caelum.revolution.tools.Tool;

@IsTool(name="Commit Per Author Count", configs={})
public class CommitPerAuthorCountFactory implements SpecificToolFactory {

	public Tool build(Config config) {
		return new CommitPerAuthorCountTool();
	}

}
