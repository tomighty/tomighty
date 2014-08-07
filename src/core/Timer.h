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

#ifndef TIMER_H
#define TIMER_H

#include "TimerSignalEmitter.h"

namespace tmty
{
  class Interval;
  enum class IntervalType;

  struct timer_not_set_error     : public std::exception {};
  struct timer_already_set_error : public std::exception {};

  class Timer : public TimerSignalEmitter
  {
    public:
      explicit Timer(QObject *parent = 0);

      virtual void set(int seconds, IntervalType type) = 0;
      virtual void interrupt() = 0;
      virtual bool isRunning() const = 0;
  };
}

#endif // TIMER_H
