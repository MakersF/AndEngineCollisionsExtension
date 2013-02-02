# AndEngine Collision Extension

This is an extension that aims to bring different collision methods (perfect or approximations) to AndEngine GLES2 
## Supported Collision Methods:
 * Pixel-Perfect Collision (supports: translation, scale, rotation, screw)
 * It also support pixel-perfect collision between pixel-perfect shapes and retangular shapes, without the need for the latter to be a pixel-perfect shape.
 * Alpha values different from 0 (you set the threshold that identify if a pixel is solid or not)
 * You ca unse the utils methods to check the performances in your app or to output the collision mask to check if it is what you need

##Changes Needed to AndEngine
In order to make this work you'll need to aply some very small modifications to your copy of AndEngine sources.

Just take the code from my commits:
 * 3d397e5c17d061b96733802fc6cb92a43339bede (https://github.com/MakersF/AndEngine/commit/3d397e5c17d061b96733802fc6cb92a43339bede)
 * 6c16a3728fb0eb510f7864d8cf8cb11ea6c3be9d (https://github.com/MakersF/AndEngine/commit/6c16a3728fb0eb510f7864d8cf8cb11ea6c3be9d)