#include "MockSoundNotifications.h"
#include "SoundClip.h"

using tmty::ui::SoundClip;
using tmty::ui::MockSoundNotifications;

MockSoundNotifications::MockSoundNotifications(SoundClip &timerSet, SoundClip &timeElapsing, SoundClip &timerExpired) :
  _timerSet(timerSet),
  _timeElapsing(timeElapsing),
  _timerExpired(timerExpired)
{
}

SoundClip &MockSoundNotifications::timerSet()
{
  return _timerSet;
}

SoundClip &MockSoundNotifications::timeElapsing()
{
  return _timeElapsing;
}

SoundClip &MockSoundNotifications::timerExpired()
{
  return _timerExpired;
}
