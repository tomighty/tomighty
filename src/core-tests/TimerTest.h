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

#ifndef TIMERTEST_H
#define TIMERTEST_H

#include <QObject>

namespace tmty
{
  class Timer;
  class ManualClockwork;

  class TimerTest : public QObject
  {
      Q_OBJECT

    private Q_SLOTS:
      void init();

      // Initial state
      void test_timer_is_not_running();

      // Setting the timer
      void test_setting_the_timer_to_zero_length_is_illegal();
      void test_setting_the_timer_to_negative_length_is_illegal();
      void test_timer_is_running_after_it_is_set();
      void test_setting_the_timer_emits_a_timerSet_signal();

      // When the timer is set
      void test_it_is_not_allowed_to_set_the_timer_when_it_is_already_set();

      // As time elapses
      void test_timer_is_still_running_while_time_left_is_greater_than_zero();
      void test_timer_emits_a_secondElapsed_signal_as_time_elapses();

      // When the timer expires
      void test_timer_is_not_running_after_it_expires();
      void test_timer_pauses_the_clockwork_when_it_expires();
      void test_timer_emits_a_timerStopped_signal_when_it_expires();
      void test_timer_does_not_emit_a_secondElapsed_signal_at_the_moment_of_expiration();

      // Interrupting the timer
      void test_timer_is_not_running_after_it_is_interrupted();
      void test_timer_pauses_the_clockwork_when_interrupted();
      void test_timer_emits_a_timerStopped_signal_when_it_is_interrupted();
      void test_it_is_not_allowed_to_interrupt_the_timer_when_it_is_not_running();
      void test_timer_does_not_emit_a_timerStopped_signal_on_attempt_to_interrupt_it_while_not_running();

    private:
      Timer *_timer;
      ManualClockwork *_clockwork;
  };
}

#endif // TIMERTEST_H
