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

#include "MockPomodoroEngine.h"

using tmty::IntervalType;
using tmty::MockPomodoroEngine;
using tmty::PomodoroEngine;

MockPomodoroEngine::MockPomodoroEngine(QObject *parent) :
  PomodoroEngine(parent)
{
  _isStarted = false;
  _currentIntervalType = (IntervalType) -1;
}

void MockPomodoroEngine::start(IntervalType intervalType)
{
  _isStarted = true;
  _currentIntervalType = intervalType;
}

void MockPomodoroEngine::stop()
{
  _isStarted = false;
}

bool MockPomodoroEngine::isStarted() const
{
  return _isStarted;
}

IntervalType MockPomodoroEngine::getCurrentIntervalType() const
{
  return _currentIntervalType;
}
