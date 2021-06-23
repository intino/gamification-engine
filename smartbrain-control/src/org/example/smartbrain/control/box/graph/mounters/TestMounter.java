package org.example.smartbrain.control.box.graph.mounters;

import org.example.smartbrain.control.box.ControlBox;
import io.intino.alexandria.event.Event;

import org.example.smartbrain.control.box.mounters.Mounter;
import org.example.smartbrain.control.graph.*;
import org.example.smartbrain.datahub.events.smartbrain.TestCreate;

import java.util.List;

public class TestMounter implements Mounter {

	private final ControlBox box;

	public TestMounter(ControlBox box) {
		this.box = box;
	}

	public void handle(org.example.smartbrain.datahub.events.smartbrain.TestCreate event) {
		Test test = box.graph().create().test(event.name());
		setQuestions(test, event.questionList());
		test.save$();
	}

	private void setQuestions(Test test, List<TestCreate.Question> questionsFromEvent) {
		for(TestCreate.Question q : questionsFromEvent) {
			Question question = test.graph().create().question(q.text(), AbstractQuestion.Type.valueOf(q.type().name()));
			for(TestCreate.Question.Response r : q.responseList())
				question.graph().create().response(r.value(), r.correct());
			question.save$();
		}
	}

	public void handle(org.example.smartbrain.datahub.events.smartbrain.TestBegin event) {
		box.graph().create()
				.testResult(event.testId(), event.testName(), event.patientId())
				.startTime(event.ts())
				.save$();
	}

	public void handle(org.example.smartbrain.datahub.events.smartbrain.TestResponse event) {
		TestResult result = box.graph().testResultList(t -> matches(t, event.testName(), event.patientId(), event.testId()))
				.findFirst().orElse(null);
		if(result == null) return;
		QuestionResult qr = result.graph().create().questionResult(getQuestion(event.testName(), event.question()));
		result.results().add(qr);
		qr.save$();
	}

	private Question getQuestion(String test, String question) {
		return box.graph().question(test, question);
	}

	public void handle(org.example.smartbrain.datahub.events.smartbrain.TestEnd event) {
		TestResult result = box.graph().testResultList(t -> matches(t, event.testName(), event.patientId(), event.testId())).findFirst().orElse(null);
		if(result == null) return;
		result.endTime(event.ts());
		result.save$();
	}

	private boolean matches(TestResult test, String testName, String patientId, int testId) {
		return test.testId() == testId && test.name().equals(testName) && test.patientId().equals(patientId);
	}

	public void handle(Event event) {
		if	(event instanceof org.example.smartbrain.datahub.events.smartbrain.TestCreate) handle((org.example.smartbrain.datahub.events.smartbrain.TestCreate) event);
		else if(event instanceof org.example.smartbrain.datahub.events.smartbrain.TestEnd) handle((org.example.smartbrain.datahub.events.smartbrain.TestEnd) event);
		else if(event instanceof org.example.smartbrain.datahub.events.smartbrain.TestResponse) handle((org.example.smartbrain.datahub.events.smartbrain.TestResponse) event);
		else if(event instanceof org.example.smartbrain.datahub.events.smartbrain.TestBegin) handle((org.example.smartbrain.datahub.events.smartbrain.TestBegin) event);
	}
}