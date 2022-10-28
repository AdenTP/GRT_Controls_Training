# GRT_Controls_Training

**Notes on the funny**

Some notes won't be reflected in commit history because I couldn't figure out how to commit to git and just wrote the thing.

**Vars**
  List<AnalogPotentionmeter> sensors: Originally just 3 AnalogPotentiometers named sensor0, sensor1, sensor2. I was originally planning to do some arithmetic to access a     different sensor based on heldBalls, but realized that wasn't necessary. Still, I like this method of access better.

  boolean conveyorRun/flywheelRun: For motor control, could be unnecessary.
  
  boolean storing/shooting: I was afraid overlapping shooting/storing operations would cause issues, so these are checked in the start conditions to make sure the robot    doesn't start another operation while busy.
  
