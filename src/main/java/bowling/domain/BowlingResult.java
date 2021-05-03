package bowling.domain;

import java.util.*;
import java.util.stream.Collectors;

public class BowlingResult {
    private final Map<FrameNumber, SingleFrameResult> frameResultsMap;

    public BowlingResult() {
        frameResultsMap = new HashMap<>();
    }

    public BowlingResult(List<SingleFrameResult> singleFrameResultsMap) {
        this.frameResultsMap = new HashMap<>();

        FrameNumber frameNumber = new FrameNumber(1);
        for (SingleFrameResult singleFrameResult : singleFrameResultsMap) {
            add(frameNumber, singleFrameResult);
            frameNumber = frameNumber.increase();
        }
    }

    public void add(FrameNumber frameNumber, SingleFrameResult result) {
        Score aggregatedScore = prevAggregatedScore(frameNumber);
        result = new SingleFrameResult(result.pointSymbols(), result.score(), aggregatedScore.add(result.score()));
        frameResultsMap.put(frameNumber, result);
    }

    private Score prevAggregatedScore(FrameNumber frameNumber) {
        if (frameNumber.equals(new FrameNumber(1))) {
            return Score.create(0);
        }

        SingleFrameResult prevSingleFrameResult = frameResultsMap.get(frameNumber.decrease());
        if (prevSingleFrameResult == null) {
            return Score.create(0);
        }
        return prevSingleFrameResult.aggregatedScore();
    }

    public List<SingleFrameResult> results() {
        return Collections.unmodifiableList(frameResultsMapToList());
    }

    public SingleFrameResult result(FrameNumber frameNumber) {
        if (!frameResultsMap.containsKey(frameNumber)) {
            return new SingleFrameResult();
        }
        return frameResultsMap.get(frameNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BowlingResult that = (BowlingResult) o;
        return Objects.equals(frameResultsMap, that.frameResultsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(frameResultsMap);
    }

    private List<SingleFrameResult> frameResultsMapToList() {
        return frameResultsMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> (FrameNumber.compare(entry1.getKey(), entry2.getKey())))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
