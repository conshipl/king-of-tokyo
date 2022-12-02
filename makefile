AbstractDice.class: AbstractDice.java
	javac -g AbstractDice.java

Dice.class: Dice.java AbstractDice.class
	javac -g Dice.java

TwentyDice.class: TwentyDice.java AbstractDice.class
	javac -g TwentyDice.java

Monster.class: Monster.java TwentyDice.class
	javac -g Monster.java

Player.class: Player.java Monster.class
	javac -g Player.java

ScreenHelper.class: ScreenHelper.java
	javac -g ScreenHelper.java

Game.class: Game.java ScreenHelper.class Dice.class Player.class
	javac -g Game.java

MainMenu.class: MainMenu.java ScreenHelper.class Game.class Player.class
	javac -g MainMenu.java

clean:
	rm *.class

run: MainMenu.class
	java MainMenu

debug: MainMenu.class
	jdb MainMenu
