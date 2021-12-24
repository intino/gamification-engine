package io.tetrabot.test.util;

import io.tetrabot.util.time.Scale;
import io.tetrabot.util.time.TimeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

public class TimeUtils_ {

	@Test
	public void instantOf() {

		Assert.assertEquals(TimeUtils.getInstantOf(2020), Instant.parse("2020-01-01T00:00:00Z"));
		Assert.assertEquals(TimeUtils.getInstantOf(2020, 12), Instant.parse("2020-12-01T00:00:00Z"));
		Assert.assertEquals(TimeUtils.getInstantOf(2020, 12, 31), Instant.parse("2020-12-31T00:00:00Z"));
		Assert.assertEquals(TimeUtils.getInstantOf(2020, 12, 16, 8), Instant.parse("2020-12-16T08:00:00Z"));
		Assert.assertEquals(TimeUtils.getInstantOf(2020, 12, 16, 8, 16), Instant.parse("2020-12-16T08:16:00Z"));
		Assert.assertEquals(TimeUtils.getInstantOf(2020, 2, 28), Instant.parse("2020-02-28T00:00:00Z"));
		Assert.assertEquals(TimeUtils.getInstantOf(2020, 2, 29), Instant.parse("2020-02-29T00:00:00Z"));
		Assert.assertEquals(TimeUtils.getInstantOf(2019, 2, 28), Instant.parse("2019-02-28T00:00:00Z"));

		assertNull(TimeUtils.getInstantOf(-2020));
		assertNull(TimeUtils.getInstantOf(2020, -12));
		assertNull(TimeUtils.getInstantOf(2020, 0));
		assertNull(TimeUtils.getInstantOf(2020, 13));
		assertNull(TimeUtils.getInstantOf(2020, 12, -16));
		assertNull(TimeUtils.getInstantOf(2020, 12, 0));
		assertNull(TimeUtils.getInstantOf(2020, 12, 32));
		assertNull(TimeUtils.getInstantOf(2020, 11, -16));
		assertNull(TimeUtils.getInstantOf(2020, 11, 0));
		assertNull(TimeUtils.getInstantOf(2020, 11, 31));
		assertNull(TimeUtils.getInstantOf(2020, 2, 30));
		assertNull(TimeUtils.getInstantOf(2019, 2, 29));
		assertNull(TimeUtils.getInstantOf(2019, 2, 30));
		assertNull(TimeUtils.getInstantOf(2020, 12, 16, -1));
		assertNull(TimeUtils.getInstantOf(2020, 12, 16, 25));
		assertNull(TimeUtils.getInstantOf(2020, 12, 16, 8, -1));
		assertNull(TimeUtils.getInstantOf(2020, 12, 16, 8, 60));
	}

	@Test
	public void componentOf() {

		Instant instant = TimeUtils.getInstantOf(2020, 12, 16, 8, 16);

		Assert.assertEquals(TimeUtils.minuteOf(instant), 16);
		Assert.assertEquals(TimeUtils.hourOf(instant), 8);
		Assert.assertEquals(TimeUtils.monthOf(instant), 12);
		Assert.assertEquals(TimeUtils.yearOf(instant), 2020);
	}

	@Test
	public void truncateOf() {

		Instant instant = TimeUtils.getInstantOf(2020, 12, 16, 8, 16);

		Assert.assertEquals(TimeUtils.truncateTo(instant, Scale.Hour), TimeUtils.getInstantOf(2020, 12, 16, 8));
		Assert.assertEquals(TimeUtils.truncateTo(instant, Scale.Day), TimeUtils.getInstantOf(2020, 12, 16));
		Assert.assertEquals(TimeUtils.truncateTo(instant, Scale.Week), TimeUtils.getInstantOf(2020, 12, 14));
		Assert.assertEquals(TimeUtils.truncateTo(instant, Scale.Month), TimeUtils.getInstantOf(2020, 12));
		Assert.assertEquals(TimeUtils.truncateTo(instant, Scale.Year), TimeUtils.getInstantOf(2020));
	}

