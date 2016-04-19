package uk.co.rossbeazley.wear.hours;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.TestWorld;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.rossbeazley.wear.hours.HoursModeConfigItem.HR_12;

public class ConfiguringHourModeWithConfigService implements CanReceiveHoursUpdates {

    private String timeComponentString;
    private CanBeTicked hours;
    private Calendar calendar;
    private TestWorld testWorld;

    @Before
    public void
    buildaTwoPMTwelveHourWorld() {
        testWorld = new TestWorld();
        assembleUseCase(testWorld);
    }

    private void assembleUseCase(TestWorld testWorld) {
        testWorld.with(new HoursModeConfigItem(HR_12));
        testWorld.build();
        hours = testWorld.core.canBeTicked;
        testWorld.core.canBeObservedForChangesToHours.addListener(this);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 2);
        calendar.set(Calendar.AM_PM, Calendar.PM);

        timeComponentString = "";
    }

    @Test
    public void
    its2PM() {

        final HoursModeConfigItem hoursModeConfigItem = testWorld.hoursModeConfigItem;
        String hoursModeId = hoursModeConfigItem.itemId();
        testWorld.configService.configureItem(hoursModeId);
        testWorld.configService.chooseOption(hoursModeConfigItem.optionFromHoursMode(hoursModeConfigItem.HR_12));

        hours.tick(calendar);

        assertThat(timeComponentString, is("02"));
    }

    @Test
    public void
    itsFourteenHundredHours() {

        final HoursModeConfigItem hoursModeConfigItem = testWorld.hoursModeConfigItem;
        String hoursModeId = hoursModeConfigItem.itemId();
        testWorld.configService.configureItem(hoursModeId);
        testWorld.configService.chooseOption(hoursModeConfigItem.optionFromHoursMode(hoursModeConfigItem.HR_24));

        hours.tick(calendar);

        assertThat(timeComponentString, is("14"));
    }

    @Test
    public void
    its2PMAfter1400() {

        final HoursModeConfigItem hoursModeConfigItem = testWorld.hoursModeConfigItem;
        String hoursModeId = hoursModeConfigItem.itemId();
        testWorld.configService.configureItem(hoursModeId);
        testWorld.configService.chooseOption(hoursModeConfigItem.optionFromHoursMode(HoursModeConfigItem.HR_24));

        final HashMapPersistence hashMapPersistence = testWorld.hashMapPersistence;
        this.testWorld = new TestWorld();
        testWorld.with(hashMapPersistence);

        assembleUseCase(testWorld);

        this.testWorld.configService.configureItem(hoursModeId);
        this.testWorld.configService.chooseOption(hoursModeConfigItem.optionFromHoursMode(HoursModeConfigItem.HR_12));

        hours.tick(calendar);

        assertThat(timeComponentString, is("02"));
    }

    @Override
    public void hoursUpdate(HourBase24 hourBase24) {
        this.timeComponentString = hourBase24.toBase10TwelveHour();
    }
}
