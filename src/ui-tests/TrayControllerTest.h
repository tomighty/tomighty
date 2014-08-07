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

#ifndef TRAYCONTROLLERTEST_H
#define TRAYCONTROLLERTEST_H

#include <QObject>

namespace tmty
{
  class MockPomodoroEngine;
  class MockTimerSignalEmitter;

  enum class IntervalType;

  namespace ui
  {
    class MockTray;
    class MockTrayIconFiles;
    class TrayController;

    class TrayControllerTest : public QObject
    {
        Q_OBJECT

      private Q_SLOTS:
        void init();

        // Upon initialization
        void test_disable_the_stop_timer_action_upon_initialization();
        void test_disable_the_pomodoro_count_reset_action_upon_initialization();
        void test_enable_pomodoro_action_upon_initialization();
        void test_enable_short_break_action_upon_initialization();
        void test_enable_long_break_action_upon_initialization();
        void test_remaining_time_is_set_to_zero_upon_initialization();
        void test_tray_icon_is_set_to_idle_state_upon_initialization();

        // The tray icon
        void test_show_tray_icon();

        // Starting the pomodoro engine
        void test_start_a_pomodoro_when_the_user_executes_the_pomodoro_action();
        void test_start_a_short_break_when_the_user_executes_the_short_break_action();
        void test_start_a_long_break_when_the_user_executes_the_long_break_action();

        // Stopping the pomodoro engine
        void test_stop_the_pomodoro_engine_when_the_user_executes_the_stop_timer_action();

        // When the timer is set
        void test_remaining_time_is_set_to_interval_length_when_the_timer_is_set();
        void test_enable_the_stop_timer_action_when_the_timer_is_set();
        void test_change_tray_icon_according_to_the_interval_type_when_the_timer_is_set();

        // When a pomodoro starts
        void test_disable_the_pomodoro_action_when_a_pomodoro_starts();
        void test_enable_the_short_break_action_when_a_pomodoro_starts();
        void test_enable_the_long_break_action_when_a_pomodoro_starts();

        // When a short break starts
        void test_enable_the_pomodoro_action_when_a_short_break_starts();
        void test_disable_the_short_break_action_when_a_short_break_starts();
        void test_enable_the_long_break_action_when_a_short_break_starts();

        // When a long break starts
        void test_enable_the_pomodoro_action_when_a_long_break_starts();
        void test_enable_the_short_break_action_when_a_long_break_starts();
        void test_disable_the_long_break_action_when_a_long_break_starts();

        // When the timer stops
        void test_disable_the_stop_timer_action_when_the_timer_stops();
        void test_enable_the_pomodoro_action_when_the_timer_stops();
        void test_enable_the_short_break_action_when_the_timer_stops();
        void test_enable_the_long_break_action_when_the_timer_stops();
        void test_remaining_time_is_set_to_zero_when_the_timer_stops();
        void test_change_tray_icon_when_the_timer_stops();

        // As time elapses
        void test_remaining_time_is_updated_each_time_a_second_elapses();

      private:
        TrayController *_trayController;
        MockTray *_tray;
        MockTrayIconFiles *_trayIconFiles;
        MockPomodoroEngine *_pomodoroEngine;
        MockTimerSignalEmitter *_timerSignalEmitter;
    };
  }
}

#endif // TRAYCONTROLLERTEST_H
