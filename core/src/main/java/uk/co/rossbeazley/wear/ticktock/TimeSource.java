package uk.co.rossbeazley.wear.ticktock;

import java.util.Calendar;

/**
* Created by rdlb on 14/11/14.
*/
interface TimeSource {
    Calendar time();
}
