Story: the player interacts with the glyph

As a game player
I want to move the shape
So that I can make space for the next glyph

Scenario: the player moves the glyph

Given the first glyph is displayed on the board
When the player presses the right button
Then the glyph moves right
When the player presses the left button
Then the glyph moves left
When the player presses the down button
Then the glyph moves down
and the heartbeat is interrupted

Scenario: the player rotates the glyph left

Given the first glyph is displayed on the board
When the player presses left rotate
Then the glyph turns to one quarter
When the player presses left rotate
Then the glyph turns to one half
When the player presses left rotate
Then the glyph turns to three quarters
When the player presses left rotate
Then the glyph turns to upright

Scenario: the player rotates the glyph right

Given the first glyph is displayed on the board
When the player presses right rotate
Then the glyph turns to three quarters
When the player presses right rotate
Then the glyph turns to one half
When the player presses right rotate
Then the glyph turns to one quarter
When the player presses right rotate
Then the glyph turns to upright


