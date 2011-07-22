package br.com.caelum.revolution.builds.ant;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.revolution.builds.Build;
import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.builds.ant.Ant;
import br.com.caelum.revolution.executor.CommandExecutor;


public class AntTest {

	private Build ant;
	private CommandExecutor executor;
	private String task;
	private String buildPath;

	@Before
	public void setUp() {
		task = "compile";
		buildPath = "/build/path";
		
		executor = mock(CommandExecutor.class);
		ant = new Ant(executor, task, buildPath);
	}
	
	@Test
	public void shouldCallAnt() throws Exception {
		String path = "some/path";
		ant.build(path);
		
		verify(executor).execute("ant " + task, path);
	}
	
	@Test
	public void shouldReturnBuildPath() throws Exception {
		BuildResult result = ant.build("some/path");
		
		assertEquals(buildPath, result.getDirectory());
	}
}