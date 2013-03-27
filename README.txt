Please create your own package within src, with your name.

Then create a new class for your AI. You may make helper classes.
Then create a new class for your factory. At a simple level, it could just return a new instance of your AI.

Your Bot should inherit Root.UserBot.
your Factory should inherit Root.Factory.

No use of threading, static fields or IO are allowed. Ever.

Please don't make your AI take too long.

You can use any of the Game classes to test with. They are JFrames, and they show themselves. You should not use Game, use TestGame or make you own. Look inside TestGame to see how its done.

Notes on geometry:
Most objects in the game are described as circles. The terrain straight edges polygons (you will only be given the parts of the edges you can see).
Data passed in to the update method has been offset by your own location. Thus your own location is always zero, and all positions are relative to you.
However your angle (and the angle of everything else) is absolute. Also the 'x' and 'y' axis do NOT rotate with you.

I apologise in advance for bad documentation, magic numbers, global statics (I'm slowly taking them out) and plain shoddy code where I got lazy.