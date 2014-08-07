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

#include "IntervalType.h"
#include "StandardPreferences.h"

using tmty::IntervalType;
using tmty::Preferences;
using tmty::StandardPreferences;

StandardPreferences::StandardPreferences(QObject *parent) :
  Preferences(parent)
{
}

void StandardPreferences::setIntervalLengthInMinutes(IntervalType intervalType, int minutes)
{
  //TODO
}

int StandardPreferences::intervalLengthInMinutes(IntervalType intervalType) const
{
  if(intervalType == IntervalType::POMODORO)
    return 25;

  if(intervalType == IntervalType::SHORT_BREAK)
    return 5;

  return 15;
}
