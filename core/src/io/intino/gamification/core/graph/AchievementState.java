package io.intino.gamification.core.graph;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;

import java.time.Instant;

import static io.intino.gamification.core.box.events.achievement.AchievementState.Achieved;

public class AchievementState extends AbstractAchievementState {

	public AchievementState(io.intino.magritte.framework.Node node) {
		super(node);
	}

	public io.intino.gamification.core.box.events.achievement.AchievementState state() {
		return io.intino.gamification.core.box.events.achievement.AchievementState.valueOf(stateName);
	}

	public AchievementState state(io.intino.gamification.core.box.events.achievement.AchievementState state) {
		stateName(state.name());
		return this;
	}

	public void progress(CoreBox box) {
		count(count() + 1).save$();
		if(count() >= achievement.maxCount()) {
			AchievementNewState achievementNewState = new AchievementNewState();
			achievementNewState.ts(Instant.now());
			achievementNewState.id(achievement().id());
			achievementNewState.state(Achieved);
			achievementNewState.player(context());
			box.engineTerminal().feed(achievementNewState);
		}
	}
}