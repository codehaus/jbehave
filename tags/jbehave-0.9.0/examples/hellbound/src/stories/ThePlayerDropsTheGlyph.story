Story: the player drops the glyph

As a game player
I want to drop the glyph
So that  I can save time

Scenario: the player drops the glyph into an empty pit

Given the first glyph is displayed on the board
When the player presses the drop key
Then the glyph should fall to the bottom
When time passes
Then the glyph segments should become junk
and the next glyph should appear

Scenario: the player drops the glyph onto junk

Given the player drops the glyph into an empty pit
When the player presses the drop key
Then the glyph should fall onto the junk
and the glyph segments should become junk
and the next glyph should appear
