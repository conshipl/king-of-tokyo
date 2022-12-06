# King Of Tokyo

My final project for Computing II course at IUPUI.

A recreation of the "King of Tokyo" boardgame written in Java.

## Main Menu Screen: ##

[![mainmenu](https://github.com/conshipl/king-of-tokyo/blob/master/mainmenu.PNG)](mainmenu.PNG)

## Average Player Turn Sequence: ##

[![playerturn](https://github.com/conshipl/king-of-tokyo/blob/master/playerturn.PNG)](playerturn.PNG)

## View All Saved Players' Stats: ##

[![allplayerstats](https://github.com/conshipl/king-of-tokyo/blob/master/allplayerstats.PNG)](allplayerstats.PNG)

## Search For A Specific Player's Stats: ##

[![searchplayerstats](https://github.com/conshipl/king-of-tokyo/blob/master/searchplayerstats.PNG)](searchplayerstats.PNG)


## Backstory
For my final project I’ve recreated the “King of Tokyo” boardgame in Java. In the game, 2-4 players choose a Kaiju-style monster (such as “Cyberkitty”, “Space Penguin”, or “MekaDragon”) to battle other monsters for control of Tokyo. There are two locations a monster can exist in: Tokyo or outside of Tokyo, every monster begins the game outside of Tokyo, and only one monster can occupy Tokyo at a time. Players receive 1 victory point when entering Tokyo, and 2 victory points when beginning their turn in Tokyo. If no player is occupying Tokyo at the beginning of a player’s turn, that player must move into Tokyo. Players start with 10 health and 0 victory points, and the goal is to smash all other players down to 0 health or be the first to acquire 20 victory points.

The first player rolls six dice with values of 1, 2, 3, Smash, Energy, or Heal three times, choosing which dice to keep in between each roll. If a player rolls 3 of a kind of any digit, they receive the face value of that digit as victory points plus an additional victory point for each additional die of that digit (i.e. rolling 4 threes would yield 3 victory points + 1 victory point = 4 total). Rolling a smash damages enemies in the opposite location of the player, so a player that rolls a smash in Tokyo would damage all players outside of Tokyo. If a player outside of Tokyo smashes the player in Tokyo, the player in Tokyo may yield the city, exiting it and forcing the player who attacked them to move into it. Rolling an energy grants the player +1 energy, which can be used to purchase power-up cards. Rolling a heal grants the player +1 health (up to a maximum of 10 health) and can only be used outside of Tokyo.

And that’s basically the game: it’s a series of dice rolls and resolution of the rolls. Each player must login or create an account at the beginning of each game, and player accounts are awarded wins, draws, and losses at the end of each game, then serialized into an external file. All player stats or a specific player’s stats can be viewed from the main menu.

## Project Status
Working.

The project currently runs as expected, but only as a Dice-Roller (Card System has not been implemented).

See Known Issues for more information.

## Known Issues
Known issues include the card system, smash damage application, user input checks, and no account already exists check when creating a new account. The card system was going to add a layer of complexity that I wasn’t comfortable taking on in the amount of time given for this project, so I decided to nix it, which makes Energy effectively worthless. The game is already decently complex as is, and the cards would add new features like Poison and Fire DoT counters, increased max health or number of attacks, armor, damage reflection, cards to force the current King to yield, etc. (events that would trigger a series of cascading effects, essentially). I’d like to continue working on the project over winter break, and think I’ll try to implement a Card class and use operator overloading to “add” or “subtract” the cards to specific Monsters.

When a player outside of Tokyo attacks the King of Tokyo and the King yields, the player outside moves into Tokyo and then is allowed to continue applying Smash damage to the remaining players outside of Tokyo. In reality, the Smash damage should all be resolved at once, so only the King of Tokyo should take damage in that situation, then the player outside would move into Tokyo. Currently, the method performing this action is iterating through a list of all opponent players, comparing their location to the player’s current location, then applying smash damage as needed. This issue could be solved by assigning the value of the player’s location to a variable at the beginning of the function, then comparing the opponent’s location to this variable instead of the player’s current location.

User input is not always validated, particularly and most glaringly in the case of a player entering which dice they’d like to keep as a CSV. This is purely just due to my own laziness in not wanting to write a bunch of while loops or try-catch statements, but in the case of the CSV validation, I’ve brainstormed the following function that could be implemented in a while loop to catch bad input:

```
public boolean isValidCSV(String[] s){
    try:
        for (int i = 0; i < s.length; i++){
            cast s[i] as int;
            if (int s[i] is not between 0-7){
                return false;
            } // end if
        } // end for
        return true;
    catch (cast type error):
        return false;
} // end isValidCSV
```

The function would accept an array of Strings as input (so ideally would look like [“1”, “3”, “5”]), then iterate through the array, try to cast each String as an int, and check its value to make sure it’s between 0 and 7. If all elements pass, the function would return true; if an element causes a cast type error or is not between acceptable values, the function would return false.

The final major known issue is that when creating a new account, there is no check to make sure the account name isn’t already taken. I’m not exactly sure what issue this would cause (I think it might actually be okay if the passwords are different), but my guess is that it would add an identical account to the end of the ArrayList storing all accounts, and if the two players ever tried to login and play at the same time again, they would both access and deserialize the same account (whichever one was created first and therefore comes first in the ArrayList), which may cause some sort of merge issue when the ArrayList is serialized again or would just keep adding new instances of the same account in the ArrayList every time this happened. This could be solved by creating a boolean findPlayer or checkAccountName function implemented in a while loop that would force the user to create a unique username.

## Technologies
Java, Serialization/File IO, makefiles and .jar files

## ***See Project_Proposal.docx For Additional Documentation***
