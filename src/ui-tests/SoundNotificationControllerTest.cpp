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

#include "mock/MockSoundClip.h"
#include "mock/MockSoundNotifications.h"
#include "Interval.h"
#include "IntervalType.h"
#include "InMemoryPreferences.h"
#include "MockTimerSignalEmitter.h"
#include "SoundNotificationController.h"
#include "SoundNotificationControllerTest.h"

using tmty::Interval;
using tmty::IntervalType;
using tmty::InMemoryPreferences;
using tmty::MockTimerSignalEmitter;
using tmty::ui::MockSoundClip;
using tmty::ui::MockSoundNotifications;
using tmty::ui::SoundNotificationController;
using tmty::ui::SoundNotificationControllerTest;

void SoundNotificationControllerTest::init()
{
  _timerSignalEmitter = new MockTimerSignalEmitter(this);
  _timerSetSoundClip = new MockSoundClip();
  _timeElapsingSoundClip = new MockSoundClip();
  _timerExpiredSoundClip = new MockSoundClip();
  _soundNotifications = new MockSoundNotifications(*_timerSetSoundClip, *_timeElapsingSoundClip, *_timerExpiredSoundClip);
  _preferences = new InMemoryPreferences(this);
  _soundNotificationController = new SoundNotificationController(*_soundNotifications, *_preferences);

  _soundNotificationController->listenTo(*_timerSignalEmitter);
}

void SoundNotificationControllerTest::test_play_sound_notification_when_the_timer_is_set()
{
  _preferences->setShouldPlaySoundNotificationWhenTimerIsSet(true);
  _preferences->setShouldPlaySoundNotificationWhenTimerExpires(true);
  _preferences->setShouldPlaySoundNotificationDuringPomodoros(true);
  _preferences->setShouldPlaySoundNotificationDuringBreaks(true);

  Interval interval;
  emit _timerSignalEmitter->timerSet(interval);
  QCOMPARE(_timerSetSoundClip->timesPlayed(), 1);
  QCOMPARE(_timeElapsingSoundClip->timesPlayed(), 0);
  QCOMPARE(_timerExpiredSoundClip->timesPlayed(), 0);
}

void SoundNotificationControllerTest::test_play_sound_notification_when_the_timer_expires()
{
  _preferences->setShouldPlaySoundNotificationWhenTimerIsSet(true);
  _preferences->setShouldPlaySoundNotificationWhenTimerExpires(true);
  _preferences->setShouldPlaySoundNotificationDuringPomodoros(true);
  _preferences->setShouldPlaySoundNotificationDuringBreaks(true);

  int timeLeft = 0;
  Interval interval;
  emit _timerSignalEmitter->timerStopped(timeLeft, interval);

  QCOMPARE(_timerExpiredSoundClip->timesPlayed(), 1);
  QCOMPARE(_timerSetSoundClip->timesPlayed(), 0);
  QCOMPARE(_timeElapsingSoundClip->timesPlayed(), 0);
}

void SoundNotificationControllerTest::test_repeatedly_play_the_time_elapsing_sound_during_a_pomodoro()
{
  _preferences->setShouldPlaySoundNotificationWhenTimerIsSet(true);
  _preferences->setShouldPlaySoundNotificationWhenTimerExpires(true);
  _preferences->setShouldPlaySoundNotificationDuringPomodoros(true);
  _preferences->setShouldPlaySoundNotificationDuringBreaks(false);

  Interval interval = Interval(999, IntervalType::POMODORO);
  emit _timerSignalEmitter->timerSet(interval);
  QCOMPARE(_timeElapsingSoundClip->isLooping(), true);
  QCOMPARE(_timerSetSoundClip->isLooping(), false);
  QCOMPARE(_timerExpiredSoundClip->isLooping(), false);
}

void SoundNotificationControllerTest::test_repeatedly_play_the_time_elapsing_sound_during_a_short_break()
{
  _preferences->setShouldPlaySoundNotificationWhenTimerIsSet(true);
  _preferences->setShouldPlaySoundNotificationWhenTimerExpires(true);
  _preferences->setShouldPlaySoundNotificationDuringPomodoros(false);
  _preferences->setShouldPlaySoundNotificationDuringBreaks(true);

  Interval interval = Interval(999, IntervalType::SHORT_BREAK);
  emit _timerSignalEmitter->timerSet(interval);

  QCOMPARE(_timeElapsingSoundClip->isLooping(), true);
  QCOMPARE(_timerSetSoundClip->isLooping(), false);
  QCOMPARE(_timerExpiredSoundClip->isLooping(), false);
}

void SoundNotificationControllerTest::test_repeatedly_play_the_time_elapsing_sound_during_a_long_break()
{
  _preferences->setShouldPlaySoundNotificationWhenTimerIsSet(true);
  _preferences->setShouldPlaySoundNotificationWhenTimerExpires(true);
  _preferences->setShouldPlaySoundNotificationDuringPomodoros(false);
  _preferences->setShouldPlaySoundNotificationDuringBreaks(true);

  Interval interval = Interval(999, IntervalType::LONG_BREAK);
  emit _timerSignalEmitter->timerSet(interval);

  QCOMPARE(_timeElapsingSoundClip->isLooping(), true);
  QCOMPARE(_timerSetSoundClip->isLooping(), false);
  QCOMPARE(_timerExpiredSoundClip->isLooping(), false);
}

