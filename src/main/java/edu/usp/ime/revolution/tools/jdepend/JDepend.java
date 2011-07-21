package edu.usp.ime.revolution.tools.jdepend;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.persistence.ToolThatPersists;
import edu.usp.ime.revolution.tools.Tool;
import edu.usp.ime.revolution.tools.ToolException;

public class JDepend implements Tool, ToolThatPersists {

	private final CommandExecutor executor;
	private final JDependXMLInterpreter interpreter;
	private final String jDependPath;
	private Session session;

	public JDepend(CommandExecutor executor, JDependXMLInterpreter interpreter, String jDependPath) {
		this.executor = executor;
		this.interpreter = interpreter;
		this.jDependPath = jDependPath;	
	}

	public void calculate(Commit commit, BuildResult current) throws ToolException {
		try {
			executor.setEnvironmentVar("CLASSPATH", jDependPath);
			String output = executor.execute("java jdepend.xmlui.JDepend " + current.getDirectory(), current.getDirectory());
			
			String response = extractJDependXMLBlockFrom(output);
			
			List<JDependMetric> infos = interpreter.interpret(new ByteArrayInputStream(response.getBytes("UTF-8")));
			for(JDependMetric info : infos) {
				session.save(info);
			}
		} catch (Exception e) {
			throw new ToolException(e);
		}
	}

	public String getName() {
		return "jdepend";
	}
	
	private String extractJDependXMLBlockFrom(String output) {
		return output.substring(0, output.lastIndexOf("</JDepend>")+10);
	}

	public List<Class<?>> classesToPersist() {
		ArrayList<Class<?>> list = new ArrayList<Class<?>>();
		list.add(JDependMetric.class);
		return list;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
