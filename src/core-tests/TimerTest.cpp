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

#include <QList>
#include <QString>
#include <QtTest>
#include <QVariant>
#include <stdexcept>

#include "Interval.h"
#include "IntervalType.h"
#include "StandardTimer.h"
#include "TimerTest.h"
#include "ManualClockwork.h"

using tmty::Interval;
using tmty::IntervalType;
using tmty::ManualClockwork;
using tmty::StandardTimer;
using tmty::Timer;
using tmty::TimerTest;

Q_DECLARE_METATYPE(Interval)


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// TEST SETUP
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TimerTest::init()
{
  _clockwork = new ManualClockwork(this);
  _timer = new StandardTimer(*_clockwork, this);

  qRegisterMetaType<Interval>("Interval");
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// HELPER MACROS
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

#define spy_on_timerSet_signal \
  QSignalSpy spy(_timer, SIGNAL(timerSet(const Interval &)))

#define spy_on_secondElapsed_signal \
  QSignalSpy spy(_timer, SIGNAL(secondElapsed(int, const Interval &)))

#define spy_on_timerStopped_signal \
  QSignalSpy spy(_timer, SIGNAL(timerStopped(int, const Interval &)))


// I'm not proud of this:

#define assert_emitted_timerSet_signal(INTERVAL_LENGTH, INTERVAL_TYPE) \
  QCOMPARE(spy.count(), 1); \
  QCOMPARE(spy.at(0).count(), 1); \
  QCOMPARE(spy.at(0).at(0).value<Interval>().seconds(), INTERVAL_LENGTH); \
  QCOMPARE(spy.at(0).at(0).value<Interval>().type(), IntervalType::INTERVAL_TYPE); \
  spy.clear()

#define assert_emitted_secondElapsed_signal(SECONDS_LEFT, INTERVAL_LENGTH, INTERVAL_TYPE) \
  QCOMPARE(spy.count(), 1); \
  QCOMPARE(spy.at(0).count(), 2); \
  QCOMPARE(spy.at(0).at(0).toInt(), SECONDS_LEFT); \
  QCOMPARE(spy.at(0).at(1).value<Interval>().seconds(), INTERVAL_LENGTH); \
  QCOMPARE(spy.at(0).at(1).value<Interval>().type(), IntervalType::INTERVAL_TYPE); \
  spy.clear()

#define assert_emitted_timerStopped_signal(SECONDS_LEFT, INTERVAL_LENGTH, INTERVAL_TYPE) \
  QCOMPARE(spy.count(), 1); \
  QCOMPARE(spy.at(0).count(), 2); \
  QCOMPARE(spy.at(0).at(0).toInt(), SECONDS_LEFT); \
  QCOMPARE(spy.at(0).at(1).value<Interval>().seconds(), INTERVAL_LENGTH); \
  QCOMPARE(spy.at(0).at(1).value<Interval>().type(), IntervalType::INTERVAL_TYPE); \
  spy.clear()


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// INITIAL STATE
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TimerTest::test_timer_is_not_running()
{
  QCOMPARE(_timer->isRunning(), false);
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// SETTING THE TIMER
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TimerTest::test_setting_the_timer_to_zero_length_is_illegal()
{
  try
  {
    _timer->set(0, IntervalType::POMODORO);
    QFAIL("Should throw invalid_argument exception");
  }
  catch(std::invalid_argument) {}
}

void TimerTest::test_setting_the_timer_to_negative_length_is_illegal()
{
  try
  {
    _timer->set(-1, IntervalType::POMODORO);
    QFAIL("Should throw invalid_argument exception");
  }
  catch(std::invalid_argument) {}
}

void TimerTest::test_timer_is_running_after_it_is_set()
{
  _timer->set(100, IntervalType::POMODORO);
  QCOMPARE(_timer->isRunning(), true);
}

void TimerTest::test_setting_the_timer_emits_a_timerSet_signal()
{
  spy_on_timerSet_signal;
  _timer->set(200, IntervalType::LONG_BREAK);
  assert_emitted_timerSet_signal(200, LONG_BREAK);
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// WHEN THE TIMER IS SET
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TimerTest::test_it_is_not_allowed_to_set_the_timer_when_it_is_already_set()
{
  _timer->set(10, IntervalType::POMODORO);

  try
  {
    _timer->set(10, IntervalType::POMODORO);
    QFAIL("Should throw timer_already_set_error");
  }
  catch (timer_already_set_error) {}
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// AS TIME ELAPSES
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TimerTest::test_timer_is_still_running_while_time_left_is_greater_than_zero()
{
  _timer->set(10, IntervalType::POMODORO);

  _clockwork->moveOneSecond();
  QCOMPARE(_timer->isRunning(), true);

  _clockwork->moveOneSecond();
  QCOMPARE(_timer->isRunning(), true);
}

void TimerTest::test_timer_emits_a_secondElapsed_signal_as_time_elapses()
{
  spy_on_secondElapsed_signal;

  _timer->set(10, IntervalType::POMODORO);

  _clockwork->moveOneSecond();
  assert_emitted_secondElapsed_signal(9, 10, POMODORO);

  _clockwork->moveOneSecond();
  assert_emitted_secondElapsed_signal(8, 10, POMODORO);
}

void TimerTest::test_timer_does_not_emit_a_secondElapsed_signal_at_the_moment_of_expiration()
{
  _timer->set(3, IntervalType::POMODORO);

  _clockwork->moveOneSecond();
  _clockwork->moveOneSecond();

  QSignalSpy spy(_timer, SIGNAL(secondElapsed(int, const Interval &)));
  _clockwork->moveOneSecond();

  QCOMPARE(spy.count(), 0);
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// WHEN THE TIMER EXPIRES
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TimerTest::test_timer_is_not_running_after_it_expires()
{
  _timer->set(3, IntervalType::POMODORO);

  _clockwork->moveOneSecond();
  _clockwork->moveOneSecond();
  _clockwork->moveOneSecond();

  QCOMPARE(_timer->isRunning(), false);
}

void TimerTest::test_timer_pauses_the_clockwork_when_it_expires()
{
  _timer->set(3, IntervalType::POMODORO);

  _clockwork->moveOneSecond();
  _clockwork->moveOneSecond();
  _clockwork->moveOneSecond();

  QCOMPARE(_clockwork->isRunning(), false);
}

void TimerTest::test_timer_emits_a_timerStopped_signal_when_it_expires()
{
  spy_on_timerStopped_signal;

  _timer->set(3, IntervalType::POMODORO);

  _clockwork->moveOneSecond();
  _clockwork->moveOneSecond();
  _clockwork->moveOneSecond();

  assert_emitted_timerStopped_signal(0, 3, POMODORO);
}


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// INTERRUPTING THE TIMER
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void TimerTest::test_timer_is_not_running_after_it_is_interrupted()
{
  _timer->set(100, IntervalType::POMODORO);
  _timer->interrupt();
  QCOMPARE(_timer->isRunning(), false);
}

void TimerTest::test_timer_pauses_the_clockwork_when_interrupted()
{
  _timer->set(10, IntervalType::SHORT_BREAK);
  _timer->interrupt();
  QCOMPARE(_clockwork->isRunning(), false);
}

void TimerTest::test_timer_emits_a_timerStopped_signal_when_it_is_interrupted()
{
  spy_on_timerStopped_signal;

  _timer->set(10, IntervalType::SHORT_BREAK);

  _clockwork->moveOneSecond();
  _clockwork->moveOneSecond();
  _clockwork->moveOneSecond();

  _timer->interrupt();

  assert_emitted_timerStopped_signal(7, 10, SHORT_BREAK);
}

void TimerTest::test_it_is_not_allowed_to_interrupt_the_timer_when_it_is_not_running()
{
  try
  {
    _timer->interrupt();
    QFAIL("Should throw timer_not_set_error");
  }
  catch (timer_not_set_error) {}
}

void TimerTest::test_timer_does_not_emit_a_timerStopped_signal_on_attempt_to_interrupt_it_while_not_running()
{
  QSignalSpy spy(_timer, SIGNAL(timerStopped(int, const Interval &)));

  try
  {
    _timer->interrupt();
  }
  catch (timer_not_set_error) {}

  QCOMPARE(spy.count(), 0);
}
