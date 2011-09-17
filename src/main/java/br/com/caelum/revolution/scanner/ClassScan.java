package br.com.caelum.revolution.scanner;

import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

public class ClassScan {

	public Set<String> findAll(Class<?> clazz) {
		try {
			URL url = ClasspathUrlFinder.findClassBase(ClassScan.class);
			AnnotationDB db = new AnnotationDB();
			db.scanArchives(url);

			Map<String, Set<String>> annotationIndex = db.getAnnotationIndex();
			Set<String> tabs = annotationIndex.get(clazz.getName());
			return tabs;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