void SoundNotificationControllerTest::test_stop_the_time_elapsing_sound_when_the_timer_stops()
{
  int timeLeft = 999;
  Interval interval;

  emit _timerSignalEmitter->timerStopped(timeLeft, interval);
  QCOMPARE(_timeElapsingSoundClip->timesStopped(), 1);
}

void SoundNotificationControllerTest::test_do_not_play_any_sound_notification_when_the_timer_is_interrupted()
{
  _preferences->setShouldPlaySoundNotificationWhenTimerIsSet(true);
  _preferences->setShouldPlaySoundNotificationWhenTimerExpires(true);
  _preferences->setShouldPlaySoundNotificationDuringPomodoros(true);
  _preferences->setShouldPlaySoundNotificationDuringBreaks(true);

  int timeLeft = 999;
  Interval interval;
  emit _timerSignalEmitter->timerStopped(timeLeft, interval);

  QCOMPARE(_timerExpiredSoundClip->timesPlayed(), 0);
  QCOMPARE(_timerSetSoundClip->timesPlayed(), 0);
  QCOMPARE(_timeElapsingSoundClip->timesPlayed(), 0);
}

void SoundNotificationControllerTest::test_disable_the_timer_set_sound_notification()
{
  _preferences->setShouldPlaySoundNotificationWhenTimerIsSet(false);
  _preferences->setShouldPlaySoundNotificationWhenTimerExpires(true);
  _preferences->setShouldPlaySoundNotificationDuringPomodoros(true);
  _preferences->setShouldPlaySoundNotificationDuringBreaks(true);

  Interval interval;
  emit _timerSignalEmitter->timerSet(interval);
  QCOMPARE(_timerSetSoundClip->timesPlayed(), 0);
  QCOMPARE(_timeElapsingSoundClip->timesPlayed(), 0);
  QCOMPARE(_timerExpiredSoundClip->timesPlayed(), 0);
}

void SoundNotificationControllerTest::test_disable_the_timer_expired_sound_notification()
{
  _preferences->setShouldPlaySoundNotificationWhenTimerIsSet(true);
  _preferences->setShouldPlaySoundNotificationWhenTimerExpires(false);
  _preferences->setShouldPlaySoundNotificationDuringPomodoros(true);
  _preferences->setShouldPlaySoundNotificationDuringBreaks(true);

  int timeLeft = 0;
  Interval interval;
  emit _timerSignalEmitter->timerStopped(timeLeft, interval);

  QCOMPARE(_timerExpiredSoundClip->timesPlayed(), 0);
  QCOMPARE(_timerSetSoundClip->timesPlayed(), 0);
  QCOMPARE(_timeElapsingSoundClip->timesPlayed(), 0);
}

void SoundNotificationControllerTest::test_disable_the_time_elapsing_sound_notification_during_pomodoros()
{
  _preferences->setShouldPlaySoundNotificationWhenTimerIsSet(true);
  _preferences->setShouldPlaySoundNotificationWhenTimerExpires(true);
  _preferences->setShouldPlaySoundNotificationDuringPomodoros(false);
  _preferences->setShouldPlaySoundNotificationDuringBreaks(true);

  Interval interval = Interval(999, IntervalType::POMODORO);
  emit _timerSignalEmitter->timerSet(interval);
  QCOMPARE(_timeElapsingSoundClip->isLooping(), false);
  QCOMPARE(_timerSetSoundClip->isLooping(), false);
  QCOMPARE(_timerExpiredSoundClip->isLooping(), false);
}

void SoundNotificationControllerTest::test_disable_the_time_elapsing_sound_notification_during_short_breaks()
{
  _preferences->setShouldPlaySoundNotificationWhenTimerIsSet(true);
  _preferences->setShouldPlaySoundNotificationWhenTimerExpires(true);
  _preferences->setShouldPlaySoundNotificationDuringPomodoros(true);
  _preferences->setShouldPlaySoundNotificationDuringBreaks(false);

  Interval interval = Interval(999, IntervalType::SHORT_BREAK);
  emit _timerSignalEmitter->timerSet(interval);
  QCOMPARE(_timeElapsingSoundClip->isLooping(), false);
  QCOMPARE(_timerSetSoundClip->isLooping(), false);
  QCOMPARE(_timerExpiredSoundClip->isLooping(), false);
}

void SoundNotificationControllerTest::test_disable_the_time_elapsing_sound_notification_during_long_breaks()
{
  _preferences->setShouldPlaySoundNotificationWhenTimerIsSet(true);
  _preferences->setShouldPlaySoundNotificationWhenTimerExpires(true);
  _preferences->setShouldPlaySoundNotificationDuringPomodoros(true);
  _preferences->setShouldPlaySoundNotificationDuringBreaks(false);

  Interval interval = Interval(999, IntervalType::LONG_BREAK);
  emit _timerSignalEmitter->timerSet(interval);
  QCOMPARE(_timeElapsingSoundClip->isLooping(), false);
  QCOMPARE(_timerSetSoundClip->isLooping(), false);
  QCOMPARE(_timerExpiredSoundClip->isLooping(), false);
}
