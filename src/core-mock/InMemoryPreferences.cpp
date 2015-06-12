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

#include <QMap>

#include "InMemoryPreferences.h"

using tmty::InMemoryPreferences;

InMemoryPreferences::InMemoryPreferences(QObject *parent) :
  Preferences(parent),
  _shouldPlaySoundNotificationWhenTimerIsSet(false),
  _shouldPlaySoundNotificationWhenTimerExpires(false),
  _shouldPlaySoundNotificationDuringPomodoros(false),
  _shouldPlaySoundNotificationDuringBreaks(false)
{}

int InMemoryPreferences::intervalLengthInMinutes(IntervalType intervalType) const
{
  return _intervalLengths[intervalType];
}

void InMemoryPreferences::setIntervalLengthInMinutes(IntervalType intervalType, int minutes)
{
  _intervalLengths[intervalType] = minutes;
}

bool InMemoryPreferences::shouldPlaySoundNotificationWhenTimerIsSet()
{
  return _shouldPlaySoundNotificationWhenTimerIsSet;
}

void InMemoryPreferences::setShouldPlaySoundNotificationWhenTimerIsSet(bool shouldPlay)
{
  _shouldPlaySoundNotificationWhenTimerIsSet = shouldPlay;
}

bool InMemoryPreferences::shouldPlaySoundNotificationDuringPomodoros()
{
  return _shouldPlaySoundNotificationDuringPomodoros;
}

void InMemoryPreferences::setShouldPlaySoundNotificationDuringPomodoros(bool shouldPlay)
{
  _shouldPlaySoundNotificationDuringPomodoros = shouldPlay;
}

bool InMemoryPreferences::shouldPlaySoundNotificationDuringBreaks()
{
  return _shouldPlaySoundNotificationDuringBreaks;
}

void InMemoryPreferences::setShouldPlaySoundNotificationDuringBreaks(bool shouldPlay)
{
  _shouldPlaySoundNotificationDuringBreaks = shouldPlay;
}

bool InMemoryPreferences::shouldPlaySoundNotificationWhenTimerExpires()
{
  return _shouldPlaySoundNotificationWhenTimerExpires;
}

void InMemoryPreferences::setShouldPlaySoundNotificationWhenTimerExpires(bool shouldPlay)
{
  _shouldPlaySoundNotificationWhenTimerExpires = shouldPlay;
}
