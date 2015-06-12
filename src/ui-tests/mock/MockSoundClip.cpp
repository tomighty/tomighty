#include "MockSoundClip.h"

using tmty::ui::MockSoundClip;

MockSoundClip::MockSoundClip() :
  SoundClip(),
  _timesPlayed(0),
  _timesStopped(0),
  _isLooping(false)
{
}

void MockSoundClip::play()
{
  ++_timesPlayed;
}

void MockSoundClip::loop()
{
  _isLooping = true;
}

void MockSoundClip::stop()
{
  ++_timesStopped;
}

int MockSoundClip::timesPlayed()
{
  return _timesPlayed;
}

int MockSoundClip::timesStopped()
{
  return _timesStopped;
}

bool MockSoundClip::isLooping()
{
  return _isLooping;
}
