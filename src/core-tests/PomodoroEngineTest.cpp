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

#include "Interval.h"
#include "IntervalType.h"
#include "PomodoroEngineTest.h"
#include "StandardPomodoroEngine.h"
#include "mock/FakeTimer.h"
#include "mock/InMemoryPreferences.h"

using tmty::FakeTimer;
using tmty::InMemoryPreferences;
using tmty::Interval;
using tmty::IntervalType;
using tmty::PomodoroEngineTest;
using tmty::StandardPomodoroEngine;

void PomodoroEngineTest::init()
{
  _timer = new FakeTimer(this);
  _preferences = new InMemoryPreferences(this);
  _pomodoroEngine = new StandardPomodoroEngine(*_timer, *_preferences, this);

  _preferences->setIntervalLengthInMinutes(IntervalType::POMODORO, 25);
  _preferences->setIntervalLengthInMinutes(IntervalType::SHORT_BREAK, 5);
}

void PomodoroEngineTest::test_starting_the_engine_should_set_the_timer()
{
  _preferences->setIntervalLengthInMinutes(IntervalType::POMODORO, 25);

  _pomodoroEngine->start(IntervalType::POMODORO);

  QCOMPARE(_timer->isRunning(), true);
  QCOMPARE(_timer->interval().seconds(), 25 * 60);
  QCOMPARE(_timer->interval().type(), IntervalType::POMODORO);
}

void PomodoroEngineTest::test_starting_the_engine_while_it_is_running_should_interrupt_the_timer_first()
{
  _pomodoroEngine->start(IntervalType::POMODORO);
  _pomodoroEngine->start(IntervalType::SHORT_BREAK);

  QCOMPARE(_timer->isRunning(), true);
  QCOMPARE(_timer->interval().seconds(), 5 * 60);
  QCOMPARE(_timer->interval().type(), IntervalType::SHORT_BREAK);
}

void PomodoroEngineTest::test_stopping_the_engine_should_interrupt_the_timer()
{
  _pomodoroEngine->start(IntervalType::POMODORO);
  _pomodoroEngine->stop();
  QCOMPARE(_timer->isRunning(), false);
}

void PomodoroEngineTest::test_stopping_the_engine_while_it_is_not_running_has_no_effect()
{
  _pomodoroEngine->stop();
  QCOMPARE(_timer->isRunning(), false);
}
