package org.example.vaccine.control.graph;

import io.intino.magritte.framework.Graph;
import org.example.cinepolis.control.graph.AbstractGraph;
import org.example.cinepolis.control.graph.Hospital;
import org.example.cinepolis.control.graph.Patient;
import org.example.cinepolis.control.graph.Vaccine;

import java.util.HashMap;
import java.util.Map;

public class ControlGraph extends AbstractGraph {

	private final Map<Class<?>, Map<String, Object>> names = new HashMap<>();

	public ControlGraph(Graph graph) {
		super(graph);
	}

	public ControlGraph(Graph graph, ControlGraph wrapper) {
	    super(graph, wrapper);
	}

	public boolean contains(String name, Class<?> clazz) {
		return get(name, clazz) != null;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name, Class<T> clazz) {
		Map<String, Object> names = this.names.computeIfAbsent(clazz, k -> new HashMap<>());
		return (T) names.computeIfAbsent(name, k -> find(name, clazz));
	}

	private Object find(String name, Class<?> clazz) {
		if(clazz.equals(Hospital.class)) return hospitalList(e -> e.name.equals(name)).findFirst().orElse(null);
		if(clazz.equals(Vaccine.class)) return vaccineList(e -> e.name.equals(name)).findFirst().orElse(null);
		if(clazz.equals(Patient.class)) return patientList(e -> e.name.equals(name)).findFirst().orElse(null);
		return null;
	}
}