	@Test
	public void offsetInstant() {

		Instant instant = TimeUtils.getInstantOf(2020, 12, 16, 8, 16);
		Instant previousInstant = TimeUtils.getInstantOf(2021, 1, 1, 0, 1);
		Instant nextInstant = TimeUtils.getInstantOf(2020, 12, 31, 23, 59);

		Assert.assertEquals(TimeUtils.previousInstant(instant, Scale.Hour), TimeUtils.getInstantOf(2020, 12, 16, 7, 16));
		Assert.assertEquals(TimeUtils.previousInstant(instant, Scale.Day), TimeUtils.getInstantOf(2020, 12, 15, 8, 16));
		Assert.assertEquals(TimeUtils.previousInstant(instant, Scale.Week), TimeUtils.getInstantOf(2020, 12, 9, 8, 16));
		Assert.assertEquals(TimeUtils.previousInstant(instant, Scale.Month), TimeUtils.getInstantOf(2020, 11, 16, 8, 16));
		Assert.assertEquals(TimeUtils.previousInstant(instant, Scale.Year), TimeUtils.getInstantOf(2019, 12, 16, 8, 16));
		Assert.assertEquals(TimeUtils.previousInstant(instant, Scale.Hour, 2), TimeUtils.getInstantOf(2020, 12, 16, 6, 16));
		Assert.assertEquals(TimeUtils.previousInstant(instant, Scale.Day, 2), TimeUtils.getInstantOf(2020, 12, 14, 8, 16));
		Assert.assertEquals(TimeUtils.previousInstant(instant, Scale.Week, 2), TimeUtils.getInstantOf(2020, 12, 2, 8, 16));
		Assert.assertEquals(TimeUtils.previousInstant(instant, Scale.Month, 2), TimeUtils.getInstantOf(2020, 10, 16, 8, 16));
		Assert.assertEquals(TimeUtils.previousInstant(instant, Scale.Year, 2), TimeUtils.getInstantOf(2018, 12, 16, 8, 16));
		Assert.assertEquals(TimeUtils.previousInstant(previousInstant, Scale.Hour), TimeUtils.getInstantOf(2020, 12, 31, 23, 1));
		Assert.assertEquals(TimeUtils.previousInstant(previousInstant, Scale.Day), TimeUtils.getInstantOf(2020, 12, 31, 0, 1));
		Assert.assertEquals(TimeUtils.previousInstant(previousInstant, Scale.Week), TimeUtils.getInstantOf(2020, 12, 25, 0, 1));
		Assert.assertEquals(TimeUtils.previousInstant(previousInstant, Scale.Month), TimeUtils.getInstantOf(2020, 12, 1, 0, 1));
		Assert.assertEquals(TimeUtils.previousInstant(previousInstant, Scale.Year), TimeUtils.getInstantOf(2020, 1, 1, 0, 1));

		Assert.assertEquals(TimeUtils.nextInstant(instant, Scale.Hour), TimeUtils.getInstantOf(2020, 12, 16, 9, 16));
		Assert.assertEquals(TimeUtils.nextInstant(instant, Scale.Day), TimeUtils.getInstantOf(2020, 12, 17, 8, 16));
		Assert.assertEquals(TimeUtils.nextInstant(instant, Scale.Week), TimeUtils.getInstantOf(2020, 12, 23, 8, 16));
		Assert.assertEquals(TimeUtils.nextInstant(instant, Scale.Month), TimeUtils.getInstantOf(2021, 1, 16, 8, 16));
		Assert.assertEquals(TimeUtils.nextInstant(instant, Scale.Year), TimeUtils.getInstantOf(2021, 12, 16, 8, 16));
		Assert.assertEquals(TimeUtils.nextInstant(instant, Scale.Hour, 2), TimeUtils.getInstantOf(2020, 12, 16, 10, 16));
		Assert.assertEquals(TimeUtils.nextInstant(instant, Scale.Day, 2), TimeUtils.getInstantOf(2020, 12, 18, 8, 16));
		Assert.assertEquals(TimeUtils.nextInstant(instant, Scale.Week, 2), TimeUtils.getInstantOf(2020, 12, 30, 8, 16));
		Assert.assertEquals(TimeUtils.nextInstant(instant, Scale.Month, 2), TimeUtils.getInstantOf(2021, 2, 16, 8, 16));
		Assert.assertEquals(TimeUtils.nextInstant(instant, Scale.Year, 2), TimeUtils.getInstantOf(2022, 12, 16, 8, 16));
		Assert.assertEquals(TimeUtils.nextInstant(nextInstant, Scale.Hour), TimeUtils.getInstantOf(2021, 1, 1, 0, 59));
		Assert.assertEquals(TimeUtils.nextInstant(nextInstant, Scale.Day), TimeUtils.getInstantOf(2021, 1, 1, 23, 59));
		Assert.assertEquals(TimeUtils.nextInstant(nextInstant, Scale.Week), TimeUtils.getInstantOf(2021, 1, 7, 23, 59));
		Assert.assertEquals(TimeUtils.nextInstant(nextInstant, Scale.Month), TimeUtils.getInstantOf(2021, 1, 31, 23, 59));
		Assert.assertEquals(TimeUtils.nextInstant(nextInstant, Scale.Year), TimeUtils.getInstantOf(2021, 12, 31, 23, 59));

		Assert.assertEquals(TimeUtils.previousInstant(TimeUtils.getInstantOf(2020, 4, 30), Scale.Month), TimeUtils.getInstantOf(2020, 3, 30));
		Assert.assertEquals(TimeUtils.previousInstant(TimeUtils.getInstantOf(2020, 5, 31), Scale.Month), TimeUtils.getInstantOf(2020, 4, 30));
		Assert.assertEquals(TimeUtils.nextInstant(TimeUtils.getInstantOf(2020, 4, 30), Scale.Month), TimeUtils.getInstantOf(2020, 5, 30));
		Assert.assertEquals(TimeUtils.nextInstant(TimeUtils.getInstantOf(2020, 5, 31), Scale.Month), TimeUtils.getInstantOf(2020, 6, 30));
		Assert.assertEquals(TimeUtils.previousInstant(TimeUtils.getInstantOf(2020, 2, 29), Scale.Year), TimeUtils.getInstantOf(2019, 2, 28));
		Assert.assertEquals(TimeUtils.nextInstant(TimeUtils.getInstantOf(2020, 2, 29), Scale.Year), TimeUtils.getInstantOf(2021, 2, 28));
	}

