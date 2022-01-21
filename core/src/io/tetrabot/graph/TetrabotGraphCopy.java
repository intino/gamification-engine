package io.tetrabot.graph;

import io.tetrabot.graph.model.Competition;
import io.tetrabot.graph.model.Round;
import io.tetrabot.graph.model.Season;

import java.util.function.Predicate;

public class TetrabotGraphCopy {

    private Predicate<Competition> competitionFilter = none();
    private Predicate<Season> seasonFilter = none();
    private Predicate<Round> roundFilter = none();

    public TetrabotGraph copy(TetrabotGraph original) {
        TetrabotGraph copy = new TetrabotGraph();
        deepCopy(copy, original);
        return copy;
    }

    private void deepCopy(TetrabotGraph dst, TetrabotGraph src) {
        for(Competition srcCompetition : src.competitions()) {
            if(competitionFilter.test(srcCompetition)) {
                dst.competitions().add(srcCompetition.new Copy().seasonFilter(seasonFilter).create());
            }
        }
    }

    public TetrabotGraphCopy competitionFilter(Predicate<Competition> competitionFilter) {
        this.competitionFilter = competitionFilter == null ? none() : competitionFilter;
        return this;
    }

    public TetrabotGraphCopy seasonFilter(Predicate<Season> seasonFilter) {
        this.seasonFilter = seasonFilter == null ? none() : seasonFilter;
        return this;
    }

    public TetrabotGraphCopy roundFilter(Predicate<Round> roundFilter) {
        this.roundFilter = roundFilter == null ? none() : roundFilter;
        return this;
    }

    private <T> Predicate<T> none() {
        return x -> true;
    }
}
