# Wordle

A Wordle clone built with Jetpack Compose.

### Requirements
- **Target**: Guess a randomly selected 6-letter word.
- **Attempts**: 5 chances.
- **Validations**: Exactly 5 letters, alphabets only.
- **Feedback**: 
  - Green: Correct letter, correct position.
  - Yellow: Correct letter, wrong position.
  - Gray/Black: Letter not in word.

### Out of Scope
- Words with non-unique letters (only considering unique 5-letter words for now).
- Animations.
- "Play Again" functionality.

### State Management
- `noOfAttempts`: Track usage.
- `currentTypedWord`: Current input buffer.
- `gameState`: WON, LOST, or PENDING.
- `pastGuesses`: History of attempts with color mappings.
- `errorMessage`: Validation feedback.

### Key Logic
- `isWordValid`: Checks input constraints.
- `doesWordMatch`: Comparison logic.
- `checkSubmittedWord`: Main game loop step.
- `updateGameStatus`: Transitions state based on results.
