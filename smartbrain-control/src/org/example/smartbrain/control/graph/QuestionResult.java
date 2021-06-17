package org.example.smartbrain.control.graph;

public class QuestionResult extends AbstractQuestionResult {

	public QuestionResult(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public float score() {
		float score = 0;
		for(int selected : selectedResponses) {
			Response response = question.responses(selected);
			score += response.correct() ? 1 : -1;
		}
		return Math.max(score / question.correctResponses().size(), 0.0f);
	}
}