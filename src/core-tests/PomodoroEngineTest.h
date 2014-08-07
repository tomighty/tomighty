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

#ifndef POMODOROENGINETEST_H
#define POMODOROENGINETEST_H

#include <QObject>

namespace tmty
{
  class FakeTimer;
  class PomodoroEngine;
  class InMemoryPreferences;

  class PomodoroEngineTest : public QObject
  {
      Q_OBJECT

    private Q_SLOTS:
      void init();

      void test_starting_the_engine_should_set_the_timer();
      void test_starting_the_engine_while_it_is_running_should_interrupt_the_timer_first();
      void test_stopping_the_engine_should_interrupt_the_timer();
      void test_stopping_the_engine_while_it_is_not_running_has_no_effect();

    private:
      PomodoroEngine *_pomodoroEngine;
      InMemoryPreferences *_preferences;
      FakeTimer *_timer;
  };
}

#endif // POMODOROENGINETEST_H
