#include <QString>

#include "StandardSoundNotifications.h"
#include "ImmutableSoundClip.h"

using tmty::ui::ImmutableSoundClip;
using tmty::ui::SoundClip;
using tmty::ui::StandardSoundNotifications;

StandardSoundNotifications::StandardSoundNotifications() :
  _timerSet(new ImmutableSoundClip(QString(":/sound/timer_set.wav"))),
  _timeElapsing(new ImmutableSoundClip(QString(":/sound/clock_ticking.wav"))),
  _timerExpired(new ImmutableSoundClip(QString(":/sound/timer_expired.wav")))
{
}

SoundClip &StandardSoundNotifications::timerSet()
{
  return *_timerSet;
}

SoundClip &StandardSoundNotifications::timeElapsing()
{
  return *_timeElapsing;
}

SoundClip &StandardSoundNotifications::timerExpired()
{
  return *_timerExpired;
}
