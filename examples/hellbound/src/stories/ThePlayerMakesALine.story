Story: the player makes a line

As a game player
I want lines to disappear when I complete them
So that I can progress the game

Scenario: the player drops the glyph to make a line

Given that the junk has a hole
and a glyph of the right shape is above the hole
When the player presses the drop button
and time passes
Then the glyph becomes junk
and the line disappears
and the next glyph appears