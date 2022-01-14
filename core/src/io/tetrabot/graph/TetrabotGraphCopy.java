package io.tetrabot.graph;

import io.tetrabot.graph.model.Competition;
import io.tetrabot.graph.model.Node;
import io.tetrabot.graph.model.Round;
import io.tetrabot.graph.model.Season;
import io.tetrabot.util.Json;

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
                Competition dstCompetition = shallowCopy(srcCompetition);
                dst.competitions().add(dstCompetition);
                copySeasons(dstCompetition, srcCompetition);
            }
        }
    }

    private void copySeasons(Competition dstCompetition, Competition srcCompetition) {
        for(Season srcSeason : srcCompetition.seasons()) {
            if(seasonFilter.test(srcSeason)) {
                Season dstSeason = shallowCopy(srcSeason);
                dstCompetition.seasons().add(dstSeason);
                copyRounds(dstSeason, srcSeason);
            }
        }
    }

    private void copyRounds(Season dstSeason, Season srcSeason) {
        for(Round srcRound : srcSeason.rounds()) {
            if(roundFilter.test(srcRound)) {
                Round dstRound = shallowCopy(srcRound);
                dstSeason.rounds().add(dstRound);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Node> T shallowCopy(T node) {
        return (T) Json.fromJson(node.getClass(), Json.toJson(node));
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
