Story: the player loses the game

As a game player
I want to lose the game
So that the game is a challenge

Scenario: no space for the next glyph

Given that the junk is almost to the top
When the glyph appears
and the player drops it
and time passes
Then the glyph should add to the junk
and the next glyph should appear overlapping the junk
and the game should be over