	@Test
	public void instantDiff() {

		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Year(), instant2(), Scale.Week, true), 53);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Year(), instant2(), Scale.Week, false), 52);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Year(), instant2(), Scale.Day, true), 365);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Year(), instant2(), Scale.Day, false), 365);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Year(), instant2(), Scale.Hour, true), 8760);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Year(), instant2(), Scale.Hour, false), 8760);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Year(), instant2(), Scale.Minute, true), 525600);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Year(), instant2(), Scale.Minute, false), 525600);

		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Month(), instant2(), Scale.Week, true), 9);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Month(), instant2(), Scale.Week, false), 8);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Month(), instant2(), Scale.Day, true), 61);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Month(), instant2(), Scale.Day, false), 61);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Month(), instant2(), Scale.Hour, true), 1464);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Month(), instant2(), Scale.Hour, false), 1464);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Month(), instant2(), Scale.Minute, true), 87840);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Month(), instant2(), Scale.Minute, false), 87840);

		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Day(), instant2(), Scale.Week, true), 7);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Day(), instant2(), Scale.Week, false), 6);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Day(), instant2(), Scale.Day, true), 44);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Day(), instant2(), Scale.Day, false), 44);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Day(), instant2(), Scale.Hour, true), 1056);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Day(), instant2(), Scale.Hour, false), 1056);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Day(), instant2(), Scale.Minute, true), 63360);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Day(), instant2(), Scale.Minute, false), 63360);

		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Hour(), instant2(), Scale.Week, true), 7);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Hour(), instant2(), Scale.Week, false), 6);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Hour(), instant2(), Scale.Day, true), 44);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Hour(), instant2(), Scale.Day, false), 43);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Hour(), instant2(), Scale.Hour, true), 1044);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Hour(), instant2(), Scale.Hour, false), 1044);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Hour(), instant2(), Scale.Minute, true), 62640);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Hour(), instant2(), Scale.Minute, false), 62640);

		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Minute(), instant2(), Scale.Week, true), 7);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Minute(), instant2(), Scale.Week, false), 6);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Minute(), instant2(), Scale.Day, true), 44);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Minute(), instant2(), Scale.Day, false), 43);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Minute(), instant2(), Scale.Hour, true), 1044);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Minute(), instant2(), Scale.Hour, false), 1043);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Minute(), instant2(), Scale.Minute, true), 62585);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Minute(), instant2(), Scale.Minute, false), 62585);

		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Second(), instant2(), Scale.Week, true), 7);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Second(), instant2(), Scale.Week, false), 6);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Second(), instant2(), Scale.Day, true), 44);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Second(), instant2(), Scale.Day, false), 43);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Second(), instant2(), Scale.Hour, true), 1044);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Second(), instant2(), Scale.Hour, false), 1043);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Second(), instant2(), Scale.Minute, true), 62585);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Second(), instant2(), Scale.Minute, false), 62584);

		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Milli(), instant2(), Scale.Week, true), 7);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Milli(), instant2(), Scale.Week, false), 6);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Milli(), instant2(), Scale.Day, true), 44);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Milli(), instant2(), Scale.Day, false), 43);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Milli(), instant2(), Scale.Hour, true), 1044);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Milli(), instant2(), Scale.Hour, false), 1043);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Milli(), instant2(), Scale.Minute, true), 62585);
		Assert.assertEquals(TimeUtils.getInstantDiff(instant1Milli(), instant2(), Scale.Minute, false), 62584);
	}

	private static Instant instant1Year() {
		return TimeUtils.getInstantOf(2021);
	}

	private static Instant instant1Month() {
		return TimeUtils.getInstantOf(2021, 11);
	}

	private static Instant instant1Day() {
		return TimeUtils.getInstantOf(2021, 11, 18);
	}

	private static Instant instant1Hour() {
		return TimeUtils.getInstantOf(2021, 11, 18, 12);
	}

	private static Instant instant1Minute() {
		return TimeUtils.getInstantOf(2021, 11, 18, 12, 55);
	}

	private static Instant instant1Second() {
		return TimeUtils.getInstantOf(2021, 11, 18, 12, 55, 34);
	}

	private static Instant instant1Milli() {
		return TimeUtils.getInstantOf(2021, 11, 18, 12, 55, 34, 236);
	}

	private static Instant instant2() {
		return TimeUtils.getInstantOf(2022);
	}

	@Test
	public void instantRange() {

		Instant instant = TimeUtils.getInstantOf(2020, 12, 16, 8, 16);

		assertTrue(TimeUtils.instantIsInRange(instant, TimeUtils.getInstantOf(2020), TimeUtils.getInstantOf(2021)));
		assertTrue(TimeUtils.instantIsInRange(instant, TimeUtils.getInstantOf(2020, 12, 16), TimeUtils.getInstantOf(2020, 12, 17)));
		assertFalse(TimeUtils.instantIsInRange(instant, TimeUtils.getInstantOf(2020, 12, 15), TimeUtils.getInstantOf(2020, 12, 16)));
		assertTrue(TimeUtils.instantIsInRange(instant, TimeUtils.getInstantOf(2020, 12, 16, 8, 16), TimeUtils.getInstantOf(2020, 12, 16, 8, 17)));
		assertFalse(TimeUtils.instantIsInRange(instant, TimeUtils.getInstantOf(2020, 12, 16, 8, 15), TimeUtils.getInstantOf(2020, 12, 16, 8, 16)));
	}
}
