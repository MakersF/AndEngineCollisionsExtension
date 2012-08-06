# AndEngine Collision Extension

This is an extension that aims to bring different collision methods (perfect or approximations) to AndEngine GLES2 
## Supported Collision Methods:
 * Pixel-Perfect Collision (supports: translation, scale, rotation, screw)
 ** It also support pixel-perfect collision between pixel-perfect shapes and retangular shapes, without the need for the latter to be a pixel-perfect shape.

##Changes Needed to AndEngine
In order to make this work you'll need to aply some very small modifications to your copy of AndEngine sources.

Just take the code from my commits:
 * 1f3ab6ba14f824f0fe813ce23546e407ab25d31f (https://github.com/MakersF/AndEngine/commit/1f3ab6ba14f824f0fe813ce23546e407ab25d31f)
 * bc149defd18c6e18015408fd58bd55fb53f2a04d (https://github.com/MakersF/AndEngine/commit/bc149defd18c6e18015408fd58bd55fb53f2a04d)