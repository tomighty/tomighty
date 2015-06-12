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

#include <stdexcept>

#include "Interval.h"
#include "IntervalType.h"
#include "FakeTimer.h"

using tmty::FakeTimer;
using tmty::Interval;
using tmty::IntervalType;

FakeTimer::FakeTimer(QObject *parent) :
  Timer(parent)
{
  _isRunning = false;
  _interval = new Interval();
}

void FakeTimer::set(int seconds, IntervalType type)
{
  if(_isRunning)
    throw std::runtime_error("fake timer already running");

  _isRunning = true;
  _interval = new Interval(seconds, type);
}

const Interval &FakeTimer::interval() const
{
  return *_interval;
}

bool FakeTimer::isRunning() const
{
  return _isRunning;
}

void FakeTimer::interrupt()
{
  if(!_isRunning)
    throw std::runtime_error("fake timer not running");

  _isRunning = false;
}
