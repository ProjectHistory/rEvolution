package br.com.caelum.revolution.tools;

import java.util.List;
import org.junit.Test;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.tools.Tool;
import br.com.caelum.revolution.tools.ToolsFactory;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ToolsFactoryTest {

	@Test
	public void shouldAddAllToolsInConfig() {
		Config config = mock(Config.class);
		when(config.contains("tools.1")).thenReturn(true);
		when(config.asString("tools.1")).thenReturn("br.com.caelum.revolution.tools.files.NumberOfFilesFactory");
		when(config.contains("tools.2")).thenReturn(true);
		when(config.asString("tools.2")).thenReturn("br.com.caelum.revolution.tools.files.NumberOfFilesFactory");
		when(config.contains("tools.3")).thenReturn(false);
		
		List<Tool> tools = new ToolsFactory().basedOn(config);
		
		assertEquals(2, tools.size());
	}
	
	@Test
	public void shouldLoadConfig() {
		Config config = mock(Config.class);
		when(config.contains("tools.1")).thenReturn(true);
		when(config.asString("tools.1")).thenReturn("br.com.caelum.revolution.tools.files.NumberOfFilesFactory");
		when(config.asString("tools.1.extension")).thenReturn("java");
		when(config.contains("tools.2")).thenReturn(false);
		
		new ToolsFactory().basedOn(config);
		
		verify(config).asString("tools.1.extension");
	}
}
