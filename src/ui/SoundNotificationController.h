#ifndef SOUNDNOTIFICATIONCONTROLLER_H
#define SOUNDNOTIFICATIONCONTROLLER_H

#include <QObject>

namespace tmty
{
  class Interval;
  class Preferences;
  class TimerSignalEmitter;

  namespace ui
  {
    class SoundNotifications;

    class SoundNotificationController : public QObject
    {
        Q_OBJECT

      public:
        explicit SoundNotificationController(SoundNotifications &soundNotifications, Preferences &preferences, QObject *parent = 0);

        void listenTo(TimerSignalEmitter &timerSignalEmitter);

      private slots:
        void whenTimerIsSet(const Interval &interval);
        void whenTimerStops(int timeLeft);

      private:
        bool shouldPlayTimeElapsingSoundNotification(const Interval &interval);

        SoundNotifications &_soundNotifications;
        Preferences &_preferences;
    };
  }
}

#endif // SOUNDNOTIFICATIONCONTROLLER_H
