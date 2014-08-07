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

#include "StandardTimer.h"
#include "../Clockwork.h"
#include "../Interval.h"
#include "../IntervalType.h"

using tmty::Clockwork;
using tmty::Interval;
using tmty::IntervalType;
using tmty::StandardTimer;

StandardTimer::StandardTimer(tmty::Clockwork &clockwork, QObject *parent) :
  Timer(parent),
  _clockwork(clockwork)
{
  _isRunning = false;
  _secondsLeft = 0;
  _interval = 0;

  connect(&_clockwork, SIGNAL(secondElapsed()),
          this, SLOT(decreaseOneSecond()));
}

bool StandardTimer::isRunning() const
{
  return _isRunning;
}

void StandardTimer::set(int seconds, IntervalType type)
{
  if(seconds <= 0)
    throw std::invalid_argument("interval length in seconds must be greater than zero");

  if(_isRunning)
    throw timer_already_set_error();

  delete _interval;

  _interval = new Interval(seconds, type);
  _secondsLeft = seconds;
  _isRunning = true;
  _clockwork.resume();

  emit timerSet(*_interval);
}

void StandardTimer::interrupt()
{
  if(!_isRunning)
    throw timer_not_set_error();

  stop();
}

void StandardTimer::decreaseOneSecond()
{
  --_secondsLeft;

  if(_secondsLeft > 0)
  {
    emit secondElapsed(_secondsLeft, *_interval);
  }
  else
  {
    stop();
  }
}

void StandardTimer::stop()
{
  _isRunning = false;
  _clockwork.pause();
  emit timerStopped(_secondsLeft, *_interval);
}
