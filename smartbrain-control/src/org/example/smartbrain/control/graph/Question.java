package org.example.smartbrain.control.graph;

import java.util.List;

public class Question extends AbstractQuestion {

	public Question(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public List<Response> correctResponses() {
		return responses(Response::correct);
	}

	public List<Response> incorrectResponses() {
		return responses(r -> !r.correct());
	}
}