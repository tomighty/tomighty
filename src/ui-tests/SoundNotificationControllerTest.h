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

#ifndef SOUNDNOTIFICATIONCONTROLLERTEST_H
#define SOUNDNOTIFICATIONCONTROLLERTEST_H

#include <QObject>

namespace tmty
{
  class MockSoundClip;
  class MockSoundNotifications;
  class Preferences;
  class TimerSignalEmitter;
  class SoundNotificationController;

  namespace ui
  {
    class SoundNotificationControllerTest : public QObject
    {
        Q_OBJECT

      private Q_SLOTS:
        void init();

        void test_play_sound_notification_when_the_timer_is_set();
        void test_play_sound_notification_when_the_timer_expires();
        void test_repeatedly_play_the_time_elapsing_sound_during_a_pomodoro();
        void test_repeatedly_play_the_time_elapsing_sound_during_a_short_break();
        void test_repeatedly_play_the_time_elapsing_sound_during_a_long_break();
        void test_stop_the_time_elapsing_sound_when_the_timer_stops();
        void test_do_not_play_any_sound_notification_when_the_timer_is_interrupted();

        void test_disable_the_timer_set_sound_notification();
        void test_disable_the_timer_expired_sound_notification();
        void test_disable_the_time_elapsing_sound_notification_during_pomodoros();
        void test_disable_the_time_elapsing_sound_notification_during_short_breaks();
        void test_disable_the_time_elapsing_sound_notification_during_long_breaks();


      private:
        SoundNotificationController *_soundNotificationController;
        Preferences *_preferences;
        MockSoundNotifications *_soundNotifications;
        MockSoundClip *_timerSetSoundClip;
        MockSoundClip *_timeElapsingSoundClip;
        MockSoundClip *_timerExpiredSoundClip;
        TimerSignalEmitter *_timerSignalEmitter;
    };
  }
}
#endif // SOUNDNOTIFICATIONCONTROLLERTEST_H
