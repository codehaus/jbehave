Story: the glyph is constrained by the pit

As a game player
I want the glyph to be constrained by the pit
So that the game has boundaries

Scenario: the glyph will not move right

Given the glyph is against the right wall
When the player presses the right key
Then the glyph will not move

Scenario: the glyph will not move left

Given the glyph is against the left wall
When the player presses the left key
Then the glyph will not move

Scenario: the glyph will not move down

Given the glyph is against the floor
When the player presses the down key
Then the glyph becomes junk
and the next glyph appears
