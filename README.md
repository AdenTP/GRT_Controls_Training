# GRT_Controls_Training

**Notes on the funny**

Some notes won't be reflected in commit history because I couldn't figure out how to commit to git and just wrote the thing.
UPDATE: ok I also didn't commit a couple intermediate versions because all the changes were in remote and never pushed or something, idk
if it works it works, I'll learn Git stuff soonish
my formatting is also a little lacking, but at least I figured out how to bold text. (embolden?)

**Vars**

  List<AnalogPotentionmeter> sensors: Originally just 3 AnalogPotentiometers named sensor0, sensor1, sensor2. I was originally planning to do some arithmetic to access a     different sensor based on heldBalls, but realized that wasn't necessary since the scale is so small. Still, I like this method of access better because it would be more scalable to the 5-story tall 2053 robot.

  boolean conveyorRun/flywheelRun: Not sure if these strictly necessary but I feel like they make it more readable.
  
  boolean storing/shooting: I was afraid overlapping shooting/storing operations would cause issues, so these are checked in the start conditions to make sure the robot    doesn't start another operation while busy.
  
  boolean shoot_all: Just a toggle for shooting individual or all. May be more semantically correct to have shootingIndividual and shootingAll be separate states, but that's a lot of work.
  
**Start State Checks**
  
  **Shooting**
if(getAButtonPressed)/if(getBButtonPressed): OK these look redundant, but having them be in one if statement doesn't really save space because I still need to check whether either A or B was pressed to start the shooting process, then check which one it was to see which way to toggle shoot_all. The ternary should make it so that get(A/B)ButtonPressed isn't checked when busy storing, so that pressing the button during a storing operation will queue the shot for when storing concludes. 
  
  **Storing**
Not much to say. The reading for empty space was 0.35 and the reading for a ball was 0.41 and this is in the middle. Magic number, cry about it.
  
**End State Checks**

**Storing**
Yes, I switched the order of these operations. Yes, it's easily fixable with a cut and paste. No, I won't. My storage solution isn't very efficient. The space between sensors 1 and 2 is pretty big, and if a ball has reached sensor 2 it's too high. I could just have it run for a fixed time instead of needing a sensor to be tripped, but that would need to be changed if conveyorMotor's speed was changed, and I failed division. Another alternative would be, in the case where heldBalls > 1, for it to start spinning, mark when a ball leaves sensor 1, and stop when a second ball reaches it. I'd probably do this if I had more time to test the sensors, since the magic number would need to be a little more precise. I'll have code for this in a comment somewhere probably.

update: jk it's right here now too
```
  if (storing && heldBalls >= 1 && sensors.get(1).get() < 0.3) {
    pass = true;
  }

  //if reading dips down, there's empty space, so it should stop the next time it sees it go back up
    
  if (storing && pass && sensors.get(1).get() > 0.3) {
    storing = false;
    conveyorRun = false;
    heldBalls++;
  }

  if heldBalls == 0 it should use old process
 ```
  
**Shooting**
Two stages of shooting: moving the conveyor to get a ball in position to shoot, then launching it with the flywheel. Especially with a lower conveyor speed, I didn't want to needlessly run the flywheel while the ball was still moving up into position. This only runs the flywheel when necessary, using an arbitary time value that could definitely be tuned with more time.

**Run Motors**
  
**Bottom Text**
  
**Obstacles Faced**

had multiple skill issues while testing code. debug speedrun any% route could definitely be optimized. the robot broke a little too.
  
**Ideas for Improvement**

Storage was inefficient. Setting motors to max speed would also improve the device's capabilities as a siege engine. I'm also not entirely sure if this code runs properly, specifically shoot_all since I didn't have time to test it. If the code doesn't work, that could probably be improved.

**Conclusion**

thumbsup emoji
