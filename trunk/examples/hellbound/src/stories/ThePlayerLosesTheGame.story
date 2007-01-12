Story: the player loses the game

As a game player
I want to lose the game
So that the game is a challenge

Scenario: there is no space for the next glyph

Given the junk is almost to the top
When the player presses the drop key
and time passes
Then the glyph should become junk and the next glyph should appear overlapping it
..L....
..L....
..LL...
...XX..
...X...
...X...
..XX...
...XX..
..XX...
..XX...
...XX..
..XXX..
...X...

and the game should be over