package org.example.cinepolis.control.graph;

import io.intino.magritte.framework.Graph;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControlGraph extends org.example.cinepolis.control.graph.AbstractGraph {

	public ControlGraph(Graph graph) {
		super(graph);
	}

	public ControlGraph(io.intino.magritte.framework.Graph graph, ControlGraph wrapper) {
	    super(graph, wrapper);
	}

	public Asset asset(String id) {
		return assetList(a -> a.id().equals(id)).findFirst().orElse(null);
	}

	public Alert alert(List<Alert> alerts, String id) {
		return alerts.stream().filter(a -> a.id().equals(id)).findFirst().orElse(null);
	}

	public Employee employee(String id) {
		return employeeList(a -> a.id().equals(id)).findFirst().orElse(null);
	}

	public List<Employee> employeesByArea(String area) {
		return employeeList(a -> a.area().equals(area)).collect(Collectors.toList());
	}

	public Employee employeeByArea(String area) {
		List<Employee> employees = employeesByArea(area);
		return employees.isEmpty() ? null : employees.get(0);
	}

	public List<Asset> assetsByArea(String area) {
		return assetList(a -> a.area().equals(area)).collect(Collectors.toList());
	}
}