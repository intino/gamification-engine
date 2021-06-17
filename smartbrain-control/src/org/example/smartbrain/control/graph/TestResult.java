package org.example.smartbrain.control.graph;

public class TestResult extends AbstractTestResult {

	public TestResult(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public float score() {
		return (float) results.stream().mapToDouble(QuestionResult::score).sum() / results.size();
	}
}