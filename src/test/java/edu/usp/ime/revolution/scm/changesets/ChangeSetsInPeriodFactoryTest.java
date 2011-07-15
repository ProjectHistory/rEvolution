package edu.usp.ime.revolution.scm.changesets;

import java.text.SimpleDateFormat;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.scm.SCM;

public class ChangeSetsInPeriodFactoryTest {

	private SCM scm;
	private Config config;

	@Before
	public void setUp() {
		scm = mock(SCM.class);
		config = mock(Config.class);
	}
	
	@Test(expected=ChangeSetNotFoundException.class)
	public void shouldThrownAnExceptionIfConfigIsNotFound() {
		when(config.get("changesets.all.startPeriod")).thenReturn("no-date-here");
		when(config.get("changesets.all.endPeriod")).thenReturn("2009-10-10 10:00:00");
		
		ChangeSetsInPeriodFactory factory = new ChangeSetsInPeriodFactory();
		factory.build(scm, config);
	}
	
	@Test
	public void shouldSetStartAndEndPeriod() {		
		when(config.get("changesets.all.startPeriod")).thenReturn("2008-10-10 10:00:00");
		when(config.get("changesets.all.endPeriod")).thenReturn("2009-10-10 11:00:00");
		
		ChangeSetsInPeriodFactory factory = new ChangeSetsInPeriodFactory();
		ChangeSetsInPeriod cs = (ChangeSetsInPeriod) factory.build(scm, config);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		assertEquals("2008-10-10 10:00:00", sdf.format(cs.getStartPeriod().getTime()));
		assertEquals("2009-10-10 11:00:00", sdf.format(cs.getEndPeriod().getTime()));
	}
}
