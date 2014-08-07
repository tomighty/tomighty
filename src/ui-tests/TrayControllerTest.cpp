/*
 * Copyright (c) 2010-2014 Célio Cidral Junior.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

#include <QtTest>

#include "mock/MockPomodoroEngine.h"
#include "mock/MockTimerSignalEmitter.h"
#include "mock/MockTray.h"
#include "mock/MockTrayIconFiles.h"
#include "Interval.h"
#include "IntervalType.h"
#include "StandardTrayController.h"
#include "TrayControllerTest.h"

using tmty::Interval;
using tmty::IntervalType;
using tmty::MockPomodoroEngine;
using tmty::MockTimerSignalEmitter;
using tmty::ui::MockTray;
using tmty::ui::MockTrayIconFiles;
using tmty::ui::TrayControllerTest;
using tmty::ui::StandardTrayController;


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// TEST SETUP
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TrayControllerTest::init()
{
  _timerSignalEmitter = new MockTimerSignalEmitter(this);
  _pomodoroEngine = new MockPomodoroEngine(this);
  _tray = new MockTray(this);
  _trayIconFiles = new MockTrayIconFiles();

  _trayIconFiles->setIdleIcon("/path/to/idle.icon");
  _trayIconFiles->setIconForIntervalType(IntervalType::POMODORO, "/path/to/pomodoro.icon");
  _trayIconFiles->setIconForIntervalType(IntervalType::SHORT_BREAK, "/path/to/short_break.icon");
  _trayIconFiles->setIconForIntervalType(IntervalType::LONG_BREAK, "/path/to/long_break.icon");

  _trayController = new StandardTrayController(
      *_tray,
      *_trayIconFiles,
      *_pomodoroEngine,
      *_timerSignalEmitter,
      this
    );
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// HELPER MACROS
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

#define DISABLED  false
#define ENABLED   true
#define MINUTE    60

#define set_timer(LENGTH_IN_SECONDS, INTERVAL_TYPE) \
  Interval interval = Interval(LENGTH_IN_SECONDS, IntervalType::INTERVAL_TYPE); \
  emit _timerSignalEmitter->timerSet(interval)


#define given_that_a_pomodoro_has_started \
  set_timer(999, POMODORO)

#define given_that_a_short_break_has_started \
  set_timer(999, SHORT_BREAK)

#define given_that_a_long_break_has_started \
  set_timer(999, LONG_BREAK)

#define given_that_a_second_has_elapsed(SECONDS_LEFT) \
  emit _timerSignalEmitter->secondElapsed(SECONDS_LEFT, interval)

#define given_that_the_timer_has_stopped_during_a(INTERVAL_TYPE) \
  set_timer(999, INTERVAL_TYPE); \
  emit _timerSignalEmitter->timerStopped(-1, interval)


#define assert_pomodoro_engine_is_running_a(INTERVAL_TYPE) \
  QCOMPARE(_pomodoroEngine->isStarted(), true); \
  QCOMPARE(_pomodoroEngine->getCurrentIntervalType(), IntervalType::INTERVAL_TYPE)

#define assert_stop_timer_action_is(ENABLED_OR_DISABLED) \
  QCOMPARE(_tray->isStopTimerActionEnabled(), ENABLED_OR_DISABLED)

#define assert_pomodoro_action_is(ENABLED_OR_DISABLED) \
  QCOMPARE(_tray->isPomodoroActionEnabled(), ENABLED_OR_DISABLED)

#define assert_short_break_action_is(ENABLED_OR_DISABLED) \
  QCOMPARE(_tray->isShortBreakActionEnabled(), ENABLED_OR_DISABLED)

#define assert_long_break_action_is(ENABLED_OR_DISABLED) \
  QCOMPARE(_tray->isLongBreakActionEnabled(), ENABLED_OR_DISABLED)

#define assert_remaining_time_is(REMAINING_TIME) \
  QCOMPARE(_tray->remainingTimeText(), QString(REMAINING_TIME))

#define assert_current_tray_icon_file_is(ICON_FILE_PATH) \
  QCOMPARE(_tray->icon(), QString(ICON_FILE_PATH))


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// UPON INITIALIZATION
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TrayControllerTest::test_disable_the_stop_timer_action_upon_initialization()
{
  assert_stop_timer_action_is(DISABLED);
}

void TrayControllerTest::test_disable_the_pomodoro_count_reset_action_upon_initialization()
{
  QCOMPARE(_tray->isPomodoroCountResetActionEnabled(), false);
}

void TrayControllerTest::test_enable_pomodoro_action_upon_initialization()
{
  assert_pomodoro_action_is(ENABLED);
}

void TrayControllerTest::test_enable_short_break_action_upon_initialization()
{
  assert_short_break_action_is(ENABLED);
}

void TrayControllerTest::test_enable_long_break_action_upon_initialization()
{
  assert_long_break_action_is(ENABLED);
}

void TrayControllerTest::test_remaining_time_is_set_to_zero_upon_initialization()
{
  assert_remaining_time_is("00:00");
}

void TrayControllerTest::test_tray_icon_is_set_to_idle_state_upon_initialization()
{
  assert_current_tray_icon_file_is("/path/to/idle.icon");
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// THE TRAY ICON
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TrayControllerTest::test_show_tray_icon()
{
  _trayController->showTrayIcon();
  QCOMPARE(_tray->isShowing(), true);
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// STARTING THE POMODORO ENGINE
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TrayControllerTest::test_start_a_pomodoro_when_the_user_executes_the_pomodoro_action()
{
  emit _tray->pomodoroClicked();
  assert_pomodoro_engine_is_running_a(POMODORO);
}

void TrayControllerTest::test_start_a_short_break_when_the_user_executes_the_short_break_action()
{
  emit _tray->shortBreakClicked();
  assert_pomodoro_engine_is_running_a(SHORT_BREAK);
}

void TrayControllerTest::test_start_a_long_break_when_the_user_executes_the_long_break_action()
{
  emit _tray->longBreakClicked();
  assert_pomodoro_engine_is_running_a(LONG_BREAK);
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// STOPPING THE POMODORO ENGINE
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TrayControllerTest::test_stop_the_pomodoro_engine_when_the_user_executes_the_stop_timer_action()
{
  emit _tray->pomodoroClicked();
  emit _tray->stopTimerClicked();
  QCOMPARE(_pomodoroEngine->isStarted(), false);
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// WHEN THE TIMER IS SET
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TrayControllerTest::test_remaining_time_is_set_to_interval_length_when_the_timer_is_set()
{
  set_timer(12 * MINUTE + 34, POMODORO);
  assert_remaining_time_is("12:34");
}

void TrayControllerTest::test_enable_the_stop_timer_action_when_the_timer_is_set()
{
  given_that_a_pomodoro_has_started;
  assert_stop_timer_action_is(ENABLED);
}

void TrayControllerTest::test_change_tray_icon_according_to_the_interval_type_when_the_timer_is_set()
{
  set_timer(999, SHORT_BREAK);
  assert_current_tray_icon_file_is("/path/to/short_break.icon");
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// WHEN A POMODORO STARTS
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TrayControllerTest::test_disable_the_pomodoro_action_when_a_pomodoro_starts()
{
  given_that_a_pomodoro_has_started;
  assert_pomodoro_action_is(DISABLED);
}

void TrayControllerTest::test_enable_the_short_break_action_when_a_pomodoro_starts()
{
  given_that_a_pomodoro_has_started;
  assert_short_break_action_is(ENABLED);
}

void TrayControllerTest::test_enable_the_long_break_action_when_a_pomodoro_starts()
{
  given_that_a_pomodoro_has_started;
  assert_long_break_action_is(ENABLED);
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// WHEN A SHORT BREAK STARTS
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TrayControllerTest::test_enable_the_pomodoro_action_when_a_short_break_starts()
{
  given_that_a_short_break_has_started;
  assert_pomodoro_action_is(ENABLED);
}

void TrayControllerTest::test_disable_the_short_break_action_when_a_short_break_starts()
{
  given_that_a_short_break_has_started;
  assert_short_break_action_is(DISABLED);
}

void TrayControllerTest::test_enable_the_long_break_action_when_a_short_break_starts()
{
  given_that_a_short_break_has_started;
  assert_long_break_action_is(ENABLED);
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// WHEN A LONG BREAK STARTS
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TrayControllerTest::test_enable_the_pomodoro_action_when_a_long_break_starts()
{
  given_that_a_long_break_has_started;
  assert_pomodoro_action_is(ENABLED);
}

void TrayControllerTest::test_enable_the_short_break_action_when_a_long_break_starts()
{
  given_that_a_long_break_has_started;
  assert_short_break_action_is(ENABLED);
}

void TrayControllerTest::test_disable_the_long_break_action_when_a_long_break_starts()
{
  given_that_a_long_break_has_started;
  assert_long_break_action_is(DISABLED);
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// WHEN THE TIMER STOPS
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TrayControllerTest::test_disable_the_stop_timer_action_when_the_timer_stops()
{
  given_that_the_timer_has_stopped_during_a(POMODORO);
  assert_stop_timer_action_is(DISABLED);
}

void TrayControllerTest::test_enable_the_pomodoro_action_when_the_timer_stops()
{
  given_that_the_timer_has_stopped_during_a(POMODORO);
  assert_pomodoro_action_is(ENABLED);
}

void TrayControllerTest::test_enable_the_short_break_action_when_the_timer_stops()
{
  given_that_the_timer_has_stopped_during_a(SHORT_BREAK);
  assert_short_break_action_is(ENABLED);
}

void TrayControllerTest::test_enable_the_long_break_action_when_the_timer_stops()
{
  given_that_the_timer_has_stopped_during_a(LONG_BREAK);
  assert_long_break_action_is(ENABLED);
}

void TrayControllerTest::test_remaining_time_is_set_to_zero_when_the_timer_stops()
{
  given_that_the_timer_has_stopped_during_a(POMODORO);
  assert_remaining_time_is("00:00");
}

void TrayControllerTest::test_change_tray_icon_when_the_timer_stops()
{
  given_that_the_timer_has_stopped_during_a(POMODORO);
  assert_current_tray_icon_file_is("/path/to/idle.icon");
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// AS TIME ELAPSES
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TrayControllerTest::test_remaining_time_is_updated_each_time_a_second_elapses()
{
  set_timer(MINUTE + 8, POMODORO);

  given_that_a_second_has_elapsed(MINUTE + 7);
  assert_remaining_time_is("01:07");

  given_that_a_second_has_elapsed(MINUTE + 6);
  assert_remaining_time_is("01:06");
}
