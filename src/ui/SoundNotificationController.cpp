#include "Interval.h"
#include "IntervalType.h"
#include "Preferences.h"
#include "SoundClip.h"
#include "SoundNotifications.h"
#include "SoundNotificationController.h"
#include "TimerSignalEmitter.h"

using tmty::Interval;
using tmty::IntervalType;
using tmty::Preferences;
using tmty::TimerSignalEmitter;
using tmty::ui::SoundClip;
using tmty::ui::SoundNotifications;
using tmty::ui::SoundNotificationController;

SoundNotificationController::SoundNotificationController(SoundNotifications &soundNotifications, Preferences &preferences, QObject *parent) :
  QObject(parent),
  _soundNotifications(soundNotifications),
  _preferences(preferences)
{
}

void SoundNotificationController::listenTo(TimerSignalEmitter &timerSignalEmitter)
{
  connect(&timerSignalEmitter, SIGNAL(timerSet(const Interval &)), this, SLOT(whenTimerIsSet(const Interval &)));
  connect(&timerSignalEmitter, SIGNAL(timerStopped(int, Interval)), this, SLOT(whenTimerStops(int)));
}

void SoundNotificationController::whenTimerIsSet(const Interval &interval)
{
  if(_preferences.shouldPlaySoundNotificationWhenTimerIsSet())
  {
    _soundNotifications.timerSet().play();
  }
  if(shouldPlayTimeElapsingSoundNotification(interval))
  {
    _soundNotifications.timeElapsing().loop();
  }
}

void SoundNotificationController::whenTimerStops(int timeLeft)
{
  _soundNotifications.timeElapsing().stop();

  bool timerHasExpired = timeLeft == 0;
  if(timerHasExpired && _preferences.shouldPlaySoundNotificationWhenTimerExpires())
  {
    _soundNotifications.timerExpired().play();
  }
}

bool SoundNotificationController::shouldPlayTimeElapsingSoundNotification(const Interval &interval)
{
  bool isPomodoro = interval.type() == IntervalType::POMODORO;
  bool isBreak = interval.type() == IntervalType::SHORT_BREAK || interval.type() == IntervalType::LONG_BREAK;

  return
      (isPomodoro && _preferences.shouldPlaySoundNotificationDuringPomodoros())
      ||
      (isBreak && _preferences.shouldPlaySoundNotificationDuringBreaks());
}
