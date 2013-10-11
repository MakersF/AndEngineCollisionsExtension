# AndEngine Collision Extension

This is an extension that aims to bring different collision methods (perfect or approximations) to AndEngine GLES2 
## Supported Collision Methods:
 * Pixel-Perfect Collision (supports: translation, scale, rotation, screw)
 * It also support pixel-perfect collision between pixel-perfect shapes and rectangular shapes, without the need for the latter to be a pixel-perfect shape.
 * Alpha values different from 0 (you set the threshold that identify if a pixel is solid or not)
 * You can use the utility methods to check the performances in your application or to output the collision mask to check if it is what you need

##Changes Needed to AndEngine
Since the commit 624bd750d151fc601d79bd2987576e8d0c006d7c you do not need to modify AndEngine any more

##Dependencies
Since the core code for the collision checking was moved to another pure Java project in order to make it easier to develop and test, you need to add to your build path the src directory of the [CollisionCore project](https://github.com/MakersF/CollisionCore CollisionCore)