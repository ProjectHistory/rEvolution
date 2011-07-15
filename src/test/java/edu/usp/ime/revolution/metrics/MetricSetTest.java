package edu.usp.ime.revolution.metrics;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

public class MetricSetTest {

	@Test
	public void shouldHaveInformationAboutChangeSet() {
		Calendar csDate = GregorianCalendar.getInstance();
		MetricSet set = new MetricSet("set name", csDate);
		
		assertEquals("set name", set.getName());
		assertEquals(csDate, set.getDate());
	}
	
	@Test
	public void shouldStoreAMetric() {
		MetricSet set = new MetricSet("set name", GregorianCalendar.getInstance());
		
		set.setMetric("metric name", 1.34, "target", "level", "tool");
		
		assertEquals(1.34, set.getMetric("metric name", "target", "tool").getValue(), 0.01);
		assertEquals("target", set.getMetric("metric name", "target", "tool").getTarget());
		assertEquals("level", set.getMetric("metric name", "target", "tool").getLevel());
		assertEquals("tool", set.getMetric("metric name", "target", "tool").getTool());
	}
	
	@Test(expected=MetricAlreadyInSetException.class)
	public void shouldNotReplaceAnExistingMetric() {
		MetricSet set = new MetricSet("set name", GregorianCalendar.getInstance());
		
		set.setMetric("lcom", 1.34, "target", "level", "tool");
		set.setMetric("lcom", 5.1, "target", "level", "tool");	
	}
	
	@Test 
	public void shouldReturnAllMetrics() {
		MetricSet set = new MetricSet("set name", GregorianCalendar.getInstance());
		set.setMetric("lcom", 1, "target", "level", "tool");
		set.setMetric("afferent-coupling", 2, "target", "level", "tool");
		
		assertEquals(2, set.getMetrics().size());
		assertEquals(1, set.getMetric("lcom", "target", "tool").getValue(), 0.01);
		assertEquals(2, set.getMetric("afferent-coupling", "target", "tool").getValue(), 0.01);
	}
}