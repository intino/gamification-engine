package org.example.smartbrain.control.graph;

import io.intino.alexandria.Scale;
import io.intino.alexandria.Timetag;
import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.Node;
import org.example.smartbrain.control.box.ControlBox;

import java.time.Instant;

public class ControlGraph extends org.example.smartbrain.control.graph.AbstractGraph {

	private ControlBox box;

	public ControlGraph(Graph graph) {
		super(graph);
	}

	public ControlGraph(io.intino.magritte.framework.Graph graph, ControlGraph wrapper) {
	    super(graph, wrapper);
	}

	public ControlGraph setBox(ControlBox box) {
		this.box = box;
		return this;
	}

	public Question question(String testName, String questionName) {
		Node node = core$().load(box.datamart().getQuestion(testName, questionName).toString());
		return node == null ? null : node.as(Question.class);
	}

	public TestResult testResult(String testName, String patientId, Instant month, int testId) {
		Node node = core$().load(box.datamart().getTestResults(patientId, testName, Timetag.of(month, Scale.Month)) + "#" + testId);
		return node == null ? null : node.as(TestResult.class);
	}

	public TestResult task(String taskId, String taskName, String patientId) {
		Node node = core$().load(box.datamart().getTestResults(patientId, testName, Timetag.of(month, Scale.Month)) + "#" + testId);
		return node == null ? null : node.as(TestResult.class);
	}
}