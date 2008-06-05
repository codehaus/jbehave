Story: the player interacts with the glyph

As a game player
I want to move the shape
So that I can make space for the next glyph

Scenario: the player moves the glyph

Given the first glyph is displayed on the board
When the player presses the right key
Then the glyph should move right
When the player presses the left key
Then the glyph should be centred at the top of the pit
When the player presses the down key
Then the glyph should move downwards
and the heartbeat should be skipped

Scenario: the player rotates the glyph left

Given the first glyph is displayed on the board
When the player presses left rotate
Then the glyph should turn to one quarter
When the player presses left rotate
Then the glyph turns to two quarters
When the player presses left rotate
Then the glyph turns to three quarters
When the player presses left rotate
Then the glyph should be centred at the top of the pit

Scenario: the player rotates the glyph right

Given the first glyph is displayed on the board
When the player presses right rotate
Then the glyph turns to three quarters
When the player presses right rotate
Then the glyph turns to two quarters
When the player presses right rotate
Then the glyph should turn to one quarter
When the player presses right rotate
Then the glyph should be centred at the top of the pit
